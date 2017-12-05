package com.christos.service;

import com.christos.model.Profile;
import com.christos.model.ProfileDao;
import com.christos.model.SiteUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    ProfileDao profileDao;

    public void save(Profile profile) {
        profileDao.save(profile);
    }

    public Profile getUserProfile(SiteUser user) {
        return profileDao.findByUser(user);
    }
}
