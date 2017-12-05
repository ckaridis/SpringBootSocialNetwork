package com.christos.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// The first thing we pass as argument is the type of the object, and second the type of the primary key
@Repository
public interface VerificationDao extends CrudRepository<VerificationToken, Long>{

    VerificationToken findByToken(String token);

}
