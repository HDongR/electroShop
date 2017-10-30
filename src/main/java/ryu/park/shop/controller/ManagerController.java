package ryu.park.shop.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;

import ryu.park.shop.service.GoodsService;
import ryu.park.shop.service.ManagerService;
import ryu.park.shop.type.OrderType;
import ryu.park.shop.utils.BoardPager;
import ryu.park.shop.utils.ImgStore;
import ryu.park.shop.utils.ImgStore.IMG_STORE_TYPE;
import ryu.park.shop.utils.JsonFormatter;
import ryu.park.shop.utils.SecurityUtils;
import ryu.park.shop.vo.CategoryHighVO;
import ryu.park.shop.vo.GoodsVO;
import ryu.park.shop.vo.UserVO;

/**
 * @Class		ManagerController.java
 * @packagename	ryu.park.shop.controller
 * @author		hodongryu
 * @since		2017.10.30.
 * @version		1.0
 * @see			매니저 업무 컨트롤러
 * <pre>
 * << 개정이력(Modification Information) >>
 *    수정일       수정자          수정내용
 *    -------      -------     -------------------
 *    2017.10.30.  hodongryu      최초작성
 * </pre>
 */
@RequestMapping("/manager/*")
@Controller
public class ManagerController {
	private static final Logger logger = LoggerFactory.getLogger(ManagerController.class);

	@Autowired
	private ManagerService managerService;
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private SecurityUtils securityUtils;

