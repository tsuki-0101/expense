package com.shivam.expenseSplitter.dao.repositories;

import com.shivam.expenseSplitter.dao.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.email = ?1")
    User findUserByEmail(String email);

    Page<User> findAllByOrderByNameAsc(Pageable pageable);
}
