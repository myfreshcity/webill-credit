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
</style>
<script type="text/javascript">
	$(document).ready(function(){
		$.ajax({
	        type: 'POST',
	        url: BASE_PATH+'category/cateJson',
	        dataType: 'json',
	        success: function (data) {
	        	//页面加载完毕后开始执行的事件
	    	    var second_obj = data;
	    	    for (var key in second_obj) {
	    	        $("#firstCat").append("<option value='"+key+"'>"+key+"</option>");
	    	    }
	    	    $("#firstCat").change(function(){
	    	        var now_firstCat = $(this).val();
	    	        $("#secondCat").html('<option value="">请选择二级分类</option>');
	    	        for(var k in second_obj[now_firstCat]) {
	    	            var now_secondCat = second_obj[now_firstCat][k];
	    	            $("#secondCat").append('<option value="'+now_secondCat+'">'+now_secondCat+'</option>');
	    	        }
	    	    });
	        }
	    });
	});
	
	/**************************************************************添加商品****************start***********************************/
	function addProduct() {
	    $.ajax({
	        type: 'POST',
	        url: BASE_PATH+'product/addProduct',
	        data: $('#addProduct').serialize(),
	        dataType: 'json',
	        success: function (data) {
	        	if(data.status==200){
	        		alert(data.msg);
	        		location.reload();
	        	}
	        },
	        error: function () {
	            alert(data.msg);
	        }
	    });
	}
	/**************************************************************添加商品****************end*************************************/
	
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
		  	$('#prodImgUrl').val(data.success);
		}).fail(function(data) {
			alert("上传失败，请检查网络后重试"); 
		});
	}
	/**************************************************************上传图片****************end*************************************/
	
