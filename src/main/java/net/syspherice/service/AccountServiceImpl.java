package net.syspherice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.syspherice.dao.AccountDao;
import net.syspherice.dao.DaoFactory;
import net.syspherice.form.Account;
import net.syspherice.form.Contact;

@Service
public class AccountServiceImpl implements AccountService{
	
	private AccountDao accountDao;
	
	public AccountServiceImpl() {
		accountDao = DaoFactory.getAccountDao();
	}
		
	 public Boolean add(Account data) {
	       return accountDao.insert(data);
	    }
	 public List<Account> all(){
		 return accountDao.all();
	 }
	 public Boolean update (Account dataOrigine,Account dataUpdate){
		 return accountDao.update(dataOrigine, dataUpdate);
	 }
	 public Boolean delete (String username){
		 return accountDao.delete(username);
	 }
	 public Account single(String username){
		 return accountDao.single(username);
	 }
	 public Account login(String username, String password){
		 return accountDao.login(username, password);
	 }
	 public long count(){
			return accountDao.count();
		}
		public List<Account> paging(int page, int pagesize){
			return accountDao.paging(page, pagesize);
		}
}
