<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">    
		
	 <h1> MAIN </h1>


<!-- 카테고리 선택 -->
<script type="text/javascript"> 

	/* var jsonData = <c:out value="${categoryJson}" escapeXml="false"/>;
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
	} */
</script>