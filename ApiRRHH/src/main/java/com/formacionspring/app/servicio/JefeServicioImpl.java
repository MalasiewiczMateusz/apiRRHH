package com.formacionspring.app.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.formacionspring.app.dao.JefeDAO;
import com.formacionspring.app.entity.Jefe;

@Service
public class JefeServicioImpl implements JefeServicio{
	
	@Autowired
	private JefeDAO repository;

	@Override
	public List<Jefe> findAllJefes() {
		
		return (List<Jefe>) repository.findAll();
	}

	@Override
	public Jefe findById(Long id) {
		
		return repository.findById(id).orElse(null);
	}

	@Override
	public Jefe saveJefe(Jefe jefe) {
		
		return repository.save(jefe);
	}

	@Override
	public void deleteJefe(Long id) {
		repository.deleteById(id);
	}

}
