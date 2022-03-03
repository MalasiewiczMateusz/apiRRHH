package com.formacionspring.app.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.formacionspring.app.dao.DepartamentoDAO;
import com.formacionspring.app.entity.Departamento;

@Service
public class DepartamentoServicioImpl implements DepartamentoServicio{
	
	@Autowired
	private DepartamentoDAO repository;

	@Override
	public List<Departamento> findAllDepartamentos() {
	
		return (List<Departamento>) repository.findAll();
	}

	@Override
	public Departamento finById(Long id) {
		
		return repository.findById(id).orElse(null);
	}

	@Override
	public Departamento saveDepartamento(Departamento departamento) {
	
		return repository.save(departamento);
	}

	@Override
	public void deleteDepartamento(Long id) {
		repository.deleteById(id);
		
	}

}
