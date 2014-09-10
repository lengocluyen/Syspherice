package net.syspherice.utils;

import java.io.File;

public class ImageUtil {
	public static void resize(File f,int width,int height){
		net.syspherice.utils.Image image=new net.syspherice.utils.Image(f);
		image.resize(width, height);
		image.saveAs(f);
	}
	public static void crop(File f,int width,int height,int x,int y){
		net.syspherice.utils.Image image=new net.syspherice.utils.Image(f);
		image.crop(x, y, width, height);
		image.saveAs(f);
	}
	public static void rotate(File f,double degree){
		net.syspherice.utils.Image image=new net.syspherice.utils.Image(f);
		image.rotate(degree);
		image.saveAs(f);
	}
}
