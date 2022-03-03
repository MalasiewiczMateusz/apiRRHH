package com.formacionspring.app.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.formacionspring.app.entity.Departamento;

@Repository
public interface DepartamentoDAO extends CrudRepository<Departamento,Long> {

}
