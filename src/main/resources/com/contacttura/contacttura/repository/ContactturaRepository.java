package com.contacttura.contacttura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contacttura.contacttura.model.Contacttura;

@Repository
public interface ContactturaRepository extends JpaRepository<Contacttura, Long> {}
