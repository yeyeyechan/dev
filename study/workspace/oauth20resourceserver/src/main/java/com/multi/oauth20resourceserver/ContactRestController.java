package com.multi.oauth20resourceserver;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multi.oauth20resourceserver.dao.ContactRepository;
import com.multi.oauth20resourceserver.domain.Contact;

@RestController
@RequestMapping(value="/api")
public class ContactRestController {  
  @Autowired
  ContactRepository contactRepository;

  @GetMapping(value="/contacts", produces = {"application/json"})
  public List<Contact> getContactList() {
    return contactRepository.findAll();
  }
  
  @GetMapping(value="/profiles", produces = {"application/json"})
  public HashMap<String, String> getProfile() {
    HashMap<String,String> profile = new HashMap<String, String>();
    profile.put("system", "Test Auth Server");
    profile.put("devenv", "Spring Boot 2.x");
    return profile;
  }
}

