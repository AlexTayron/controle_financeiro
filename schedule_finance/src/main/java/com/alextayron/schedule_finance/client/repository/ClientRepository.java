package com.alextayron.schedule_finance.client.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alextayron.schedule_finance.client.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {

}
