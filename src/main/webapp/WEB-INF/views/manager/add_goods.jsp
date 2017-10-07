<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript" src="/resources/ckeditor4/ckeditor.js"></script>
 
<div class="form-group">
 <label for="noticeContent">내용</label>
 <textarea class="form-control" name="noticeContent" id="noticeContent" cols="50" rows="15"></textarea>
 <script>
 CKEDITOR.replace('noticeContent', {'filebrowserUploadUrl' : '/manager/upload_img'});
 </script>
</div>

		