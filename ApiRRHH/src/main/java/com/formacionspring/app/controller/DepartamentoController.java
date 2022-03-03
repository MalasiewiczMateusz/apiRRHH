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

import com.formacionspring.app.entity.Departamento;
import com.formacionspring.app.servicio.DepartamentoServicio;


@RestController
@RequestMapping("/api")
public class DepartamentoController {

	@Autowired
	private DepartamentoServicio servicio;
	
	@GetMapping({"/departamentos","/todos"})
	public List<Departamento> index(){
		return servicio.findAllDepartamentos();
	}
	
	@GetMapping("departamentos/{id}")
	public ResponseEntity<?> findDepartamentoById(@PathVariable Long id){
		Departamento departamento=null;
		
		Map<String,Object> response= new HashMap<>();
		
		try {
			departamento=servicio.finById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(departamento==null) {
			response.put("mensaje", "El cliente ID: "+id+" no existe en la base de datos");
			
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Departamento>(departamento, HttpStatus.OK);
	}
	
	@PostMapping("/departamento")
	public ResponseEntity<?>saveDepartamento(@RequestBody Departamento departamento){
		Departamento departamentoNew= null;
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			departamentoNew=servicio.saveDepartamento(departamento);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar un insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", departamentoNew);
		response.put("departamento", "El departamento ha sido creado con éxito");
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/departamento/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?>updateDepartamento(@RequestBody Departamento departamento,@PathVariable Long id){
		
		Departamento departamentoUpdate=servicio.finById(id);
		
		Map<String, Object> response = new HashMap<>();
		

		if (departamentoUpdate == null) {
			response.put("mensaje", "Error: no se puedo editar el departamento con ID: " + id);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			departamentoUpdate.setId(id);
			departamentoUpdate.setNombre(departamento.getNombre());
			departamentoUpdate.setUbicacion(departamento.getUbicacion());
		
			

			servicio.saveDepartamento(departamentoUpdate);
		} catch (DataAccessException e) {

			response.put("mensaje", "Error al realizar un update en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("Departamento", departamentoUpdate);
		response.put("mensaje", "El departamento ha sido actualizado con éxito");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/departamento/{id}")
	public ResponseEntity<?> deleteDepartamento(@PathVariable Long id) {

		Departamento departamentoDelete = servicio.finById(id);
		Map<String, Object> response = new HashMap<>();

		if (departamentoDelete == null) {
			response.put("mensaje", "Error: no se pudo eliminar el departamento con ID: " + id);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			
			servicio.deleteDepartamento(id);
		} catch (DataAccessException e) {

			response.put("mensaje", "Error al eliminar la información en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("departamento", departamentoDelete);
		response.put("mensaje", "El departamento ha sido eliminado con éxito");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
