package com.multi.contacsapp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.multi.contacsapp.domain.Contact;

@RepositoryRestResource(path="contacts2", collectionResourceRel = "contacts")
public interface ContactRepository extends JpaRepository<Contact, Long> {
	 @RestResource(path = "findbyname", exported = true) 
	  List<Contact> findByNameContainingOrderByNameDesc(@Param("name") String name);
}
