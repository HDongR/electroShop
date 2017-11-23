package ryu.park.shop.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Class		ImgStore.java
 * @packagename	ryu.park.shop.utils
 * @author		hodongryu
 * @since		2017.10.30.
 * @version		1.0
 * @see			이미지 저장 클래스(저장할때마다 생성)
 * <pre>
 * << 개정이력(Modification Information) >>
 *    수정일       수정자          수정내용
 *    -------      -------     -------------------
 *    2017.10.30.  hodongryu      최초작성
 * </pre>
 */
public class ImgStore {
	private static final Logger logger = LoggerFactory.getLogger(ImgStore.class);
	
	/**
	 * 프로젝트 폴더 내 저장되는 패스
	 */
	final private static String IMG_STORE_PATH = "/resources/uploadImg/";
	/**
	 * 상품 메인 이미지 헤더이름
	 */
	final private static String IMG_MAIN_NAME_HEADER = "mainImg_";
	/**
	 * 상품 콘텐츠 이미지 헤더이름
	 */
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
			this.fileNames.add(DateUtils.now() + "_" + fileName);
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

	/**
	 * @method		build : 이미지를 저장하고 상대패스를 리턴해준다.
	 * @param multipartFiles : 멀티파트 파일(여러개 가능)
	 * @return 이미지들의 상대패스
	 * @throws IllegalStateException
	 * @throws IOException
	 * @author		hodongryu
	 * @since		2017.10.30.
	 * @version		1.0
	 * @see
	 * <pre>
	 * << 개정이력(Modification Information) >>
	 *    수정일       수정자          수정내용
	 *    -------      -------     -------------------
	 *    2017.10.30.  hodongryu      최초작성
	 * </pre>
	 */
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
	
	/**
	 * @method		validParam : build 메소드에서 모든 파라미터를 검증
	 * @param fileNames : 파일이름(여러개 가능)
	 * @param multipartFiles : 멀티파일(여러개 가능)
	 * @param parms	: build 메소드 전에 필요한 프로퍼티 검증
	 * @return
	 * @author		hodongryu
	 * @since		2017.10.30.
	 * @version		1.0
	 * @see
	 * <pre>
	 * << 개정이력(Modification Information) >>
	 *    수정일       수정자          수정내용
	 *    -------      -------     -------------------
	 *    2017.10.30.  hodongryu      최초작성
	 * </pre>
	 */
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