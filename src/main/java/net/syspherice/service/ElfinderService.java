package net.syspherice.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import net.syspherice.vo.AddedResponse;
import net.syspherice.vo.ElFile;
import net.syspherice.vo.Response;
import net.syspherice.vo.TreeResponse;

public interface ElfinderService {
	/**
	 * @param target
	 * @param init
	 * @param tree
	 * @return
	 * @throws IOException
	 */
	Response open(String target,Boolean init,Boolean tree) throws IOException;
	/**
	 * @param target
	 * @return
	 * @throws IOException 
	 */
	TreeResponse parents(String target) throws IOException;
	/**
	 * @param target
	 * @param name
	 * @param isdir
	 * @return
	 * @throws IOException 
	 */
	AddedResponse mkfile(String target,String name,boolean isdir) throws IOException;
	/**
	 * @param target
	 * @param name
	 * @return
	 * @throws IOException 
	 */
	AddedResponse rename(String target,String name) throws IOException;
	AddedResponse upload(String target,MultipartFile[] files) throws IOException;
	void download(String target,Boolean download,HttpServletResponse response) throws IOException;
	AddedResponse remove(String[] targets) throws IOException;
	AddedResponse paste(String dst,String[] targets,Boolean cut) throws IOException;
	Map<String,Map<String,String>> tmb(String[] targets);
	/**
	 * @param targets
	 * @return
	 * @throws IOException 
	 */
	Map<String,Long> size(String[] targets) throws IOException;
	Map<String,String> getContent(String target) throws IOException;
	/**
	 * @param target
	 * @return
	 */
	Map<String,String> dim(String target) throws IOException;
	/**
	 * @param targets
	 * @return
	 * @throws IOException
	 */
	AddedResponse duplicate(String[] targets) throws IOException;
	Map<String,ElFile[]> editContent(String target,String content) throws IOException;
	Map<String,List<ElFile>> search(String condition) throws IOException;
	/**
	 * @param target
	 * @return
	 * @throws IOException
	 */
	TreeResponse subfolders(String target) throws IOException;
	/**
	 * @param target
	 * @return
	 * @throws IOException
	 */
	Map<String,List<String>> ls(String target) throws IOException;
	Map<String,ElFile[]> changeImage(String target,String mode,int width,int height,int x,int y,double degree) throws IOException;
}
