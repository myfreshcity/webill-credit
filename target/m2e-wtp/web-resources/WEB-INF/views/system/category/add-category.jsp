<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/comtag.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 
<html lang="en">
<head>
<!-- BEGIN PAGE LEVEL PLUGINS -->
	<link href="${staticBase}/assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
	<link href="${staticBase}/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
	<link href="${staticBase}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
	<!-- BEGIN GLOBAL MANDATORY STYLES -->
  	<link href="${staticBase}/assets/global/plugins/bootstrap-fileinput-master/css/fileinput.css" rel="stylesheet" type="text/css"/>
  	
	<script src="${staticBase}/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
  	<script src="${staticBase}/assets/global/plugins/bootstrap-fileinput-master/js/fileinput.js" type="text/javascript"></script>
  	<script src="${staticBase}/assets/global/plugins/bootstrap-fileinput-master/js/fileinput_locale_zh.js" type="text/javascript"></script>
<style type="text/css">
	#imageWrapper img{
		height:100%
	}
	.bootstrap-switch .bootstrap-switch-handle-on.bootstrap-switch-primary{
	   background:#1bbc9b;
	}
</style>
<script type="text/javascript">
	$(document).ready(function(){
		$.post(BASE_PATH+'category/firstCatList',function(data){
			for(var i=0; i<data.length; i++){
    	    	$("#firstCat").append("<option value='"+data[i].id+"'>"+data[i].catName+"</option>");
    	    }
		});
	});
	/**************************************************************检查分类名称****************start***********************************/
	function checkCatName(){
		$.post(BASE_PATH+'category/checkCatName',{catName:$('#catName').val()},function(data){
			if(data.status == 200){
        		$('#catFlag').html(data.msg);
        	}else {
        		$('#catFlag').html("");
        	}
        });
	}
	/**************************************************************检查分类名称****************end*************************************/
	/**************************************************************添加商品****************start***********************************/
	function addCategory() {
		var catName = $('#catName').val();
		if(catName !== null && catName !== undefined && catName !== ''){
			$.post(BASE_PATH+'category/addCat', $('#category').serialize(), function(data){
				if(data.status == 200){
	        		alert(data.msg);
	        		window.location.href=BASE_PATH+"/category/";
	        	}else if(data.status == 300){
	        		$('#catFlag').html(data.msg);
	        	}
			});
		}else {
			alert("请填写分类名称！");
		}
	}
	/**************************************************************添加商品****************end*************************************/
	
	/**************************************************************上传图片****************start*************************************/
	function uploadImg(){
		//判断是否有选择上传文件  
        var imgPath = $("#uploadFile").val();  
        var imagSize =  document.getElementById("uploadFile").files[0].size;
        
        if (imgPath == "") {  
            alert("请选择上传图片！");  
            return;  
        }  
        //判断上传文件的后缀名  
        var strExtension = imgPath.substr(imgPath.lastIndexOf('.') + 1);  
        if (strExtension != 'jpg' && strExtension != 'gif' && strExtension != 'png' && strExtension != 'bmp') {  
            alert("请选择图片文件！");  
            return;  
        } else{
      	  	if(imagSize > 204800){
      	   		alert("图片尺寸请不要大于200KB！");
      	   		return;
      	  	}
        }
		//FormData对象上传文件
		var formData = new FormData();
		formData.append('file', $('#uploadFile')[0].files[0]);
		$.ajax({
		    url: BASE_PATH+'category/upload/cateIcon',
		    type: 'POST',
		    cache: false,
		    data: formData,
		    processData: false,
		    contentType: false
		}).done(function(data) {
			alert("上传成功");
		  	console.log("文件路径："+data.success);
		  	$('#iconUrl').val(data.success);
		}).fail(function(data) {
			alert("上传失败，请检查网络后重试"); 
		});
	}
	/**************************************************************上传图片****************end*************************************/
	
