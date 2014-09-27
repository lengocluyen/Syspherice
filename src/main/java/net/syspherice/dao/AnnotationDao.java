package net.syspherice.dao;

import java.util.ArrayList;
import java.util.List;

import net.syspherice.enumeration.AnnotationEnum;
import net.syspherice.form.Annotation;
import net.syspherice.utils.Common;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class AnnotationDao  extends AbstractDAO {
	public AnnotationDao() {
		super();
	}

	public List<Annotation> all() {
		List<Annotation> result = new ArrayList();
		DBCursor cursor = this.getAll();
		DBObject doc;
		Annotation data;
		try {
			while (cursor.hasNext()) {
				doc = cursor.next();
				result.add(this.getAnnotation(doc));
			}
		} finally {
			cursor.close();
		}
		return result;
	}

	public Boolean insert(Annotation data) {
		try {
			BasicDBObject doc = this.getBasicDBObject(data);
			this.insert(doc);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Boolean update(Annotation annotationOrigine, Annotation annotationUpdate) {
		try {
			BasicDBObject docUpdate = this
					.getBasicDBObjectUpdate(annotationUpdate);
			BasicDBObject docOrigineQuery = new BasicDBObject();
			docOrigineQuery.append(AnnotationEnum.AnnotationID.toString(),
					annotationOrigine.getAnnotationID());

			int result = this.update(docOrigineQuery, docUpdate);
			if (result > 0)
				return true;
			else
				return false;

		} catch (Exception e) {
			return false;
		}
	}

	public Boolean delete(String annotationID) {
		try {
			BasicDBObject query = new BasicDBObject();
			query.append(Common.ConvertToString(AnnotationEnum.AnnotationID.toString(), ""),annotationID);
			int result = (int) this.remove(query);
			if(result>0)
				return true;
			else
				return false;
			
		} catch (Exception e) {
			return false;
		}
	}

	public Annotation single(String annotationID) {
		try {
			
			DBObject doc = new BasicDBObject(Common.ConvertToString(AnnotationEnum.AnnotationID.toString(), ""),
					annotationID);
			Annotation account = this.getAnnotation(this.getDbCollection().findOne(doc));
			return account;
			} catch (Exception e) {
			return null;
		}
	}
	public List<Annotation> findByObjectID(String objectID){
		DBObject queryDoc = new BasicDBObject(Common.ConvertToString(AnnotationEnum.ObjectID.toString(), ""), objectID);
		DBCursor cursor = this.getDbCollection().find(queryDoc);
		DBObject doc;
		List<Annotation> result = new ArrayList();
		try {
			while (cursor.hasNext()) {
				doc = cursor.next();
				result.add(this.getAnnotation(doc));
			}
		} finally {
			cursor.close();
		}
		return result;
	}
	

	public long count() {
		return this.getDbCollection().count();
	}

	public List<Annotation> paging(int page, int pagesize) {
		List<Annotation> result = new ArrayList<Annotation>();
		DBCursor cursor = this.getDbCollection().find()
				.skip(pagesize * (page - 1)).limit(pagesize);
		DBObject doc;
		try {
			while (cursor.hasNext()) {
				doc = cursor.next();
				result.add(this.getAnnotation(doc));
			}
		} finally {
			cursor.close();
		}
		return result;
	}

	public Annotation getAnnotation(DBObject doc) {
		Annotation data = new Annotation();
		data.setAnnotationID(Common.ConvertToString(
				doc.get(AnnotationEnum.AnnotationID.toString()), ""));
		data.setObjectID(Common.ConvertToString(
				doc.get(AnnotationEnum.ObjectID.toString()), ""));
		data.setDateCreate(Common.ConvertToString(
				doc.get(AnnotationEnum.DateCreate.toString()), ""));
		data.setDateModify(Common.ConvertToString(
				doc.get(AnnotationEnum.DateModify.toString()), ""));
		data.setUserCreate(Common.ConvertToString(
				doc.get(AnnotationEnum.UserCreate.toString()), ""));
		data.setContent(Common.ConvertToString(
				doc.get(AnnotationEnum.Content.toString()), ""));
		data.setState(Common.ConvertToString(
				doc.get(AnnotationEnum.State.toString()), ""));
		return data;
	}

	public BasicDBObject getBasicDBObject(Annotation data) {
		BasicDBObject doc = new BasicDBObject();
		doc.put(AnnotationEnum.AnnotationID.toString(), data.getAnnotationID());
		doc.put(AnnotationEnum.ObjectID.toString(), data.getObjectID());
		doc.put(AnnotationEnum.DateCreate.toString(), data.getDateCreate());
		doc.put(AnnotationEnum.DateModify.toString(), data.getDateModify());
		doc.put(AnnotationEnum.UserCreate.toString(), data.getUserCreate());
		doc.put(AnnotationEnum.Content.toString(), data.getContent());
		doc.put(AnnotationEnum.State.toString(), data.getState());
		return doc;
	}

	public BasicDBObject getBasicDBObjectUpdate(Annotation data) {

		BasicDBObject doc = new BasicDBObject();
		//doc.put(AnnotationEnum.AnnotationID.toString(), data.getAnnotationID());
		doc.put(AnnotationEnum.ObjectID.toString(), data.getObjectID());
		doc.put(AnnotationEnum.DateCreate.toString(), data.getDateCreate());
		doc.put(AnnotationEnum.DateModify.toString(), data.getDateModify());
		doc.put(AnnotationEnum.UserCreate.toString(), data.getUserCreate());
		doc.put(AnnotationEnum.Content.toString(), data.getContent());
		doc.put(AnnotationEnum.State.toString(), data.getState());
		BasicDBObject bdoc = new BasicDBObject("$set", doc);
		return bdoc;
	}
}
