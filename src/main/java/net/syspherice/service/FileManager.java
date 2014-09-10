package net.syspherice.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.syspherice.utils.Base64Util;
import net.syspherice.utils.FileUtil;
import net.syspherice.utils.HashUtil;
import net.syspherice.utils.ImageUtil;
import net.syspherice.utils.*;
import net.syspherice.vo.AddedResponse;
import net.syspherice.vo.ElFile;
import net.syspherice.vo.Options;
import net.syspherice.vo.Response;
import net.syspherice.vo.TreeResponse;
@Service
public class FileManager implements ElfinderService{
	
	public Response open(String target,Boolean init,Boolean tree) throws IOException{
		Response rsp=new Response();
		if(init!=null&&init){
			rsp.setApi(Config.API);
		}
		rsp.setOptions(Options.getInstance());
		rsp.setUplMaxSize(Config.UPL_MAX_SIZE);
		if(StringUtils.isBlank(target)){
			target=Config.DEFAULT_VOLUMNID+Base64Util.encode(Config.SEPARATOR);
		}
		ElFile cwd=ElFile.getInstance(target);
		rsp.setCwd(cwd);
		String[] strs=HashUtil.decode(target);
		File pf=new File(Config.ROOT_PATH,strs[1]);
		List<ElFile> files=new ArrayList<ElFile>();
		if(tree!=null&&tree){
			addRootAndChildren(strs[0],files);
		}
		boolean isRoot=StringUtils.isBlank(cwd.getPhash());
		if((tree!=null&&tree)&&isRoot){
			//do nothing
		}else{
			for(File f:pf.listFiles()){
				String hash=HashUtil.encode(strs[0], strs[1], f);
				files.add(ElFile.getInstance(hash));
			}
		}
		rsp.setFiles(files);
		return rsp;
	}
	private void addRootAndChildren(String volumnid,List<ElFile> files) throws IOException{
		String rootHash=volumnid+Base64Util.encode(Config.SEPARATOR);
		files.add(ElFile.getInstance(rootHash));
		File rootFile=new File(Config.ROOT_PATH);
		for(File f:rootFile.listFiles()){
			String hash=HashUtil.encode(volumnid, Config.SEPARATOR, f);
			files.add(ElFile.getInstance(hash));
		}
	}

