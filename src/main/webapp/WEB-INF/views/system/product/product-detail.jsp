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
  	
  	<link href="${staticBase}/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.css" rel="stylesheet" type="text/css"/>
    <!-- BEGIN THEME GLOBAL STYLES -->
    <link href="${staticBase}/assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
    <link href="${staticBase}/assets/global/css/plugins.min.css" rel="stylesheet" type="text/css" />
    <link href="${staticBase}/assets/global/plugins/icheck/skins/all.css" rel="stylesheet" type="text/css" />
    <link href="${staticBase}/assets/global/plugins/bootstrap-tagsinput/bootstrap-tagsinput.css" rel="stylesheet" type="text/css" />
    <!-- END THEME GLOBAL STYLES -->
	<script src="${staticBase}/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
  	<script src="${staticBase}/assets/global/plugins/bootstrap-fileinput-master/js/fileinput.js" type="text/javascript"></script>
  	<script src="${staticBase}/assets/global/plugins/bootstrap-switch/js/bootstrap-switch.js" type="text/javascript"></script>
  	<script src="${staticBase}/assets/global/plugins/bootstrap-fileinput-master/js/fileinput_locale_zh.js" type="text/javascript"></script>
<style type="text/css">
	#imageWrapper img{
		height:100%
	}
	.bootstrap-switch .bootstrap-switch-handle-on.bootstrap-switch-primary{
	   background:#1bbc9b;
	}
	.bs-select{
	    float:left;
	}

</style>
<script type="text/javascript">
	$(document).ready(function(){
		
		$.post(BASE_PATH+'category/firstCatList',function(data){
			for(var i=0; i<data.length; i++){
    	    	$("#firstCat").append("<option value='"+data[i].id+"'>"+data[i].catName+"</option>");
    	    }
		});
		$("#firstCat").change(function(){   
			var id = $("#firstCat").val(); 
			$.post(BASE_PATH+'category/secondCatList',{id:id},function(data){
				$("#secondCat").empty();
				$("#secondCat").append("<option value=''>请选择二级分类</option>");
				for(var i=0; i<data.length; i++){
	    	    	$("#secondCat").append("<option value='"+data[i].id+"'>"+data[i].catName+"</option>");
	    	    }
			});
        });
		
		$.post(BASE_PATH+'label/labelGroupList',function(data){
			for(var i=0; i<data.length; i++){
    	    	$("#labelGroup").append("<option value='"+data[i].id+"'>"+data[i].groupName+"</option>");
    	    }
		});
		$("#labelGroup").change(function(){   
			var id = $("#labelGroup").val(); 
			$.post(BASE_PATH+'label/labelListByGroupId',{id:id},function(data){
				$("#labelValue").empty();
				$("#labelValue").append("<option value=''>请选择标签值</option>");
				for(var i=0; i<data.length; i++){
	    	    	$("#labelValue ").append("<option value='"+data[i].id+"'>"+data[i].labelName+"</option>");
	    	    }
			});
        });
	});
	
	/**************************************************************修改商品****************start***********************************/
	function updateProduct() {
	    $.ajax({
	        type: 'POST',
	        url: BASE_PATH+'product/updateProduct',
	        data: $('#updateProduct').serialize(),
	        dataType: 'json',
	        success: function (data) {
	        	if(data.status==200){
	        		alert(data.msg);
	        		window.location.href=BASE_PATH+'product/';
	        	}
	        },
	        error: function () {
	            alert(data.msg);
	        }
	    });
	}
	/**************************************************************修改商品****************end*************************************/
	
	/**************************************************************上传图片****************start*************************************/
	
	function uploadProdImg(){
		//判断是否有选择上传文件  
        var imgPath = $("#uploadFile").val();  
        if (imgPath == "") {  
            alert("请选择上传图片！");  
            return;  
        }  
        //判断上传文件的后缀名  
        var strExtension = imgPath.substr(imgPath.lastIndexOf('.') + 1);  
        if (strExtension != 'jpg' && strExtension != 'gif' && strExtension != 'png' && strExtension != 'bmp') {  
            alert("请选择图片文件");  
            return;  
        }
		//FormData对象上传文件
		var formData = new FormData();
		formData.append('file', $('#uploadFile')[0].files[0]);
		$.ajax({
		    url: BASE_PATH+'product/upload/productImg',
		    type: 'POST',
		    cache: false,
		    data: formData,
		    processData: false,
		    contentType: false
		}).done(function(data) {
			alert("上传成功");
		  	console.log("文件路径："+data.success);
		  	$('#imgUrlShow').val(data.success);
		}).fail(function(data) {
			alert("上传失败，请检查网络后重试"); 
		});
	}
	/**************************************************************上传图片****************end*************************************/
	/**************************************************************上传图片推荐图****************start*************************************/
	function uploadRecomImg(){
		//判断是否有选择上传文件  
        var imgPath = $("#recommendFile").val();  
        if (imgPath == "") {  
            alert("请选择上传图片！");  
            return;  
        }  
        //判断上传文件的后缀名  
        var strExtension = imgPath.substr(imgPath.lastIndexOf('.') + 1);  
        if (strExtension != 'jpg' && strExtension != 'gif' && strExtension != 'png' && strExtension != 'bmp') {  
            alert("请选择图片文件");  
            return;  
        }
		//FormData对象上传文件
		var formData = new FormData();
		formData.append('file', $('#recommendFile')[0].files[0]);
		$.ajax({
		    url: BASE_PATH+'product/upload/productImg',
		    type: 'POST',
		    cache: false,
		    data: formData,
		    processData: false,
		    contentType: false
		}).done(function(data) {
			alert("上传成功");
		  	console.log("文件路径："+data.success);
		  	$('#recommendUrl').val(data.success);
		}).fail(function(data) {
			alert("上传失败，请检查网络后重试"); 
		});
	}
	/**************************************************************上传图片推荐图****************end*************************************/
	function go(){
		history.go(-1);
	}
