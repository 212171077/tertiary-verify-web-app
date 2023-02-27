package com.pc.service.lookup;

import com.pc.entities.lookup.Title;
import com.pc.framework.AbstractService;
import com.pc.repositories.lookup.TitleRepository;
import com.pc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TitleService extends AbstractService {
    @Autowired
    private TitleRepository repository;
    @Autowired
    private UserService userService;

    public void saveTitle(Title title) throws Exception {

        if (title.getId() == null) {
            checkIfExist(title.getDescription());
            title.setDeleted(false);
            title.setCreateDate(new Date());
        } else {
            if (getCurrentUser() != null) {
                title.setLastUpdateUser(getCurrentUser());
            }
            title.setLastUpdateDate(new Date());
        }
        repository.save(title);
    }

    public void deleteTitle(Title title) throws Exception {
        checkIfInUse(title);
        title.setDeleted(true);
        repository.save(title);
    }

    public List<Title> findAllTitle() throws Exception {
        return repository.findByDeleted(false);
    }

    public Object findById(Long parseLong) throws Exception {
        return repository.findByIdAndDeleted(parseLong,false);
    }

    private void checkIfExist(String desc) throws Exception {
        List<Title> list = repository.findByDescriptionAndDeleted(desc,false);
        if (list != null && list.size() > 0) {
            throw new Exception("Title already exist");
        }
    }

    private void checkIfInUse(Title title) throws Exception {
        long count = userService.countByTitle(title);
        if (count > 0) {
            throw new Exception("This title cannot be deleted because it's being used");
        }
    }


}
