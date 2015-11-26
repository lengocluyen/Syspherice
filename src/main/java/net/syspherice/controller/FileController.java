package net.syspherice.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerMapping;


@Controller
@RequestMapping(value = "/file")
public class FileController {
	@RequestMapping(value="/**",method = RequestMethod.GET)
	public void get(HttpServletRequest request,HttpServletResponse response) throws FileNotFoundException, IOException{
		String url = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		String path=url.replaceFirst("/file", "");
		File f=new File(net.syspherice.utils.Config.ROOT_PATH,path);
		IOUtils.copy(new FileInputStream(f), response.getOutputStream());
	}
}
