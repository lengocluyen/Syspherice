package net.syspherice.dao;

import java.util.Calendar;

import net.syspherice.enumeration.FileObjectEnum;
import net.syspherice.form.FileObject;
import net.syspherice.utils.Common;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class FileObjectDao extends AbstractDAO{
	public FileObjectDao() {
		super();
	}
	public int insert(FileObject fileObject){
		
		return 1;
	}
	public FileObject getFileObject(DBObject doc) {
		FileObject data = new FileObject();
		data.setFileObjectID(Common.ConvertToString(
				doc.get(FileObjectEnum.FileObjectID.toString()), ""));
		data.setFileType(Common.ConvertToString(
				doc.get(FileObjectEnum.FileType.toString()), ""));
		data.setFileName(Common.ConvertToString(
				doc.get(FileObjectEnum.FileName.toString()), ""));
		//GridFSO
		return data;
	}

	public BasicDBObject getBasicDBObject(FileObject data) {
		BasicDBObject doc = new BasicDBObject();
		if(data.getFileObjectID()!=""){
			doc.put(FileObjectEnum.FileObjectID.toString(), data.getFileObjectID());
		}
		doc.put(FileObjectEnum.FileType.toString(), data.getFileType());
		doc.put(FileObjectEnum.FileName.toString(), data.getFileName());
		return doc;
	}
}
