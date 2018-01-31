<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/comtag.jsp"%>
<html>
<head>
<!-- BEGIN PAGE LEVEL PLUGINS -->
<link
	href="${staticBase}/assets/global/plugins/datatables/datatables.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="${staticBase}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<link
	href="${staticBase}/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.css"
	rel="stylesheet" type="text/css" />
<script src="${staticBase}/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
<style type="text/css">
	.bootstrap-switch .bootstrap-switch-handle-on.bootstrap-switch-primary{
	   background:#1bbc9b;
	}
</style>
<script type="text/javascript">
	function RZZF(){
		document.getElementById("payBillForm").submit();
	}
</script>
<!-- END PAGE LEVEL PLUGINS -->
</head>
<body>
	<div>
		<form id="payBillForm" action="https://cashier.lianlianpay.com/payment/authpay.htm" method="post">
	                <input type="hidden" name="version" value="1.0"/>
	                <input type="hidden" name="oid_partner" value="201408071000001543"/>
	                <input type="hidden" name="user_id" value="1"/>
	                <input type="hidden" name="sign_type" value="RSA"/>
	                <input type="hidden" name="sign" value="5dbmEnCfiFYChin+iUecdkwq4pbo148WtsorY5N2EGnnJUcE7HOIfzEV7JWs1x5GouFQnCiG9bODTIJBej0JVb3Ld7vLCNf+XvN9wARtJgsDsAehkvcBTZmPIjtVtk4PGqzBoNpIzeRLGQtroKtivSZ4bCi1xhpXIKA6V/EWL/Q="/>
	                <input type="hidden" name="busi_partner" value="101001"/>
	                <input type="hidden" name="no_order" value="RZZF151736430181795440"/>
	                <input type="hidden" name="dt_order" value="20180131100501"/>
	                <input type="hidden" name="name_goods" value="微账房信息报告购买"/>
	                <input type="hidden" name="info_order" value="微账房信息报告购买"/>
	                <input type="hidden" name="money_order" value="0.01"/>
	                <input type="hidden" name="notify_url" value="http://yadong.test.manmanh.com/webill-app/api/trade/notifyUrl"/>
	                <input type="hidden" name="url_return" value="http://yadong.test.manmanh.com/webill-app/api/trade/urlReturn"/>
	                <input type="hidden" name="userreq_ip" value="127_0_0_1"/>
	                <input type="hidden" name="risk_item" value='{\"frms_ware_category\":\"1008\",\"user_info_bind_phone\":\"15121193141\",\"user_info_dt_register\":\"201801311101159\",\"user_info_mercht_userno\":\"1\"}'/> 
	                <input type="hidden" name="timestamp" value="20180131111215"/>
	                <input type="hidden" name="id_type" value="0"/>
	                <input type="hidden" name="id_no" value="341225199307088210"/>
	                <input type="hidden" name="acct_name" value="张亚东"/>
	                <input type="hidden" name="flag_modify" value="1"/>
	                   
	                <%-- <input type="hidden" name="version" value="${RzzfModel.version}"/>
	                <input type="hidden" name="oid_partner" value="${RzzfModel.oid_partner}"/>
	                <input type="hidden" name="user_id" value="${RzzfModel.user_id}"/>
	                <input type="hidden" name="sign_type" value="${RzzfModel.sign_type}"/>
	                <input type="hidden" name="sign" value="${RzzfModel.sign}"/>
	                <input type="hidden" name="busi_partner" value="${RzzfModel.busi_partner}"/>
	                <input type="hidden" name="no_order" value="${RzzfModel.no_order}"/>
	                <input type="hidden" name="dt_order" value="${RzzfModel.dt_order}"/>
	                <input type="hidden" name="name_goods" value="${RzzfModel.name_goods}"/>
	                <input type="hidden" name="info_order" value="${RzzfModel.info_order}"/>
	                <input type="hidden" name="money_order" value="${RzzfModel.money_order}"/>
	                <input type="hidden" name="notify_url" value="${RzzfModel.notify_url}"/>
	                <input type="hidden" name="url_return" value="${RzzfModel.url_return}"/>
	                <input type="hidden" name="userreq_ip" value="${RzzfModel.userreq_ip}"/>
	                <input type="hidden" name="url_order" value="${RzzfModel.url_order}"/>
	                <input type="hidden" name="valid_order" value="${RzzfModel.valid_order}"/>
	                <input type="hidden" name="risk_item" value='${RzzfModel.risk_item}'/>
	                <input type="hidden" name="timestamp" value="${RzzfModel.timestamp}"/>
	                <input type="hidden" name="id_type" value="${RzzfModel.id_type}"/>
	                <input type="hidden" name="id_no" value="${RzzfModel.id_no}"/>
	                <input type="hidden" name="acct_name" value="${RzzfModel.acct_name}"/>
	                <input type="hidden" name="flag_modify" value="${RzzfModel.flag_modify}"/> --%>
	        </form>
	        <button type="button" class="btn green-meadow" style="float: left; margin: 10px 5px; padding: 3px 10px; line-height: 30px; color: white;" data-toggle="modal" onclick="RZZF()">认证支付</button>
	</div>

</body>
<content tag="pageJs"> <!-- BEGIN PAGE LEVEL PLUGINS -->
<script
	src="${base}/assets/global/scripts/datatable.js" type="text/javascript"></script>
<script
	src="${staticBase}/assets/global/plugins/datatables/datatables.min.js"
	type="text/javascript"></script> <script
	src="${staticBase}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js"
	type="text/javascript"></script> <script
	src="${staticBase}/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"
	type="text/javascript"></script> <!-- END PAGE LEVEL PLUGINS --> <!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="${staticBase}/assets/global/plugins/bootstrap-switch/js/bootstrap-switch.js" type="text/javascript"></script>
<script src="${base}/assets/pages/system/product/product-list.js"
	type="text/javascript"></script> <!-- END PAGE LEVEL SCRIPTS --> </content>
</html>
