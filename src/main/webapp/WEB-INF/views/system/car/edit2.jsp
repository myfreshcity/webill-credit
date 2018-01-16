<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/comtag.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">
<head>
	<link href="${staticBase}/assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
	<link href="${staticBase}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
	<!-- BEGIN GLOBAL MANDATORY STYLES -->
	<link href="${staticBase}/assets/global/plugins/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css"/>
  	<link href="${staticBase}/assets/global/plugins/bootstrap-fileinput-master/css/fileinput.css" media="all" rel="stylesheet" type="text/css"/>
  	
	<script src="${staticBase}/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
  	<script src="${staticBase}/assets/global/plugins/bootstrap-fileinput-master/js/fileinput.js" type="text/javascript"></script>
  	<script src="${staticBase}/assets/global/plugins/bootstrap-fileinput-master/js/fileinput_locale_zh.js" type="text/javascript"></script>
  	<script src="${staticBase}/assets/global/plugins/bootstrap-fileinput-master/js/bootstrap.min.js" type="text/javascript"></script>
    <!-- END GLOBAL MANDATORY STYLES -->
    <link rel="shortcut icon" href="favicon.ico" /> 
</head>
<body>

<!-- BEGIN PAGE TITLE-->
<h3 class="page-title"> 车辆信息
    <small></small>
</h3>
<!-- END PAGE TITLE-->
<form action="#" id="form_sample_3" class="form-horizontal" >
<div class="row">
    <div class="col-md-12">
        <!-- BEGIN VALIDATION STATES-->
        <div class="portlet light portlet-fit portlet-form bordered">
            <div class="portlet-title">
                <div class="caption">
                    <i class="icon-settings font-dark"></i>
                    <span class="caption-subject font-dark sbold uppercase">车辆编辑</span>
                </div>
            </div>
            <div class="portlet-body">
                <!-- BEGIN FORM-->
                	<input name="curUserId" value="${ci.curUserId}" style="display:none" >
					<input name="curId" id="curId" value="${ci.curId}" style="display:none" >
					<input name="id" id="id" value="${ci.id}" style="display:none" >
                    <div class="form-body">
                        <div class="alert alert-danger display-hide">
                            <button class="close" data-close="alert"></button> 部分项目填写有误，详细见下 
                            </div>
                        <div class="alert alert-success display-hide">
                            <button class="close" data-close="alert"></button> 信息添加成功!
                         </div>
                        <div class="form-group">
                            <label class="control-label col-md-3">车牌号
                            </label>
                            <div class="col-md-4">
                                <input type="text" name="licenseNo" id="licenseNo" value="${ci.licenseNo}" data-required="1" class="form-control"   />
                            </div>
                            <c:if test="${ci.curId==''||ci.curId==null}">
                            	<button onclick="validatingCar()" class="btn green" type="button">验证车辆</button>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-3">车主姓名
                            </label>
                            <div class="col-md-4">
                                <input type="text" name="carOwner" id="carOwner" value="${ci.carOwner}" data-required="1" class="form-control"  />
                             </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-3">车主号码
                            </label>
                            <div class="col-md-4">
                                <input type="text" name="curMobileNo" id="curMobileNo" value="${ci.curMobileNo}" data-required="1" class="form-control"  />
                             </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-3">车架号
                            </label>
                            <div class="col-md-4">
                                <input type="text" name="frameNo" id="frameNo" value="${ci.frameNo}" data-required="1" class="form-control"  />
                             </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-3">发动机号
                            </label>
                            <div class="col-md-4">
                                <input type="text" name="engineNo" id="engineNo" value="${ci.engineNo}" data-required="1" class="form-control"  />
                             </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-3">品牌型号
                            </label>
                            <div class="col-md-4">
                                <input type="text" name="brandName" id="brandName" value="${ci.brandName}" data-required="1" class="form-control"  />
                             </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-3">车座数
                            </label>
                            <div class="col-md-4">
                                <input type="text" name="seatCount" id="seatCount" value="${ci.seatCount}" data-required="1" class="form-control"/>
                             </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-3">车辆价格
                            </label>
                            <div class="col-md-4">
                                <input type="text" name="purchasePrice" id="purchasePrice" value="${ci.purchasePrice}" data-required="1" class="form-control" />
                             </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-3">上期投保公司
                            </label>
                            <div class="col-md-4">
                                <input type="text" name="insurerCom" id="insurerCom" value="${ci.insurerCom}" data-required="1" class="form-control"  />
                             </div>
                        </div>
						<div class="form-group">
                            <label class="control-label col-md-3">车险到期时间</label>
                            <div class="col-md-4">
                                <div class="input-group input-large date-picker input-daterange" data-date-format="yyyy-mm-dd">
                                    <input type="text" class="form-control form-filter" name="prmEndTime" id="prmEndTime" readonly value="<fmt:formatDate value='${ci.prmEndTime}' pattern='yyyy-MM-dd'/>"/>
                                    <span class="input-group-btn">
                                        <button class="btn default date-reset" type="button">
                                            <i class="fa fa-times"></i>
                                        </button>
                                        <button class="btn default date-set" type="button">
                                            <i class="fa fa-calendar"></i>
                                        </button>
                                    </span>                                            
                                </div>
                                <!-- /input-group -->
                            </div>
                        </div>                        
                       	<div class="form-group">
                            <label class="control-label col-md-3">补充资料</label>
                            <div class="col-md-4">
	                            <button type="button" style=" line-height: 30px;" data-toggle="modal" data-target="#myModal" class="btn green">编辑</button>
				             </div>
                        </div>
                    </div>
                    <div class="form-actions">
                        <div class="row">
                            <div class="col-md-offset-3 col-md-9">
                                <button type="submit" class="btn green">提交</button>
                                <button type="button" class="btn default" onclick="javascript:history.back(-1);">取消</button>
                            </div>
                        </div>
                    </div>
                <!-- END FORM-->
            </div>
            <!-- END VALIDATION STATES-->
        </div>
    </div>
