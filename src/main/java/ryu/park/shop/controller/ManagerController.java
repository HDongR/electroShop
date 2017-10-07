package ryu.park.shop.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ryu.park.shop.service.ManagerService;
import ryu.park.shop.utils.SecurityUtils;
import ryu.park.shop.vo.UserVO;

@RequestMapping("/manager/*")
@Controller
public class ManagerController {
	private static final Logger logger = LoggerFactory.getLogger(ManagerController.class);
	
	@Autowired
	private ManagerService service;
	
	@Autowired
	private SecurityUtils securityUtils;
	
	@RequestMapping(value = "join", method = RequestMethod.POST)
	public void joinManager(@Valid UserVO manager, BindingResult bindingResult, ServletRequest req, ServletResponse res) {
		
	}
	
	@RequestMapping(value = "join_page", method = RequestMethod.GET)
	public String joinPage() {
		return "manager/manager_join";
	}
	
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public void loginManager(@Valid UserVO manager, BindingResult bindingResult, HttpServletRequest req, HttpServletResponse res) throws IOException{
		
		logger.info("managerLogin");
		
		if (bindingResult.hasErrors()) {
			logger.info("valid error");
			res.getWriter().print("validError");
		} else {
			manager.setPassword(securityUtils.getHash(manager.getPassword()));
			UserVO managerResult = service.loginManager(manager);
			
			if(managerResult == null) {
				res.getWriter().print("invalid Email or Pwd");
			} else {
				HttpSession session = req.getSession(true);
				session.setAttribute("user", managerResult);
				res.getWriter().print("loginComplete");
			}
		}
	}
	
	@RequestMapping(value = "login_page", method = RequestMethod.GET)
	public String loginPage() {
		return "manager/manager_login";
	}
	
	@RequestMapping(value = "add_goods", method = RequestMethod.GET)
	public String addGoodsPage() {
		return "manager/add_goods";
	}
	
	@RequestMapping(value = "upload_img", method = RequestMethod.POST)
	public String uploadImg(HttpServletRequest req, @RequestParam MultipartFile upload, Model model, String CKEditorFuncNum) {
		logger.info("uploadImg");
		logger.info(upload.toString());
		Date date = new Date();
		int year = date.getYear() + 1900;
		int month = date.getMonth();
		String monthStr = "";
		
		if(month < 10 ) monthStr = "0" + month;
		else monthStr = "" + month;
		String defaultPath = req.getRealPath("/");
		String contextPath = req.getSession().getServletContext().getContextPath();
		String fileUploadPathTail = "ckeImage/" + year + "" +monthStr;
		String fileUploadPath = defaultPath + "/" + fileUploadPathTail;
		
		try {
			if(upload != null) {
				String fileName = upload.getOriginalFilename();
				String fileNameExt = fileName.substring(fileName.indexOf(".") + 1);
				
				if(! "".equals(fileName)) {
					File destD = new File(fileUploadPath);
					
					if(!destD.exists()) {
						destD.mkdirs();
					}
					
					File destination = File.createTempFile("ckeditor_", "." + fileNameExt, destD);
					upload.transferTo(destination); 
				
					model.addAttribute("CKEditorFuncNum", CKEditorFuncNum);
					model.addAttribute("imageUrl", contextPath + "/" + fileUploadPathTail + "/" + destination.getName());
					
				}
			}
		}catch(Exception ve) {
			
		}
	
		
		
		
		return "ckeditorImageUpload";
	}
	
}
