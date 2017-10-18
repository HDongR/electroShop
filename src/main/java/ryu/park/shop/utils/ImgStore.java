package ryu.park.shop.utils;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public class ImgStore {
	final private static String IMG_STORE_PATH = "/resources/uploadImg/";
	final private static String IMG_MAIN_NAME_HEADER = "mainImg_";
	final private static String IMG_CONTENTS_NAME_HEADER = "ckeImg_";

	private String rootPath;
	private String fileNameHeader;
	private String fileName;
	
	private MultipartFile multipartFile;

	public enum IMG_STORE_TYPE {
		IMG_GOODS_MAIN, IMG_GOODS_CONTENTS
	}

	public ImgStore setRealRootPath(String realRootPath) {
		this.rootPath = realRootPath;
		return this;
	}

	public ImgStore setFileName(MultipartFile multipartFile) {
		if(multipartFile == null) {
			throw new IllegalArgumentException("multipartFile is Null");
		}
		this.multipartFile = multipartFile;
		this.fileName = DateUtils.getInstance().now() + "_" + this.multipartFile.getOriginalFilename();
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

	public String build() throws IllegalStateException, IOException {
		if(validParam(rootPath, fileNameHeader, fileName)) {
			throw new IllegalArgumentException("check in->" + " rootPath:" + rootPath + " fileNameHeader:" + fileNameHeader + " fileName:" + fileName);
		}
		multipartFile.transferTo(new File(rootPath + IMG_STORE_PATH + fileNameHeader + fileName));
		return IMG_STORE_PATH + fileNameHeader + fileName;
	}
	
	private boolean validParam(String... strNames) {
		for(String str : strNames) {
			if(str == null){
				return false;
			}else if(str.equals("") || str.length() == 0){
				return false;
			}
		}
		return true;
	}

}