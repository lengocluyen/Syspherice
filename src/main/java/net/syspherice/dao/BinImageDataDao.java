package net.syspherice.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.syspherice.enumeration.ImageDataEnum;
import net.syspherice.form.BinImageData;
import net.syspherice.mongoutils.ImageHandle;
import net.syspherice.utils.Common;
import net.syspherice.utils.Config;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class BinImageDataDao extends AbstractDAO{
	public BinImageDataDao() {
		super();
	}

	public List<BinImageData> all() {
		List<BinImageData> result = new ArrayList();
		DBCursor cursor = this.getAll();
		DBObject doc;
		BinImageData data;
		BinImageData bid = new BinImageData();
		try {
			while (cursor.hasNext()) {
				doc = cursor.next();
				result.add(this.UpdateUrlTemp(bid));
			}
		} finally {
			cursor.close();
		}
		return result;
	}

	public Boolean insert(BinImageData data) {
		try {
			BasicDBObject doc = this.getBasicDBObject(data);
			ImageHandle.SaveImage(this.getDbCollection().getDB(), data.getUrl(), data.getName());
			this.insert(doc);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Boolean update(BinImageData imageDataOrigine, BinImageData imageDataUpdate) {
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
			BinImageData bid = this.single(imageDataID);
			int result = (int) this.remove(query);
			ImageHandle.DeleteImage(bid.getName(), this.getDbCollection().getDB());
			if(result>0)
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

	public BinImageData single(String imageDataID) {
		try {
			DBObject doc = new BasicDBObject(Common.ConvertToString(ImageDataEnum.ImageID.toString(), ""), new ObjectId(imageDataID));
			BinImageData imageData = this.getImageData(this.getDbCollection().findOne(doc));
			
			return this.UpdateUrlTemp(imageData);
		} catch (Exception e) {
			return null;
		}
	}

	public long count(){
		return this.getDbCollection().count();
	}
	public List<BinImageData> paging(int page, int pagesize){
		List<BinImageData> result = new ArrayList<BinImageData>();
		DBCursor cursor = this.getDbCollection().find().skip(pagesize*(page-1)).limit(pagesize);
		DBObject doc;
		BinImageData bid = new BinImageData();
		try {
			while (cursor.hasNext()) {
				doc = cursor.next();
				bid = this.getImageData(doc);
				result.add(this.UpdateUrlTemp(bid));
			}
		} finally {
			cursor.close();
		}
		return result;
	}
	public BinImageData UpdateUrlTemp(BinImageData bid){
		BinImageData result = bid;
		if(bid.getUrlTemps()==""||bid.getUrlTemps()==null){
			String physicalPath = Config.TEMPS_PHYSICAL_PATH_IMAGE +"/"+ bid.getName();
			File imageTemps = ImageHandle.GetImage(this.getDbCollection().getDB(), physicalPath, bid.getName());
			
			if(imageTemps.exists()){
				String linkWeb = Config.TEMPS_WEB_PATH_IMAGE+"/"+bid.getName();
				bid.setUrlTemps(linkWeb);
				this.update(bid, result);
				return result;
			}
			else
				return bid;
		}
		return bid;
	}
	public List<BinImageData> findByPlantID(String id){
		DBObject queryDoc = new BasicDBObject(Common.ConvertToString(ImageDataEnum.PlantID.toString(), ""), id);
		DBCursor cursor = this.getDbCollection().find(queryDoc);
		DBObject doc;
		List<BinImageData> result = new ArrayList();
		BinImageData bid = new BinImageData();
		try {
			while (cursor.hasNext()) {
				doc = cursor.next();
				bid = this.getImageData(doc);
				result.add(this.UpdateUrlTemp(bid));
			}
		} finally {
			cursor.close();
		}
		return result;
	}
	
	public BinImageData getImageData(DBObject doc) {
		BinImageData data = new BinImageData();
		data.setImageID(Common.ConvertToString(
				doc.get(ImageDataEnum.ImageID.toString()), ""));
		data.setName(Common.ConvertToString(
				doc.get(ImageDataEnum.Name.toString()), ""));
		data.setUrl(Common.ConvertToString(
				doc.get(ImageDataEnum.Url.toString()), ""));
		data.setUrlTemps(Common.ConvertToString(
				doc.get(ImageDataEnum.UrlTemps.toString()), ""));
		
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
	public BasicDBObject getBasicDBObject(BinImageData data) {
		BasicDBObject doc = new BasicDBObject();
		if(data.getImageID()!=""){
			doc.put(ImageDataEnum.ImageID.toString(), data.getImageID());
		}
		doc.put(ImageDataEnum.Name.toString(), data.getName());
		doc.put(ImageDataEnum.Url.toString(), data.getUrl());
		doc.put(ImageDataEnum.UrlTemps.toString(), data.getUrlTemps());
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
	public BasicDBObject getBasicDBObjectUpdate(BinImageData data) {
		
		BasicDBObject doc = new BasicDBObject();
		doc.append(ImageDataEnum.Name.toString(), data.getName());
		doc.append(ImageDataEnum.Url.toString(), data.getUrl());
		doc.append(ImageDataEnum.UrlTemps.toString(), data.getUrlTemps());
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
