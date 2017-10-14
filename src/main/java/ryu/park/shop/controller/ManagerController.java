package ryu.park.shop.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ryu.park.shop.service.ManagerService;
import ryu.park.shop.type.JoinType;
import ryu.park.shop.utils.BoardPager;
import ryu.park.shop.utils.SecurityUtils;
import ryu.park.shop.vo.GoodsVO;
import ryu.park.shop.vo.UserVO;

@RequestMapping("/manager/*")
@Controller
public class ManagerController {
	private static final Logger logger = LoggerFactory.getLogger(ManagerController.class);
	int k;
	@Autowired
	private ManagerService service;
	@Autowired
	private SecurityUtils securityUtils;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String manager_home(Model model, HttpServletRequest req) {
		logger.info("manager page");

		model.addAttribute("condition", "manager_main");
		return "manager_main";
	}

	@RequestMapping(value = "join", method = RequestMethod.POST)
	public void joinManager(@Valid UserVO manager, BindingResult bindingResult, ServletRequest req,
			ServletResponse res) {

	}

	@RequestMapping(value = "join_page", method = RequestMethod.GET)
	public String joinPage() {
		return "manager/manager_join";
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public void loginManager(@Valid UserVO manager, BindingResult bindingResult, HttpServletRequest req,
			HttpServletResponse res) throws IOException {

		logger.info("managerLogin");

		if (bindingResult.hasErrors()) {
			logger.info("valid error");
			res.getWriter().print("validError");
		} else {
			manager.setPassword(securityUtils.getHash(manager.getPassword()));
			UserVO managerResult = service.loginManager(manager);

			if (managerResult == null) {
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

	@RequestMapping(value = "goods/add_goods_page", method = RequestMethod.GET)
	public String addGoodsPage(Model model) {
		model.addAttribute("condition", "add_goods_page");
		return "manager/goods/add_goods";
	}

	@RequestMapping(value = "add_goods", method = RequestMethod.POST)
	public void addGoods(@Valid GoodsVO goodsVO, BindingResult bindingResult, HttpServletRequest req,
			HttpServletResponse res, Model model, @RequestParam MultipartFile mainPic) throws IOException {
		logger.info("addGoods");

		String root_path = req.getSession().getServletContext().getRealPath("/");
		String attach_path = "/resources/uploadImg/";
		String fileName = "mainImg_" + "_" + mainPic.getOriginalFilename();

		mainPic.transferTo(new File(root_path + attach_path + fileName));
		String fileUrl = attach_path + fileName;
		goodsVO.setMainPicUrl(fileUrl);

		if (bindingResult.hasErrors()) {
			logger.info("valid error" + bindingResult.getFieldError());
			res.getWriter().print("validError");
		} else {
			int r = service.addGoods(goodsVO);
			logger.info("result:" + r);
			if (r > 0) {
				res.getWriter().print("completeAddedGoods");
			} else {
				res.getWriter().print("databaseError");
			}
		}

		logger.info("goods seq:" + goodsVO.getGoodsSeq());

	}

	@RequestMapping(value = "upload_img", method = RequestMethod.POST)
	public void uploadImg(HttpServletRequest req, HttpServletResponse res, @RequestParam MultipartFile upload) {
		logger.info("uploadImg");

		Date date = new Date();
		int year = date.getYear() + 1900;
		int month = date.getMonth();
		String monthStr = "";

		if (month < 10)
			monthStr = "0" + month;
		else
			monthStr = "" + month;

		res.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");

		try {

			String root_path = req.getSession().getServletContext().getRealPath("/");
			String attach_path = "/resources/uploadImg/";
			String fileName = "ckeImg_" + year + monthStr + "_" + upload.getOriginalFilename();

			upload.transferTo(new File(root_path + attach_path + fileName));

			String callback = req.getParameter("CKEditorFuncNum");
			String fileUrl = attach_path + fileName;

			logger.info(fileUrl);

			res.getWriter().println("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction("
					+ callback + ",'" + fileUrl + "','이미지를 업로드 하였습니다.'" + ")</script>");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "goods/goods_manage_page", method = {RequestMethod.GET, RequestMethod.POST})
	public String goodsManagePage(@RequestParam(defaultValue = "all") String searchOption,
			@RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") int curPage,
			Model model) {
		logger.info("goods_manage_page");
		model.addAttribute("condition", "goods_manage_page");

		int count = service.goodsTotalCount(searchOption, keyword);
		logger.info("count:" + count);  
		// 페이지 나누기 관련 처리
		BoardPager boardPager = new BoardPager(count, curPage);
		int start = boardPager.getPageBegin();
		int end = boardPager.getPageEnd();
		logger.info("start: " + start);
		logger.info("end: " + end);

		List<GoodsVO> list = service.getGoodsList(start, end, searchOption, keyword);
		
		logger.info("result Size:" + list.size());  
		logger.info("totpage : " + boardPager.getTotPage());
		logger.info("block start: " + boardPager.getBlockBegin());
		logger.info("block end: " + boardPager.getBlockEnd());
		
		
		model.addAttribute("list", list); // list
		model.addAttribute("count", count); // 레코드의 갯수
		model.addAttribute("searchOption", searchOption); // 검색옵션
		model.addAttribute("keyword", keyword); // 검색키워드
		model.addAttribute("boardPager", boardPager);

		return "manager/goods/goods_manage";
	}

	@RequestMapping(value = "goods/modify_goods_page/{goodsSeq}", method = RequestMethod.GET)
	public String goodsModifyPage(@PathVariable("goodsSeq") int goodsSeq, Model model) {
		logger.info("goods_modify_page");
		GoodsVO goodsVO = service.getGoodsOne(goodsSeq);
		model.addAttribute("goodsVO", goodsVO);
		return "manager/goods/modify_goods";
	}

	@RequestMapping(value = "goods/modify_goods", method = RequestMethod.POST)
	public void modifyGoods(@Valid GoodsVO goodsVO, BindingResult bindingResult, HttpServletRequest req,
			HttpServletResponse res, @RequestParam MultipartFile mainPic) throws Exception, IOException {
		logger.info("modifyGoods");

		if (mainPic.getSize() < 1) {
			logger.info("mainPic is NuLl");
		} else {
			String root_path = req.getSession().getServletContext().getRealPath("/");
			String attach_path = "/resources/uploadImg/";
			String fileName = "mainImg_" + "_" + mainPic.getOriginalFilename();

			mainPic.transferTo(new File(root_path + attach_path + fileName));
			String fileUrl = attach_path + fileName;
			goodsVO.setMainPicUrl(fileUrl);
		}

		if (bindingResult.hasErrors()) {
			logger.info("valid error" + bindingResult.getFieldError());
			res.getWriter().print("validError");
		} else {
			service.updateGoodsOne(goodsVO);
			res.getWriter().print("completeUpdatedGoods");
		}
	}

}