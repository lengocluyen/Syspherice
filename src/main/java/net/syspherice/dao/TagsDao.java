package net.syspherice.dao;

import java.util.ArrayList;
import java.util.List;

import net.syspherice.enumeration.TagsEnum;
import net.syspherice.form.Tags;
import net.syspherice.utils.Common;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class TagsDao extends AbstractDAO {
	public TagsDao() {
		super();
	}

	public List<Tags> all() {
		List<Tags> result = new ArrayList();
		DBCursor cursor = this.getAll();
		DBObject doc;
		try {
			while (cursor.hasNext()) {
				doc = cursor.next();
				result.add(this.getTag(doc));
			}
		} finally {
			cursor.close();
		}
		return result;
	}

	public Boolean insert(Tags data) {
		try {
			BasicDBObject doc = this.getBasicDBObject(data);
			this.insert(doc);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Boolean update(Tags TagOrigine, Tags TagUpdate) {
		try {
			BasicDBObject docUpdate = this.getBasicDBObjectUpdate(TagUpdate);
			BasicDBObject docOrigineQuery = new BasicDBObject();
			docOrigineQuery.append(TagsEnum.TagID.toString(), new ObjectId(
					TagOrigine.getTagID()));

			int result = this.update(docOrigineQuery, docUpdate);
			if (result > 0)
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

	public Boolean delete(String TagID) {
		try {
			BasicDBObject query = new BasicDBObject();
			query.append(TagsEnum.TagID.toString(), new ObjectId(TagID));
			int result = (int) this.remove(query);
			if (result > 0)
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

	public Tags single(String TagID) {
		try {
			DBObject doc = new BasicDBObject(Common.ConvertToString(
					TagsEnum.TagID.toString(), ""), new ObjectId(TagID));
			Tags Tag = this.getTag(this.getDbCollection().findOne(doc));
			return Tag;
		} catch (Exception e) {
			return null;
		}
	}

	public Tags singleByName(String name) {
		try {
			DBObject queryDoc = new BasicDBObject(Common.ConvertToString(
					TagsEnum.Name.toString(), ""), name);
			Tags Tag = this.getTag(this.getDbCollection().findOne(queryDoc));
			return Tag;
		} catch (Exception e) {
			return null;
		}
	}

	public long count() {
		return this.getDbCollection().count();
	}

	public List<Tags> paging(int page, int pagesize) {
		List<Tags> result = new ArrayList<Tags>();
		DBCursor cursor = this.getDbCollection().find()
				.skip(pagesize * (page - 1)).limit(pagesize);
		DBObject doc;
		try {
			while (cursor.hasNext()) {
				doc = cursor.next();
				result.add(this.getTag(doc));
			}
		} finally {
			cursor.close();
		}
		return result;
	}

	public Tags getTag(DBObject doc) {
		Tags data = new Tags();
		data.setTagID(Common.ConvertToString(
				doc.get(TagsEnum.TagID.toString()), ""));
		data.setName(Common.ConvertToString(doc.get(TagsEnum.Name.toString()),
				""));
		data.setDescription(Common.ConvertToString(
				doc.get(TagsEnum.Description.toString()), ""));
		return data;
	}

	public BasicDBObject getBasicDBObject(Tags data) {
		BasicDBObject doc = new BasicDBObject();
		if (data.getTagID() != "") {
			doc.put(TagsEnum.TagID.toString(), data.getTagID());
		}
		doc.put(TagsEnum.Name.toString(), data.getName());
		doc.put(TagsEnum.Description.toString(), data.getDescription());
		return doc;
	}

	public BasicDBObject getBasicDBObjectUpdate(Tags data) {

		BasicDBObject doc = new BasicDBObject();
		doc.append(TagsEnum.Name.toString(), data.getName());
		doc.append(TagsEnum.Description.toString(), data.getDescription());
		BasicDBObject bdoc = new BasicDBObject("$set", doc);
		return bdoc;
	}
}
