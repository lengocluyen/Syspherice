package net.syspherice.mongoutils;

import java.util.*;

public class Config
{
	public static final String HOST;
	public static final String PORT;
	public static final String DATABASE;
	public static final String COLLECTION;
	public static final String USERNAME;
	public static final String PASSWORD;
	static{
		ResourceBundle rb=ResourceBundle.getBundle("mongodb");
		
		HOST=rb.getString("mongodb_host");
		PORT=rb.getString("mongodb_port");
		DATABASE=rb.getString("mongodb_database");
		COLLECTION=rb.getString("mongodb_collection");
		USERNAME=rb.getString("mongodb_username");
		PASSWORD=rb.getString("mongodb_password");
	}
}
