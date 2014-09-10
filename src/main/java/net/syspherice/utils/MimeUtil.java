package net.syspherice.utils;

import java.io.File;
import java.util.Collection;

import javax.activation.MimetypesFileTypeMap;

import eu.medsea.mimeutil.MimeType;

public class MimeUtil {
	static{
		eu.medsea.mimeutil.MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
		eu.medsea.mimeutil.MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.ExtensionMimeDetector");
//		eu.medsea.mimeutil.MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.OpendesktopMimeDetector");
	}
	@SuppressWarnings("unchecked")
	public static String getMime(File f){
		if(f.isDirectory()){
			return "directory";
		}
		
		Collection<MimeType> mimes = eu.medsea.mimeutil.MimeUtil.getMimeTypes(f);
		if (!mimes.isEmpty()) {
			return mimes.iterator().next().toString();
		}
		// not found try the second method...
		return new MimetypesFileTypeMap().getContentType(f);
	}
}
