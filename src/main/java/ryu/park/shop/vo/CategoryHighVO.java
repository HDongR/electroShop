package ryu.park.shop.vo;

import java.util.List; 

public class CategoryHighVO {
	// 카테고리_대분류_시퀀스 
    private Integer catHighSeq;

    // 카테고리_대분류_이름 
    private String catHighName;
    
    private List<CategoryMidVO> categoryMidList; 
    
	public List<CategoryMidVO> getCategoryMidList() {
		return categoryMidList;
	}

	public void setCategoryMidList(List<CategoryMidVO> categoryMidList) {
		this.categoryMidList = categoryMidList;
	}

	public Integer getCatHighSeq() {
        return catHighSeq;
    }

    public void setCatHighSeq(Integer catHighSeq) {
        this.catHighSeq = catHighSeq;
    }

    public String getCatHighName() {
        return catHighName;
    }

    public void setCatHighName(String catHighName) {
        this.catHighName = catHighName;
    }
    
    public static class CategoryMidVO {
    	// 카테고리_중분류_시퀀스 
        private Integer catMidSeq;

        // 카테고리_중분류_이름 
        private String catMidName;

        // 카테고리_대분류_시퀀스 
        private Integer catHighSeq;

        public Integer getCatMidSeq() {
            return catMidSeq;
        }

        public void setCatMidSeq(Integer catMidSeq) {
            this.catMidSeq = catMidSeq;
        }

        public String getCatMidName() {
            return catMidName;
        }

        public void setCatMidName(String catMidName) {
            this.catMidName = catMidName;
        }

        public Integer getCatHighSeq() {
            return catHighSeq;
        }

        public void setCatHighSeq(Integer catHighSeq) {
            this.catHighSeq = catHighSeq;
        }
    }

}
