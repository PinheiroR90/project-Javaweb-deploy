package com.example.projetojavaweb.repositories;

import com.example.projetojavaweb.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query(value = "SELECT u FROM User u WHERE UPPER(trim(u.name)) like %?1%")
     List<User> getForName(String name);
}
