package com.vape.repository;

import com.vape.entity.Account;
import com.vape.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM user WHERE email=:email", nativeQuery = true)
    public Account getUserByEmail(@Param("email") String email);

    @Query(value = "DELETE * FROM user WHERE email=:email", nativeQuery = true)
    public Account deleteUserByEmail(@Param("email") String email);
}
