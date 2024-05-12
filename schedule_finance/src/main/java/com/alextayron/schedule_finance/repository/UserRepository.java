package com.alextayron.schedule_finance.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alextayron.schedule_finance.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

}
