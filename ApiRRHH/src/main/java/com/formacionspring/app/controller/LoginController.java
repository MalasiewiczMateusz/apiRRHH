package com.formacionspring.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.formacionspring.app.entity.Login;
import com.formacionspring.app.servicio.LoginServicio;

@RestController
@RequestMapping("/api")
public class LoginController {
	
	@Autowired
	private LoginServicio servicio;
	
	@GetMapping({"/logins","/todos"})
	public List<Login> index(){
		return servicio.findAllLogin();
	}
	
	
	@GetMapping("logins/{id}")
	public ResponseEntity<?> findLoginById(@PathVariable Long id){
		Login login=null;
		
		Map<String,Object> response= new HashMap<>();
		
		try {
			login=servicio.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(login==null) {
			response.put("mensaje", "El login ID: "+id+" no existe en la base de datos");
			
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Login>(login, HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?>saveLogin(@RequestBody Login login){
		Login loginNew= null;
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			loginNew=servicio.saveLogin(login);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar un insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", loginNew);
		response.put("login", "El login ha sido creado con éxito");
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}
	
	
	@PutMapping("/login/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?>updateLogin(@RequestBody Login login,@PathVariable Long id){
		
		Login loginUpdate =servicio.findById(id);
		
		Map<String, Object> response = new HashMap<>();
		

		if (loginUpdate == null) {
			response.put("mensaje", "Error: no se puedo editar el login con ID: " + id);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			loginUpdate.setId(id);
			loginUpdate.setNombreUsuario(login.getNombreUsuario());
			loginUpdate.setContrasena(login.getContrasena());
		
			

			servicio.saveLogin(loginUpdate);
		} catch (DataAccessException e) {

			response.put("mensaje", "Error al realizar un update en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("Login", loginUpdate);
		response.put("mensaje", "El login ha sido actualizado con éxito");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/login/{id}")
	public ResponseEntity<?> deleteLogin(@PathVariable Long id) {

		Login loginDelete = servicio.findById(id);
		Map<String, Object> response = new HashMap<>();

		if (loginDelete == null) {
			response.put("mensaje", "Error: no se pudo eliminar el login con ID: " + id);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			
			servicio.deleteLogin(id);
		} catch (DataAccessException e) {

			response.put("mensaje", "Error al eliminar la información en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("login", loginDelete);
		response.put("mensaje", "El login ha sido eliminado con éxito");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