	/**
	 * @method		manager_home : 매니저 메인페이지 
	 * @param model
	 * @param req
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
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String manager_home(Model model, HttpServletRequest req) {
		logger.info("manager page");

		model.addAttribute("condition", "manager_main");
		return "manager_main";
	}  

	/**
	 * @method		loginManager : 매니저 로그인
	 * @param manager : 유저모델 
	 * @param bindingResult : 유저모델 바인딩 결과
	 * @param req
	 * @param res
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
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public void loginManager(@Valid UserVO manager, BindingResult bindingResult, HttpServletRequest req,
			HttpServletResponse res) throws IOException {

		logger.info("managerLogin");

		if (bindingResult.hasErrors()) {
			logger.info("valid error");
			res.getWriter().print("validError");
		} else {
			manager.setUserPassword(securityUtils.getHash(manager.getUserPassword()));
			UserVO managerResult = managerService.loginManager(manager);

			if (managerResult == null) {
				res.getWriter().print("invalid Email or Pwd");
			} else {
				HttpSession session = req.getSession(true);
				session.setAttribute("user", managerResult);
				res.getWriter().print("loginComplete");
			}
		}
	}

	/**
	 * @method		loginPage : 매니저 로그인 페이지 
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
	@RequestMapping(value = "login_page", method = RequestMethod.GET)
	public String loginPage() {
		return "manager/manager_login";
	}

	/**
	 * @method		addGoodsPage : 매니저 상품등록 페이지
	 * @param model
	 * @return
	 * @throws JsonProcessingException
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
	@RequestMapping(value = "goods/add_goods_page", method = RequestMethod.GET)
	public String addGoodsPage(Model model) throws JsonProcessingException {
		logger.info("add_goods_page");
		model.addAttribute("condition", "add_goods_page");

		Map<Integer, CategoryHighVO> category = goodsService.getGoodsCat(false);
		String jsonCategory = JsonFormatter.INSTANCE.getObjectMapper().writeValueAsString(category);
		model.addAttribute("categoryJson", jsonCategory);

		return "manager/goods/add_goods";
	}

	/**
	 * @method		addGoods : 매니저 상품등록
	 * @param goodsVO : 상품모델
	 * @param bindingResult : 상품모델 바인딩 결과
	 * @param req
	 * @param res
	 * @param model
	 * @param goodsMainPic : 상품메인사진
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
	@RequestMapping(value = "add_goods", method = RequestMethod.POST)
	public void addGoods(@Valid GoodsVO goodsVO, BindingResult bindingResult, HttpServletRequest req,
			HttpServletResponse res, Model model, @RequestParam MultipartFile goodsMainPic) throws IOException {
		logger.info("addGoods");

		if (bindingResult.hasErrors() || goodsMainPic.getSize() < 1) {
			logger.info("valid error" + bindingResult.getFieldError());
			res.getWriter().print("validError");
		} else {

			String fileUrl = new ImgStore().setRealRootPath(req.getSession().getServletContext().getRealPath("/"))
					.setImgStoreType(IMG_STORE_TYPE.IMG_GOODS_MAIN).setFileName(goodsMainPic.getOriginalFilename())
					.build(goodsMainPic)[0];

			goodsVO.setGoodsMainPicUrl(fileUrl);

			int r = goodsService.addGoods(goodsVO);
			logger.info("result:" + r);
			if (r > 0) {
				res.getWriter().print("completeAddedGoods");
				logger.info("inserted goods seq:" + goodsVO.getGoodsSeq());
			} else {
				res.getWriter().print("databaseError");
			}
		}  

	}

	/**
	 * @method		uploadImg : ck editor 이미지 업로드
	 * @param req
	 * @param res
	 * @param upload : 업로드 이미지 파일
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
	@RequestMapping(value = "upload_img", method = RequestMethod.POST)
	public void uploadImg(HttpServletRequest req, HttpServletResponse res, @RequestParam MultipartFile upload)
			throws IllegalStateException, IOException {
		logger.info("uploadImg");

		String fileUrl = new ImgStore().setRealRootPath(req.getSession().getServletContext().getRealPath("/"))
				.setImgStoreType(IMG_STORE_TYPE.IMG_GOODS_CONTENTS).setFileName(upload.getOriginalFilename())
				.build(upload)[0];

		res.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");
		String callback = req.getParameter("CKEditorFuncNum");

		res.getWriter().println("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction(" + callback
				+ ",'" + fileUrl + "','이미지를 업로드 하였습니다.'" + ")</script>");

	}

	/**
	 * @method		goodsManagePage : 매니저 상품 관리 페이지
	 * @param searchOption : 검색옵션
	 * @param keyword : 검색키워드
	 * @param curPage : 현재페이지
	 * @param goodsCatHighSeq : 상품상위카테고리 번호
	 * @param goodsCatMidSeq : 상품하위카테괼 번호
	 * @param orderType : 정렬방식
	 * @param order : ASC DESC
	 * @param model
	 * @return
	 * @throws JsonProcessingException
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
	@RequestMapping(value = "goods/goods_manage_page", method = { RequestMethod.GET, RequestMethod.POST })
	public String goodsManagePage(@RequestParam(defaultValue = "allGoods") String searchOption,
			@RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") int curPage,
			@RequestParam(defaultValue = "0") int goodsCatHighSeq, @RequestParam(defaultValue = "0") int goodsCatMidSeq,
			@RequestParam(defaultValue = "SEQ") OrderType orderType, @RequestParam(defaultValue = "DESC") String order,
			Model model) throws JsonProcessingException {
		logger.info("goods_manage_page");
		model.addAttribute("condition", "goods_manage_page");

		int count = goodsService.goodsTotalCount(searchOption, keyword, goodsCatHighSeq, goodsCatMidSeq);
		
		BoardPager boardPager = new BoardPager(count, curPage, 10);
		int start = boardPager.getPageBegin();
		int end = boardPager.getPageEnd();

		List<GoodsVO> list = goodsService.getGoodsList(start, end, searchOption, keyword, goodsCatHighSeq,
				goodsCatMidSeq, orderType, order);

		model.addAttribute("list", list);
		model.addAttribute("count", count);
		model.addAttribute("searchOption", searchOption);
		model.addAttribute("keyword", keyword);
		model.addAttribute("boardPager", boardPager);
		model.addAttribute("goodsCatHighSeq", goodsCatHighSeq);
		model.addAttribute("goodsCatMidSeq", goodsCatMidSeq);

		Map<Integer, CategoryHighVO> category = goodsService.getGoodsCat(true);

		String jsonCategory = JsonFormatter.INSTANCE.getObjectMapper().writeValueAsString(category);
		model.addAttribute("categoryJson", jsonCategory);

		return "manager/goods/goods_manage";
	}

	/**
	 * @method		goodsModifyPage : 매니저 상품 수정 페이지
	 * @param goodsSeq : 상품번호
	 * @param model
	 * @return
	 * @throws JsonProcessingException
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
	@RequestMapping(value = "goods/modify_goods_page/{goodsSeq}", method = RequestMethod.GET)
	public String goodsModifyPage(@PathVariable("goodsSeq") int goodsSeq, Model model) throws JsonProcessingException {
		logger.info("goods_modify_page");
		GoodsVO goodsVO = goodsService.getGoodsOne(goodsSeq);
		model.addAttribute("goodsVO", goodsVO);

		Map<Integer, CategoryHighVO> category = goodsService.getGoodsCat(false);
		String jsonCategory = JsonFormatter.INSTANCE.getObjectMapper().writeValueAsString(category);
		model.addAttribute("categoryJson", jsonCategory);

		return "manager/goods/modify_goods";
	}

	/**
	 * @method		modifyGoods : 상품수정 
	 * @param goodsVO : 상품모델
	 * @param bindingResult : 상품모델 바인딩 결과
	 * @param req
	 * @param res
	 * @param goodsMainPic
	 * @throws Exception
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
						.setImgStoreType(IMG_STORE_TYPE.IMG_GOODS_MAIN).setFileName(goodsMainPic.getOriginalFilename())
						.build(goodsMainPic)[0];

				goodsVO.setGoodsMainPicUrl(fileUrl);
			}

			goodsService.updateGoodsOne(goodsVO);
			res.getWriter().print("completeUpdatedGoods");
		}
	}

	/**
	 * @method		goodsDelete : 상품삭제
	 * @param goodsSeqList : 상품번호 리스트
	 * @param res
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
	@RequestMapping(value = "goods/delete_goods", method = RequestMethod.POST)
	public void goodsDelete(@RequestParam("goodsSeqList[]") List<Integer> goodsSeqList, HttpServletResponse res)
			throws IOException {
		logger.info("goodsDelete");
		int result = goodsService.deleteGoodsList(goodsSeqList);
		if (goodsSeqList.size() == result) {
			res.getWriter().print("completeDeleteGoods");
		} else if (result == -1) {
			res.getWriter().print("databaseError");
		} else {
			res.getWriter().print("retryPlz");
		}

	}

	/**
	 * @method		userManagePage : 유저관리 페이지
	 * @param searchOption : 유저검색 옵션
	 * @param keyword : 유저검색 키워드
	 * @param curPage : 현재페이지
	 * @param model
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
	@RequestMapping(value = "user/user_manage_page", method = { RequestMethod.GET, RequestMethod.POST })
	public String userManagePage(@RequestParam(defaultValue = "allUser") String searchOption,
			@RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") int curPage,
			Model model) {
		logger.info("user_manage_page");

		model.addAttribute("condition", "user_manage_page");

		int count = managerService.userTotalCount(searchOption, keyword);
		 
		BoardPager boardPager = new BoardPager(count, curPage, 10);
		int start = boardPager.getPageBegin();
		int end = boardPager.getPageEnd();

		List<UserVO> list = managerService.getUserList(start, end, searchOption, keyword);

		model.addAttribute("list", list);
		model.addAttribute("count", count);
		model.addAttribute("searchOption", searchOption);
		model.addAttribute("keyword", keyword);
		model.addAttribute("boardPager", boardPager);

		return "manager/user/user_manage";
	}

	/**
	 * @method		userModifyPage : 매니저 유저 수정 페이지
	 * @param email
	 * @param model
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
	@RequestMapping(value = "user/modify_user_page", method = RequestMethod.POST)
	public String userModifyPage(@RequestParam("userEmail") String email, Model model) {
		logger.info("user_modify_page");
		UserVO userVO = managerService.getUserOne(email);
		model.addAttribute("userVO", userVO);
		return "manager/user/modify_user";
	}

	/**
	 * @method		modifyUser : 매니저 유저 수정
	 * @param userVO : 유저모델
	 * @param bindingResult : 유저모델 바인딩 결과
	 * @param req
	 * @param res
	 * @throws Exception
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
	@RequestMapping(value = "user/modify_user", method = RequestMethod.POST)
	public void modifyUser(@Valid UserVO userVO, BindingResult bindingResult, HttpServletRequest req,
			HttpServletResponse res) throws Exception, IOException {
		logger.info("modifyUser");

		if (bindingResult.hasErrors()) {
			logger.info("valid error" + bindingResult.getFieldError());
			res.getWriter().print("validError");
		} else {
			int r = managerService.updateUserOne(userVO);
			if (r > 0)
				res.getWriter().print("completeUpdatedUser");
			else {
				res.getWriter().print("error");
			}
		}
	}
}
