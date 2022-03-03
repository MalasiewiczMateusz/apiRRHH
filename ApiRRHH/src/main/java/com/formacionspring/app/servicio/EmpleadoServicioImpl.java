package com.formacionspring.app.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.formacionspring.app.dao.EmpleadoDAO;
import com.formacionspring.app.entity.Empleado;


@Service
public class EmpleadoServicioImpl implements EmpleadoServicio{
	
	@Autowired
	private EmpleadoDAO repository;

	@Override
	public List<Empleado> findAllEmpleados() {
		
		return (List<Empleado>) repository.findAll();
	}

	@Override
	public Empleado findById(Long id) {
		
		return repository.findById(id).orElse(null);
	}

	@Override
	public Empleado saveEmpleado(Empleado empleado) {
		
		return repository.save(empleado);
	}

	@Override
	public void deleteEmpleado(Long id) {
		repository.deleteById(id);
		
	}

}
