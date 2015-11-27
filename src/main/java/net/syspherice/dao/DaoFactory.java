package net.syspherice.dao;

import java.util.List;

import com.mongodb.DBCollection;

import net.syspherice.form.Account;
import net.syspherice.form.Annotation;
import net.syspherice.form.BinImageData;
import net.syspherice.form.BinXmlData;
import net.syspherice.form.Contact;
import net.syspherice.form.ExcelDataDoc;
import net.syspherice.form.ItemTag;
import net.syspherice.form.SearchType;
import net.syspherice.form.Tags;
import net.syspherice.mongoutils.MongoDBUtil;


public class DaoFactory {

	public static TagsDao getTagDao(){
		return (TagsDao) getDAOByClassAndName(TagsDao.class, Tags.class.getSimpleName());
	}
	
	public static ItemTagDao getItemTagDao(){
		return (ItemTagDao) getDAOByClassAndName(ItemTagDao.class, ItemTag.class.getSimpleName());
	}
	
	public static List<String> getAllCollections(){
		return MongoDBUtil.getAllCollections();
	} 
	public static BinImageDataDao getBinImageDataDao(){
		return (BinImageDataDao) getDAOByClassAndName(BinImageDataDao.class, BinImageData.class.getSimpleName());
	}
	
	public static BinXmlDataDao getBinXmlDataDao(){
		return (BinXmlDataDao) getDAOByClassAndName(BinXmlDataDao.class, BinXmlData.class.getSimpleName());
	}
	
	public static FileObjectDao getFileObjectDao(String nameCollection){
		return (FileObjectDao) getDAOByClassAndName(FileObjectDao.class, nameCollection);
	}
	public static SearchTypeDao getSearchTypeDao(){
		return (SearchTypeDao) getDAOByClassAndName(SearchTypeDao.class, SearchType.class.getSimpleName());
	}
	public static ExcelDataDocDao getExcelDataDocDao() {
		return (ExcelDataDocDao) getDAOByClassAndName(ExcelDataDocDao.class, ExcelDataDoc.class.getSimpleName());
	}
	
	public static AccountDao getAccountDao(){
		return (AccountDao) getDAOByClassAndName(AccountDao.class, Account.class.getSimpleName());
	}
	public static AnnotationDao getAnnotationDao(){
		return (AnnotationDao) getDAOByClassAndName(AnnotationDao.class, Annotation.class.getSimpleName());
	}
	public static ContactDao getContactDao(){
		return  (ContactDao) getDAOByClassAndName(ContactDao.class, Contact.class.getSimpleName());
	}
	public static UnidentifiedObjectDao getUnidentifiedObjectDao(String nameCollection){
		return (UnidentifiedObjectDao) getDAOByClassAndName(UnidentifiedObjectDao.class, nameCollection);
	}
	
	public static AbstractDAO getDAOByClassAndName(Class c, String name) {
		try {
			DBCollection collection = getCollection(name);

			AbstractDAO d = (AbstractDAO) c.newInstance();
			d.setDbCollection(collection);
			return d;
		} catch (InstantiationException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static DBCollection getCollection(String name) {
		return MongoDBUtil.getCollection(name);
	}
	public static DBCollection createCollection(String name){
		return MongoDBUtil.createCollection(name);
	}
}
