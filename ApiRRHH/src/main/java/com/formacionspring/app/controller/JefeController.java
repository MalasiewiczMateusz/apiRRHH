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

import com.formacionspring.app.entity.Jefe;
import com.formacionspring.app.servicio.JefeServicio;

@RestController
@RequestMapping("/api")
public class JefeController {

	@Autowired
	private JefeServicio servicio;
	
	@GetMapping({"/jefes","/todos"})
	public List<Jefe> index(){
		return servicio.findAllJefes();
	}
	
	@GetMapping("jefes/{id}")
	public ResponseEntity<?> findJefeById(@PathVariable Long id){
		Jefe jefe=null;
		
		Map<String,Object> response= new HashMap<>();
		
		try {
			jefe=servicio.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(jefe==null) {
			response.put("mensaje", "El jefe ID: "+id+" no existe en la base de datos");
			
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Jefe>(jefe, HttpStatus.OK);
	}
	
	
	@PostMapping("/jefe")
	public ResponseEntity<?>saveJefe(@RequestBody Jefe jefe){
		Jefe jefeNew= null;
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			jefeNew=servicio.saveJefe(jefe);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar un insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", jefeNew);
		response.put("jefe", "El jefe ha sido creado con éxito");
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}
	
	
	@PutMapping("/jefe/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?>updateJefe(@RequestBody Jefe jefe,@PathVariable Long id){
		
		Jefe jefeUpdate=servicio.findById(id);
		
		Map<String, Object> response = new HashMap<>();
		

		if (jefeUpdate == null) {
			response.put("mensaje", "Error: no se puedo editar el jefe con ID: " + id);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			jefeUpdate.setId(id);
			jefeUpdate.setDni(jefe.getDni());
			jefeUpdate.setNombre(jefe.getNombre());
			jefeUpdate.setSalario(jefe.getSalario());
			jefeUpdate.setTelefono(jefe.getTelefono());
			jefeUpdate.setDepartamento(jefe.getDepartamento());
		
			

			servicio.saveJefe(jefeUpdate);
		} catch (DataAccessException e) {

			response.put("mensaje", "Error al realizar un update en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("Jefe", jefeUpdate);
		response.put("mensaje", "El jefe ha sido actualizado con éxito");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/jefe/{id}")
	public ResponseEntity<?> deleteJefe(@PathVariable Long id) {

		Jefe jefeDelete = servicio.findById(id);
		Map<String, Object> response = new HashMap<>();

		if (jefeDelete == null) {
			response.put("mensaje", "Error: no se pudo eliminar el jefe con ID: " + id);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			
			servicio.deleteJefe(id);
		} catch (DataAccessException e) {

			response.put("mensaje", "Error al eliminar la información en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("jefe", jefeDelete);
		response.put("mensaje", "El jefe ha sido eliminado con éxito");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	
	
}
