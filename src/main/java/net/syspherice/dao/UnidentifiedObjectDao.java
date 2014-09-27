package net.syspherice.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bson.BSONObject;
import org.bson.types.ObjectId;
import org.json.JSONObject;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;

import net.syspherice.enumeration.ContactEnum;
import net.syspherice.enumeration.ExcelDataDocEnum;
import net.syspherice.enumeration.SearchTypeEnum;
import net.syspherice.enumeration.UnidentifiedObjectEnum;
import net.syspherice.form.Contact;
import net.syspherice.form.ExcelDataDoc;
import net.syspherice.form.FieldCollection;
import net.syspherice.form.SearchType;
import net.syspherice.form.UnidentifiedObject;
import net.syspherice.utils.Common;
import net.syspherice.utils.SheetDataCell;
import net.syspherice.utils.SheetDataRow;
import net.syspherice.utils.SheetInfo;

public class UnidentifiedObjectDao extends AbstractDAO {

	public UnidentifiedObjectDao() {
		super();
	}

	public UnidentifiedObject convertDBObjectToUnidentifiedObject(DBObject obj) {
		UnidentifiedObject result = new UnidentifiedObject();
		result.setDocId(Common.ConvertToString(obj.get(UnidentifiedObjectEnum.DocID.toString()),""));
		
		result.setTextDataDocID(Common.ConvertToString(obj.get(
				UnidentifiedObjectEnum.textDataDocID.toString()),""));
		obj.toMap().remove(
				obj.get(UnidentifiedObjectEnum.DocID.toString()).toString());
		if(obj.get(UnidentifiedObjectEnum.textDataDocID.toString())!=null)
			obj.toMap().remove(
				obj.get(UnidentifiedObjectEnum.textDataDocID.toString())
						.toString());
		result.setData(obj.toMap());
		return result;

	}
	public Map<String, List<UnidentifiedObject>> singlebyID(String collectioname,String ID) {
		Map<String, List<UnidentifiedObject>> result = new HashMap<String, List<UnidentifiedObject>>();
		DBObject doc = new BasicDBObject(Common.ConvertToString(
				UnidentifiedObjectEnum.DocID.toString(), ""), new ObjectId(ID));
		List<UnidentifiedObject> data= new ArrayList<UnidentifiedObject>();
		data.add(this.convertDBObjectToUnidentifiedObject(this.getDbCollection()
				.findOne(doc)));
			result.put(collectioname, data);
		return result;
	}
	
	// Very Important
	public int insert(ExcelDataDoc excelData, SheetInfo info,
			SheetDataRow title, List<SheetDataRow> datas) {

		int count = 0;
		for (SheetDataRow sdr : datas) {

			BasicDBObject doc = new BasicDBObject();
			int i = 0;
			String idColumn = info.getColonneID();
			doc.put(UnidentifiedObjectEnum.textDataDocID.toString(),
					excelData.getTextDataDocID());
			String id = "";
			for (SheetDataCell sdc : title.getColomns()) {
				// set field identify
				if (idColumn == sdc.getCell()) {
					id = sdr.getColomns().get(i).getCell();
					doc.put(UnidentifiedObjectEnum.ID.toString(), id);
				}
				doc.put(sdc.getCell(), sdr.getColomns().get(i).getCell());
				i++;
			}
			if (this.single(id) == null) {
				this.getDbCollection().ensureIndex(new BasicDBObject(info.getColonneIndex()[0], 1), new BasicDBObject("unique", true));
				this.insert(doc);
				count++;
			}
			// collUnObj.getDbCollection().createIndex(info.getColonneIndex(),1);//create
			// index on "i", ascending
			// Handle with index:non
		}
		return count;
	}

	public DBObject single(String ID) {
		BasicDBObject whereQuery = new BasicDBObject(
				UnidentifiedObjectEnum.ID.toString(), ID);
		DBCursor db = this.getDbCollection().find(whereQuery).limit(1);
		DBObject result = null;
		try {
			while (db.hasNext()) {
				result = db.next();
			}
		} finally {
			db.close();
		}
		return result;
	}

	public int deleteBytextDocID(String excelDataDocID) {
		List<String> allCollection = DaoFactory.getAllCollections();
		for (String nameCollection : allCollection) {
			try {

				DBCollection collection = DaoFactory
						.getCollection(nameCollection);
				BasicDBObject query = new BasicDBObject();
				query.append(ExcelDataDocEnum.TextDataDocID.toString(),
						new ObjectId(excelDataDocID));
				WriteResult wResult = collection.remove(query);
				int result = wResult.getN();
				return result;
			} catch (Exception e) {
				return -1;
			}
		}
		return -1;
	}

	public List<String> getOneforSearchProperties() {
		List<String> result = new ArrayList<String>();
		DBObject dbObj = getDbCollection().findOne();

		JSONObject jsonObj = new JSONObject(dbObj.toString());
		Iterator keys = jsonObj.keys();

		while (keys.hasNext()) {
			String key = (String) keys.next();
			if (key != "_id"
					&& key != UnidentifiedObjectEnum.textDataDocID.toString())
				result.add(key);
		}
		return result;
	}

	public Map<String, List<UnidentifiedObject>> findbyField(String name,
			SearchType searchType) {
		Map<String, List<UnidentifiedObject>> result = new HashMap<String, List<UnidentifiedObject>>();
		for (FieldCollection fieldCollection : searchType
				.getFieldOfCollections()) {
			BasicDBObject query = new BasicDBObject();
			query.put(fieldCollection.getField(), java.util.regex.Pattern.compile(Common.regexForSearchUnicode(name)));
			DBCursor cursor = DaoFactory.getCollection(
					fieldCollection.getCollection()).find(query);
			List<UnidentifiedObject> collections = new ArrayList<UnidentifiedObject>();
			try {
				while (cursor.hasNext()) {
					collections
							.add(this
									.convertDBObjectToUnidentifiedObject(cursor
											.next()));
				}
			} finally {
				cursor.close();
			}
			result.put(fieldCollection.getCollection(), collections);
		}
		return result;
	}
}