	public TreeResponse parents(String target) throws IOException {
		String[] strs=HashUtil.decode(target);
		List<String> paths=FileUtil.getParentPaths(strs[1]);
		List<ElFile> tree=new ArrayList<ElFile>();
		for(String p:paths){
			String hash=HashUtil.encode(strs[0], p);
			tree.add(ElFile.getInstance(hash));
		}
		String directParent=paths.get(paths.size()-1);
		File dpf=new File(Config.ROOT_PATH,directParent);
		for(File f:dpf.listFiles()){
			String hash=HashUtil.encode(strs[0], directParent, f);
			tree.add(ElFile.getInstance(hash));
		}
		TreeResponse rsp=new TreeResponse();
		rsp.setTree(tree);
		return rsp;
	}
	public AddedResponse mkfile(String target, String name,boolean isdir) throws IOException {
		String[] strs=HashUtil.decode(target);
		String filePath=strs[1]+Config.SEPARATOR+name;
		File f=new File(Config.ROOT_PATH,filePath);
		if(isdir)
			f.mkdir();
		else
			f.createNewFile();
		AddedResponse rsp=new AddedResponse();
		rsp.getAdded().add(ElFile.getInstance(strs[0],filePath));
		return rsp;
	}
	public AddedResponse rename(String target, String name) throws IOException {
		String[] strs=HashUtil.decode(target);
		File f=new File(Config.ROOT_PATH,strs[1]);
		String newPath=FileUtil.getParentPath(strs[1])+Config.SEPARATOR+name;
		f.renameTo(new File(Config.ROOT_PATH,newPath));
		AddedResponse rsp=new AddedResponse();
		rsp.getAdded().add(ElFile.getInstance(strs[0],newPath));
		rsp.addRemoved(target);
		return rsp;
	}
	public AddedResponse upload(String target, MultipartFile[] files) throws IOException {
		AddedResponse rsp=new AddedResponse();
		String[] strs=HashUtil.decode(target);
		for(MultipartFile file:files){
			String path=strs[1]+Config.SEPARATOR+file.getOriginalFilename();
			IOUtils.copy(file.getInputStream(), new FileOutputStream(new File(Config.ROOT_PATH,path)));
			rsp.getAdded().add(ElFile.getInstance(strs[0],path));
		}
		return rsp;
	}
	public void download(String target, Boolean download,
			HttpServletResponse response) throws IOException {
		String[] strs=HashUtil.decode(target);
	File f=new File(Config.ROOT_PATH,strs[1]);
		response.setCharacterEncoding("utf-8");
		String finalFileName = URLEncoder.encode(f.getName(),"UTF-8");
		if(download!=null&&download){
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setHeader("Content-Disposition", "attachment; filename=\""+finalFileName+"\"");
			response.setHeader("Content-Location", finalFileName);
		}
		IOUtils.copy(new FileInputStream(f),response.getOutputStream());
	}
	public AddedResponse remove(String[] targets) throws IOException {
		AddedResponse rsp=new AddedResponse();
		for(String target:targets){
			String[] strs=HashUtil.decode(target);
			delFile(rsp,strs[0],strs[1]);
		}
		return rsp;
	}
	private void delFile(AddedResponse rsp,String volumnid,String path) throws UnsupportedEncodingException{
		File f=new File(Config.ROOT_PATH,path);
		if(f.isDirectory()){
			for(File cf:f.listFiles()){
				String cfpath=path+Config.SEPARATOR+cf.getName();
				delFile(rsp,volumnid,cfpath);
			}
		}
		f.delete();
		rsp.addRemoved(HashUtil.encode(volumnid, path));
	}
	public AddedResponse paste(String dst, String[] targets, Boolean cut)
			throws IOException {
		AddedResponse rsp=new AddedResponse();
		String[] dstStrs=HashUtil.decode(dst);
//		File dstFile=new File(ROOT_PATH,dstStrs[1]);
		for(String target:targets){
			String[] strs=HashUtil.decode(target);
			File src=new File(Config.ROOT_PATH,strs[1]);
			String newPath=dstStrs[1]+Config.SEPARATOR+src.getName();
			File newFile=new File(Config.ROOT_PATH,newPath);
			if(newFile.exists()){
				if(src.isDirectory()&&newFile.isDirectory()){
					delFile(rsp,strs[0],newPath);
				}else if(src.isFile()&&newFile.isFile()){
					delFile(rsp,strs[0],newPath);
				}else{
					rsp.addWarning("errNotReplace");
					rsp.addWarning(Config.ROOT_NAME+Config.SEPARATOR+newPath);
					continue;
				}
			}
			pasteFile(rsp,strs[0],dstStrs[1],src);
			//if cut is true, delete the source file
			if(cut!=null&&cut){
				delFile(rsp,strs[0],strs[1]);
			}
		}
		//return result
		return rsp;
	}
	private void pasteFile(AddedResponse rsp,String volumnid,String dstPath,File src) throws IOException{
		String newPath=dstPath+Config.SEPARATOR+src.getName();
		File newFile=new File(Config.ROOT_PATH,newPath);
		if(src.isDirectory()){
			newFile.mkdir();
			rsp.getAdded().add(ElFile.getInstance(volumnid,newPath));
			for(File f:src.listFiles()){
				pasteFile(rsp,volumnid,newPath,f);
			}
		}else{
			newFile.createNewFile();
			IOUtils.copy(new FileInputStream(src), new FileOutputStream(newFile));
			rsp.getAdded().add(ElFile.getInstance(volumnid,newPath));
		}
	}
	public Map<String, Map<String, String>> tmb(String[] targets) {
		Map<String,Map<String,String>> result=new HashMap<String,Map<String,String>>();
		Map<String,String> images=new HashMap<String,String>();
		String suffix="_"+System.currentTimeMillis();
		for(String target:targets){
			images.put(target, target+suffix);
		}
		result.put("images", images);
		return result;
	}
	public Map<String, Long> size(String[] targets) throws IOException {
		long result=0;
		for(String target:targets){
			//get file's path
			String[] strs=HashUtil.decode(target);
			//get file's size
			File f=new File(Config.ROOT_PATH,strs[1]);
			result+=fileSize(f);
		}
		//return result
		Map<String,Long> map=new HashMap<String,Long>();
		map.put("size", result);
		return map;
	}
	private long fileSize(File f){
		long size=0;
		if(f.isDirectory()){
			for(File cf:f.listFiles()){
				size+=fileSize(cf);
			}
		}else{
			size+=f.length();
		}
		return size;
	}
	public Map<String, String> getContent(String target) throws IOException {
		//get file path
		String[] strs=HashUtil.decode(target);
		//read file content
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		IOUtils.copy(new FileInputStream(new File(Config.ROOT_PATH,strs[1])), out);
		//return result
		Map<String,String> result=new HashMap<String,String>();
		result.put("content", new String(out.toByteArray(),"utf-8"));
		return result;
	}
	public Map<String, String> dim(String target) throws IOException {
		String[] strs=HashUtil.decode(target);
		File f=new File(Config.ROOT_PATH,strs[1]);
		BufferedImage image = ImageIO.read(f);
		String dim=image.getWidth()+"x"+image.getHeight();
		Map<String,String> result=new HashMap<String,String>();
		result.put("dim", dim);
		return result;
	}
	public AddedResponse duplicate(String[] targets) throws IOException {
		AddedResponse rsp=new AddedResponse();
		for(String target:targets){
			String[] strs=HashUtil.decode(target);
			File tf=new File(Config.ROOT_PATH,strs[1]);
			String nn=FileUtil.duplicateFileName(tf);
			File nf=new File(tf.getParentFile(),nn);
			copyFile(tf,nf);
			String newPath=FileUtil.getParentPath(strs[1])+Config.SEPARATOR+nn;
			rsp.getAdded().add(ElFile.getInstance(strs[0], newPath));
		}
		return rsp;
	}
	private void copyFile(File of,File nf) throws FileNotFoundException, IOException{
		if(of.isFile()){
			IOUtils.copy(new FileInputStream(of), new FileOutputStream(nf));
		}else{
			nf.mkdir();
			for(File f:of.listFiles()){
				copyFile(f,new File(nf,f.getName()));
			}
		}
	}
	public Map<String, ElFile[]> editContent(String target,
			String content) throws IOException {
		String[] strs=HashUtil.decode(target);
		File tf=new File(Config.ROOT_PATH,strs[1]);
		IOUtils.copy(new ByteArrayInputStream(content.getBytes("utf-8")), new FileOutputStream(tf));
		Map<String, ElFile[]> map=new HashMap<String, ElFile[]>();
		map.put("changed", new ElFile[]{ElFile.getInstance(target)});
		return map;
	}
	public Map<String, List<ElFile>> search(String condition) throws IOException {
		List<ElFile> list=new ArrayList<ElFile>();
		File root=new File(Config.ROOT_PATH);
		for(File file:root.listFiles())
			query(file,null,condition,list);
		Map<String, List<ElFile>> map=new HashMap<String, List<ElFile>>();
		map.put("files", list);
		return map;
	}
	private void query(File file,String parentPath,String condition,List<ElFile> list) throws IOException{
		String path=null;
		if(parentPath==null){//root
			path=file.getName();
		}else{
			path=parentPath+Config.SEPARATOR+file.getName();
		}
		
		if(file.isDirectory()){
			for(File f:file.listFiles()){
				query(f,path,condition,list);
			}
		}else{
			if(file.getName().startsWith(condition)){
				list.add(ElFile.getSearchInstance(Config.DEFAULT_VOLUMNID, path));
			}
		}
	}
	public TreeResponse subfolders(String target) throws IOException {
		List<ElFile> list=new ArrayList<ElFile>();
		String[] strs=HashUtil.decode(target);
		File pf=new File(Config.ROOT_PATH,strs[1]);
		for(File f:pf.listFiles()){
			if(f.isDirectory()){
				list.add(ElFile.getInstance(strs[0], strs[1]+Config.SEPARATOR+f.getName()));
			}
		}
		TreeResponse rsp=new TreeResponse();
		rsp.setTree(list);
		return rsp;
	}
	public Map<String, List<String>> ls(String target) throws IOException {
		List<String> list=new ArrayList<String>();
		String[] strs=HashUtil.decode(target);
		File pf=new File(Config.ROOT_PATH,strs[1]);
		for(File f:pf.listFiles()){
			if(f.isFile()){
				list.add(f.getName());
			}
		}
		Map<String, List<String>> map=new HashMap<String, List<String>>();
		map.put("list", list);
		return map;
	}
	public Map<String, ElFile[]> changeImage(String target, String mode,
			int width, int height, int x, int y, double degree)
			throws IOException {
		String[] strs=HashUtil.decode(target);
		File f=new File(Config.ROOT_PATH,strs[1]);
		if("resize".equals(mode)){
			ImageUtil.resize(f, width, height);
		}else if("crop".equals(mode)){
			ImageUtil.crop(f, width, height, x, y);
		}else if("rotate".equals(mode)){
			System.out.println("rotate:"+degree);
			ImageUtil.rotate(f, degree);
		}
		Map<String, ElFile[]> map=new HashMap<String, ElFile[]>();
		map.put("changed", new ElFile[]{ElFile.getNewTmbInstance(target)});
		return map;
	}
}
