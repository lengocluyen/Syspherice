package net.syspherice.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.bson.types.ObjectId;

import net.syspherice.enumeration.ContactEnum;
import net.syspherice.form.Contact;
import net.syspherice.utils.Common;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

public class ContactDao extends AbstractDAO{
	public ContactDao() {
		super();
	}

	public List<Contact> all() {
		List<Contact> result = new ArrayList();
		DBCursor cursor = this.getAll();
		DBObject doc;
		Contact data;
		try {
			while (cursor.hasNext()) {
				doc = cursor.next();
				result.add(this.getContact(doc));
			}
		} finally {
			cursor.close();
		}
		return result;
	}

	public Boolean insert(Contact data) {
		try {
			data.setIsRead(false);
			data.setDateSend(Calendar.getInstance().getTime().toString());
			BasicDBObject doc = this.getBasicDBObject(data);
			this.insert(doc);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Boolean update(Contact contactOrigine, Contact contactUpdate) {
		try {
			//BasicDBObject docUpdate = new BasicDBObject("$set",new BasicDBObject().append(ContactEnum.IsRead.toString(), contactUpdate.getIsRead());
			BasicDBObject docUpdate= this.getBasicDBObjectUpdate(contactUpdate);
			BasicDBObject docOrigineQuery = new BasicDBObject();
			docOrigineQuery.append(ContactEnum.ContactID.toString(),new ObjectId(contactOrigine.getContactID()));
			
			int result = this.update(docOrigineQuery, docUpdate);
			if(result>0)
			return true;
			else return false;
		} catch (Exception e) {
			return false;
		}
	}

	public Boolean delete(String contactID) {
		try {
			BasicDBObject query = new BasicDBObject();
			query.append(ContactEnum.ContactID.toString(), new ObjectId(contactID));
			int result = (int) this.remove(query);
			if(result>0)
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

	public Contact single(String contactID) {
		try {
			DBObject doc = new BasicDBObject(Common.ConvertToString(ContactEnum.ContactID.toString(), ""), new ObjectId(contactID));
			Contact contact = this.getContact(this.getDbCollection().findOne(doc));
			return contact;
		} catch (Exception e) {
			return null;
		}
	}

	public long count(){
		return this.getDbCollection().count();
	}
	public List<Contact> paging(int page, int pagesize){
		List<Contact> result = new ArrayList<Contact>();
		DBCursor cursor = this.getDbCollection().find().skip(pagesize*(page-1)).limit(pagesize);
		DBObject doc;
		try {
			while (cursor.hasNext()) {
				doc = cursor.next();
				result.add(this.getContact(doc));
			}
		} finally {
			cursor.close();
		}
		return result;
	}
	
	
	public Contact getContact(DBObject doc) {
		Contact data = new Contact();
		data.setContactID(Common.ConvertToString(
				doc.get(ContactEnum.ContactID.toString()), ""));
		data.setFirstName(Common.ConvertToString(
				doc.get(ContactEnum.FirstName.toString()), ""));
		data.setLastName(Common.ConvertToString(
				doc.get(ContactEnum.LastName.toString()), ""));
		data.setEmail(Common.ConvertToString(
				doc.get(ContactEnum.Email.toString()), ""));
		data.setMessage(Common.ConvertToString(
				doc.get(ContactEnum.Message.toString()), ""));
		data.setIsRead(Common.ConvertToBoolean(
				doc.get(ContactEnum.IsRead.toString()), false));
		data.setDateSend(Common.ConvertToString(doc.get(ContactEnum.DateSend.toString()), Calendar.getInstance().getTime().toString()));
		return data;
	}

	public BasicDBObject getBasicDBObject(Contact data) {
		BasicDBObject doc = new BasicDBObject();
		if(data.getContactID()!=""){
			doc.put(ContactEnum.ContactID.toString(), data.getContactID());
		}
		doc.put(ContactEnum.FirstName.toString(), data.getFirstName());
		doc.put(ContactEnum.LastName.toString(), data.getLastName());
		doc.put(ContactEnum.Email.toString(), data.getEmail());
		doc.put(ContactEnum.Message.toString(), data.getMessage());
		doc.put(ContactEnum.IsRead.toString(), data.getIsRead());
		doc.put(ContactEnum.DateSend.toString(), data.getDateSend());
		return doc;
	}
	public BasicDBObject getBasicDBObjectUpdate(Contact data) {
		
		BasicDBObject doc = new BasicDBObject();
		doc.append(ContactEnum.FirstName.toString(), data.getFirstName());
		doc.append(ContactEnum.LastName.toString(), data.getLastName());
		doc.append(ContactEnum.Email.toString(), data.getEmail());
		doc.append(ContactEnum.Message.toString(), data.getMessage());
		doc.append(ContactEnum.IsRead.toString(), data.getIsRead());
		doc.append(ContactEnum.DateSend.toString(), data.getDateSend());
		BasicDBObject bdoc = new BasicDBObject("$set",doc);
		return bdoc;
	}
}
