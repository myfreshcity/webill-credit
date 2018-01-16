<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/comtag.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 
<html>
<head>
<!-- BEGIN PAGE LEVEL PLUGINS -->
<link
	href="${staticBase}/assets/global/plugins/datatables/datatables.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="${staticBase}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticBase}/assets/global/plugins/jquery.min.js"></script>	
<script type="text/javascript">
	/**************************************************************险种搜索****************start*************************************/
	function getPremiumList() {
	    $.ajax({
	        type: 'POST',
	        url: BASE_PATH+'premiumOrder/premiumList',
	        data: $('#getPremiumList').serialize(),
	        dataType: 'json',
	        success: function (data) {
	        	var plist = data;
	        	var tbody = "";
                var trs = "";
	        	$.each(plist,function(n,p){
	                trs = "<tr><td><input name='premiumId' type='checkbox' value="+p.id+"></td><td>"+p.prmTypeStr+"</td><td>"+p.prmName+"</td></tr>";
	                tbody += trs;
	            });
	        	alert(tbody);
	        	$('#premiuBody').html(tbody);
	        },
	        error: function () {
	            alert("出错了,请重试!");
	        }
	    });
	}
	/**************************************************************险种搜索****************end*************************************/
	
	
	/**************************************************************添加险种****************start***********************************/
	function premiumSubmit() {
	    $.ajax({
	        type: 'POST',
	        url: BASE_PATH+'premiumOrder/addPremiumToOrder',
	        data: $('#premiumSubmit').serialize(),
	        dataType: 'json',
	        success: function (data) {
	        	if(data.status==200){
	        		alert(data.msg);
	        	}
	        },
	        error: function () {
	            alert(data.msg);
	        }
	    });
	}
	/**************************************************************添加险种****************end*************************************/
    $(function() {
    	$("#allPremium").click(function() {
			var f = $(this).is(":checked");    		
    		$('input:checkbox').each(function() {
    	        $(this).prop('checked', f);
    		});
        });
     });
</script>
<!-- END PAGE LEVEL PLUGINS -->
</head>
<body>
	<form action="#" id="form_sample_3" class="form-horizontal" >
		<!-- END PAGE TITLE-->
		<div class="row">
			<div class="col-md-12">
				<div class="portlet light portlet-fit portlet-datatable bordered">
					<div class="portlet-body">
						<div class="form-body">
							<input id="userId" name="userId" value="${po.userId}" >
							<div class="form-group">
								<label class="control-label col-md-3">微信昵称 <span
									class="required"> * </span>
								</label>
								<div class="col-md-5">
									<input type="text" name="weixinNick" value="${uc.weixinNick }"
										data-required="1" class="form-control"/>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3">收件人姓名<span
									class="required"> * </span>
								</label>
								<div class="col-md-5">
									<input type="text" name="contactName" value="${uc.contactName}"
										data-required="1" class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3">联系人手机 <span
									class="required"> * </span>
								</label>
								<div class="col-md-5">
									<input type="text" name="mobile" value="${uc.mobile}" data-required="1" class="form-control"/>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3">电子邮箱 <span
									class="required"> * </span>
								</label>
								<div class="col-md-5">
									<input type="text" name="email" value="${uc.email}" data-required="1" class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3">所在地区<span
									class="required"> * </span>
								</label>
								<div class="col-md-5">
									<input type="text" name="city" value="${uc.city}"
										data-required="1" class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3">详细地址<span
									class="required"> * </span>
								</label>
								<div class="col-md-5">
									<input type="text" name="address" value="${uc.address}"
										data-required="1" class="form-control" />
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
<content tag="pageJs"> <!-- BEGIN PAGE LEVEL PLUGINS --> 
<script language="javascript">
	function confirmPremiumOrder(){
		alert(1);
		$('#myform').submit();
	}
	function addPremium(){
		$('#myModal').on('shown.bs.modal', function () {
			  $('#myInput').focus()
		})
		/*  layer.open({
			  type: 2,
			  title: '保险列表',
			  shadeClose: true,
			  shade: 0.8,
			  area: ['60%', '80%'],
			  content: '${base}/premiumOrder/addPremium' //iframe的url
			});  */
		 /*window.opener.location.reload(); //刷新父窗口中的网页
		window.close(); *///关闭当前窗窗口
	}
	//iframe层
	
	
</script>




<script src="${staticBase}/assets/global/plugins/select2/js/select2.full.min.js" type="text/javascript"></script>
<script src="${staticBase}/assets/global/plugins/bootstrap-select/js/bootstrap-select.min.js" type="text/javascript"></script>
<script src="${staticBase}/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript" charset="UTF-8"></script>
<script src="${staticBase}/assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script> 
<script src="${staticBase}/assets/layouts/layout/scripts/table-datatables-managed.min.js"></script>
<script src="${staticBase}/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${staticBase}/assets/global/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>
<script src="${base}/assets/global/scripts/datatable.js" type="text/javascript"></script>
<script src="${staticBase}/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
<script src="${staticBase}/assets/global/plugins/jquery-multi-select/js/jquery.multi-select.js" type="text/javascript"></script>
<script src="${staticBase}/assets/global/plugins/jquery.quicksearch.js" type="text/javascript"></script>
<script src="${base}/assets/pages/system/premiumOrder/detail-list.js" type="text/javascript"></script>
     <script>
        jQuery(document).ready(function() {
        	
        });
    </script>
</content>
</html>
