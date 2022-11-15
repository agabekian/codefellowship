package com.armen.lab16.repositories;

import com.armen.lab16.models.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteUserRepo extends JpaRepository<SiteUser, Long> {
    public SiteUser findByUsername(String UserName);
}
