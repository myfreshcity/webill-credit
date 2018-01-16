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
	
	function transfer(id, obj){
		var parentCatName = $(obj).attr("data-id");
		$('#parentCatName').val(parentCatName);
		$('#oneselfId').val(id);
	}

	function updateCatParentId() {
		$.post(BASE_PATH+'category/updateCatParentId', $('#catInfo').serialize(), function(data){
			if(data.status == 200){
        		alert(data.msg);
        		window.location.href=BASE_PATH+"category/";
        	}
		});
	}

	/**************************************************************显示分类****************start*************************************/
	function showCategory(id, isDisplay) {
		$.post(BASE_PATH+'category/showCategory', {id:id}, function(data){
			if(data.status == 200){
				alert(data.msg);
				window.location.reload();
			}
		});
	}
	function stopCategory(id, isDisplay) {
		$.post(BASE_PATH+'category/stopCategory', {id:id}, function(data){
			if(data.status == 200){
				alert(data.msg);
				window.location.reload();
			}
		});
	}
	/**************************************************************显示分类****************end*************************************/
	/**************************************************************导出****************start*************************************/
	function catExport(){
		$.post(BASE_PATH+'category/list', {catName:$('#catName').val(), level:$('#level').val()}, function(data){
			if(data.records.length > 0){
				var catlist = data.records;
		        var obj = {title:"", titleForKey:"", data:""};
		        obj.title = ["编号","分类名称","级别","排序","显示状态"];
		 		obj.titleForKey = ["id","catName","level","sortIndex","isDisplay"];
		 		for(var i=0; i<catlist.length; i++){
		        	if(catlist[i].isDisplay == 0){
		        		catlist[i].isDisplay = "显示";
		        	}else {
		        		catlist[i].isDisplay = "不显示";
		        	}
		        	if(catlist[i].level == 1){
		        		catlist[i].level = "一级";
		        	}else if(catlist[i].level == 2){
		        		catlist[i].level = "二级";
		        	}
	 			}
				obj.data = catlist;
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
      downloadLink.download = "商品分类列表.csv"; 
      document.body.appendChild(downloadLink);
      downloadLink.click();
      document.body.removeChild(downloadLink); 
    }
	/**************************************************************导出****************end*************************************/
	function addCat(){
		window.location.href = BASE_PATH+'category/addCate';
	}
	/**************************************************************删除分类****************start*************************************/
	function deleteCat(data){
		$.post(BASE_PATH+'category/deleteCat', {id:data}, function(data){
			if(data.status == 200){
				alert(data.msg);
				window.location.reload();
			}
		});
	}
	/**************************************************************删除分类****************end*************************************/
	function updateCatModal(id, catName, sortIndex){
		$('#catIdModal').val(id);
		$('#catNameModal').val(catName);
		$('#sortIndexModal').val(sortIndex);
	}
	function confirmUpdateCat(){
		$.post(BASE_PATH+'category/updateCat', $('#catModalInfo').serialize(), function(data){
			if(data.status == 200){
				alert(data.msg);
				window.location.reload();
			}
		});
	}
		
</script>
<!-- END PAGE LEVEL PLUGINS -->
</head>
<body>

	<h3 class="page-title">
		商品分类
	</h3>
	<div class="row">
		<div class="col-md-12">
			<!-- Begin: life time stats -->
			<div class="portlet light portlet-fit portlet-datatable bordered">
				<div class="portlet-title">
					<div class="caption">
						<i class="icon-settings font-dark"></i> <span
							class="caption-subject font-dark sbold uppercase">商品分类列表</span>
					</div>
					<button type="button" class="btn green-meadow" style="float: right; margin: 10px 5px; padding: 3px 10px; line-height: 30px; color: white;" data-toggle="modal" onclick="catExport()">导出</button>
					<button type="button" class="btn green-meadow" style="float: right; margin: 10px 5px; padding: 3px 10px; line-height: 30px; color: white;" data-toggle="modal" onclick="addCat()">添加</button>
				</div>
				<div class="portlet-body">
					<div class="table-container">
						<div class="form horizontal-form">
							<div class="form-body col-md-12">
								<div class="form-group col-md-3">
									<label class="control-label col-md-4">分类名称 </label>
									<div class="col-md-8">
										<input type="text" name="catName" id="catName" value="${cat.catName}"
											class="form-control form-filter" />
									</div>
								</div>
								<div class="form-group col-md-3">
									<label class="control-label col-md-4">分类级别 </label>
									<div class="col-md-8">
										<select name="level" id="level" class="form-control form-filter">
											<option value="">请选择分类级别</option>
											<option value="1">一级</option>
											<option value="2">二级</option>
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
									<th width="10%" style="text-align: center;">编号</th>
									<th width="10%" style="text-align: center;">分类名称</th>
									<th width="10%" style="text-align: center;">级别</th>
									<th width="10%" style="text-align: center;">排序</th>
									<th width="10%" style="text-align: center;">显示状态</th>
									<th width="10%" style="text-align: center;">是否显示</th>
									<th width="10%" style="text-align: center;">设置</th>
									<th width="10%" style="text-align: center;">操作</th>
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
	
	
<!-------------------------------------------------------- 转移分类--------------------------------------start---------------------------------------->
<div class="modal fade" id="transferCat" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h3 class="modal-title" id="myModalLabel">转移分类</h3>
      </div>
      <div class="modal-body">

		<form action="" id="catInfo" class="form-horizontal" method="post">
			<!-- END PAGE TITLE-->
			<div class="row">
				<div class="col-md-12">
					<div class="portlet light portlet-fit portlet-datatable bordered">
						<div class="portlet-body">
							<div class="form-body">
								<input type="text" name="id" id="oneselfId" style="display:none"/>
								<div class="form-group">
									<label class="control-label col-md-4">原商品分类
									</label>
									<div class="col-md-5">
										<input type="text" id="parentCatName" name="parentCatName" value="${parentId}" readonly="readonly" style="border: 0;"
										data-required="1" class="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-4">目标商品分类
									</label>
									<div class="col-md-5">
										<select name="parentId" id="firstCat" class="bs-select form-control" data-width="100px">
					                        <option value="">请选择分类</option>
					                    </select>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
					
	  </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" onclick="updateCatParentId()">确定</button>
      </div>
    </div>
  </div>
</div>
<!--------------------------------------------------------转移分类---------------------------end------------------------------------------------------>	
<!--------------------------------------------------------编辑分类--------------------------------------start---------------------------------------->
<div class="modal fade" id="updateCatModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h3 class="modal-title" id="myModalLabel">编辑分类</h3>
      </div>
      <div class="modal-body">

		<form action="" id="catModalInfo" class="form-horizontal" method="post">
			<!-- END PAGE TITLE-->
			<div class="row">
				<div class="col-md-12">
					<div class="portlet light portlet-fit portlet-datatable bordered">
						<div class="portlet-body">
							<div class="form-body">
								<input type="text" name="id" id="catIdModal" style="display:none"/>
								<div class="form-group">
									<label class="control-label col-md-4">分类名称
									</label>
									<div class="col-md-5">
										<input type="text" id="catNameModal" name="catName" class="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-4">排序
									</label>
									<div class="col-md-5">
										<input type="text" id="sortIndexModal" name="sortIndex" class="form-control" />
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
					
	  </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" onclick="confirmUpdateCat()">确定</button>
      </div>
    </div>
  </div>
</div>
<!--------------------------------------------------------编辑分类---------------------------end------------------------------------------------------>	
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
<script src="${base}/assets/pages/system/category/list.js"
	type="text/javascript"></script> <!-- END PAGE LEVEL SCRIPTS --> </content>
</html>
