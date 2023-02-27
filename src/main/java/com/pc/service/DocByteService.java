package com.pc.service;

import com.pc.entities.DocByte;
import com.pc.framework.AbstractService;
import com.pc.repositories.DocByteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DocByteService extends AbstractService {
    @Autowired
    DocByteRepository repository;

    public void saveDocByte(DocByte docByte) throws Exception {
        if (docByte.getId() == null) {
            docByte.setCreateDate(new Date());
        } else {
            if (getCurrentUser() != null) {
                docByte.setLastUpdateUser(getCurrentUser());
            }
            docByte.setLastUpdateDate(new Date());
        }
        repository.save(docByte);
    }

    public void deleteDocByte(DocByte docByte) throws Exception {
        repository.delete(docByte);
    }

    public void deleteDocByteByID(Integer arg0) throws Exception {
        repository.deleteById(arg0);
    }

    public List<DocByte> findAllDocByte() throws Exception {
        return repository.findAll();
    }

    public Page<DocByte> findAllDocByte(Pageable p) throws Exception {
        return repository.findAll(p);
    }

    public List<DocByte> findAllDocByte(Sort s) throws Exception {
        return repository.findAll(s);
    }

    public DocByte findById(Long parseLong) throws Exception {
        return repository.findById(parseLong);
    }
	
	/*public List<DocByte> findByDescriptionStartingWith(String description)  throws Exception
	{
		return repository.findByDescriptionStartingWith(description);
	}*/


}
