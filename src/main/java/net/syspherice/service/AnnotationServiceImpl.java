package net.syspherice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import net.syspherice.dao.AccountDao;
import net.syspherice.dao.AnnotationDao;
import net.syspherice.dao.DaoFactory;
import net.syspherice.form.Account;
import net.syspherice.form.Annotation;

@Service
public class AnnotationServiceImpl implements AnnotationService{
private AnnotationDao annotationDao;
	
	public AnnotationServiceImpl() {
		annotationDao = DaoFactory.getAnnotationDao();
	}
		
	 public Boolean add(Annotation data) {
	       return annotationDao.insert(data);
	    }
	 public List<Annotation> all(){
		 return annotationDao.all();
	 }
	 public Boolean update (Annotation dataOrigine,Annotation dataUpdate){
		 return annotationDao.update(dataOrigine, dataUpdate);
	 }
	 public Boolean delete (String annotationID){
		 return annotationDao.delete(annotationID);
	 }
	 public Annotation single(String annotationID){
		 return annotationDao.single(annotationID);
	 }
	 public long count(){
			return annotationDao.count();
		}
	 public List<Annotation> findbyObjectID(String objectID){
		 return annotationDao.findByObjectID(objectID);
	 }
		public List<Annotation> paging(int page, int pagesize){
			return annotationDao.paging(page, pagesize);
		}
}
