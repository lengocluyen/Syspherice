package net.syspherice.service;

import java.util.List;

import net.syspherice.form.Account;

public interface AccountService {
	Boolean add(Account data);

	List<Account> all();

	Boolean update(Account dataOrigine, Account dataUpdate);

	Boolean delete(String username);

	Account single(String username);

	Account login(String username, String password);

	long count();

	List<Account> paging(int page, int pagesize);
}
