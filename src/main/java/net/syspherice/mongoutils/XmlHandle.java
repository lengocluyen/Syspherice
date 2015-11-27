package net.syspherice.mongoutils;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

public class XmlHandle {
	public static boolean Urlexists(String URLName){
	    try {
	      HttpURLConnection.setFollowRedirects(false);
	      // note : you may also need
	      //        HttpURLConnection.setInstanceFollowRedirects(false)
	      HttpURLConnection con =
	         (HttpURLConnection) new URL(URLName).openConnection();
	      con.setRequestMethod("HEAD");
	      return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
	    }
	    catch (Exception e) {
	       e.printStackTrace();
	       return false;
	    }
	  }
	public static boolean SaveXml(DB db,String fileImagePath,String newImageName) {
		File xmlFile = new File(fileImagePath);
		GridFS gfsPhoto = new GridFS(db,"xmldata");
		GridFSInputFile gfsFile;
		try {
			gfsFile = gfsPhoto.createFile(xmlFile);
		} catch (IOException e) {
			Logger.getLogger(ImageHandle.class.getName()).log(Level.SEVERE,
					null, e);
			return false;
		}
		gfsFile.setFilename(newImageName);
		gfsFile.save();
		return true;
	}
	public static boolean ModifyXml(DB db, String nameImage, String newImagePath){
		GridFS gfsPhoto = new GridFS(db,"xmldata");
		GridFSDBFile imageForOutput = gfsPhoto.findOne(nameImage);
		if(imageForOutput!=null){
			try {
				imageForOutput.writeTo(newImagePath);
				return true;
			} catch (IOException e) {
				return false;
			}
		}
		return false;
	}
	public static File GetXml(DB db,String newPath, String newFileName){
		File xml = new File(newPath);
		GridFS gfsPhoto = new GridFS(db,"xmldata");
		GridFSDBFile imageForOuput = gfsPhoto.findOne(newFileName);
		try {
			imageForOuput.writeTo(xml);
			
			return xml;
		} catch (IOException e) {
			Logger.getLogger(ImageHandle.class.getName()).log(Level.SEVERE,
					null, e);
			return null;
		}
		
	}
	public static boolean DeleteXml(String xmlName, DB db){
		GridFS gfsPhoto = new GridFS(db,"xmldata");
		GridFSDBFile file = gfsPhoto.findOne(xmlName);
		if(file!=null){
			gfsPhoto.remove(file);
			return true;
		}
		return false;
	}
}
