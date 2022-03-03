package com.formacionspring.app.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.formacionspring.app.entity.Empleado;;

@Repository
public interface EmpleadoDAO extends CrudRepository<Empleado, Long>{

}
