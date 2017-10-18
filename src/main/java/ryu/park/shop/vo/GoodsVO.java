package ryu.park.shop.vo;

import java.util.Date;
 
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Alias("GoodsVO")
public class GoodsVO {
	private Integer goodsSeq;
	@NotEmpty
	@Size(min=0, max=100)
	private String subject;
	@NotNull
	@Range(min=0)
	private Integer cost;
	private String mainPicUrl;
	@NotEmpty
	private String contents;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") 
	private Date crtDate;
	@NotNull
	@Range(min=0)
	private Integer stock;
	
	public GoodsVO() {
		
	}
	
	public void setGoodsSeq(Integer goodsSeq) {
		this.goodsSeq = goodsSeq;
	} 
	public Integer getGoodsSeq() {
		return goodsSeq;
	}  
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public Integer getCost() {
		return cost;
	}
	public void setCost(Integer cost) {
		this.cost = cost;
	}
	public String getMainPicUrl() {
		return mainPicUrl;
	}
	public void setMainPicUrl(String mainPicUrl) {
		this.mainPicUrl = mainPicUrl;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}

	public Date getCrtDate() {
		return crtDate;
	}

	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	} 
	  
	
	
}
