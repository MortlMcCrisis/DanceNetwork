package com.mortl.dancenetwork.repository;

import com.mortl.dancenetwork.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
