package ryu.park.shop.vo;


import java.util.Date;
 
import javax.validation.constraints.Pattern; 

import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat; 
 
import ryu.park.shop.type.JoinType; 

@Alias("UserVO")
public class UserVO {
	@NotEmpty
	@Email
	private String userEmail;
	private String userPhoneNum; 
	private String userPassword;
	private String userAddrCity;
	private String userAddrArea;
	private String userAddrDetail;
	private String userName; 
	@Pattern(regexp="[0-9가-힣a-zA-Z]{2,10}")
	private String userNickname;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") 
	private Date userJoinDate; 
	private JoinType userJoinType; 
	
	public UserVO() {  
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPhoneNum() {
		return userPhoneNum;
	}

	public void setUserPhoneNum(String userPhoneNum) {
		this.userPhoneNum = userPhoneNum;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserAddrCity() {
		return userAddrCity;
	}

	public void setUserAddrCity(String userAddrCity) {
		this.userAddrCity = userAddrCity;
	}

	public String getUserAddrArea() {
		return userAddrArea;
	}

	public void setUserAddrArea(String userAddrArea) {
		this.userAddrArea = userAddrArea;
	}

	public String getUserAddrDetail() {
		return userAddrDetail;
	}

	public void setUserAddrDetail(String userAddrDetail) {
		this.userAddrDetail = userAddrDetail;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	public Date getUserJoinDate() {
		return userJoinDate;
	}

	public void setUserJoinDate(Date userJoinDate) {
		this.userJoinDate = userJoinDate;
	}

	public JoinType getUserJoinType() {
		return userJoinType;
	}

	public void setUserJoinType(JoinType userJoinType) {
		this.userJoinType = userJoinType;
	}

	
	
}
