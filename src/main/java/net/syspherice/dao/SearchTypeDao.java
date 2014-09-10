package net.syspherice.dao;

import java.util.ArrayList;
import java.util.List;

import net.syspherice.enumeration.AccountEnum;
import net.syspherice.enumeration.FieldCollectionEnum;
import net.syspherice.enumeration.SearchTypeEnum;
import net.syspherice.form.Account;
import net.syspherice.form.FieldCollection;
import net.syspherice.form.SearchType;
import net.syspherice.utils.Common;

import org.bson.BSONObject;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class SearchTypeDao extends AbstractDAO {
	public SearchTypeDao() {
		super();
	}

	public List<SearchType> all() {
		List<SearchType> result = new ArrayList();
		DBCursor cursor = this.getAll();
		DBObject doc;
		SearchType data = new SearchType();
		try {
			while (cursor.hasNext()) {
				doc = cursor.next();
				result.add(this.getSearchType(doc));
			}
		} finally {
			cursor.close();
		}
		return result;
	}

	public List<SearchType> findByProjectName(String projectName) {
		DBObject queryDoc = new BasicDBObject(Common.ConvertToString(SearchTypeEnum.ProjectName.toString(), ""), projectName);
		DBCursor cursor = this.getDbCollection().find(queryDoc);
		DBObject doc;
		List<SearchType> result = new ArrayList();
		try {
			while (cursor.hasNext()) {
				doc = cursor.next();
				result.add(this.getSearchType(doc));
			}
		} finally {
			cursor.close();
		}
		return result;
	}

	public Boolean insert(SearchType data) {
		try {
			BasicDBObject doc = this.getBasicDBObject(data);
			this.insert(doc);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Boolean update(SearchType searchTypeOrigine,
			SearchType searchTypeUpdate) {
		try {
			// BasicDBObject docUpdate = new BasicDBObject("$set",new
			// BasicDBObject().append(SearchTypeEnum.IsRead.toString(),
			// SearchTypeUpdate.getIsRead());
			BasicDBObject docUpdate = this
					.getBasicDBObjectUpdate(searchTypeUpdate);
			BasicDBObject docOrigineQuery = new BasicDBObject();
			docOrigineQuery.append(SearchTypeEnum.SearchTypeID.toString(),
					new ObjectId(searchTypeOrigine.getSearchTypeID()));

			int result = this.update(docOrigineQuery, docUpdate);
			if (result > 0)
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

	public Boolean delete(String searchTypeID) {
		try {
			BasicDBObject query = new BasicDBObject();
			query.append(SearchTypeEnum.SearchTypeID.toString(), new ObjectId(
					searchTypeID));
			int result = (int) this.remove(query);
			if (result > 0)
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

	public SearchType single(String searchTypeID) {
		try {
			DBObject doc = new BasicDBObject(Common.ConvertToString(
					SearchTypeEnum.SearchTypeID.toString(), ""), new ObjectId(
					searchTypeID));
			SearchType SearchType = this.getSearchType(this.getDbCollection()
					.findOne(doc));
			return SearchType;
		} catch (Exception e) {
			return null;
		}
	}

	public SearchType singleByDisplayNameAndProjectName(String displayName,
			String projectName) {
		try {
			DBObject queryDoc = new BasicDBObject();

			List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
			obj.add(new BasicDBObject(Common.ConvertToString(
					SearchTypeEnum.NameDisplay.toString(), ""), displayName));
			obj.add(new BasicDBObject(Common.ConvertToString(
					SearchTypeEnum.ProjectName.toString(), ""), projectName));
			queryDoc.put("$and", obj);
			SearchType searchType = this.getSearchType(this.getDbCollection()
					.findOne(queryDoc));
			return searchType;
		} catch (Exception e) {
			return null;
		}
	}

	public long count() {
		return this.getDbCollection().count();
	}

	public List<SearchType> paging(int page, int pagesize) {
		List<SearchType> result = new ArrayList<SearchType>();
		DBCursor cursor = this.getDbCollection().find()
				.skip(pagesize * (page - 1)).limit(pagesize);
		DBObject doc;
		try {
			while (cursor.hasNext()) {
				doc = cursor.next();
				result.add(this.getSearchType(doc));
			}
		} finally {
			cursor.close();
		}
		return result;
	}

	public SearchType getSearchType(DBObject doc) {
		SearchType data = new SearchType();
		data.setSearchTypeID(Common.ConvertToString(
				doc.get(SearchTypeEnum.SearchTypeID.toString()), ""));
		data.setNameDisplay(Common.ConvertToString(
				doc.get(SearchTypeEnum.NameDisplay.toString()), ""));
		data.setFieldOfCollections(this.getFieldCollection((BasicDBList) doc
				.get(SearchTypeEnum.FieldOfCollections.toString())));
		data.setDateCreate(Common.ConvertToString(
				doc.get(SearchTypeEnum.DateCreate.toString()), ""));
		data.setUserCreate(Common.ConvertToString(
				doc.get(SearchTypeEnum.UserCreate.toString()), ""));
		data.setState(Common.ConvertToString(
				doc.get(SearchTypeEnum.State.toString()), ""));
		data.setProjectName(Common.ConvertToString(
				doc.get(SearchTypeEnum.ProjectName.toString()), ""));
		data.setDescription(Common.ConvertToString(
				doc.get(SearchTypeEnum.Description.toString()), ""));
		return data;
	}

	public List<FieldCollection> getFieldCollection(BasicDBList fcs) {
		List<FieldCollection> result = new ArrayList<FieldCollection>();
		BasicDBObject[] fieldOfCollectionArr = fcs
				.toArray(new BasicDBObject[0]);
		for (BasicDBObject dbObj : fieldOfCollectionArr) {
			FieldCollection fieldCollection = new FieldCollection();
			fieldCollection.setField(Common.ConvertToString(
					dbObj.get(FieldCollectionEnum.Field.toString()), ""));
			fieldCollection.setCollection(Common.ConvertToString(
					dbObj.get(FieldCollectionEnum.Collection.toString()), ""));
			result.add(fieldCollection);
		}
		return result;
	}

	public BasicDBList setFieldCollectionToDBList(
			List<FieldCollection> fieldCollections) {
		BasicDBList result = new BasicDBList();
		for (FieldCollection fc : fieldCollections) {
			BasicDBObject obj = new BasicDBObject();
			obj.put(FieldCollectionEnum.Field.toString(), fc.getField());
			obj.put(FieldCollectionEnum.Collection.toString(),
					fc.getCollection());
			result.add(obj);
		}
		return result;
	}

	public BasicDBObject getBasicDBObject(SearchType data) {
		BasicDBObject doc = new BasicDBObject();
		if (data.getSearchTypeID() != "") {
			doc.put(SearchTypeEnum.SearchTypeID.toString(),
					data.getSearchTypeID());
		}
		doc.put(SearchTypeEnum.NameDisplay.toString(), data.getNameDisplay());
		doc.put(SearchTypeEnum.FieldOfCollections.toString(),
				this.setFieldCollectionToDBList(data.getFieldOfCollections()));
		doc.put(SearchTypeEnum.DateCreate.toString(), data.getDateCreate());
		doc.put(SearchTypeEnum.UserCreate.toString(), data.getUserCreate());
		doc.put(SearchTypeEnum.State.toString(), data.getState());
		doc.put(SearchTypeEnum.ProjectName.toString(), data.getProjectName());
		doc.put(SearchTypeEnum.Description.toString(), data.getDescription());
		return doc;
	}

	public BasicDBObject getBasicDBObjectUpdate(SearchType data) {

		BasicDBObject doc = new BasicDBObject();
		doc.append(SearchTypeEnum.NameDisplay.toString(), data.getNameDisplay());
		doc.append(SearchTypeEnum.FieldOfCollections.toString(),
				this.setFieldCollectionToDBList(data.getFieldOfCollections()));
		doc.append(SearchTypeEnum.DateCreate.toString(), data.getDateCreate());
		doc.append(SearchTypeEnum.UserCreate.toString(), data.getUserCreate());
		doc.append(SearchTypeEnum.State.toString(), data.getState());
		doc.append(SearchTypeEnum.ProjectName.toString(), data.getProjectName());
		doc.append(SearchTypeEnum.Description.toString(), data.getDescription());

		BasicDBObject bdoc = new BasicDBObject("$set", doc);
		return bdoc;
	}
}
