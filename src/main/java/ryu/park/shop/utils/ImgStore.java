package ryu.park.shop.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class ImgStore {
	private static final Logger logger = LoggerFactory.getLogger(ImgStore.class);
	
	final private static String IMG_STORE_PATH = "/resources/uploadImg/";
	final private static String IMG_MAIN_NAME_HEADER = "mainImg_";
	final private static String IMG_CONTENTS_NAME_HEADER = "ckeImg_";

	private String rootPath;
	private String fileNameHeader;
	private List<String> fileNames;
	
	public ImgStore() {
		fileNames = new ArrayList<String>();
	}

	public enum IMG_STORE_TYPE {
		IMG_GOODS_MAIN, IMG_GOODS_CONTENTS
	}

	public ImgStore setRealRootPath(String rootPath) {
		this.rootPath = rootPath;
		return this;
	}

	public ImgStore setFileName(String... fileNames) {  
		for(String fileName : fileNames) {  
			this.fileNames.add(DateUtils.getInstance().now() + "_" + fileName);
		}
		return this;
	}

	public ImgStore setImgStoreType(IMG_STORE_TYPE imgStoreType) {
		switch (imgStoreType) {
			case IMG_GOODS_MAIN: {
				fileNameHeader = IMG_MAIN_NAME_HEADER;
				break;
			}
			case IMG_GOODS_CONTENTS: {
				fileNameHeader = IMG_CONTENTS_NAME_HEADER;
				break;
			}
		}

		return this;
	}

	public String[] build(MultipartFile... multipartFiles) throws IllegalStateException, IOException {
		if(!validParam(fileNames, multipartFiles, rootPath, fileNameHeader)) {
			throw new IllegalArgumentException("check in->\r\n" + "rootPath:" + rootPath + "\n\rfileNameHeader:" + fileNameHeader + "\n\rfileName size:" + fileNames.size() + "\n\rfile size:" + multipartFiles.length + "\r\r");
		}
		
		for(int i=0; i<multipartFiles.length; i++) {
			multipartFiles[i].transferTo(new File(rootPath + IMG_STORE_PATH + fileNameHeader + fileNames.get(i)));
		}
		List<String> rtn = new ArrayList<String>();
		for(int i=0; i<fileNames.size(); i++) {
			rtn.add(IMG_STORE_PATH + fileNameHeader + fileNames.get(i));
		}
		 
		return rtn.toArray(new String[rtn.size()]);
	}
	
	private boolean validParam(List<String> fileNames, MultipartFile[] multipartFiles, String... parms) { 
		if(fileNames.size() == 0 || multipartFiles.length == 0) {return false;}
		if(fileNames.size() !=  multipartFiles.length) {return false;}
		for(String parm : parms) {
			if(parm == null){ 
				return false;
			}else if(parm.equals("") || parm.length() == 0){ 
				return false;
			}
		} 
		return true;
	}

}