</script>
<!-- END PAGE LEVEL PLUGINS -->
</head>
<body>
<form action="#" id="addProduct" class="form-horizontal" >
	<!-- BEGIN PAGE TITLE-->
	<div style="float: left; margin: 3px;">
		<h3 class="page-title">添加商品信息</h3>
	</div>

	<!-- END PAGE TITLE-->
	<div class="row">
		<div class="col-md-12">
			<!-- Begin: life time stats -->
			<div class="portlet light portlet-fit portlet-datatable bordered">
	
				<div class="portlet-body">
					<h4><span class="caption-subject font-dark sbold uppercase">基本信息</span></h4>
						<div class="form-body">
							<div class="alert alert-danger display-hide">
								<button class="close" data-close="alert"></button>
								部分项目填写有误，详细见下
							</div>
							<div class="alert alert-success display-hide">
								<button class="close" data-close="alert"></button>
								信息添加成功!
							</div>
							<input type="text" name="caseCode" value="${prod.caseCode}" style="display:none"/>
							<input type="text" name="companyLogoUrl" value="${prod.companyLogoUrl}" style="display:none"/>
							<input type="text" name="fristCategory" value="${prod.fristCategory}" style="display:none"/>
							<input type="text" name="secondCategory" value="${prod.secondCategory}" style="display:none"/>
							<input type="text" name="imgUrlSrc" value="${prod.imgUrlSrc}" style="display:none"/>
							<input type="text" name="prodRead" value="${prod.prodRead}" style="display:none"/>
							<input type="text" name="premiumTable" value="${prod.premiumTable}" style="display:none"/>
							<input id="prodImgUrl" name="imgUrlShow" value="${prod.imgUrlShow}"  type="text" style="display:none">
							<input type="text" value="${secondCat}" onkeyup="this.value=value.replace(/[^\u4E00-\u9FA5\w\\,\\，\\.\\。\\(\\)]/g,'')" style="display:none"/>
							<input type="text" value="${navlab}" onkeyup="this.value=value.replace(/[^\u4E00-\u9FA5\w\\,\\，\\.\\。\\(\\)]/g,'')" style="display:none"/>
							
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
								<label class="control-label col-md-3">最低承保年龄<span
									class="required"> * </span>
								</label>
								<div class="col-md-3">
									<input type="text" name="minInsurDate" value="${prod.minInsurDate}" readonly="readonly" style="border: 0;"
										data-required="1" class="form-control"/>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3">最高承保年龄 <span
									class="required"> * </span>
								</label>
								<div class="col-md-3">
									<input type="text" name="maxInsurDate" value="${prod.maxInsurDate}" readonly="readonly" style="border: 0;"
										data-required="1" class="form-control"/>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3">商品分类<span
										class="required"> * </span>
								</label>
								<div class="col-md-3">
									<select name="firstCat" id="firstCat" style="height:30px">
				                        <option value="">请选择一级分类</option>
				                    </select>
				                    <select name="secondCat" id="secondCat" style="height:30px">
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
                                        	<img id="showImg" src="${prod.imgUrlSrc}" style="height: 100%;"/>
                                        </div>
                                        <div>
                                            <input type="file" name="uploadFile" id="uploadFile" style="display:none" >
                                            <a href="javascript:;" class="btn green-meadow" data-dismiss="fileinput">删除图片</a>
                                            <button type="button" class="btn green-meadow" onclick="uploadProdImg()">上传图片</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
	                     </div>
						</div>
					
					<div class="portlet-body">
						<h4><span class="caption-subject font-dark sbold uppercase">选择标签</span></h4>
						<div class="table-container">
							<div class="form-group">
								<label class="control-label col-md-3">导航标签<span
										class="required"> * </span>
								</label>
								<div class="col-md-3">
									<select name="navlab" id="navlab" class="bs-select form-control" data-width="100px">
				                        <option value="">选择导航标签</option>
				                        <c:forEach var="nav" items="${navlabList}">
							    			<option value="${nav.id }">${nav.labelName}</option>
							    		</c:forEach>
				                    </select>
				                    
				                    <%-- <select class="form-control" style="width: 250px" id="equipmentnameid" name="equipmentnameid">
							    		<option value="">请选择...</option>
							    		<c:forEach var="Equipmentname" items="${equipmentnamelist}">
							    			<option value="${Equipmentname.id }">${Equipmentname.name}</option>
							    		</c:forEach>
							    	</select> --%>
								</div>
								
							</div>
							
							<div class="form-group">
								<label class="control-label col-md-3">是否上架<span
									class="required"> * </span>
								</label>
								<div class="col-md-3">
									<input type="checkbox" checked class="make-switch" data-size="normal" name="onAndOff" id="onAndOff">
								</div>								
							</div>
							<div class="form-group">
								<label class="control-label col-md-3">
								</label>
								<div class="col-md-8">
									如不选择商品上架，系统默认商品保存至库存中
								</div>
							</div>
							
							<!-- <div class="modal-footer" style="text-align: center;">
								<button type="button" class="btn green-meadow" onclick="addProduct()">完成，提交商品</button>
							 	<button type="button" class="btn green-meadow" onclick="addProduct()">完成，提交商品</button>
							</div> -->
						</div>
						<br>
						<div class="form-group">
							<label class="control-label col-md-3">
							</label>
							<div class="col-md-8">
								<!-- <button type="button" class="btn green-meadow" onclick="addProduct()">完成，提交商品</button> -->
						 	<button type="button" class="btn green-meadow" onclick="addProduct()">添加商品</button>
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
<script type="text/javascript">
function autoAskPrice(id) {
	alert(id);
    $.ajax({
        type: 'POST',
        url: BASE_PATH+'premiumOrder/autoAskPrice',
        data: {'id':id},
        dataType: 'json',
        success: function (data) {
        	if(data.status=='200'){
        		alert(data.msg);
        		window.location.reload();
        	}else if(data.status=='500'){
        		alert(data.msg);
        	}
        },
        error: function () {
            alert("错误！");
        }
    });
}
    $('#payImg').fileinput({
        language : 'zh', //设置语言
     	uploadUrl: BASE_PATH+'premiumOrder/upload/payImg', //上传的地址
        autoReplace : true,
        maxFileCount : 1, //表示允许同时上传的最大文件个数
        allowedFileExtensions : [ "jpg", "png"],//可接收的文件后缀
        uploadAsync: true, //默认异步上传
        showUpload: true, //是否显示上传按钮
        showRemove : true, //显示移除按钮
        showPreview : true, //是否显示预览
        showCaption: true,//是否显示标题
        browseClass: "btn btn-primary", //按钮样式     
        dropZoneEnabled: true,//是否显示拖拽区域
        enctype: 'multipart/form-data',
        validateInitialCount:false,
        overwriteInitial: true,
        previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
        initialPreview: [
        	 "<img src='${fileBase}${po.payImgUrl}' class='file-preview-image' alt='' title='' style='max-width:100%;max-height:100%' >"
        ],
        initialPreviewAsData: true,
        initialPreviewConfig: [
            {	key:1,
            	showDelete: false,
            	showUpload: false,
            	disabled: false,
		        deleteUrl: "/site/file-delete"
            }
        ]
    }).on("fileuploaded", function(e, data) { //异步上传返回结果处理
        var res = data.response;
    	console.log(res.success);
    	if(res.pathFlag=='payImg'){
	    	$('#payImgUrl').val(res.success);
    	}
    }).on('filepreupload', function(event, data, previewId, index) { //上传前
        var form = data.form;
        var files = data.files;
        var extra = data.extra;
        var response = data.response;
        var reader = data.reader;
    }); 
    
    $('#payImg').on('fileselect', function(event, numFiles, label) {
    	$('.file-preview-thumbnails .file-preview-initial').remove();
    });

</script>
</body>
</html>
