package com.formacionspring.app.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.formacionspring.app.entity.Jefe;

@Repository
public interface JefeDAO extends CrudRepository<Jefe, Long>{

}
