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

public class ImageHandle {
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
	public static boolean SaveImage(DB db,String fileImagePath,String newImageName) {
		File imgeFile = new File(fileImagePath);
		GridFS gfsPhoto = new GridFS(db,"photo");
		GridFSInputFile gfsFile;
		try {
			gfsFile = gfsPhoto.createFile(imgeFile);
		} catch (IOException e) {
			Logger.getLogger(ImageHandle.class.getName()).log(Level.SEVERE,
					null, e);
			return false;
		}
		gfsFile.setFilename(newImageName);
		gfsFile.save();
		return true;
	}
	public static boolean ModifyImage(DB db, String nameImage, String newImagePath){
		GridFS gfsPhoto = new GridFS(db,"photo");
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
	public static File GetImage(DB db,String newPath, String newFileName){
		File image = new File(newPath);
		GridFS gfsPhoto = new GridFS(db,"photo");
		GridFSDBFile imageForOuput = gfsPhoto.findOne(newFileName);
		try {
			imageForOuput.writeTo(image);
			
			return image;
		} catch (IOException e) {
			Logger.getLogger(ImageHandle.class.getName()).log(Level.SEVERE,
					null, e);
			return null;
		}
		
	}
	public static boolean DeleteImage (String imageName, DB db){
		GridFS gfsPhoto = new GridFS(db,"photo");
		GridFSDBFile file = gfsPhoto.findOne(imageName);
		if(file!=null){
			gfsPhoto.remove(file);
			return true;
		}
		return false;
	}
}
