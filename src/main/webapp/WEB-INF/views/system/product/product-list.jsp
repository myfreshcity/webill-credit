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
	$(document).ready(function(){
		$.post(BASE_PATH+'category/firstCatList',function(data){
			for(var i=0; i<data.length; i++){
		    	$("#firstCat").append("<option value='"+data[i].id+"'>"+data[i].catName+"</option>");
		    }
		});
	});
	
	/**************************************************************上下架操作****************start*************************************/
	function onShelf(id, price, caseCode) {
		$.post(BASE_PATH+'product/onShelf', {id:id, defaultPrice:price, caseCode:caseCode}, function(data){
			if(data.status == 200){
				alert(data.msg);
				window.location.reload();
			}
		});
	}
	function offShelf(id, price, caseCode) {
		$.post(BASE_PATH+'product/offShelf', {id:id, defaultPrice:price, caseCode:caseCode}, function(data){
			if(data.status == 200){
				alert(data.msg);
				window.location.reload();
			}
		});
	}
	/**************************************************************上下架操作****************end*************************************/
	/**************************************************************导出****************start*************************************/
	function prodExport(){
		$.post(BASE_PATH+'product/list', {prodName:$('#prodName').val(), catId:$('#firstCat').val(),offShelf:$('#offShelf').val()}, function(data){
			if(data.records.length > 0){
				var dlist = data.records;
		        var obj = {title:"", titleForKey:"", data:""};
		        obj.title = ["编号","商品图片","商品名称","计划名称","保险公司","商品分类","价格","上架/下架","支付方式"];
		 		obj.titleForKey = ["caseCode","imgUrlShow","prodName","planName","companyName","catName","defaultPrice","offShelf","autoPay"];
		 		for(var i=0; i<dlist.length; i++){
		        	if(dlist[i].offShelf == 0){
		        		dlist[i].offShelf = "已上架";
		        	}else {
		        		dlist[i].offShelf = "未上架";
		        	}
		        	if(dlist[i].autoPay == 0){
		        		dlist[i].autoPay = "手动支付";
		        	}else if(dlist[i].autoPay == 1) {
		        		dlist[i].autoPay = "银行代扣";
		        	}
	 			}
				obj.data = dlist;
	            exportCsv(obj);
			} else{
				alert("没有数据，无法导出！");
			}
		});
    }
	function exportCsv(obj){
         var title = obj.title;
         var titleForKey = obj.titleForKey;
         var data = obj.data;
         var str = [];
         str.push(obj.title.join(",")+"\n");
         for(var i=0;i<data.length;i++){
             var temp = [];
             for(var j=0;j<titleForKey.length;j++){
                 temp.push(data[i][titleForKey[j]]);
          }
          str.push(temp.join(",")+"\n");
      }
      var uri = 'data:text/csv;charset=utf-8,' + encodeURIComponent(str.join(""));  
      var downloadLink = document.createElement("a");
      downloadLink.href = uri;
      downloadLink.download = "商品列表.csv"; 
      document.body.appendChild(downloadLink);
      downloadLink.click();
      document.body.removeChild(downloadLink); 
    }
	/**************************************************************导出****************end*************************************/
	/**************************************************************逻辑删除商品****************start*************************************/
	function deleteProd(id){
		$.post(BASE_PATH+'product/deleleProd', {id:id}, function(data){	
			if(data.status == 200){
				alert(data.msg);
				window.location.reload();
			}
		});
	}
	/**************************************************************逻辑删除商品****************end*************************************/
</script>
<!-- END PAGE LEVEL PLUGINS -->
</head>
<body>

	<h3 class="page-title">
		商品列表
	</h3>
	<div class="row">
		<div class="col-md-12">
			<!-- Begin: life time stats -->
			<div class="portlet light portlet-fit portlet-datatable bordered">
				<div class="portlet-title">
					<div class="caption">
						<i class="icon-settings font-dark"></i> <span
							class="caption-subject font-dark sbold uppercase">商品列表</span>
					</div>
					<button type="button" class="btn green-meadow" style="float: right; margin: 10px 5px; padding: 3px 10px; line-height: 30px; color: white;" data-toggle="modal" onclick="prodExport()">导出</button>
				</div>
				<div class="portlet-body">
					<div class="table-container">
						<div class="form horizontal-form">
							<div class="form-body col-md-12">
								<div class="form-group col-md-3">
									<label class="control-label col-md-4">商品名称 </label>
									<div class="col-md-8">
										<input type="text" name="prodName" id="prodName" class="form-control form-filter" />
									</div>
								</div>
								<div class="form-group col-md-3">
									<label class="control-label col-md-4">商品分类 </label>
									<div class="col-md-8">
										<select name="catId" id="firstCat" class="form-control form-filter">
											<option value="">请选择商品分类</option>
										</select>
									</div>
								</div>
								<div class="form-group col-md-3">
									<label class="control-label col-md-4">上架状态</label>
									<div class="col-md-8">
										<select name="offShelf" id="offShelf" class="form-control form-filter">
											<option value="">请选择上架状态</option>
											<option value="0">已上架</option>
											<option value="1">未上架</option>
										</select>
									</div>
								</div>
							</div>
							<div class="form-actions right">
								<div class="row">
									<div class="col-md-offset-3 col-md-9">
										<button
											class="btn btn-sm green btn-outline filter-submit margin-bottom">
											<i class="fa fa-search"></i>搜索
										</button>
										<button class="btn btn-sm red btn-outline filter-cancel">
											<i class="fa fa-times"></i> 重置
										</button>
									</div>
								</div>
							</div>
							<!-- END FORM-->
						</div>
						<table
							class="table table-striped table-bordered table-hover table-checkable"
							id="datatable_ajax">
							<thead>
								<tr role="row" class="heading">
									<th width="8%" style="text-align: center;">编号</th>
									<th width="8%" style="text-align: center;">商品图片</th>
									<th width="8%" style="text-align: center;">商品名称</th>
									<th width="8%" style="text-align: center;">计划名称</th>
									<th width="8%" style="text-align: center;">保险公司</th>
									<th width="8%" style="text-align: center;">商品分类</th>
									<th width="8%" style="text-align: center;">价格</th>
									<th width="8%" style="text-align: center;">上架/下架</th>
									<th width="8%" style="text-align: center;">是否上架</th>
									<th width="8%" style="text-align: center;">支付方式</th>
									<th width="15%" style="text-align: center;">操作</th>
								</tr>
							</thead>
							<tbody></tbody>
						</table>
					</div>
				</div>
			</div>
			<!-- End: life time stats -->
		</div>
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
