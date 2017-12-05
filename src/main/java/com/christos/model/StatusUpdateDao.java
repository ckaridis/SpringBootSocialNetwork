package com.christos.model;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

// DAO stands for Data Access Object
// Similar to Repository
// CRUD stands for Create Read Update Delete
// In the bruckets <> we specify the type of data we want it to store and then the primary key.
// The primary key can not be a primitive type (That's why we got long instead of long).
// This is a part of Spring Data JPA. We don't need any configuration, Spring does all automatically.
@Repository
public interface StatusUpdateDao extends PagingAndSortingRepository<StatusUpdate, Long> {

    // This is a method implemented by Spring. The return type is StatusUpdate
    // findFirstByOrder is the first part. Created a method that will find only the first object if
    // it's shorted in a particular order.
    // By "Added" is the field that will be shorted
    // Desc is in descending order.
    StatusUpdate findFirstByOrderByAddedDesc();
}
