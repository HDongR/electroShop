<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  
<div class="container">
	<h2>유저수정</h2>
	<br/> 

	<form id="modifyUserForm" method="post" class="form-horizontal">
		<div id="formGroupUserEmail" class="form-group">
			<label class="control-label col-sm-2" for="userEmail">Email:</label>
			<div class="col-sm-8">
				<input class="form-control" name="userEmail" id="userEmail" value="${userVO.userEmail}" disabled>
			</div>
		</div> 
	 
		
	 	<div id="formGroupUserNickname" class="form-group">
			<label class="control-label col-sm-2" for="userNickname">닉네임:</label>
			<div class="col-sm-6"> 
				<input id="userNickname" class="form-control" name="userNickname" value="${userVO.userNickname}" >
			</div>
			<div class="col-sm-2">
				<h6>
					특문제외 2~10자
				</h6>
			</div>
		</div>  
 		
 		<div id="formGroupAddrCity" class="form-group">
			<label class="control-label col-sm-2" for="userAddr">주소지:</label>
			<div class="col-sm-6"> 
				<input id="userAddr" class="form-control" name="userAddr" value="${userVO.userAddr}" disabled>
			</div> 
			<div class="col-sm-2">
				<button type="button" id="userAddrDialogBtn" class="btn btn-primary btn-md" data-toggle="modal" data-target="#addrModal">주소검색</button>
    
				 <!-- Modal -->
			 	<div class="modal fade" id="addrModal" role="dialog">
				    <div class="modal-dialog">
				    
				      <!-- Modal content-->
				      <div class="modal-content">
				        <div class="modal-header">
				          <button type="button" class="close" data-dismiss="modal">&times;</button>
				          <h4 class="modal-title">주소검색</h4>
				        </div>
				        <div class="modal-body">
				        		<div class="input-group col-sm-6">
								<input id="addrQuery" type="text" class="form-control" placeholder="주소지를 입력하세요">
								<div class="input-group-btn">
									<button id="addrQueryBtn" type="button"" class="btn btn-default">검색</button>
								</div>
							</div>
							<div class="text-center">  
								<table class="table">
							    <thead>
							      <tr> 
							        <th class="text-center ">우편번호</th>
							        <th class="text-center ">주소명(도로명)</th>
							        <th class="text-center ">주소명(지번)</th> 
							        <th class="text-center "></th> 
							      </tr>
							    </thead>
							    <tbody id="addrListBody"> 
							    </tbody>
							    </table> 
							</div> 
				        </div>
				     	<div align="center">
				        		<nav aria-label="Page navigation">
	  							<ul id="addrPageBox" class="pagination"> 
	  							
	  							</ul>
							</nav>
				        </div>
				      </div>
				      
				    </div>
			    </div>
			</div>
		</div>
		  
		<div id="formGroupAddrDetail" class="form-group">
			<label class="control-label col-sm-2" for="userAddrDetail">상세주소:</label>
			<div class="col-sm-8"> 
				<input id="userAddrDetail" class="form-control" name="userAddrDetail" value="${userVO.userAddrDetail}" >
			</div> 
		</div>
		<div class="form-group">
			<label class="control-label col-sm-2" for="modifyBtn"></label>
			<div class="col-sm-8"> 
				<input id="modifyBtn" class="btn btn-primary btn-md" type="button" value="유저 정보수정 완료">
			</div> 
		</div> 
	</form>     
</div>

