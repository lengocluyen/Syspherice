package net.syspherice.service;

import java.util.List;
import java.util.UUID;

import net.syspherice.form.DocumentObjet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DocumentObjetService {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	public static final String COLLECTION_NAME = "DocumentObjet";
    
    public void addDocumentObjet(DocumentObjet documentObjet) {
        if (!mongoTemplate.collectionExists(DocumentObjet.class)) {
            mongoTemplate.createCollection(DocumentObjet.class);
        }       
        documentObjet.setId(UUID.randomUUID().toString());
        mongoTemplate.insert(documentObjet, COLLECTION_NAME);
    }
     
    public List<DocumentObjet> listDocumentObjet() {
        return mongoTemplate.findAll(DocumentObjet.class, COLLECTION_NAME);
    }
     
    public void deleteDocumentObjet(DocumentObjet documentObjet) {
        mongoTemplate.remove(documentObjet, COLLECTION_NAME);
    }
     
    public void updateDocumentObjet(DocumentObjet documentObjet) {
        mongoTemplate.insert(documentObjet, COLLECTION_NAME);      
    }
}
