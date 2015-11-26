package net.syspherice.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import net.syspherice.enumeration.ExcelDataDocEnum;
import net.syspherice.enumeration.ExcelMetaDataEnum;
import net.syspherice.form.ExcelDataDoc;
import net.syspherice.form.ExcelMetaData;
import net.syspherice.utils.Common;

public class ExcelDataDocDao extends AbstractDAO{
	public ExcelDataDocDao(){
		super();
	}
	
	public List<ExcelDataDoc> all() {
		List<ExcelDataDoc> result = new ArrayList();
		DBCursor cursor = this.getAll();
		DBObject doc;
		ExcelDataDoc data;
		try {
			while (cursor.hasNext()) {
				doc = cursor.next();
				result.add(this.getExcelDataDoc(doc));
			}
		} finally {
			cursor.close();
		}
		return result;
	}

	public Boolean insert(ExcelDataDoc data) {
		try {
			BasicDBObject doc = this.getBasicDBObject(data);
			this.insert(doc);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Boolean update(ExcelDataDoc excelDataDocOrigine, ExcelDataDoc excelDataDocUpdate) {
		try {
			//BasicDBObject docUpdate = new BasicDBObject("$set",new BasicDBObject().append(ExcelDataDocEnum.IsRead.toString(), ExcelDataDocUpdate.getIsRead());
			BasicDBObject docUpdate= this.getBasicDBObjectUpdate(excelDataDocUpdate);
			BasicDBObject docOrigineQuery = new BasicDBObject();
			docOrigineQuery.append(ExcelDataDocEnum.TextDataDocID.toString(),new ObjectId(excelDataDocOrigine.getTextDataDocID()));
			
			int result = this.update(docOrigineQuery, docUpdate);
			if(result>0)
			return true;
			else return false;
		} catch (Exception e) {
			return false;
		}
	}
	
	public Boolean delete(String excelDataDocID) {
		try {
			BasicDBObject query = new BasicDBObject();
			query.append(ExcelDataDocEnum.TextDataDocID.toString(), new ObjectId(excelDataDocID));
			int result = (int) this.remove(query);
			if(result>0)
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}
	
	
	
	public ExcelDataDoc single(String excelDataDocID) {
		try {
			DBObject doc = new BasicDBObject(Common.ConvertToString(ExcelDataDocEnum.TextDataDocID.toString(), ""), new ObjectId(excelDataDocID));
			ExcelDataDoc ExcelDataDoc = this.getExcelDataDoc(this.getDbCollection().findOne(doc));
			return ExcelDataDoc;
		} catch (Exception e) {
			return null;
		}
	}
	public ExcelDataDoc singleByprojectName(String nameProject){
		try {
			DBObject doc = new BasicDBObject(Common.ConvertToString(ExcelDataDocEnum.ProjectName.toString(), ""), nameProject);
			ExcelDataDoc ExcelDataDoc = this.getExcelDataDoc(this.getDbCollection().findOne(doc));
			return ExcelDataDoc;
		} catch (Exception e) {
			return null;
		}
	}
	
	public long count(){
		return this.getDbCollection().count();
	}
	public List<ExcelDataDoc> paging(int page, int pagesize){
		List<ExcelDataDoc> result = new ArrayList<ExcelDataDoc>();
		DBCursor cursor = this.getDbCollection().find().skip(pagesize*(page-1)).limit(pagesize);
		DBObject doc;
		try {
			while (cursor.hasNext()) {
				doc = cursor.next();
				result.add(this.getExcelDataDoc(doc));
			}
		} finally {
			cursor.close();
		}
		return result;
	}
	
	
	public ExcelDataDoc getExcelDataDoc(DBObject doc) {
		ExcelDataDoc data = new ExcelDataDoc();
		data.setTextDataDocID(Common.ConvertToString(doc.get(ExcelDataDocEnum.TextDataDocID.toString()),""));
		data.setProjectname(Common.ConvertToString(doc.get(ExcelDataDocEnum.ProjectName.toString()), ""));
		data.setFileUrl(Common.ConvertToString(doc.get(ExcelDataDocEnum.FileUrl.toString()), ""));
		data.setDateImport(Common.ConvertToString(doc.get(ExcelDataDocEnum.DateImport.toString()), ""));
		data.setUserImport(Common.ConvertToString(doc.get(ExcelDataDocEnum.UserImport.toString()), ""));
		data.setDescription(Common.ConvertToString(doc.get(ExcelDataDocEnum.Description.toString()), ""));
		ExcelMetaData meta = new ExcelMetaData();
		DBObject metaDoc =(DBObject) doc.get(ExcelDataDocEnum.Metadata.toString());
		
		meta.setTitle(Common.ConvertToString(metaDoc.get(ExcelMetaDataEnum.Title.toString()), ""));
		meta.setAuthor(Common.ConvertToString(metaDoc.get(ExcelMetaDataEnum.Author.toString()),""));
		meta.setComments(Common.ConvertToString(metaDoc.get(ExcelMetaDataEnum.Comments.toString()),""));
		meta.setKeywords(Common.ConvertToString(metaDoc.get(ExcelMetaDataEnum.Keywords.toString()),""));
		meta.setCreateDateTime(Common.ConvertToString(metaDoc.get(ExcelMetaDataEnum.CreateDateTime.toString()),""));
		meta.setLastSaveDateTime(Common.ConvertToString(metaDoc.get(ExcelMetaDataEnum.LastSaveDateTime.toString()),""));
		data.setMetadata(meta);
		return data;
	}

	public BasicDBObject getBasicDBObject(ExcelDataDoc data) {
		BasicDBObject doc = new BasicDBObject();
		if(data.getTextDataDocID()!=""){
			doc.put(ExcelDataDocEnum.TextDataDocID.toString(), data.getTextDataDocID());
		}	
		doc.put(ExcelDataDocEnum.ProjectName.toString(), data.getProjectname());
		doc.put(ExcelDataDocEnum.FileUrl.toString(), data.getFileUrl());
		doc.put(ExcelDataDocEnum.DateImport.toString(), data.getDateImport());
		doc.put(ExcelDataDocEnum.UserImport.toString(), data.getUserImport());
		doc.put(ExcelDataDocEnum.Description.toString(),data.getDescription());
		BasicDBObject meta = new BasicDBObject();
		meta.put(ExcelMetaDataEnum.Title.toString(), data.getMetadata().getTitle());
		meta.put(ExcelMetaDataEnum.Subject.toString(), data.getMetadata().getSubjet());
		meta.put(ExcelMetaDataEnum.Author.toString(), data.getMetadata().getAuthor());
		meta.put(ExcelMetaDataEnum.Comments.toString(), data.getMetadata().getComments());
		meta.put(ExcelMetaDataEnum.Keywords.toString(), data.getMetadata().getKeywords());
		meta.put(ExcelMetaDataEnum.CreateDateTime.toString(), data.getMetadata().getCreateDateTime());
		meta.put(ExcelMetaDataEnum.LastSaveDateTime.toString(), data.getMetadata().getLastSaveDateTime());
		doc.put(ExcelDataDocEnum.Metadata.toString(),meta);
		return doc;
	}
	public BasicDBObject getBasicDBObjectUpdate(ExcelDataDoc data) {
		
		BasicDBObject doc = new BasicDBObject();
		doc.put(ExcelDataDocEnum.ProjectName.toString(), data.getProjectname());
		doc.put(ExcelDataDocEnum.FileUrl.toString(), data.getFileUrl());
		doc.put(ExcelDataDocEnum.DateImport.toString(), data.getDateImport());
		doc.put(ExcelDataDocEnum.UserImport.toString(), data.getUserImport());
		doc.put(ExcelDataDocEnum.Description.toString(),data.getDescription());
		BasicDBObject meta = new BasicDBObject();
		meta.put(ExcelMetaDataEnum.Title.toString(), data.getMetadata().getTitle());
		meta.put(ExcelMetaDataEnum.Subject.toString(), data.getMetadata().getSubjet());
		meta.put(ExcelMetaDataEnum.Author.toString(), data.getMetadata().getAuthor());
		meta.put(ExcelMetaDataEnum.Comments.toString(), data.getMetadata().getComments());
		meta.put(ExcelMetaDataEnum.Keywords.toString(), data.getMetadata().getKeywords());
		meta.put(ExcelMetaDataEnum.CreateDateTime.toString(), data.getMetadata().getCreateDateTime());
		meta.put(ExcelMetaDataEnum.LastSaveDateTime.toString(), data.getMetadata().getLastSaveDateTime());
		doc.put(ExcelDataDocEnum.Metadata.toString(),meta);
		BasicDBObject bdoc = new BasicDBObject("$set",doc);
		return bdoc;
	}
}
