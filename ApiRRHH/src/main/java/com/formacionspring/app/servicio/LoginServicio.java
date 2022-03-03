package com.formacionspring.app.servicio;

import java.util.List;

import com.formacionspring.app.entity.Login;

public interface LoginServicio {
	
	public List<Login>findAllLogin();
	
	public Login findById(Long id);
	
	public Login saveLogin(Login login);
	
	public void deleteLogin(Long id);

}
