package net.syspherice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import net.syspherice.dao.ContactDao;
import net.syspherice.dao.DaoFactory;
import net.syspherice.form.Contact;

@Service
public class ContactServiceImpl implements ContactService{
	private ContactDao contactDao;

	public ContactServiceImpl() {
		// TODO Auto-generated constructor stub
		contactDao = DaoFactory.getContactDao();
	}

	public Contact single(String contactID) {
		return contactDao.single(contactID);
	}

	public List<Contact> all() {
		return contactDao.all();
	}

	public Boolean add(Contact contact) {
		return contactDao.insert(contact);
	}

	public Boolean update(Contact contactOrigine, Contact contactUpdate) {
		return contactDao.update(contactOrigine, contactUpdate);
	}

	public Boolean delete(String contactID) {
		return contactDao.delete(contactID);
	}
	public long count(){
		return contactDao.count();
	}
	public List<Contact> paging(int page, int pagesize){
		return contactDao.paging(page, pagesize);
	}
}
