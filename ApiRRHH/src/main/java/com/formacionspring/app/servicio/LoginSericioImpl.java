package com.formacionspring.app.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.formacionspring.app.dao.LoginDAO;
import com.formacionspring.app.entity.Login;

@Service
public class LoginSericioImpl implements LoginServicio {

	@Autowired
	private LoginDAO repository;
	
	
	@Override
	public List<Login> findAllLogin() {
		
		return (List<Login>) repository.findAll();
	}

	@Override
	public Login findById(Long id) {

		return repository.findById(id).orElse(null);
	}

	@Override
	public Login saveLogin(Login login) {
		
		return repository.save(login);
	}

	@Override
	public void deleteLogin(Long id) {
		repository.deleteById(id);
	}

	

}