<script type="text/javascript">  
	
	function updatePaging(pager){ 
		$("#addrPageBox").empty();
		var html = '';
	 	if(pager.curBlock > 1){
	 		html+='<li class="page-item">';
	 		html+='<a class="page-link" href="javascript:postalQuery(null,'+1+')" aria-label="First">';
	 		html+='<span aria-hidden="true">&laquo;</span></a></li>';
	 	}
		 
	 	<!-- **이전페이지 블록으로 이동 : 현재 페이지 블럭이 1보다 크면 [이전]하이퍼링크를 화면에 출력 -->
	 	if(pager.curBlock > 1){
	 		html+='<li class="page-item">';
	 		html+='<a class="page-link" href="javascript:postalQuery(null,'+pager.prevPage+')" aria-label="Previous">';
	 		html+='<span aria-hidden="true">&lsaquo;</span></a></li>';
	 	}
	 	 
	 	<!-- **하나의 블럭에서 반복문 수행 시작페이지부터 끝페이지까지 -->
	 	for(var i=pager.blockBegin; i<=(pager.blockBegin+Math.abs(pager.blockEnd-pager.blockBegin)); i++){
	 		if(i == pager.curPage){
	 			html+='<li class="page-item active"><a class="page-link" href="#">'+i+'</a></li>';
	 		}else{
	 			html+='<li class="page-item"><a class="page-link" href="javascript:postalQuery(null,'+i+')">'+i+'</a></li>';
	 		}
	 	}
	 	
	 	<!-- **다음페이지 블록으로 이동 : 현재 페이지 블럭이 전체 페이지 블럭보다 작거나 같으면 [다음]하이퍼링크를 화면에 출력 -->
	 	if(pager.curBlock <= pager.totBlock){
	 		html+='<li class="page-item">';
	 		html+='<a class="page-link" href="javascript:postalQuery(null,'+pager.nextPage+')" aria-label="Next">';
	 		html+='<span aria-hidden="true">&rsaquo;</span></a></li>';
	 	}
       
	 	<!-- **끝페이지로 이동 : 현재 페이지가 전체 페이지보다 작거나 같으면 [끝]하이퍼링크를 화면에 출력 -->
	 	if(pager.curPage <= pager.totPage){
	 		html+='<li class="page-item">';
	 		html+='<a class="page-link" href="javascript:postalQuery(null,'+pager.totPage+')" aria-label="Last">';
	 		html+='<span aria-hidden="true">&raquo;</span></a></li>';
	 	}
	 	
		$("#addrPageBox").append(html);
	}
	
</script>
<script type="text/javascript">  
	$("#addrQueryBtn").click(function(){
		var query = $("#addrQuery").val();
		postalQuery(query, 1);
	});
	 
	function postalQuery(_query, currentPage){
		if(_query == "" || _query == null){
			_query = $("#addrQuery").val();
		}
		var url = "/api/postal?query="+_query+"&currentPage="+currentPage;
		$.get(url,function(data, status){
			if(status == "success"){ 
				updatePostalList(data.addrList);
				updatePaging(data.pager);
			}else{
				alert("error")
				console.log(status);
			}
		});
	} 
	
	var addrGlobalList = null;
	
	function updatePostalList(addrList){  
		addrGlobalList = addrList;
		
		$("#addrListBody").empty();
	
		for(k in addrList){
			var html = '<tr>';
			html+='<td style="vertical-align:middle">'+addrList[k].postcd+'</td>';
			html+='<td style="vertical-align:middle">'+addrList[k].address+'</td>';
			html+='<td style="vertical-align:middle">'+addrList[k].addrjibun+'</td>';
			html+='<td style="vertical-align:middle">';
			html+='<a onclick="javascript:selectAddr('+addrList[k].addrNum;
			html+=');" class="btn btn-link" data-dismiss="modal">선택</a></td></tr>';
			
			$("#addrListBody").append(html);
		}
	}
	
	function selectAddr(addrNum){
		if(addrGlobalList != null){
			for(k in addrGlobalList){
				if(addrGlobalList[k].addrNum == addrNum){
					selectAddrResult(addrGlobalList[k]);
					break;
				}
			}
		} 
	}
	
	function selectAddrResult(selectResult){
		$("#userAddr").val(selectResult.address);
	}

	$("#modifyBtn").click(function(event) {
		$("#modifyBtn").prop("disabled", true);
		$.post("/user/modify_user",
			    {
			        userEmail: $("#userEmail").val(),
			        userNickname: $("#userNickname").val(),
			       	userAddr: $("#userAddr").val(),
			       	userAddrDetail: $("#userAddrDetail").val()
			    },
			    function(data, status){ 
					if(status == 'success'){
						if(data == 'completeUpdatedUser'){
							location.href = '/';
						}else if(data == 'validError'){
							alert('알맞게 모두 입력하세요');
						}else if(data == 'error'){
							alert('다시 시도해주세요');
						}
						$("#modifyBtn").prop("disabled", false); 
			        }else{
			        		alert("error");
						console.log(e);
						$("#modifyBtn").prop("disabled", false);
			        } 
			    });  
	
	}); 

</script>