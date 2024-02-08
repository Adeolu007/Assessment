package com.assessment.assessment.repository;


import com.assessment.assessment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Boolean existsByEmail(String email);
    Boolean existsByAccountNumber(String accountNumber);

   User findByAccountNumber(String accountNumber);
}
