package com.multi.contactsapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.multi.contactsapp.domain.Contact;
import com.multi.contactsapp.domain.ContactList;
import com.multi.contactsapp.domain.Result;
import com.multi.contactsapp.service.ContactService;
import com.multi.contactsapp.util.ApiException;

@RestController
@RequestMapping(value = "/contacts")
public class ContactRestController {

	// GET /contacts
	// GET /contacts?pageno=1&pagesize=2
	@Autowired
	private ContactService contactService;

	@GetMapping()
	public ContactList getContactList(@RequestParam(value = "pageno", required = false, defaultValue = "0") int pageNo,
			@RequestParam(value = "pagesize", required = false, defaultValue = "5") int pageSize) {
		ContactList contactList;
		if (pageNo > 0) {
			contactList = contactService.getContactList(pageNo, pageSize);
		} else {
			contactList = contactService.getContactList();
		}
		return contactList;
	}

	@GetMapping("{no}")
	public Contact getContactOne(@PathVariable("no") long no) {
		Contact c = new Contact();
		c.setNo(no);
		return contactService.getContactOne(c);
	}

	@PostMapping
	public Result insertContact(@RequestBody Contact c) {
		String name = c.getName();
		String tel = c.getTel();
		if (name == null || name.equals("") || tel == null || tel.equals("")) {
			throw new ApiException("이름과 전화번호는 필수 입력 항목입니다.", "102");
		}
		return contactService.insertContact(c);
	}

	@PutMapping("{no}")
	public Result updateContact(@PathVariable("no") int no, @RequestBody Contact c) {
		String name = c.getName();
		String tel = c.getTel();
		if (name == null || name.equals("") || tel == null || tel.equals("")) {
			throw new ApiException("이름과 전화번호는 필수 입력 항목입니다.", "102");
		}
		c.setNo(no);
		return contactService.updateContact(c);
	}

	@DeleteMapping("{no}")
	public Result updateContact(@PathVariable("no") int no) {
		Contact c = new Contact();
		c.setNo(no);
		return contactService.deleteContact(c);
	}
}
