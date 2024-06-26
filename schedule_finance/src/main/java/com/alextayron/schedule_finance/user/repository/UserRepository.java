package com.alextayron.schedule_finance.user.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alextayron.schedule_finance.user.entity.User;



@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

}
