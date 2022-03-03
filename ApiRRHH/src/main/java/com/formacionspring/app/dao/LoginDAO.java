package com.formacionspring.app.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.formacionspring.app.entity.Login;

@Repository
public interface LoginDAO extends CrudRepository<Login, Long>{

}
