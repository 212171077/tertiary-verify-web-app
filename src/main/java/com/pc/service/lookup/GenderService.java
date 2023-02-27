package com.pc.service.lookup;

import com.pc.entities.lookup.Gender;
import com.pc.framework.AbstractService;
import com.pc.repositories.lookup.GenderRepository;
import com.pc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GenderService extends AbstractService {
    @Autowired
    private GenderRepository repository;

    @Autowired
    private UserService userService;

    public void saveGender(Gender gender) throws Exception {

        if (gender.getId() == null) {
            checkIfExist(gender.getGenderName());
            gender.setDeleted(false);
            gender.setCreateDate(new Date());
        } else {
            if (getCurrentUser() != null) {
                gender.setLastUpdateUser(getCurrentUser());
            }
            gender.setLastUpdateDate(new Date());
        }

        repository.saveAndFlush(gender);

    }

    public Gender findByGenderName(String name) throws Exception {
        return repository.findByGenderNameAndDeleted(name, false);
    }

    public void deleteGender(Gender gender) throws Exception {
        checkIfInUse(gender);
        gender.setDeleted(true);
        repository.save(gender);
    }

    public List<Gender> findAllGender() throws Exception {
        return repository.findByDeleted(false);
    }

    public Gender findById(Long parseLong) throws Exception {
        return repository.findByIdAndDeleted(parseLong, false);
    }

    private void checkIfExist(String desc) throws Exception {
        Gender gender = repository.findByGenderNameAndDeleted(desc, false);
        if (gender != null) {
            throw new Exception("Gender already exist");
        }
    }

    private void checkIfInUse(Gender gender) throws Exception {
        long count = userService.countByGender(gender);
        if (count > 0) {
            throw new Exception("This gender cannot be deleted because it's being used");
        }
    }

}
