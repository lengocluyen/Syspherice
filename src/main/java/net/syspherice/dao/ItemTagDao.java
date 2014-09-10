package net.syspherice.dao;

import java.util.ArrayList;
import java.util.List;

import net.syspherice.enumeration.AccountEnum;
import net.syspherice.enumeration.FieldCollectionEnum;
import net.syspherice.enumeration.ItemTagEnum;
import net.syspherice.form.Account;
import net.syspherice.form.FieldCollection;
import net.syspherice.form.ItemTag;
import net.syspherice.utils.Common;

import org.bson.BSONObject;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class ItemTagDao extends AbstractDAO {
	public ItemTagDao() {
		super();
	}

	public List<ItemTag> all() {
		List<ItemTag> result = new ArrayList();
		DBCursor cursor = this.getAll();
		DBObject doc;
		try {
			while (cursor.hasNext()) {
				doc = cursor.next();
				result.add(this.getItemTag(doc));
			}
		} finally {
			cursor.close();
		}
		return result;
	}

	public Boolean insert(ItemTag data) {
		try {
			BasicDBObject doc = this.getBasicDBObject(data);
			this.insert(doc);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Boolean update(ItemTag ItemTagOrigine, ItemTag ItemTagUpdate) {
		try {
			BasicDBObject docUpdate = this
					.getBasicDBObjectUpdate(ItemTagUpdate);
			BasicDBObject docOrigineQuery = new BasicDBObject();
			docOrigineQuery.append(ItemTagEnum.ItemTagID.toString(),
					new ObjectId(ItemTagOrigine.getItemTagID()));

			int result = this.update(docOrigineQuery, docUpdate);
			if (result > 0)
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

	public Boolean delete(String ItemTagID) {
		try {
			BasicDBObject query = new BasicDBObject();
			query.append(ItemTagEnum.ItemTagID.toString(), new ObjectId(
					ItemTagID));
			int result = (int) this.remove(query);
			if (result > 0)
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

	public ItemTag single(String ItemTagID) {
		try {
			DBObject doc = new BasicDBObject(Common.ConvertToString(
					ItemTagEnum.ItemTagID.toString(), ""), new ObjectId(
					ItemTagID));
			ItemTag ItemTag = this.getItemTag(this.getDbCollection().findOne(
					doc));
			return ItemTag;
		} catch (Exception e) {
			return null;
		}
	}

	public List<ItemTag> findByTagID(String tagID) {
		try {
			List<ItemTag> result = new ArrayList<ItemTag>();
			DBObject queryDoc = new BasicDBObject(new BasicDBObject(Common.ConvertToString(
					ItemTagEnum.TagID.toString(), ""), tagID));
			DBCursor cursor = this.getDbCollection().find(queryDoc);
			DBObject doc;
			try {
				while (cursor.hasNext()) {
					doc = cursor.next();
					result.add(this.getItemTag(doc));
				}
			} finally {
				cursor.close();
			}
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	public ItemTag singleByTagID(String tagID) {
		try {
			DBObject queryDoc = new BasicDBObject();

			List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
			obj.add(new BasicDBObject(Common.ConvertToString(
					ItemTagEnum.TagID.toString(), ""), tagID));
			ItemTag ItemTag = this.getItemTag(this.getDbCollection().findOne(
					queryDoc));
			return ItemTag;
		} catch (Exception e) {
			return null;
		}
	}

	public ItemTag singleByItems(ItemTag item) {
		try {
			DBObject queryDoc = new BasicDBObject();

			List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
			obj.add(new BasicDBObject(Common.ConvertToString(
					ItemTagEnum.ItemID.toString(), ""), item.getItemID()));
			obj.add(new BasicDBObject(Common.ConvertToString(
					ItemTagEnum.CollectionName.toString(), ""), item
					.getCollectionName()));
			obj.add(new BasicDBObject(Common.ConvertToString(
					ItemTagEnum.TagID.toString(), ""), item.getTagID()));
			queryDoc.put("$and", obj);
			ItemTag account = this.getItemTag(this.getDbCollection().findOne(
					queryDoc));
			return account;
		} catch (Exception e) {
			return null;
		}
	}

	public long count() {
		return this.getDbCollection().count();
	}

	public List<ItemTag> paging(int page, int pagesize) {
		List<ItemTag> result = new ArrayList<ItemTag>();
		DBCursor cursor = this.getDbCollection().find()
				.skip(pagesize * (page - 1)).limit(pagesize);
		DBObject doc;
		try {
			while (cursor.hasNext()) {
				doc = cursor.next();
				result.add(this.getItemTag(doc));
			}
		} finally {
			cursor.close();
		}
		return result;
	}

	public ItemTag getItemTag(DBObject doc) {
		ItemTag data = new ItemTag();
		data.setItemTagID(Common.ConvertToString(
				doc.get(ItemTagEnum.ItemTagID.toString()), ""));
		data.setTagID(Common.ConvertToString(
				doc.get(ItemTagEnum.TagID.toString()), ""));
		data.setAccountID(Common.ConvertToString(
				doc.get(ItemTagEnum.AccountID.toString()), ""));
		data.setItemID(Common.ConvertToString(
				doc.get(ItemTagEnum.ItemID.toString()), ""));
		data.setCollectionName(Common.ConvertToString(
				doc.get(ItemTagEnum.CollectionName.toString()), ""));
		return data;
	}

	public BasicDBObject getBasicDBObject(ItemTag data) {
		BasicDBObject doc = new BasicDBObject();
		if (data.getItemTagID() != "") {
			doc.put(ItemTagEnum.ItemTagID.toString(), data.getItemTagID());
		}
		doc.put(ItemTagEnum.TagID.toString(), data.getTagID());
		doc.put(ItemTagEnum.ItemID.toString(), data.getItemID());
		doc.put(ItemTagEnum.AccountID.toString(), data.getAccountID());
		doc.put(ItemTagEnum.CollectionName.toString(), data.getCollectionName());
		return doc;
	}

	public BasicDBObject getBasicDBObjectUpdate(ItemTag data) {

		BasicDBObject doc = new BasicDBObject();
		doc.append(ItemTagEnum.TagID.toString(), data.getTagID());
		doc.append(ItemTagEnum.ItemID.toString(), data.getItemID());
		doc.append(ItemTagEnum.AccountID.toString(), data.getAccountID());
		doc.append(ItemTagEnum.CollectionName.toString(),
				data.getCollectionName());
		BasicDBObject bdoc = new BasicDBObject("$set", doc);
		return bdoc;
	}
}
