package com.vape.service;

import com.vape.entity.Account;
import com.vape.entity.User;
import com.vape.repository.UserRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService  {
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findAll(Sort sort) {
        return userRepository.findAll(sort);
    }

    public <S extends User> List<S> saveAll(Iterable<S> entities) {
        return userRepository.saveAll(entities);
    }

    public User getById(Long aLong) {
        return userRepository.getById(aLong);
    }

    public <S extends User> S save(S entity) {
        return userRepository.save(entity);
    }

    public Optional<User> findById(Long aLong) {
        return userRepository.findById(aLong);
    }

    public long count() {
        return userRepository.count();
    }

    public void deleteById(Long aLong) {
        userRepository.deleteById(aLong);
    }

    public void delete(User entity) {
        userRepository.delete(entity);
    }

    @Query(value = "SELECT * FROM user WHERE email=:email", nativeQuery = true)
    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Query(value = "DELETE * FROM user WHERE ", nativeQuery = true)
    public User deleteUserByEmail(String email) {
        return userRepository.deleteUserByEmail(email);
    }

    @Query(value = "DELETE * FROM user WHERE email=:email", nativeQuery = true)
    public User getAllUsers(Integer PageNo, Integer PageSize, String sortField, String sortOrder) {
        return userRepository.getAllUsers(PageNo, PageSize, sortField, sortOrder);
    }
}
