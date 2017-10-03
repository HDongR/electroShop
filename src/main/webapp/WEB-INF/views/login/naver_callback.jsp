<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>

<script type="text/javascript">
	var id = '<spring:eval expression="@config.getProperty('naverclientkey')" />';
	var naver_id_login = new naver_id_login(id, "http://localhost:8080/user/login/naver_callback");
	naver_id_login.get_naver_userprofile("naverSignInCallback()");

	function naverSignInCallback() {
		window.opener.checkEmail('NAVER', naver_id_login
				.getProfileData('email'), naver_id_login
				.getProfileData('nickname'));
		window.close();
	}
</script>