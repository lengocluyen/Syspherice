package net.syspherice.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import net.syspherice.enumeration.AccountEnum;
import net.syspherice.enumeration.ContactEnum;
import net.syspherice.form.Account;
import net.syspherice.form.Contact;
import net.syspherice.utils.Common;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class AccountDao extends AbstractDAO {
	public AccountDao() {
		super();
	}

	public List<Account> all() {
		List<Account> result = new ArrayList();
		DBCursor cursor = this.getAll();
		DBObject doc;
		Account data;
		try {
			while (cursor.hasNext()) {
				doc = cursor.next();
				result.add(this.getAccount(doc));
			}
		} finally {
			cursor.close();
		}
		return result;
	}

	public Boolean insert(Account data) {
		try {
			BasicDBObject doc = this.getBasicDBObject(data);
			this.insert(doc);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Boolean update(Account accountOrigine, Account accountUpdate) {
		try {
			BasicDBObject docUpdate = this
					.getBasicDBObjectUpdate(accountUpdate);
			BasicDBObject docOrigineQuery = new BasicDBObject();
			docOrigineQuery.append(AccountEnum.Username.toString(),
					accountOrigine.getUsername());

			int result = this.update(docOrigineQuery, docUpdate);
			if (result > 0)
				return true;
			else
				return false;

		} catch (Exception e) {
			return false;
		}
	}

	public Boolean delete(String username) {
		try {
			BasicDBObject query = new BasicDBObject();
			query.append(Common.ConvertToString(AccountEnum.Username.toString(), ""),username);
			int result = (int) this.remove(query);
			if(result>0)
				return true;
			else
				return false;
			
		} catch (Exception e) {
			return false;
		}
	}

	public Account single(String username) {
		try {
			
			DBObject doc = new BasicDBObject(Common.ConvertToString(AccountEnum.Username.toString(), ""),
					username);
			Account account = this.getAccount(this.getDbCollection().findOne(doc));
			return account;
			
			
			
			
		} catch (Exception e) {
			return null;
		}
	}

	public Account login(String username, String password) {
		try {
			DBObject queryDoc = new BasicDBObject();
			
			List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
			obj.add(new BasicDBObject(Common.ConvertToString(AccountEnum.Username.toString(), ""),
					username));
			obj.add(new BasicDBObject(Common.ConvertToString(AccountEnum.Password.toString(), ""),
					password));
			queryDoc.put("$and", obj);
			Account account = this.getAccount(this.getDbCollection().findOne(
					queryDoc));
			return account;
		} catch (Exception e) {
			return null;
		}
	}

	public long count() {
		return this.getDbCollection().count();
	}

	public List<Account> paging(int page, int pagesize) {
		List<Account> result = new ArrayList<Account>();
		DBCursor cursor = this.getDbCollection().find()
				.skip(pagesize * (page - 1)).limit(pagesize);
		DBObject doc;
		try {
			while (cursor.hasNext()) {
				doc = cursor.next();
				result.add(this.getAccount(doc));
			}
		} finally {
			cursor.close();
		}
		return result;
	}

	public Account getAccount(DBObject doc) {
		Account data = new Account();
		data.setUsername(Common.ConvertToString(
				doc.get(AccountEnum.Username.toString()), ""));
		data.setPassword(Common.ConvertToString(
				doc.get(AccountEnum.Password.toString()), ""));
		data.setFirstName(Common.ConvertToString(
				doc.get(AccountEnum.FirstName.toString()), ""));
		data.setLastName(Common.ConvertToString(
				doc.get(AccountEnum.LastName.toString()), ""));
		data.setBirthdate(Common.ConvertToString(
				doc.get(AccountEnum.Birthdate.toString()), ""));
		data.setEmail(Common.ConvertToString(
				doc.get(AccountEnum.Email.toString()), ""));
		data.setDateCreate(Common.ConvertToString(
				doc.get(AccountEnum.DateCreate.toString()), ""));
		data.setLastLogin(Common.ConvertToString(
				doc.get(AccountEnum.LastLogin.toString()), ""));
		data.setSex(Common.ConvertToString(doc.get(AccountEnum.Sex.toString()),
				""));
		data.setDescription(Common.ConvertToString(
				doc.get(AccountEnum.Description.toString()), ""));
		data.setAvatar(Common.ConvertToString(
				doc.get(AccountEnum.Avatar.toString()), ""));
		data.setState(Common.ConvertToString(
				doc.get(AccountEnum.State.toString()), ""));
		return data;
	}

	public BasicDBObject getBasicDBObject(Account data) {
		BasicDBObject doc = new BasicDBObject();
		doc.put(AccountEnum.Username.toString(), data.getUsername());
		doc.put(AccountEnum.Password.toString(), data.getPassword());
		doc.put(AccountEnum.FirstName.toString(), data.getFirstName());
		doc.put(AccountEnum.LastName.toString(), data.getLastName());
		doc.put(AccountEnum.Birthdate.toString(), data.getBirthdate());
		doc.put(AccountEnum.Email.toString(), data.getEmail());
		doc.put(AccountEnum.DateCreate.toString(), data.getDateCreate());
		doc.put(AccountEnum.LastLogin.toString(), data.getLastLogin());
		doc.put(AccountEnum.Sex.toString(), data.getSex());
		doc.put(AccountEnum.Description.toString(), data.getDescription());
		doc.put(AccountEnum.Avatar.toString(), data.getAvatar());
		doc.put(AccountEnum.State.toString(), data.getState());
		return doc;
	}

	public BasicDBObject getBasicDBObjectUpdate(Account data) {

		BasicDBObject doc = new BasicDBObject();
		doc.put(AccountEnum.Password.toString(), data.getPassword());
		doc.put(AccountEnum.FirstName.toString(), data.getFirstName());
		doc.put(AccountEnum.LastName.toString(), data.getLastName());
		doc.put(AccountEnum.Birthdate.toString(), data.getBirthdate());
		doc.put(AccountEnum.Email.toString(), data.getEmail());
		// doc.put(AccountEnum.DateCreate.toString(), data.getDateCreate());
		doc.put(AccountEnum.LastLogin.toString(), data.getLastLogin());
		doc.put(AccountEnum.Sex.toString(), data.getSex());
		doc.put(AccountEnum.Description.toString(), data.getDescription());
		doc.put(AccountEnum.Avatar.toString(), data.getAvatar());
		doc.put(AccountEnum.State.toString(), data.getState());
		BasicDBObject bdoc = new BasicDBObject("$set", doc);
		return bdoc;
	}
}
