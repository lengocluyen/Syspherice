package net.syspherice.service;

import java.util.List;

import net.syspherice.form.Annotation;

public interface AnnotationService {
	Boolean add(Annotation data);

	List<Annotation> all();

	Boolean update(Annotation dataOrigine, Annotation dataUpdate);

	Boolean delete(String annotationID);

	Annotation single(String annotationID);

	
	long count();
	List<Annotation> findbyObjectID(String objectID);
	List<Annotation> paging(int page, int pagesize);
}
