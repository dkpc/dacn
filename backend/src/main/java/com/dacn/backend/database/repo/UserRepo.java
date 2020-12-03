package com.dacn.backend.database.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dacn.backend.database.entity.User;

public interface UserRepo extends JpaRepository<User, Integer>, UserRepoCustom{

}
