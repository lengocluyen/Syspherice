package net.syspherice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import net.syspherice.dao.AccountDao;
import net.syspherice.dao.AnnotationDao;
import net.syspherice.dao.BinImageDataDao;
import net.syspherice.dao.DaoFactory;
import net.syspherice.form.Account;
import net.syspherice.form.Annotation;
import net.syspherice.form.BinImageData;

@Service
public class AnnotationServiceImpl implements AnnotationService {
	private AnnotationDao annotationDao;

	private BinImageDataDao imageDataDao;

	public AnnotationServiceImpl() {
		annotationDao = DaoFactory.getAnnotationDao();
	}

	public Boolean add(Annotation data) {
		return annotationDao.insert(data);
	}

	public List<Annotation> all() {
		return annotationDao.all();
	}

	public Boolean update(Annotation dataOrigine, Annotation dataUpdate) {
		return annotationDao.update(dataOrigine, dataUpdate);
	}

	public Boolean delete(String annotationID) {
		return annotationDao.delete(annotationID);
	}

	public Annotation single(String annotationID) {
		return annotationDao.single(annotationID);
	}

	public long count() {
		return annotationDao.count();
	}

	public List<Annotation> findbyObjectID(String objectID) {
		return annotationDao.findByObjectID(objectID);
	}

	public List<Annotation> paging(int page, int pagesize) {
		imageDataDao = DaoFactory.getBinImageDataDao();
		List<Annotation> data = annotationDao.paging(page, pagesize);
		try {
			List<Annotation> result = new ArrayList<Annotation>();
			for (Annotation an : data) {
				BinImageData id = imageDataDao.single(an.getObjectID());
				an.setNameObject(id.getName());
				result.add(an);
			}
			return result;
		} catch (Exception e) {
			return null;
		}
	}
}
