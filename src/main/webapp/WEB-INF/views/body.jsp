<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">    
		
	<form name="form1" method="post" action="/">
        <select class="selectpicker" name="searchOption">
            <!-- 검색조건을 검색처리후 결과화면에 보여주기위해  c:out 출력태그 사용, 삼항연산자 -->
            <option value="allGoods" <c:out value="${searchOption == 'allGoods'?'selected':''}"/> >제목+내용</option>
            <option value="goods_subject" <c:out value="${searchOption == 'goods_subject'?'selected':''}"/> >이름</option>
            <option value="goods_contents" <c:out value="${searchOption == 'goods_contents'?'selected':''}"/> >내용</option>
        </select>
        
        <select id="catHighSelect" class="selectpicker" name="goodsCatHighSeq" onchange="selectMidbox();">  
        </select>
	   	<select id="catMidSelect" class="selectpicker" name="goodsCatMidSeq">  
	   	</select>
        <div class="input-group col-sm-6">
	      <input type="text" class="form-control" placeholder="제목이나 내용을 검색하세요" name="keyword" value="${keyword}">
	      <div class="input-group-btn">
	        <button class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search"></i></button>
	      </div>
	    </div>
    </form>
	<h5> ${count}개의 상품이 있습니다.</h5>
    
    
  <div class="row">
    <div class="col-sm-4">
      <div class="panel panel-primary">
        <div class="panel-heading">BLACK FRIDAY DEAL</div>
        <div class="panel-body"><img src="https://placehold.it/150x80?text=IMAGE" class="img-responsive" style="width:100%" alt="Image"></div>
        <div class="panel-footer">Buy 50 mobiles and get a gift card</div>
      </div>
    </div>
    <div class="col-sm-4"> 
      <div class="panel panel-primary">
        <div class="panel-heading">BLACK FRIDAY DEAL</div>
        <div class="panel-body"><img src="https://placehold.it/150x80?text=IMAGE" class="img-responsive" style="width:100%" alt="Image"></div>
        <div class="panel-footer">Buy 50 mobiles and get a gift card</div>
      </div>
    </div>
    <div class="col-sm-4"> 
      <div class="panel panel-primary">
        <div class="panel-heading">BLACK FRIDAY DEAL</div>
        <div class="panel-body"><img src="https://placehold.it/150x80?text=IMAGE" class="img-responsive" style="width:100%" alt="Image"></div>
        <div class="panel-footer">Buy 50 mobiles and get a gift card</div>
      </div>
    </div>
  </div>
</div><br><br>

<div class="container">    
  <div class="row">
    <div class="col-sm-4">
      <div class="panel panel-primary">
        <div class="panel-heading">BLACK FRIDAY DEAL</div>
        <div class="panel-body"><img src="https://placehold.it/150x80?text=IMAGE" class="img-responsive" style="width:100%" alt="Image"></div>
        <div class="panel-footer">Buy 50 mobiles and get a gift card</div>
      </div>
    </div>
    <div class="col-sm-4"> 
      <div class="panel panel-primary">
        <div class="panel-heading">BLACK FRIDAY DEAL</div>
        <div class="panel-body"><img src="https://placehold.it/150x80?text=IMAGE" class="img-responsive" style="width:100%" alt="Image"></div>
        <div class="panel-footer">Buy 50 mobiles and get a gift card</div>
      </div>
    </div>
    <div class="col-sm-4"> 
      <div class="panel panel-primary">
        <div class="panel-heading">BLACK FRIDAY DEAL</div>
        <div class="panel-body"><img src="https://placehold.it/150x80?text=IMAGE" class="img-responsive" style="width:100%" alt="Image"></div>
        <div class="panel-footer">Buy 50 mobiles and get a gift card</div>
      </div>
    </div>
  </div>
</div><br><br>

<div class="container">    
  <div class="row">
    <div class="col-sm-4">
      <div class="panel panel-primary">
        <div class="panel-heading">BLACK FRIDAY DEAL</div>
        <div class="panel-body"><img src="https://placehold.it/150x80?text=IMAGE" class="img-responsive" style="width:100%" alt="Image"></div>
        <div class="panel-footer">Buy 50 mobiles and get a gift card</div>
      </div>
    </div>
    <div class="col-sm-4"> 
      <div class="panel panel-primary">
        <div class="panel-heading">BLACK FRIDAY DEAL</div>
        <div class="panel-body"><img src="https://placehold.it/150x80?text=IMAGE" class="img-responsive" style="width:100%" alt="Image"></div>
        <div class="panel-footer">Buy 50 mobiles and get a gift card</div>
      </div>
    </div>
    <div class="col-sm-4"> 
      <div class="panel panel-primary">
        <div class="panel-heading">BLACK FRIDAY DEAL</div>
        <div class="panel-body"><img src="https://placehold.it/150x80?text=IMAGE" class="img-responsive" style="width:100%" alt="Image"></div>
        <div class="panel-footer">Buy 50 mobiles and get a gift card</div>
      </div>
    </div>
  </div>
</div><br><br>


<!-- 카테고리 선택 -->
<script type="text/javascript"> 

	var jsonData = <c:out value="${categoryJson}" escapeXml="false"/>;
	$(document).ready(function () {
		makeSelectHighbox(${goodsCatHighSeq});
		makeSelectMidbox(${goodsCatHighSeq}, ${goodsCatMidSeq});
	});
	
	function makeSelectHighbox(highSeq){
		for(key in jsonData){
			if(key==highSeq){
				$('#catHighSelect').append('<option value="'+ key +'" selected>' + jsonData[key].catHighName + '</option>');
			}else{
				$('#catHighSelect').append('<option value="'+ key +'">' + jsonData[key].catHighName + '</option>');
			}
		}
		$("#catHighSelect").selectpicker("refresh");
	}
	
	function makeSelectMidbox(highSeq, midSeq){ 
		$('#catMidSelect').empty();
	   	var midList = jsonData[highSeq].categoryMidList; 
	   	for(i=0; i< midList.length; i++){
	   		if(midSeq == midList[i].catMidSeq){
	   			$('#catMidSelect').append('<option value="'+ midList[i].catMidSeq +'" selected>' + midList[i].catMidName + '</option>');
	   		}else{
	   			$('#catMidSelect').append('<option value="'+ midList[i].catMidSeq +'">' + midList[i].catMidName + '</option>');
	   		}
	   	}
	   	$("#catMidSelect").selectpicker("refresh"); 
	}   
	
	function getCatSelectValue(catMidSeq){
		for(key in jsonData){
			var midList = jsonData[key].categoryMidList; 
		   	for(var i=0; i< midList.length; i++){
		   		if(catMidSeq == midList[i].catMidSeq){  
		   			return {"catHighName": jsonData[key].catHighName, "catMidName": midList[i].catMidName};
	   			}
		   	}	
		}
	}
	
	$(document).ready(function () {
		for(var i=0; i<${count}; i++){ 
			var midSeq = $("#catName" + i).text(); 
			var rsult = getCatSelectValue(midSeq); 
			$("#catName" + i).text( rsult.catHighName + '-' + rsult.catMidName);  
		} 
	}); 
	 
	function selectMidbox(){
 		var catHighSeq = $("#catHighSelect option:selected").val();
 		makeSelectMidbox(catHighSeq);
	}
</script>