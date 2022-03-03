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

import com.formacionspring.app.entity.Empleado;
import com.formacionspring.app.servicio.EmpleadoServicio;

@RestController
@RequestMapping("/api")
public class EmpleadoController {
	
	@Autowired
	private EmpleadoServicio servicio;
	
	@GetMapping({"/empleados","/todos"})
	public List<Empleado> index(){
		return servicio.findAllEmpleados();
	}
	
	
	@GetMapping("empleados/{id}")
	public ResponseEntity<?> findEmpleadoById(@PathVariable Long id){
		Empleado empleado=null;
		
		Map<String,Object> response= new HashMap<>();
		
		try {
			empleado=servicio.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(empleado==null) {
			response.put("mensaje", "El empleado ID: "+id+" no existe en la base de datos");
			
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Empleado>(empleado, HttpStatus.OK);
	}
	
	@PostMapping("/empleado")
	public ResponseEntity<?>saveEmpleado(@RequestBody Empleado empleado){
		Empleado empleadoNew= null;
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			empleadoNew=servicio.saveEmpleado(empleado);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar un insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", empleadoNew);
		response.put("empleado", "El empelado ha sido creado con éxito");
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/empleado/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?>updateEmpleado(@RequestBody Empleado empleado,@PathVariable Long id){
		
		Empleado empleadoUpdate=servicio.findById(id);
		
		Map<String, Object> response = new HashMap<>();
		

		if (empleadoUpdate == null) {
			response.put("mensaje", "Error: no se puedo editar el empleado con ID: " + id);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			empleadoUpdate.setId(id);
			empleadoUpdate.setNombre(empleado.getNombre());
			empleadoUpdate.setSalario(empleado.getSalario());
			empleadoUpdate.setTelefono(empleado.getTelefono());
			empleadoUpdate.setDepartamento(empleado.getDepartamento());
		
			

			servicio.saveEmpleado(empleadoUpdate);
		} catch (DataAccessException e) {

			response.put("mensaje", "Error al realizar un update en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("Departamento", empleadoUpdate);
		response.put("mensaje", "El empleado ha sido actualizado con éxito");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/empleado/{id}")
	public ResponseEntity<?> deleteDepartamento(@PathVariable Long id) {

		Empleado empleadoDelete = servicio.findById(id);
		Map<String, Object> response = new HashMap<>();

		if (empleadoDelete == null) {
			response.put("mensaje", "Error: no se pudo eliminar el empleado con ID: " + id);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			
			servicio.deleteEmpleado(id);
		} catch (DataAccessException e) {

			response.put("mensaje", "Error al eliminar la información en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("empleado", empleadoDelete);
		response.put("mensaje", "El empleado ha sido eliminado con éxito");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
