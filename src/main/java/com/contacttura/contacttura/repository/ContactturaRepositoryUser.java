package com.contacttura.contacttura.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contacttura.contacttura.model.ContactturaUser;

public interface ContactturaRepositoryUser extends JpaRepository<ContactturaUser, Long> {}