</div>
</form>

<!-------------------------------------------------------- 补充资料--------------------------------------start---------------------------------------->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document" style="width:1100px">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h3 class="modal-title" id="myModalLabel">编辑资料</h3>
      </div>
      <div class="modal-body" >
			<form action="" id="getCarInfo" class="form-horizontal" method="post">
				<!-- END PAGE TITLE-->
				<div class="row">
					<div class="col-md-12">
						<div class="portlet light portlet-fit portlet-datatable bordered">
							<div class="portlet-body">
								<div class="form-body">
									<div class="form-group">
							            <label class="control-label col-md-2">身份证
							                <span class="required"> * </span>
							            </label>
							            <div class="col-md-8" style="width: 35%;" >
							            	<input id="idCardP" type="file" class="file" name="idCardP" multiple="multiple" class="file-loading"ype="file" multiple>
							             </div>
							            <div class="col-md-8" style="width: 35%;">
							            	<input id="idCardN" type="file" class="file" name="idCardN" multiple="multiple" class="file-loading">
							             </div>
							       	</div>      
									<div class="form-group">
							            <label class="control-label col-md-2">驾驶证
							                <span class="required"> * </span>
							            </label>
							            <div class="col-md-8" style="width: 35%;">
							            	<input id="vehicleLicenseP" type="file" class="file" name="vehicleLicenseP" multiple="multiple" class="file-loading">
							             </div>
							             <input type="hidden" name="" id="logo">
							            <div class="col-md-8" style="width: 35%;">
							            	<input id="vehicleLicenseN" type="file" class="file" name="vehicleLicenseN" multiple="multiple" class="file-loading">
							             </div>
							       	</div>      
									<div class="form-group">
							            <label class="control-label col-md-2">行驶证
							                <span class="required"> * </span>
							            </label>
							            <div class="col-md-8" style="width: 35%;">
							            	<input id="driveLicenseP" type="file" class="file" name="driveLicenseP" multiple="multiple" class="file-loading">
							             </div>
							             <input type="hidden" name="" id="logo">
							            <div class="col-md-8" style="width: 35%;">
							            	<input id="driveLicenseN" type="file" class="file" name="driveLicenseN" multiple="multiple" class="file-loading">
							             </div>
							       	</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>      
            <br>
			<form action="" id="supplyData" class="form-horizontal" style="display:none">
				<input name="id" value="${ci.curId}" style="display:none" >
				<input id="idCardPPath" name="idCardPPath" value="${ci.idCardPPath}"  type="text" style="display:none">
				<input id="idCardNPath" name="idCardNPath" value="${ci.idCardNPath}" style="display:none" >
				<input id="vehicleLicensePPath" name="vehicleLicensePPath" value="${ci.vehicleLicensePPath}"  type="text" style="display:none" >
				<input id="vehicleLicenseNPath" name="vehicleLicenseNPath" value="${ci.vehicleLicenseNPath}"  type="text" style="display:none" >
				<input id="driveLicensePPath" name="driveLicensePPath" value="${ci.driveLicensePPath}"  type="text" style="display:none" >
				<input id="driveLicenseNPath" name="driveLicenseNPath" value="${ci.driveLicenseNPath}" type="text" style="display:none" >
			</form>	
	  </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" onclick="saveCarUserRel()">确定</button>
      </div>
    </div>
  </div>
