package com.formacionspring.app.servicio;

import java.util.List;

import com.formacionspring.app.entity.Empleado;

public interface EmpleadoServicio {
	
	
	public List<Empleado> findAllEmpleados();
	
	public Empleado findById(Long id);
	
	public Empleado saveEmpleado(Empleado empleado);
	
	public void deleteEmpleado(Long id);

}
