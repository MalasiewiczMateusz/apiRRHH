package com.formacionspring.app.servicio;

import java.util.List;

import com.formacionspring.app.entity.Departamento;

public interface DepartamentoServicio {
	
	public List<Departamento>findAllDepartamentos();
	
	public Departamento finById(Long id);
	
	public Departamento saveDepartamento(Departamento departamento);
	
	public void deleteDepartamento(Long id);
	

}
