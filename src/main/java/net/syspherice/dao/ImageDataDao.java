package net.syspherice.dao;

import java.util.ArrayList;
import java.util.List;

import net.syspherice.enumeration.ImageDataEnum;
import net.syspherice.enumeration.SearchTypeEnum;
import net.syspherice.form.ImageData;
import net.syspherice.form.SearchType;
import net.syspherice.utils.Common;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class ImageDataDao extends AbstractDAO{
	public ImageDataDao() {
		super();
	}

	public List<ImageData> all() {
		List<ImageData> result = new ArrayList();
		DBCursor cursor = this.getAll();
		DBObject doc;
		ImageData data;
		try {
			while (cursor.hasNext()) {
				doc = cursor.next();
				result.add(this.getImageData(doc));
			}
		} finally {
			cursor.close();
		}
		return result;
	}

	public Boolean insert(ImageData data) {
		try {
			BasicDBObject doc = this.getBasicDBObject(data);
			this.insert(doc);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Boolean update(ImageData imageDataOrigine, ImageData imageDataUpdate) {
		try {
			//BasicDBObject docUpdate = new BasicDBObject("$set",new BasicDBObject().append(ContactEnum.IsRead.toString(), contactUpdate.getIsRead());
			BasicDBObject docUpdate= this.getBasicDBObjectUpdate(imageDataUpdate);
			BasicDBObject docOrigineQuery = new BasicDBObject();
			docOrigineQuery.append(ImageDataEnum.ImageID.toString(),new ObjectId(imageDataOrigine.getImageID()));
			
			int result = this.update(docOrigineQuery, docUpdate);
			if(result>0)
			return true;
			else return false;
		} catch (Exception e) {
			return false;
		}
	}

	public Boolean delete(String imageDataID) {
		try {
			BasicDBObject query = new BasicDBObject();
			query.append(ImageDataEnum.ImageID.toString(), new ObjectId(imageDataID));
			int result = (int) this.remove(query);
			if(result>0)
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

	public ImageData single(String imageDataID) {
		try {
			DBObject doc = new BasicDBObject(Common.ConvertToString(ImageDataEnum.ImageID.toString(), ""), new ObjectId(imageDataID));
			ImageData imageData = this.getImageData(this.getDbCollection().findOne(doc));
			return imageData;
		} catch (Exception e) {
			return null;
		}
	}

	public long count(){
		return this.getDbCollection().count();
	}
	public List<ImageData> paging(int page, int pagesize){
		List<ImageData> result = new ArrayList<ImageData>();
		DBCursor cursor = this.getDbCollection().find().skip(pagesize*(page-1)).limit(pagesize);
		DBObject doc;
		try {
			while (cursor.hasNext()) {
				doc = cursor.next();
				result.add(this.getImageData(doc));
			}
		} finally {
			cursor.close();
		}
		return result;
	}
	
	public List<ImageData> findByPlantID(String id){
		DBObject queryDoc = new BasicDBObject(Common.ConvertToString(ImageDataEnum.PlantID.toString(), ""), id);
		DBCursor cursor = this.getDbCollection().find(queryDoc);
		DBObject doc;
		List<ImageData> result = new ArrayList();
		try {
			while (cursor.hasNext()) {
				doc = cursor.next();
				result.add(this.getImageData(doc));
			}
		} finally {
			cursor.close();
		}
		return result;
	}
	
	public ImageData getImageData(DBObject doc) {
		ImageData data = new ImageData();
		data.setImageID(Common.ConvertToString(
				doc.get(ImageDataEnum.ImageID.toString()), ""));
		data.setName(Common.ConvertToString(
				doc.get(ImageDataEnum.Name.toString()), ""));
		data.setUrl(Common.ConvertToString(
				doc.get(ImageDataEnum.Url.toString()), ""));
		data.setPlantID(Common.ConvertToString(
				doc.get(ImageDataEnum.PlantID.toString()), ""));
		data.setSowing_nb(Common.ConvertToString(
				doc.get(ImageDataEnum.Sowing_nb.toString()), ""));
		data.setRepet_nb(Common.ConvertToString(
				doc.get(ImageDataEnum.Repet_nb.toString()), ""));
		data.setPlant_nb(Common.ConvertToString(
				doc.get(ImageDataEnum.Plant_nb.toString()), ""));
		data.setPanicle_nb(Common.ConvertToString(
				doc.get(ImageDataEnum.Panicle_nb.toString()), ""));
	
		data.setTypeOfImageData(Common.ConvertToString(
				doc.get(ImageDataEnum.TypeOfImageData.toString()), ""));
		data.setTypeOfImageData(Common.ConvertToString(
				doc.get(ImageDataEnum.Order.toString()), ""));
	
		
		
		data.setDateImport(Common.ConvertToString(
				doc.get(ImageDataEnum.DateImport.toString()), ""));
		data.setUserImport(Common.ConvertToString(
				doc.get(ImageDataEnum.UserImport.toString()), ""));
			return data;
	}
	public BasicDBObject getBasicDBObject(ImageData data) {
		BasicDBObject doc = new BasicDBObject();
		if(data.getImageID()!=""){
			doc.put(ImageDataEnum.ImageID.toString(), data.getImageID());
		}
		doc.put(ImageDataEnum.Name.toString(), data.getName());
		doc.put(ImageDataEnum.Url.toString(), data.getUrl());
		doc.put(ImageDataEnum.PlantID.toString(), data.getPlantID());
		doc.put(ImageDataEnum.Sowing_nb.toString(), data.getSowing_nb());
		doc.put(ImageDataEnum.Repet_nb.toString(), data.getRepet_nb());
		doc.put(ImageDataEnum.Plant_nb.toString(), data.getPlant_nb());
		doc.put(ImageDataEnum.Panicle_nb.toString(), data.getPanicle_nb());
		doc.put(ImageDataEnum.TypeOfImageData.toString(), data.getTypeOfImageData());
		doc.put(ImageDataEnum.Order.toString(), data.getOrder());
		doc.put(ImageDataEnum.DateImport.toString(), data.getDateImport());
		doc.put(ImageDataEnum.UserImport.toString(), data.getUserImport());
		return doc;
	}
	public BasicDBObject getBasicDBObjectUpdate(ImageData data) {
		
		BasicDBObject doc = new BasicDBObject();
		doc.append(ImageDataEnum.Name.toString(), data.getName());
		doc.append(ImageDataEnum.Url.toString(), data.getUrl());
		doc.append(ImageDataEnum.PlantID.toString(), data.getPlantID());
		doc.append(ImageDataEnum.Sowing_nb.toString(), data.getSowing_nb());
		doc.append(ImageDataEnum.Repet_nb.toString(), data.getRepet_nb());
		doc.append(ImageDataEnum.Plant_nb.toString(), data.getPlant_nb());
		doc.append(ImageDataEnum.Panicle_nb.toString(), data.getPanicle_nb());
		doc.append(ImageDataEnum.TypeOfImageData.toString(), data.getTypeOfImageData());
		doc.append(ImageDataEnum.Order.toString(), data.getOrder());
		doc.append(ImageDataEnum.DateImport.toString(), data.getDateImport());
		doc.append(ImageDataEnum.UserImport.toString(), data.getUserImport());
		BasicDBObject bdoc = new BasicDBObject("$set",doc);
		return bdoc;
	}
}
