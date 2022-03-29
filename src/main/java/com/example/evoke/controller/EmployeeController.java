package com.example.evoke.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import com.example.evoke.config.Employee;

import com.example.evoke.entity.EmployeeEntity;



@RestController
public class EmployeeController 
{
	@Autowired
	com.example.evoke.service.EmployeeService service;
	
	@Autowired
	ModelMapper mapper;
	
	 
	  @PostConstruct
	  public void modelMapperConfigurationSetup() {
		 
			mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		   
	  }
	
	@PostMapping(path="/employees/save")
	public void saveEmployee(@RequestBody Employee employee) 
	{
		EmployeeEntity entity=mapper.map(employee, com.example.evoke.entity.EmployeeEntity.class);
		entity.setCreatedOn(new Date());
		service.saveEmployee(entity);
		
	 
		
	}
	
	
	@GetMapping(path="/employees/retrieve")
	public ResponseEntity<List<EmployeeEntity>> getEmployees() 
	{
		List<EmployeeEntity> employeeEntities=service.getEmployees();
		
		return ResponseEntity.status(HttpStatus.OK).body(employeeEntities);
		
	}
	
	
	@GetMapping(path="/employees/retrieve/{id}")
	public ResponseEntity<EmployeeEntity> getEmployee(@PathVariable(name = "id") Long id) 
	{
		
		EmployeeEntity employeeEntity=service.getEmployee(id);
		
		//List<EmployeeEntity> entity=(List<EmployeeEntity>) employeeEntity.orElseThrow(()-> new EmployeeNotFoundException("Employee Does not exist with given Id"));
	
		return ResponseEntity.status(HttpStatus.OK).body(employeeEntity);
	}
	
	@PutMapping(path="/employees/update/{id}")
	public void updateEmployee(@PathVariable(name = "id") Long id, @RequestBody Employee employee){
        EmployeeEntity entity=service.getEmployee(id);
		//EmployeeEntity entity=employeeEntity.orElseThrow(()-> new EmployeeNotFoundException("Employee Does not exist with given Id"));
       
        	 entity.setName(employee.getName());
     		entity.setPhone(employee.getPhone());
     		entity.setEmail(employee.getEmail());
     		entity.setCreatedBy(employee.getCreatedBy());
     		entity.setCreatedOn(new Date());
        
       
         service.saveEmployee(entity);
	    
	}
	
	@DeleteMapping(path="/employees/delete/{id}")
	public void deleteEmployee(@PathVariable(name = "id") Long id) 
	{
        service.deleteEmployee(id);
	}
	
	

}
