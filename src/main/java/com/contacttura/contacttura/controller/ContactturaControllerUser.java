package com.contacttura.contacttura.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.contacttura.contacttura.model.ContactturaUser;
import com.contacttura.contacttura.repository.ContactturaRepositoryUser;

@RestController
@RequestMapping({"/user"})
public class ContactturaControllerUser {

	@Autowired
	private ContactturaRepositoryUser repository;

//	List All
	@GetMapping
//	http://localhost:8090/user
	public List findAll() {
		return repository.findAll();
	}

//	Find by id - Busca valor pelo ID especifico
	@GetMapping(value = "{id}")
//	http://localhost:8090/user/1
	public ResponseEntity<?> findById(@PathVariable long id){
		return repository.findById(id)
				.map(user -> ResponseEntity.ok().body(user))
				.orElse(ResponseEntity.notFound().build());
	}
	
//	Create
	@PostMapping
//	http://localhost:8090/user/1
	public ContactturaUser create(@RequestBody ContactturaUser user) {
		user.setPassword(criptografarPassword(user.getPassword()));
		return repository.save(user);
	}
	
	
//	Update	
	@PutMapping(value = "{id}")
//	http://localhost:8090/user/1
	public ResponseEntity<?> update(@PathVariable long id,
			@RequestBody ContactturaUser user){
		return repository.findById(id)
				.map(record -> {
					record.setName(user.getName());
					record.setUsername(user.getUsername());
					record.setPassword(criptografarPassword(user.getPassword()));
					record.setAdmin(false);
					ContactturaUser update = repository.save(record);
					
					return ResponseEntity.ok().body("Cliente: \n"
														+ "Nome: " + update.getName() + "\n"
														+ "Username: " + update.getUsername() + "\n"
														+ "Password: Atualizado! \n"
														+ "Atualizado com sucesso!");
				}).orElse(ResponseEntity.notFound().build());
	}
	
//	Delete
	@DeleteMapping(path = {"/{id}"})
//	http://localhost:8090/1
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable long id){
		return repository.findById(id)
				.map(objeto -> {
					repository.deleteById(id);
					
					return ResponseEntity.ok().body("Cliente " + objeto.getUsername() + " foi deletado com sucesso!");
				}).orElse(ResponseEntity.notFound().build());
	}
	
	
	private String criptografarPassword(String password) {
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String passwordCriptografado = passwordEncoder.encode(password);
		
		return passwordCriptografado;
	}
	
	
	
	
	
	
	
	
	
}
