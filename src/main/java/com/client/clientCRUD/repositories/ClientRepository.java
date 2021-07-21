package com.client.clientCRUD.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.client.clientCRUD.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{

}
