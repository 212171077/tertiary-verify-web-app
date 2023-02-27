package com.pc.dao;

import com.pc.entities.enums.ApprovalStatusEnum;
import org.primefaces.model.SortOrder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

@Service
public class InstitutionDAO {
    @PersistenceContext
    private EntityManager em;

    private Predicate getFilterCondition(CriteriaBuilder cb, Root<?> entityObject, Map<String, Object> filters) {
        Predicate filterCondition = cb.conjunction();
        String wildCard = "%";
        for (Map.Entry<String, Object> filter : filters.entrySet()) {
            String value = wildCard + filter.getValue() + wildCard;
            if (!filter.getValue().equals("")) {
                javax.persistence.criteria.Path<String> path = entityObject.get(filter.getKey());

                if (path.getJavaType() != null) {
                    if (path.getJavaType().getName().contains("com.pc"))//Searching by joined Entity
                    {
                        filterCondition = cb.and(filterCondition, cb.equal(path, filter.getValue()));
                    } else if ("true".equalsIgnoreCase(String.valueOf(filter.getValue())) ||
                            "false".equalsIgnoreCase(String.valueOf(filter.getValue()))) {
                        filterCondition = cb.and(filterCondition, cb.equal(path, Boolean.valueOf(String.valueOf(filter.getValue()))));
                    } else {
                        filterCondition = cb.and(filterCondition, cb.like(path, value));
                    }
                } else {
                    filterCondition = cb.and(filterCondition, cb.like(path, value));
                }
            }
        }
        /*Making sure to select objects that are not deleted*/
        javax.persistence.criteria.Path<String> path = entityObject.get("deleted");
        filterCondition = cb.and(filterCondition, cb.notEqual(path, true));

        return filterCondition;
    }

    public int count(Map<String, Object> filters, Class entityClass) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<?> entityObject = cq.from(entityClass);
        cq.where(getFilterCondition(cb, entityObject, filters));
        cq.select(cb.count(entityObject));
        return em.createQuery(cq).getSingleResult().intValue();
    }

    public List<?> getResultList(Class entityClass, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<?> cq = cb.createQuery(entityClass);
        Root<?> entityObject = cq.from(entityClass);
        cq.where(getFilterCondition(cb, entityObject, filters));
        if (sortField != null) {
            if (sortOrder == SortOrder.ASCENDING) {
                cq.orderBy(cb.asc(entityObject.get(sortField)));
            } else if (sortOrder == SortOrder.DESCENDING) {
                cq.orderBy(cb.desc(entityObject.get(sortField)));
            }
        }
        return em.createQuery(cq).setFirstResult(first).setMaxResults(pageSize).getResultList();
    }

    /**
     * Active Institutions
     */

    public int countActiveInstitution(Map<String, Object> filters, Class entityClass) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<?> entityObject = cq.from(entityClass);
        cq.where(getActiveInstitutionFilterCondition(cb, entityObject, filters));
        cq.select(cb.count(entityObject));
        return em.createQuery(cq).getSingleResult().intValue();
    }

    public List<?> getActiveInstitutionsResultList(Class entityClass, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<?> cq = cb.createQuery(entityClass);
        Root<?> entityObject = cq.from(entityClass);
        cq.where(getActiveInstitutionFilterCondition(cb, entityObject, filters));
        if (sortField != null) {
            if (sortOrder == SortOrder.ASCENDING) {
                cq.orderBy(cb.asc(entityObject.get(sortField)));
            } else if (sortOrder == SortOrder.DESCENDING) {
                cq.orderBy(cb.desc(entityObject.get(sortField)));
            }
        }
        return em.createQuery(cq).setFirstResult(first).setMaxResults(pageSize).getResultList();
    }

    private Predicate getActiveInstitutionFilterCondition(CriteriaBuilder cb, Root<?> entityObject, Map<String, Object> filters) {
        Predicate filterCondition = cb.conjunction();
        String wildCard = "%";
        for (Map.Entry<String, Object> filter : filters.entrySet()) {
            String value = wildCard + filter.getValue() + wildCard;
            if (!filter.getValue().equals("")) {
                javax.persistence.criteria.Path<String> path = entityObject.get(filter.getKey());

                if (path.getJavaType() != null) {
                    if (path.getJavaType().getName().contains("com.pc"))//Searching by joined Entity
                    {
                        filterCondition = cb.and(filterCondition, cb.equal(path, filter.getValue()));
                    } else {
                        filterCondition = cb.and(filterCondition, cb.like(path, value));
                        if (filter.getKey().contains("institutionName") || filter.getKey().equalsIgnoreCase("institutionName")) {
                            path = entityObject.get("knownAs");
                            filterCondition = cb.or(filterCondition, cb.like(path, value));
                        }
                    }
                } else {
                    filterCondition = cb.and(filterCondition, cb.like(path, value));
                }
            }
        }
        /*Making sure to select institution that did not expired*/
        javax.persistence.criteria.Path<String> statusPath = entityObject.get("status");
        filterCondition = cb.and(filterCondition, cb.notEqual(statusPath, ApprovalStatusEnum.Expired));

        /*Making sure to select objects that are not deleted*/
        javax.persistence.criteria.Path<String> deletedPath = entityObject.get("deleted");
        filterCondition = cb.and(filterCondition, cb.notEqual(deletedPath, true));

        return filterCondition;
    }
}