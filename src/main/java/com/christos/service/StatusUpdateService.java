package com.christos.service;

import com.christos.model.StatusUpdate;
import com.christos.model.StatusUpdateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

// A service layer wraps the DAO. If we need some kind of logic before using the DAO we put it in the
// service layer. We can create for example short methods of some predefined methods of Spring.
// DAO is an interface and it can't have logic. Logic goes here.
@Service
public class StatusUpdateService {

    // We set here a constant with the number of posts we want for each page
    private final static int PAGESIZE = 5;

    // Spring automatically imports an object of the correct type
    @Autowired
    private StatusUpdateDao statusUpdateDao;

    public void save(StatusUpdate statusUpdate) {
        statusUpdateDao.save(statusUpdate);
    }

    public StatusUpdate getlatest() {
        return statusUpdateDao.findFirstByOrderByAddedDesc();
    }

    // Pages are collections. Here we create pages containing many StatusUpdate objects
    // We need the int pageNumber to "ask" for the appropriate page to be displayed.
    public Page<StatusUpdate> getPage(int pageNumber) {

        // Paging is zero-based. If we want to make it work from base 1
        PageRequest request = new PageRequest(pageNumber-1,PAGESIZE,
                //we short in DESC order, based on the field "added"
                Sort.Direction.DESC, "added");

        // We pass the page request and it will give us the requested page
        return statusUpdateDao.findAll(request);
    }

    // This is the method used for the Delete button
    public void delete(Long id) {
        statusUpdateDao.delete(id);
    }

    // This is the method to get the StatusUpdate object based on the ID we provide.
    // It calls the "findOne" method of the DAO, passes the ID and returns the object.
    public StatusUpdate get(Long id) {
        return statusUpdateDao.findOne(id);
    }
}
