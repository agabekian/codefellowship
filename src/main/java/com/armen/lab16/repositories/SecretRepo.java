package com.armen.lab16.repositories;

import com.armen.lab16.models.Secret;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecretRepo extends JpaRepository<Secret, Long> {
  public Secret findByName(String name);
}
