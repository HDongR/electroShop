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
	private String goodsSubject;
	@NotNull
	@Range(min=0)
	private Integer goodsCost;
	private String goodsMainPicUrl;
	@NotEmpty
	private String goodsContents;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") 
	private Date goodsCrtDate;
	@NotNull
	@Range(min=0)
	private Integer goodsStock;
	
	public GoodsVO() {
		
	}

	public Integer getGoodsSeq() {
		return goodsSeq;
	}

	public void setGoodsSeq(Integer goodsSeq) {
		this.goodsSeq = goodsSeq;
	}

	public String getGoodsSubject() {
		return goodsSubject;
	}

	public void setGoodsSubject(String goodsSubject) {
		this.goodsSubject = goodsSubject;
	}

	public Integer getGoodsCost() {
		return goodsCost;
	}

	public void setGoodsCost(Integer goodsCost) {
		this.goodsCost = goodsCost;
	}

	public String getGoodsMainPicUrl() {
		return goodsMainPicUrl;
	}

	public void setGoodsMainPicUrl(String goodsMainPicUrl) {
		this.goodsMainPicUrl = goodsMainPicUrl;
	}

	public String getGoodsContents() {
		return goodsContents;
	}

	public void setGoodsContents(String goodsContents) {
		this.goodsContents = goodsContents;
	}

	public Date getGoodsCrtDate() {
		return goodsCrtDate;
	}

	public void setGoodsCrtDate(Date goodsCrtDate) {
		this.goodsCrtDate = goodsCrtDate;
	}

	public Integer getGoodsStock() {
		return goodsStock;
	}

	public void setGoodsStock(Integer goodsStock) {
		this.goodsStock = goodsStock;
	}
	
	
}
