package net.syspherice.service;

import java.util.List;

import net.syspherice.dao.ContactDao;
import net.syspherice.dao.DaoFactory;
import net.syspherice.form.Contact;

import org.springframework.stereotype.Repository;

public interface ContactService {
	
	Contact single(String contactID);

	List<Contact> all();

	Boolean add(Contact contact) ;
	Boolean update(Contact contactOrigine, Contact contactUpdate) ;

	Boolean delete(String contactID);
	
	long count();
	List<Contact> paging(int page, int pagesize);

}