</div>

<!--------------------------------------------------------补充资料---------------------------end------------------------------------------------------>					
<script type="text/javascript">
	
    
</script>

<content tag="pageJs">
    <!-- BEGIN CORE PLUGINS -->
    <script src="${staticBase}/assets/global/scripts/datatable.js" type="text/javascript"></script>
    <script src="${staticBase}/assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
    <script src="${staticBase}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
    <script src="${staticBase}/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
    <!-- END CORE PLUGINS -->
	
	<script src="${staticBase}/assets/global/scripts/datatable.js" type="text/javascript"></script> 
	<script src="${staticBase}/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script> 
	<script src="${staticBase}/assets/global/plugins/jquery-validation/js/localization/messages_zh.min.js" type="text/javascript"></script>
	<script src="${staticBase}/assets/global/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>
	<script src="${staticBase}/assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
	<script src="${staticBase}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
	<script src="${staticBase}/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
	<script src="${staticBase}/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
	<script src="${staticBase}/assets/global/plugins/bootstrap-datepicker/js/bootstrap-timepicker.min.js" type="text/javascript"></script>
	<script src="${staticBase}/assets/global/plugins/components-date-time-pickers.js" type="text/javascript"></script>
	<script src="${staticBase}/assets/pages/system/car/edit2.js" type="text/javascript"></script>
