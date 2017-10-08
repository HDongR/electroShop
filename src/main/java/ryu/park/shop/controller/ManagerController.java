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
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String manager_home(Model model) { 
		model.addAttribute("condition", "manager_main");
		return "manager_main";
	}
	
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
	public String addGoodsPage(Model model) {
		model.addAttribute("condition", "add_goods");
		return "manager/add_goods";
	}
	
	@RequestMapping(value = "upload_img", method = RequestMethod.POST)
	public void uploadImg(HttpServletRequest req, HttpServletResponse res, @RequestParam MultipartFile upload) {
		logger.info("uploadImg"); 
		
		Date date = new Date();
		int year = date.getYear() + 1900;
		int month = date.getMonth();
		String monthStr = "";
		
		if(month < 10 ) monthStr = "0" + month;
		else monthStr = "" + month;
		   
        res.setCharacterEncoding("utf-8");
        res.setContentType("text/html;charset=utf-8");
 
        try{
  
            String root_path = req.getSession().getServletContext().getRealPath("/");
            String attach_path = "resources/uploadImg/";
            String fileName = "ckeImg_" + year + monthStr + "_" + upload.getOriginalFilename();
             
            upload.transferTo(new File(root_path + attach_path + fileName));
            
            String callback = req.getParameter("CKEditorFuncNum");
            String fileUrl = "http://" + req.getServerName() + ":" + req.getServerPort() + "/" + attach_path + fileName;
             
            logger.info(fileUrl);
 
            res.getWriter().println("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction("
                    + callback
                    + ",'"
                    + fileUrl
                    + "','이미지를 업로드 하였습니다.'"
                    + ")</script>"); 
 
        }catch(IOException e){
            e.printStackTrace(); 
        }
	}
	
}
