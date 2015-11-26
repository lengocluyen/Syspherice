package net.syspherice.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mortennobel.imagescaling.DimensionConstrain;
import com.mortennobel.imagescaling.ResampleOp;
import net.syspherice.utils.HashUtil;
import net.syspherice.utils.*;

@Controller
@RequestMapping(value = "/tmb")
public class ThumbnailController {
	@RequestMapping(value="/{hash}",method = RequestMethod.GET)
	public void get(@PathVariable String hash,HttpServletResponse response) throws IOException{
		String[] strs=HashUtil.decode(removeSuffix(hash));
		File f=new File(Config.ROOT_PATH,strs[1]);
		BufferedImage image = ImageIO.read(f);
		int width=48;
		ResampleOp rop = new ResampleOp(DimensionConstrain.createMaxDimension(width, -1));
		rop.setNumberOfThreads(4);
		BufferedImage b = rop.filter(image, null);
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		ImageIO.write(b, "png", baos);
//		byte[] bytesOut = baos.toByteArray();
		
		//
		response.setHeader("Last-Modified", DateUtils.addDays(Calendar.getInstance().getTime(), 2 * 360).toGMTString());
		response.setHeader("Expires", DateUtils.addDays(Calendar.getInstance().getTime(), 2 * 360).toGMTString());

		ImageIO.write(b, "png", response.getOutputStream());
	}
	/**
	 * @param hash
	 * @return
	 */
	private String removeSuffix(String hash){
		String[] ss=hash.split("_");
		if(ss.length>2){
			return ss[0]+"_"+ss[1];
		}else{
			return hash;
		}
	}
}
