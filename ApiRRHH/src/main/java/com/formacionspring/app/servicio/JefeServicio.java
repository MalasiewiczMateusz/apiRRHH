package com.formacionspring.app.servicio;

import java.util.List;

import com.formacionspring.app.entity.Jefe;



public interface JefeServicio {
	
	List<Jefe> findAllJefes();

	Jefe findById(Long id);

	Jefe saveJefe(Jefe jefe);

	void deleteJefe(Long id);

}
