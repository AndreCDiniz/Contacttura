package com.contacttura.contacttura.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.contacttura.contacttura.model.Contacttura;
import com.contacttura.contacttura.repository.ContactturaRepository;

@RestController
@RequestMapping({"/contacttura"})
public class ContactturaController {
	@Autowired
	private ContactturaRepository repository;
	
// Fluxo semelhante ao implements que define que este controlador com seus
// métodos será acessado atraves do repositorio.
//	ContactturaController(ContactturaRepository contacturaRepository){
//		this.repository = contacturaRepository;
//	}
	
// 		List All
		@GetMapping
// 		http://localhost:8090/contacttura	
		public List findAll() {
			return repository.findAll();
		}
		
//		Find by id - Buscaa valor pelo ID especifico
		@GetMapping(value = "{id}")
//		http://localhost:8090/contacttura/1			
		public ResponseEntity findById(@PathVariable long id) {
			return repository.findById(id)
					.map(record -> ResponseEntity.ok().body(record))
					.orElse(ResponseEntity.notFound().build());
		}
		
//		Create
		@PostMapping
//		http://localhost:8090/contacttura		
		public Contacttura create(@RequestBody Contacttura contacttura) {
			return repository.save(contacttura);
		}
		
//		Update
		@PutMapping(value = "{id}")
//		http://localhost:8090/contacttura/2	
		public ResponseEntity update(@PathVariable long id,  
				@RequestBody Contacttura contacttura) {
			return repository.findById(id)
					.map(record -> {
						record.setName(contacttura.getName());
						record.setEmail(contacttura.getEmail());
						record.setPhone(contacttura.getPhone());
						Contacttura update = repository.save(record);
						
						return ResponseEntity.ok().body(update);
					}).orElse(ResponseEntity.notFound().build());
					
		}
		
		@DeleteMapping(path = {"/{id}"})
//		http://localhost:8090/contacttura/2
		public ResponseEntity <?> delete(@PathVariable long id){
			return repository.findById(id)
					.map(record -> {
						repository.deleteById(id);
						return ResponseEntity.ok().build();
					}).orElse(ResponseEntity.notFound().build());
		}
		
}


