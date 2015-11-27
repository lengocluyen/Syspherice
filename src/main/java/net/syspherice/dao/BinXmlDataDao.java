package net.syspherice.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.syspherice.enumeration.BinXmlDataEnum;
import net.syspherice.enumeration.ImageDataEnum;
import net.syspherice.form.BinXmlData;
import net.syspherice.mongoutils.XmlHandle;
import net.syspherice.utils.Common;
import net.syspherice.utils.Config;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class BinXmlDataDao extends AbstractDAO{
	public BinXmlDataDao() {
		super();
	}

	public List<BinXmlData> all() {
		List<BinXmlData> result = new ArrayList();
		DBCursor cursor = this.getAll();
		DBObject doc;
		BinXmlData data;
		BinXmlData bid = new BinXmlData();
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

	public Boolean insert(BinXmlData data) {
		try {
			BasicDBObject doc = this.getBasicDBObject(data);
			XmlHandle.SaveXml(this.getDbCollection().getDB(), data.getUrl(), data.getName());
			this.insert(doc);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Boolean update(BinXmlData dataOrigine, BinXmlData dataUpdate) {
		try {
			//BasicDBObject docUpdate = new BasicDBObject("$set",new BasicDBObject().append(ContactEnum.IsRead.toString(), contactUpdate.getIsRead());
			BasicDBObject docUpdate= this.getBasicDBObjectUpdate(dataUpdate);
			BasicDBObject docOrigineQuery = new BasicDBObject();
			docOrigineQuery.append(BinXmlDataEnum.XmlID.toString(),new ObjectId(dataOrigine.getXmlID()));
			
			int result = this.update(docOrigineQuery, docUpdate);
			if(result>0)
			return true;
			else return false;
		} catch (Exception e) {
			return false;
		}
	}

	public Boolean delete(String xmlDataID) {
		try {
			BasicDBObject query = new BasicDBObject();
			query.append(BinXmlDataEnum.XmlID.toString(), new ObjectId(xmlDataID));
			BinXmlData bid = this.single(xmlDataID);
			int result = (int) this.remove(query);
			XmlHandle.DeleteXml(bid.getName(), this.getDbCollection().getDB());
			if(result>0)
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

	public BinXmlData single(String xmlDataID) {
		try {
			DBObject doc = new BasicDBObject(Common.ConvertToString(BinXmlDataEnum.XmlID.toString(), ""), new ObjectId(xmlDataID));
			BinXmlData xmlData = this.getXmlData(this.getDbCollection().findOne(doc));
			
			return this.UpdateUrlTemp(xmlData);
		} catch (Exception e) {
			return null;
		}
	}

	public long count(){
		return this.getDbCollection().count();
	}
	public List<BinXmlData> paging(int page, int pagesize){
		List<BinXmlData> result = new ArrayList<BinXmlData>();
		DBCursor cursor = this.getDbCollection().find().skip(pagesize*(page-1)).limit(pagesize);
		DBObject doc;
		BinXmlData bid = new BinXmlData();
		try {
			while (cursor.hasNext()) {
				doc = cursor.next();
				bid = this.getXmlData(doc);
				result.add(this.UpdateUrlTemp(bid));
			}
		} finally {
			cursor.close();
		}
		return result;
	}
	public BinXmlData UpdateUrlTemp(BinXmlData bid){
		BinXmlData result = bid;
		if(bid.getUrlTemps()==""||bid.getUrlTemps()==null){
			String physicalPath = Config.TEMPS_PHYSICAL_PATH_IMAGE +"/"+ bid.getName();
			File imageTemps = XmlHandle.GetXml(this.getDbCollection().getDB(), physicalPath, bid.getName());
			
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
	public List<BinXmlData> findByPlantID(String id){
		DBObject queryDoc = new BasicDBObject(Common.ConvertToString(ImageDataEnum.PlantID.toString(), ""), id);
		DBCursor cursor = this.getDbCollection().find(queryDoc);
		DBObject doc;
		List<BinXmlData> result = new ArrayList();
		BinXmlData bid = new BinXmlData();
		try {
			while (cursor.hasNext()) {
				doc = cursor.next();
				bid = this.getXmlData(doc);
				result.add(this.UpdateUrlTemp(bid));
			}
		} finally {
			cursor.close();
		}
		return result;
	}
	
	public BinXmlData getXmlData(DBObject doc) {
		BinXmlData data = new BinXmlData();
		data.setxmlID(Common.ConvertToString(
				doc.get(BinXmlDataEnum.XmlID.toString()), ""));
		data.setName(Common.ConvertToString(
				doc.get(BinXmlDataEnum.Name.toString()), ""));
		data.setUrl(Common.ConvertToString(
				doc.get(BinXmlDataEnum.Url.toString()), ""));
		data.setUrlTemps(Common.ConvertToString(
				doc.get(BinXmlDataEnum.UrlTemps.toString()), ""));
		
		data.setPlantID(Common.ConvertToString(
				doc.get(BinXmlDataEnum.PlantID.toString()), ""));
		data.setSowing_nb(Common.ConvertToString(
				doc.get(BinXmlDataEnum.Sowing_nb.toString()), ""));
		data.setRepet_nb(Common.ConvertToString(
				doc.get(BinXmlDataEnum.Repet_nb.toString()), ""));
		data.setPlant_nb(Common.ConvertToString(
				doc.get(BinXmlDataEnum.Plant_nb.toString()), ""));
		data.setPanicle_nb(Common.ConvertToString(
				doc.get(BinXmlDataEnum.Panicle_nb.toString()), ""));

	
		
		
		data.setDateImport(Common.ConvertToString(
				doc.get(BinXmlDataEnum.DateImport.toString()), ""));
		data.setUserImport(Common.ConvertToString(
				doc.get(BinXmlDataEnum.UserImport.toString()), ""));
			return data;
	}
	public BasicDBObject getBasicDBObject(BinXmlData data) {
		BasicDBObject doc = new BasicDBObject();
		if(data.getXmlID()!=""){
			doc.put(BinXmlDataEnum.XmlID.toString(), data.getXmlID());
		}
		doc.put(BinXmlDataEnum.Name.toString(), data.getName());
		doc.put(BinXmlDataEnum.Url.toString(), data.getUrl());
		doc.put(BinXmlDataEnum.UrlTemps.toString(), data.getUrlTemps());
		doc.put(BinXmlDataEnum.PlantID.toString(), data.getPlantID());
		doc.put(BinXmlDataEnum.Sowing_nb.toString(), data.getSowing_nb());
		doc.put(BinXmlDataEnum.Repet_nb.toString(), data.getRepet_nb());
		doc.put(BinXmlDataEnum.Plant_nb.toString(), data.getPlant_nb());
		doc.put(BinXmlDataEnum.Panicle_nb.toString(), data.getPanicle_nb());
		
		doc.put(BinXmlDataEnum.DateImport.toString(), data.getDateImport());
		doc.put(BinXmlDataEnum.UserImport.toString(), data.getUserImport());
		return doc;
	}
	public BasicDBObject getBasicDBObjectUpdate(BinXmlData data) {
		
		BasicDBObject doc = new BasicDBObject();
		doc.append(BinXmlDataEnum.Name.toString(), data.getName());
		doc.append(BinXmlDataEnum.Url.toString(), data.getUrl());
		doc.append(BinXmlDataEnum.UrlTemps.toString(), data.getUrlTemps());
		doc.append(BinXmlDataEnum.PlantID.toString(), data.getPlantID());
		doc.append(BinXmlDataEnum.Sowing_nb.toString(), data.getSowing_nb());
		doc.append(BinXmlDataEnum.Repet_nb.toString(), data.getRepet_nb());
		doc.append(BinXmlDataEnum.Plant_nb.toString(), data.getPlant_nb());
		doc.append(BinXmlDataEnum.Panicle_nb.toString(), data.getPanicle_nb());
		doc.append(BinXmlDataEnum.DateImport.toString(), data.getDateImport());
		doc.append(BinXmlDataEnum.UserImport.toString(), data.getUserImport());
		BasicDBObject bdoc = new BasicDBObject("$set",doc);
		return bdoc;
	}
}
