package net.syspherice.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;


public  class AbstractDAO {
    
    private DBCollection dbCollection;
    public AbstractDAO() {
    }

    /**
     * @return the dbCollectionl
     */
    public DBCollection getDbCollection() {
        return dbCollection;
    }

    /**
     * @param dbCollectionl the dbCollectionl to set
     */
    public void setDbCollection(DBCollection dbCollection) {
        this.dbCollection = dbCollection;
    }
    public  DBCursor getAll(){
        DBCursor find = dbCollection.find();
        return find;
    }
    public int insert(BasicDBObject dbObject){
        return dbCollection.insert(dbObject).getN();
    }
    public int remove(BasicDBObject dbObject){
        return dbCollection.remove(dbObject).getN();
    }
    public int update(BasicDBObject old,BasicDBObject dbObject){
        return dbCollection.update(old, dbObject).getN();
    }
    public DBObject getByID(int id){
        DBObject returnObject=null;
        BasicDBObject query=new BasicDBObject();
        query.put("id", new Integer(id));
        DBCursor find = dbCollection.find(query);
        while(find.hasNext()){
            returnObject=find.next();
            
        }
        return returnObject;
    }
}