</script>
<!-- END PAGE LEVEL PLUGINS -->
</head>
<body>
<form action="#" id="category" class="form-horizontal" >
	<!-- BEGIN PAGE TITLE-->
	<div style="float: left; margin: 3px;">
		<h3 class="page-title">添加分类</h3>
	</div>

	<!-- END PAGE TITLE-->
	<div class="row">
		<div class="col-md-12">
			<!-- Begin: life time stats -->
			<div class="portlet light portlet-fit portlet-datatable bordered">
	
				<div class="portlet-body">
						<div class="form-body">
							<div class="alert alert-danger display-hide">
								<button class="close" data-close="alert"></button>
								部分项目填写有误，详细见下
							</div>
							<div class="alert alert-success display-hide">
								<button class="close" data-close="alert"></button>
								信息添加成功!
							</div>
							<input id="iconUrl" name="iconUrl" type="text" style="display:none">
							<div class="form-group">
								<label class="control-label col-md-3">分类名称 <span
									class="required"> * </span>
								</label>
								<div class="col-md-3">
									<input type="text" id="catName" name="catName" class="form-control" onblur="checkCatName()" data-required="1"/>
								</div>
								<div class="col-md-3" style="padding-top: 7px; color: red" id="catFlag">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3">上级分类 
								</label>
								<div class="col-md-3">
									<select name="parentId" id="firstCat" class="bs-select form-control" data-width="100px">
				                        <option value="">请选择分类</option>
				                    </select>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3">
								</label>
								<div class="col-md-8">
									不选择分类默认为顶级分类
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3">排序 </label>
								<div class="col-md-3">
									<input type="text" name="sortIndex" class="form-control" />
								</div>
							</div>							
							<div class="form-group">
								<label class="control-label col-md-3">是否显示
								</label>
								<div class="col-md-3">
									<input type="checkbox" checked class="make-switch" data-size="normal" data-on-text="是" data-off-text="否" name="turnSwitch" id="turnSwitch">
								</div>								
							</div>						
                            <div class="form-group ">
                                <label class="control-label col-md-3">分类图标
								</label>
                                <div class="col-md-9">
                                    <div class="fileinput fileinput-new" data-provides="fileinput">
                                        <div class="fileinput-preview thumbnail" id="imageWrapper" data-trigger="fileinput" style="width: 200px; height: 150px;" id="imgDiv">
                                        </div>
                                        <div>
                                            <input type="file" name="uploadFile" id="uploadFile" style="display:none" >
                                            <a href="javascript:;" class="btn green-meadow" data-dismiss="fileinput">删除图片</a>
                                            <button type="button" class="btn green-meadow" onclick="uploadImg()">上传图片</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
								<label class="control-label col-md-3">
								</label>
								<div class="col-md-8">
									图标尺寸为18*18比例，大小不能超过200KB，图片只能为jpg、png、gif格式
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3">分类描述 
								</label>
								<div class="col-md-3">
									<textarea rows="5" cols="100" name="description" class="col-xs-12"></textarea>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3">
								</label>
								<div class="col-md-8">
									<button type="button" class="btn green-meadow" onclick="addCategory()">提交</button>
								</div>
							</div>
	                     </div>
						</div>
				</div>
			</div>
		</div>
</form>	

<content tag="pageJs"> <!-- BEGIN PAGE LEVEL PLUGINS --> 
<script src="${staticBase}/assets/global/plugins/select2/js/select2.full.min.js" type="text/javascript"></script>
<script src="${staticBase}/assets/global/plugins/bootstrap-select/js/bootstrap-select.min.js" type="text/javascript"></script>
<script src="${staticBase}/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript" charset="UTF-8"></script>
<script src="${staticBase}/assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script> 
<script src="${staticBase}/assets/layouts/layout/scripts/table-datatables-managed.min.js"></script>
<script src="${staticBase}/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${staticBase}/assets/global/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>
<script src="${base}/assets/global/scripts/datatable.js" type="text/javascript"></script>
<script src="${staticBase}/assets/global/plugins/jquery-multi-select/js/jquery.multi-select.js" type="text/javascript"></script>
<script src="${staticBase}/assets/global/plugins/jquery.quicksearch.js" type="text/javascript"></script>
<script src="${base}/assets/pages/system/premiumOrder/detail-list.js" type="text/javascript"></script>
<script src="${staticBase}/assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.js" type="text/javascript"></script>
</content>
</body>
</html>