</script>
<!-- END PAGE LEVEL PLUGINS -->
</head>
<body>
<form action="#" id="updateProduct" class="form-horizontal" >
	<!-- BEGIN PAGE TITLE-->
	<div style="float: left; margin: 3px;">
		<h3 class="page-title">商品信息</h3>
	</div>
	<button type="button" class="btn green-meadow" style="float: right; margin: 10px 5px; padding: 3px 10px; line-height: 30px; color: white;" data-toggle="modal" onclick="go()">返回</button>
	<!-- END PAGE TITLE-->
	<div class="row">
		<div class="col-md-12">
			<!-- Begin: life time stats -->
			<div class="portlet light portlet-fit portlet-datatable bordered">
	
				<div class="portlet-body">
					<h4><span class="caption-subject font-dark sbold uppercase">商品信息</span></h4>
					
						<div class="form-body">
							<div class="alert alert-danger display-hide">
								<button class="close" data-close="alert"></button>
								部分项目填写有误，详细见下
							</div>
							<div class="alert alert-success display-hide">
								<button class="close" data-close="alert"></button>
								信息添加成功!
							</div>
							<input type="text" name="id" value="${prod.id}" style="display:none"/>
							<input type="text" name="caseCode" value="${prod.caseCode}" style="display:none"/>
							<input id="imgUrlShow" name="imgUrlShow" value="${prod.imgUrlShow}"  type="text" style="display:none">
							<input id="recommendUrl" name="recommendUrl" value="${prod.recommendUrl}"  type="text" style="display:none">
							<input type="text" value="${secondCatId}" onkeyup="this.value=value.replace(/[^\u4E00-\u9FA5\w\\,\\，\\.\\。\\(\\)]/g,'')" style="display:none"/>
							<input type="text" value="${labelId}" onkeyup="this.value=value.replace(/[^\u4E00-\u9FA5\w\\,\\，\\.\\。\\(\\)]/g,'')" style="display:none"/>
							
							<div class="form-group">
								<label class="control-label col-md-3">商品名称 <span
									class="required"> * </span>
								</label>
								<div class="col-md-3">
									<input type="text" name="prodName" value="${prod.prodName}" readonly="readonly" style="border: 0;"
										data-required="1" class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3">计划名称
								</label>
								<div class="col-md-3">
									<input type="text" name="planName" value="${prod.planName}" readonly="readonly" style="border: 0;"
										data-required="1" class="form-control" />
								</div>
							</div>		
							<div class="form-group">
								<label class="control-label col-md-3">保险公司名称 <span
									class="required"> * </span>
								</label>
								<div class="col-md-3">
									<input type="text" name="companyName" value="${prod.companyName}" readonly="readonly" style="border: 0;"
										 class="form-control"/>
								</div>
							</div>					
							<div class="form-group">
								<label class="control-label col-md-3">商品价格(分)<span
									class="required"> * </span>
								</label>
								<div class="col-md-3">
									<input type="text" name="defaultPrice" value="${prod.defaultPrice}" readonly="readonly" style="border: 0;"
										data-required="1" class="form-control" />
								</div>
							</div>							
							<div class="form-group">
								<label class="control-label col-md-3">保障期限<span
									class="required"> * </span>
								</label>
								<div class="col-md-3">
									<input type="text" name="insurDateLimit" value="${prod.insurDateLimit}" readonly="readonly" style="border: 0;"
										data-required="1" class="form-control" />
								</div>
							</div>							
							<div class="form-group">
								<label class="control-label col-md-3">承保年龄<span
									class="required"> * </span>
								</label>
								<div class="col-md-3">
									<input type="text" name="minInsurDate" value="${prod.minInsurDate}" readonly="readonly" style="border: 0;"
										data-required="1" class="form-control"/>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3">商品简述
								</label>
								<div class="col-md-3">
									<input type="text" name="description" value="${prod.description}" class="form-control"/>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3">商品分类<span
										class="required"> * </span>
								</label>
								<div class="col-md-3">
									<select name="firstCat" id="firstCat" style="width: 50%;" class="bs-select form-control" data-width="10px" >
				                        <option value="">请选择一级分类</option>
				                    </select>
				                    <select name="secondCatId" id="secondCat" style="width: 50%;" class="bs-select form-control" data-width="10px" >
				                        <option value="">请选择二级分类</option>
				                    </select>
								</div>
							</div>

                            <div class="form-group ">
                                <label class="control-label col-md-3">商品头图<span
									class="required"> * </span>
								</label>
                                <div class="col-md-9">
                                    <div class="fileinput fileinput-new" data-provides="fileinput">
                                        <div class="fileinput-preview thumbnail" id="imageWrapper" data-trigger="fileinput" style="width: 200px; height: 150px;" id="imgDiv">
                                        	<img id="showImg" src="${prod.imgUrlShow}" style="height: 100%;"/>
                                        </div>
                                        <div>
                                            <input type="file" name="uploadFile" id="uploadFile" style="display:none" >
                                            <a href="javascript:;" class="btn green-meadow" data-dismiss="fileinput">删除图片</a>
                                            <button type="button" class="btn green-meadow" onclick="uploadProdImg()">上传图片</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
								<label class="control-label col-md-3">导航标签<span
										class="required"> * </span>
								</label>
								<div class="col-md-3">
									<select name="labelId" id="labelId" class="bs-select form-control" data-width="100px">
				                        <option value="">选择导航标签</option>
				                        <c:forEach var="nav" items="${navlabList}">
							    			<option value="${nav.id }">${nav.labelName}</option>
							    		</c:forEach>
				                    </select>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3">为你推荐<span
										class="required"> * </span>
								</label>
								<div class="col-md-3">
                                    <div class="icheck-inline">
                                        <label><input type="radio" name="recommend" <c:if test="${prod.recommend == 0}">checked</c:if> class="icheck" value="0">是</label>
                                        <label><input type="radio" name="recommend" <c:if test="${prod.recommend == 1}">checked</c:if> class="icheck" value="1">否</label>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="form-group ">
                                <label class="control-label col-md-3">商品推荐图
								</label>
                                <div class="col-md-9">
                                    <div class="fileinput fileinput-new" data-provides="fileinput">
                                        <div class="fileinput-preview thumbnail" id="imageWrapper" data-trigger="fileinput" style="width: 200px; height: 150px;" id="imgDiv">
                                        	<img id="recomImg" src="${prod.recommendUrl}" style="height: 100%;"/>
                                        </div>
                                        <div>
                                            <input type="file" name="recommendFile" id="recommendFile" style="display:none" >
                                            <a href="javascript:;" class="btn green-meadow" data-dismiss="fileinput">删除图片</a>
                                            <button type="button" class="btn green-meadow" onclick="uploadRecomImg()">上传图片</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="form-group">
                                 <label class="control-label col-md-3">业务标签</label>
                                 <div class="col-md-9">
                                     <input type="text" value="" id="object_tagsinput" name="labelArray">
                                 </div>
                            </div>
							<div class="form-group">
								<label class="control-label col-md-3">标签组</label>
								<div class="col-md-3">
									<select name="labelGroup" id="labelGroup" style="width: 50%;" class="bs-select form-control" data-width="10px" >
				                        <option value="">请选择标签组</option>
				                    </select>
				                    <select name="labelValue" id="labelValue" style="width: 50%;" class="bs-select form-control" data-width="10px" >
				                        <option value="">请选择标签值</option>
				                    </select>
								</div>
								<div class="col-md-3">
                                    <a href="javascript:;" class="btn green-meadow" id="object_tagsinput_add">添加标签</a>
                                </div>
							</div>
                            
	                     </div>
	                     <div class="form-group">
							<label class="control-label col-md-3">
							</label>
							<div class="col-md-8">
						 	<button type="button" class="btn green-meadow" onclick="updateProduct()">确认修改</button>
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
<script src="${staticBase}/assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${staticBase}/assets/global/plugins/icheck/icheck.min.js" type="text/javascript"></script>
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script src="${staticBase}/assets/global/plugins/bootstrap-tagsinput/bootstrap-tagsinput.min.js" type="text/javascript"></script>
<!-- END PAGE LEVEL PLUGINS -->
<script src="${base}/assets/pages/system/product/product-detail.js" type="text/javascript"></script>
</content>
</body>
</html>
