package net.syspherice.allegro;

import org.openrdf.repository.RepositoryException;

import com.franz.agraph.jena.AGGraphMaker;
import com.franz.agraph.jena.AGModel;
import com.franz.agraph.repository.AGCatalog;
import com.franz.agraph.repository.AGRepository;
import com.franz.agraph.repository.AGRepositoryConnection;
import com.franz.agraph.repository.AGServer;
import com.franz.*;

public class TutorialExamples {
	static private final String SERVER_URL = "http://localhost:10035";
	static private final String CATALOG_ID = "java-scratch";
	static private final String REPOSITORY_ID = "javatutorial";
	static private final String USERNAME = "hoaikhong";
	static private final String PASSWORD = "123456";

	static final String FOAF_NS = "http://xmlns.com/foaf/0.1/";

	// create a Repository
	public static AGGraphMaker Connection (boolean close) {
		// Tests getting the repository up
		try {
			AGServer server = new AGServer(SERVER_URL, USERNAME, PASSWORD);

			AGCatalog catalog = server.getCatalog(CATALOG_ID);
			AGRepository myRepository = catalog.createRepository(REPOSITORY_ID);
			myRepository.initialize();
			AGRepositoryConnection conn = myRepository.getConnection();
			
			AGGraphMaker maker = new AGGraphMaker(conn);
			if (close) {
				maker.close();
				conn.close();
				myRepository.shutDown();
				return null;
			}

			return maker;
		} catch (RepositoryException re) {
			return null;
		}
	}
}
