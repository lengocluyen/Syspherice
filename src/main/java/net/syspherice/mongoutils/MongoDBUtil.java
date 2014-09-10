package net.syspherice.mongoutils;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.syspherice.utils.Common;

public class MongoDBUtil {
	private static DB database;
	static {
		try {
			// connect to the database
			Mongo mongo = new Mongo(Config.HOST, Common.ConvertToInt(Config.PORT, 27017) );
			// get tutorials database
			database = mongo.getDB(Config.DATABASE);
			boolean auth = database.authenticate(Config.USERNAME, Config.PASSWORD.toCharArray());
		} catch (UnknownHostException ex) {
			Logger.getLogger(MongoDBUtil.class.getName()).log(Level.SEVERE,
					null, ex);
		} catch (MongoException ex) {
			Logger.getLogger(MongoDBUtil.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	// get collection from database
	public static DBCollection getCollection(String collectionName) {
		return database.getCollection(collectionName);
	}
	//create collection
	public static DBCollection createCollection(String collectioName){
		return database.createCollection(collectioName, null);
	}
	public static List<String> getAllCollections(){
		return new ArrayList<String>(database.getCollectionNames());
		
	}
}
