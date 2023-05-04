package com.vape.repository;

import com.vape.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query(value = "SELECT * FROM account WHERE email=:email", nativeQuery = true)
    public Account getAccountByEmail(@Param("email") String email);


    @Query(value = "SELECT * FROM account INNER JOIN user on account.email=user.email WHERE account.email=:email and user.role=0", nativeQuery = true)
    public Account authenAdmin(@Param("email") String email);

    @Query(value = "DELETE * FROM account WHERE email=:email", nativeQuery = true)
    public Account deleteAccountByEmail(@Param("email") String email);
}

