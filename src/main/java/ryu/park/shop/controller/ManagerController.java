package ryu.park.shop.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

import org.apache.commons.io.FileUtils;
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
import org.springframework.web.multipart.MultipartFile;

import ryu.park.shop.service.ManagerService;
import ryu.park.shop.utils.BoardPager;
import ryu.park.shop.utils.DateUtils;
import ryu.park.shop.utils.ImgStore;
import ryu.park.shop.utils.ImgStore.IMG_STORE_TYPE;
import ryu.park.shop.utils.SecurityUtils;
import ryu.park.shop.vo.GoodsVO;
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
			manager.setUserPassword(securityUtils.getHash(manager.getUserPassword()));
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
			HttpServletResponse res, Model model, @RequestParam MultipartFile goodsMainPic) throws IOException {
		logger.info("addGoods"); 
		
		if (bindingResult.hasErrors() || goodsMainPic.getSize() < 1) {
			logger.info("valid error" + bindingResult.getFieldError());
			res.getWriter().print("validError");
		} else {
			
			String fileUrl = new ImgStore()
					.setRealRootPath(req.getSession().getServletContext().getRealPath("/"))
					.setImgStoreType(IMG_STORE_TYPE.IMG_GOODS_MAIN)
					.setFileName(goodsMainPic.getOriginalFilename())
					.build(goodsMainPic)[0];

			goodsVO.setGoodsMainPicUrl(fileUrl);
			
			
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
	public void uploadImg(HttpServletRequest req, HttpServletResponse res, @RequestParam MultipartFile upload) throws IllegalStateException, IOException {
		logger.info("uploadImg");

		String fileUrl = new ImgStore()
				.setRealRootPath(req.getSession().getServletContext().getRealPath("/"))
				.setImgStoreType(IMG_STORE_TYPE.IMG_GOODS_CONTENTS)
				.setFileName(upload.getOriginalFilename())
				.build(upload)[0];
		
		res.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8"); 
		String callback = req.getParameter("CKEditorFuncNum");
		
		res.getWriter().println("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction(" 
				+ callback
				+ ",'" 
				+ fileUrl 
				+ "','이미지를 업로드 하였습니다.'" 
				+ ")</script>");

	}

	@RequestMapping(value = "goods/goods_manage_page", method = { RequestMethod.GET, RequestMethod.POST })
	public String goodsManagePage(@RequestParam(defaultValue = "allGoods") String searchOption,
			@RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") int curPage,
			Model model) {
		logger.info("goods_manage_page");
		model.addAttribute("condition", "goods_manage_page");

		int count = service.goodsTotalCount(searchOption, keyword);
		// 페이지 나누기 관련 처리
		BoardPager boardPager = new BoardPager(count, curPage);
		int start = boardPager.getPageBegin();
		int end = boardPager.getPageEnd();

		List<GoodsVO> list = service.getGoodsList(start, end, searchOption, keyword);

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
			HttpServletResponse res, @RequestParam MultipartFile goodsMainPic) throws Exception, IOException {
		logger.info("modifyGoods");
 
		if (bindingResult.hasErrors()) {
			logger.info("valid error" + bindingResult.getFieldError());
			res.getWriter().print("validError");
		} else {
			if (goodsMainPic.getSize() < 1) {
				logger.info("mainPic is NuLl");
			} else {
				String fileUrl = new ImgStore()
						.setRealRootPath(req.getSession().getServletContext().getRealPath("/"))
						.setImgStoreType(IMG_STORE_TYPE.IMG_GOODS_MAIN)
						.setFileName(goodsMainPic.getOriginalFilename())
						.build(goodsMainPic)[0];
				
				goodsVO.setGoodsMainPicUrl(fileUrl);
			}

			service.updateGoodsOne(goodsVO);
			res.getWriter().print("completeUpdatedGoods");
		}
	}

	@RequestMapping(value = "goods/delete_goods", method = RequestMethod.POST)
	public void goodsDelete(@RequestParam("goodsSeqList[]") List<Integer> goodsSeqList, HttpServletResponse res)
			throws IOException {
		logger.info("goodsDelete");
		int result = service.deleteGoodsList(goodsSeqList);
		if (goodsSeqList.size() == result) {
			res.getWriter().print("completeDeleteGoods");
		} else if (result == -1) {
			res.getWriter().print("databaseError");
		} else {
			res.getWriter().print("retryPlz");
		}

	}

	@RequestMapping(value = "user/user_manage_page", method = { RequestMethod.GET, RequestMethod.POST })
	public String userManagePage(@RequestParam(defaultValue = "allUser") String searchOption,
			@RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") int curPage,
			Model model) {
		logger.info("user_manage_page");

		model.addAttribute("condition", "user_manage_page");

		int count = service.userTotalCount(searchOption, keyword);
		// 페이지 나누기 관련 처리
		BoardPager boardPager = new BoardPager(count, curPage);
		int start = boardPager.getPageBegin();
		int end = boardPager.getPageEnd();

		List<UserVO> list = service.getUserList(start, end, searchOption, keyword);

		model.addAttribute("list", list); // list
		model.addAttribute("count", count); // 레코드의 갯수
		model.addAttribute("searchOption", searchOption); // 검색옵션
		model.addAttribute("keyword", keyword); // 검색키워드
		model.addAttribute("boardPager", boardPager);

		return "manager/user/user_manage";
	}

	@RequestMapping(value = "user/modify_user_page", method = RequestMethod.POST)
	public String userModifyPage(@RequestParam("userEmail") String email, Model model) {
		logger.info("user_modify_page");
		logger.info("email : " + email);

		UserVO userVO = service.getUserOne(email);
		logger.info("email2 : " + userVO.getUserEmail());
		model.addAttribute("userVO", userVO);
		return "manager/user/modify_user";
	}
}