</content>
	 <!-- BEGIN PAGE LEVEL PLUGINS -->
    <!--BEGIN 引入上传JS -->
    <!--END 引入上传JS -->
     <script>
        jQuery(document).ready(function() {
        	
        });
    	function saveCarUserRel() {
    	    $.ajax({
    	        type: 'POST',
    	        url: BASE_PATH+'car/saveCarUserRel',
    	        data: $('#supplyData').serialize(),
    	        dataType: 'json',
    	        success: function (data) {
    	        	if(data.status==200){
    	        		alert(data.msg);
    	        		location.reload();
    	        	}
    	        },
    	        error: function () {
    	            alert("错误！");
    	        }
    	    });
    	}
    	function validatingCar(){
			var licenseNo = $("#licenseNo").val();    		
    		$.ajax({
    	        type: 'POST',
    	        url: BASE_PATH+'user/getCarInfo',
    	        data: {'licenseNo':licenseNo},
    	        dataType: 'json',
    	        success: function (data) {
   	        		//获取车辆信息成功，页面回显
   	        		if(data.status=='500'){
   	        			//未查到车辆信息，提示客服手动填入车辆信息
						alert("未查到该车辆信息，可以直接添加！");
   	        		}else{
   	        			alert("车辆信息获取成功！");
	        		 	$('#id').val(data.id);
	        		 	$('#curId').val(data.curId);
	        		 	$('#licenseNo').val(data.licenseNo);
	        		 	$('#carOwner').val(data.carOwner);
	        		 	$('#curMobileNo').val(data.curMobileNo);
	        		 	$('#frameNo').val(data.frameNo);
	        		 	$('#engineNo').val(data.engineNo);
	        		 	$('#brandName').val(data.brandName);
	        		 	$('#seatCount').val(data.seatCount);
	        		 	$('#purchasePrice').val(data.purchasePrice);
	        		 	$('#insurerCom').val(data.insurerCom);
	        		 	
	        		 	var month=0;
	            	    var day=0;
	            	    var date=new Date(data.prmEndTime); 
	    				if ((date.getMonth() + 1) >= 10) {
	    					month = date.getMonth() + 1;
	    				} else {
	    					month = "0" + (date.getMonth() + 1);
	    				}
	    				if (date.getDate() >= 10) {
	    					day = date.getDate();
	    				} else {
	    					day = "0" + date.getDate();
	    				}
	    				var setDate = date.getFullYear() + "-" + month + "-" + day;
	        		 	$('#prmEndTime').val(setDate);
   	        		}
    	        },
    	        error: function () {
    	            alert("错误！");
    	        }
    	    });
    	}
    	$("#idCardP").fileinput({
            language : 'zh', //设置语言
         	uploadUrl: BASE_PATH+'car/upload/photo', //上传的地址
            autoReplace : true,
            allowedFileExtensions : [ "jpg", "png"],//可接收的文件后缀
            uploadAsync: true, //默认异步上传
            showUpload: true, //是否显示上传按钮
            showRemove : false, //显示移除按钮
            showPreview : true, //是否显示预览
            showCaption: false,//是否显示标题
            browseClass: "btn btn-primary", //按钮样式     
            dropZoneEnabled: true,//是否显示拖拽区域
            maxFileCount: 1, //表示允许同时上传的最大文件个数
            enctype: 'multipart/form-data',
            validateInitialCount:true,
            overwriteInitial: true,
            previewFileIcon : "<i class='glyphicon glyphicon-king'></i>",
            initialPreview: [
           	 	"<img src='${fileBase}${ci.idCardPPath}' class='file-preview-image' alt='' title='' style='max-width:100%;max-height:100%' >",
            ],
            initialPreviewAsData: false
        })
    	$("#idCardN").fileinput({
            language : 'zh', //设置语言
         	uploadUrl: BASE_PATH+'car/upload/photo', //上传的地址
            autoReplace : true,
            allowedFileExtensions : [ "jpg", "png"],//可接收的文件后缀
            uploadAsync: true, //默认异步上传
            showUpload: true, //是否显示上传按钮
            showRemove : false, //显示移除按钮
            showPreview : true, //是否显示预览
            showCaption: false,//是否显示标题
            browseClass: "btn btn-primary", //按钮样式     
            dropZoneEnabled: true,//是否显示拖拽区域
            maxFileCount: 1, //表示允许同时上传的最大文件个数
            enctype: 'multipart/form-data',
            validateInitialCount:true,
            overwriteInitial: true,
            previewFileIcon : "<i class='glyphicon glyphicon-king'></i>",
            initialPreview: [
            	 "<img src='${fileBase}${ci.idCardNPath}' class='file-preview-image' alt='' title='' style='max-width:100%;max-height:100%' >",
            ],
            initialPreviewAsData: false
        })        
    	$("#vehicleLicenseP").fileinput({
            language : 'zh', //设置语言
         	uploadUrl: BASE_PATH+'car/upload/photo', //上传的地址
            autoReplace : true,
            allowedFileExtensions : [ "jpg", "png"],//可接收的文件后缀
            uploadAsync: true, //默认异步上传
            showUpload: true, //是否显示上传按钮
            showRemove : false, //显示移除按钮
            showPreview : true, //是否显示预览
            showCaption: false,//是否显示标题
            browseClass: "btn btn-primary", //按钮样式     
            dropZoneEnabled: true,//是否显示拖拽区域
            maxFileCount: 1, //表示允许同时上传的最大文件个数
            enctype: 'multipart/form-data',
            validateInitialCount:true,
            overwriteInitial: true,
            previewFileIcon : "<i class='glyphicon glyphicon-king'></i>",
            initialPreview: [
            	 "<img src='${fileBase}${ci.vehicleLicensePPath}' class='file-preview-image' alt='' title='' style='max-width:100%;max-height:100%' >",
            ],
            initialPreviewAsData: false
        })        
    	$("#vehicleLicenseN").fileinput({
            language : 'zh', //设置语言
         	uploadUrl: BASE_PATH+'car/upload/photo', //上传的地址
            autoReplace : true,
            allowedFileExtensions : [ "jpg", "png"],//可接收的文件后缀
            uploadAsync: true, //默认异步上传
            showUpload: true, //是否显示上传按钮
            showRemove : false, //显示移除按钮
            showPreview : true, //是否显示预览
            showCaption: false,//是否显示标题
            browseClass: "btn btn-primary", //按钮样式     
            dropZoneEnabled: true,//是否显示拖拽区域
            maxFileCount: 1, //表示允许同时上传的最大文件个数
            enctype: 'multipart/form-data',
            validateInitialCount:true,
            overwriteInitial: true,
            previewFileIcon : "<i class='glyphicon glyphicon-king'></i>",
            initialPreview: [
            	 "<img src='${fileBase}${ci.vehicleLicenseNPath}' class='file-preview-image' alt='' title='' style='max-width:100%;max-height:100%' >",
            ],
            initialPreviewAsData: false
        })        
    	$("#driveLicenseP").fileinput({
            language : 'zh', //设置语言
         	uploadUrl: BASE_PATH+'car/upload/photo', //上传的地址
            autoReplace : true,
            allowedFileExtensions : [ "jpg", "png"],//可接收的文件后缀
            uploadAsync: true, //默认异步上传
            showUpload: true, //是否显示上传按钮
            showRemove : false, //显示移除按钮
            showPreview : true, //是否显示预览
            showCaption: false,//是否显示标题
            browseClass: "btn btn-primary", //按钮样式     
            dropZoneEnabled: true,//是否显示拖拽区域
            maxFileCount: 1, //表示允许同时上传的最大文件个数
            enctype: 'multipart/form-data',
            validateInitialCount:true,
            overwriteInitial: true,
            previewFileIcon : "<i class='glyphicon glyphicon-king'></i>",
            initialPreview: [
            	 "<img src='${fileBase}${ci.driveLicensePPath}' class='file-preview-image' alt='' title='' style='max-width:100%;max-height:100%' >",
            ],
            initialPreviewAsData: false
        })        
    	$("#driveLicenseN").fileinput({
            language : 'zh', //设置语言
         	uploadUrl: BASE_PATH+'car/upload/photo', //上传的地址
            autoReplace : true,
            allowedFileExtensions : [ "jpg", "png"],//可接收的文件后缀
            uploadAsync: true, //默认异步上传
            showUpload: true, //是否显示上传按钮
            showRemove : false, //显示移除按钮
            showPreview : true, //是否显示预览
            showCaption: false,//是否显示标题
            browseClass: "btn btn-primary", //按钮样式     
            dropZoneEnabled: true,//是否显示拖拽区域
            maxFileCount: 1, //表示允许同时上传的最大文件个数
            enctype: 'multipart/form-data',
            validateInitialCount:true,
            overwriteInitial: true,
            previewFileIcon : "<i class='glyphicon glyphicon-king'></i>",
            initialPreview: [
            	 "<img src='${fileBase}${ci.driveLicenseNPath}' class='file-preview-image' alt='' title='' style='max-width:100%;max-height:100%' >",
            ],
            initialPreviewAsData: false
        })        
        $(".col-md-8 :input").on("fileuploaded", function(e, data) { //异步上传返回结果处理
            var res = data.response;
        	if(res.pathFlag=='idCardPPath'){
    	    	$('#idCardPPath').val(res.success);
        	}else if(res.pathFlag=='idCardNPath'){
    	        $('#idCardNPath').val(res.success);
        	}else if(res.pathFlag=='vehicleLicensePPath'){
    	        $('#vehicleLicensePPath').val(res.success);
        	}else if(res.pathFlag=='vehicleLicenseNPath'){
    	        $('#vehicleLicenseNPath').val(res.success);
        	}else if(res.pathFlag=='driveLicensePPath'){
    	        $('#driveLicensePPath').val(res.success);
        	}else if(res.pathFlag=='driveLicenseNPath'){
    	        $('#driveLicenseNPath').val(res.success); 
        	}
        }).on('filepreupload', function(event, data, previewId, index) { //上传前
            var form = data.form;
            var files = data.files;
            var extra = data.extra;
            var response = data.response;
            var reader = data.reader;
        }); 
        $('#idCardP').on('fileselect', function(event, numFiles, label) {
        	$('#idCardP').parent().parent().find('.file-preview-thumbnails .file-preview-initial').remove();
        });
        $('#idCardN').on('fileselect', function(event, numFiles, label) {
        	$('#idCardN').parent().parent().find('.file-preview-thumbnails .file-preview-initial').remove();
        });
        $('#vehicleLicenseP').on('fileselect', function(event, numFiles, label) {
        	$('#vehicleLicenseP').parent().parent().find('.file-preview-thumbnails .file-preview-initial').remove();
        });
        $('#vehicleLicenseN').on('fileselect', function(event, numFiles, label) {
        	$('#vehicleLicenseN').parent().parent().find('.file-preview-thumbnails .file-preview-initial').remove();
        });
        $('#driveLicenseP').on('fileselect', function(event, numFiles, label) {
        	$('#driveLicenseP').parent().parent().find('.file-preview-thumbnails .file-preview-initial').remove();
        });
        $('#driveLicenseN').on('fileselect', function(event, numFiles, label) {
        	$('#driveLicenseN').parent().parent().find('.file-preview-thumbnails .file-preview-initial').remove();
        });
    </script>

</body>
</html>