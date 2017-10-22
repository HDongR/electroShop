package ryu.park.shop.vo;

public class AddrVO {
	private String postcd;
	private String address;
	private String addrjibun;
	private Integer addrNum;

	public AddrVO() {

	} 

	public AddrVO(String postcd, String address, String addrjibun, Integer addrNum) { 
		this.postcd = postcd;
		this.address = address;
		this.addrjibun = addrjibun;
		this.addrNum = addrNum;
	}

	
	public Integer getAddrNum() {
		return addrNum;
	}

	public void setAddrNum(Integer addrNum) {
		this.addrNum = addrNum;
	}

	public String getPostcd() {
		return postcd;
	}

	public void setPostcd(String postcd) {
		this.postcd = postcd;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddrjibun() {
		return addrjibun;
	}

	public void setAddrjibun(String addrjibun) {
		this.addrjibun = addrjibun;
	}

}
