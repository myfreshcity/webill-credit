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
	
        <link href="${staticBase}/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <link href="${staticBase}/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
        <link href="${staticBase}/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="${staticBase}/assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
        <link href="${staticBase}/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
        <!-- END GLOBAL MANDATORY STYLES -->
        <!-- BEGIN PAGE LEVEL PLUGINS -->
        <link href="${staticBase}/assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
        <link href="${staticBase}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
        <!-- END PAGE LEVEL PLUGINS -->
        <!-- BEGIN THEME GLOBAL STYLES -->
        <link href="${staticBase}/assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
        <link href="${staticBase}/assets/global/css/plugins.min.css" rel="stylesheet" type="text/css" />
        <!-- END THEME GLOBAL STYLES -->
        <!-- BEGIN THEME LAYOUT STYLES -->
        <link href="${staticBase}/assets/layouts/layout/css/layout.min.css" rel="stylesheet" type="text/css" />
        
<script type="text/javascript">
	function updateOrder(id) {
		$.ajax({
	        type: 'POST',
	        url: BASE_PATH+'tOrder/update',
	        data: {"id":id,"tStatus":-1},
	        dataType: 'json',
	        success: function () {
		 		location.reload();
	        },
	        error: function () {
	            alert("错误");
	        }
	    });
	}
	/**************************************************************导出****************start*************************************/
	function orderExport(){
		$.post(BASE_PATH+'tOrder/list', {policyNum:$('#policyNum').val(), cName:$('#cName').val(),createdTime:$('#createdTime').val(),tStatus:$('#tStatus').val()}, function(data){
			if(data.records.length > 0){
				var dlist = data.records;
		        var obj = {title:"", titleForKey:"", data:""};
		        obj.title = ["订单编号","提交时间","手机号","订单金额","支付方式","订单来源","订单状态"];
		 		obj.titleForKey = ["transNo","createdTime","mobile","payAmount","gateWay","id","tStatus"];
		 		for(var i=0; i<dlist.length; i++){
		        	if(dlist[i].gateWay==1){
		        		dlist[i].gateWay = '支付宝';
        			}else if(dlist[i].gateWay==3){
        				dlist[i].gateWay = '银联';
        			}else if(dlist[i].gateWay==21){
        				dlist[i].gateWay = '微信';
        			}else{
        				dlist[i].gateWay = '默认';
        			}
		        	dlist[i].id = '微商城订单';
		        	
		        	if(dlist[i].tStatus==-1){
		        		dlist[i].tStatus = "已删除";
            		}else if(dlist[i].tStatus==10){
            			dlist[i].tStatus = "待付款";
            		}else if(dlist[i].tStatus==20){
            			dlist[i].tStatus = "已支付";
            		}else if(dlist[i].tStatus==30){
            			dlist[i].tStatus = "出单中";
            		}else if(dlist[i].tStatus==40){
            			dlist[i].tStatus = "已出单";
            		}else if(dlist[i].tStatus==50){
            			dlist[i].tStatus = "退保中";
            		}else if(dlist[i].tStatus==60){
            			dlist[i].tStatus = "已退保";
            		}else if(dlist[i].tStatus==80){
            			dlist[i].tStatus = "已关闭";
            		}else{
            			dlist[i].tStatus = "已失效";
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
      downloadLink.download = "订单列表.csv"; 
      document.body.appendChild(downloadLink);
      downloadLink.click();
      document.body.removeChild(downloadLink); 
    }
	/**************************************************************导出****************end*************************************/
	/**************************************************************更新订单为关闭状态****************start***********************************/
	function updateOrderStatus(){
		$.post(BASE_PATH+'tOrder/closeProdOrder', $('#prodOrderInfo').serialize(), function (data){
			if(data.status == 200){
				alert(data.msg);
				window.location.href = BASE_PATH+'tOrder/';
			}
		});
	}
	/**************************************************************更新订单为关闭状态****************end*************************************/
	/**************************************************************关闭订单模态框****************start***********************************/
	function closeProdtOrder(id){
		$('#prodOrderId').val(id);	
	}
	/**************************************************************关闭订单模态框****************end*************************************/
</script>        
<!-- END PAGE LEVEL PLUGINS -->
</head>
<body>
<!-- BEGIN TAB PORTLET-->
<div class="portlet light bordered">
    <div class="portlet-title">
		<div class="caption">
			<i class="icon-settings font-dark"></i> <span
				class="caption-subject font-green-sharp bold uppercase">订单列表</span>
		</div>
		<button type="button" class="btn green-meadow" style="float: right; margin: 10px 5px; padding: 3px 10px; line-height: 30px; color: white;" data-toggle="modal" onclick="orderExport()">导出</button>
	</div>
    <div class="portlet-body">
        <div class="tabbable tabbable-tabdrop">
            <ul class="nav nav-pills">
                <li  <c:if test="${po.tStatus==null}"> class="active"</c:if>>
                    <a href="#tab11" onclick="reLoadPage()" data-toggle="tab">全部订单</a>
                </li>
                <li <c:if test="${po.tStatus==10}"> class="active"</c:if>>
                    <a href="#tab12" onclick="reLoadPage('10')" data-toggle="tab">待付款(<span id="dfkNum"></span>)</a>
                </li>
                <li <c:if test="${po.tStatus==40}"> class="active"</c:if>>
                    <a href="#tab13" onclick="reLoadPage('40')" data-toggle="tab">已出单(<span id="ycdNum"></span>)</a>
                </li>
                <li <c:if test="${po.tStatus==30}"> class="active"</c:if>>
                    <a href="#tab14" onclick="reLoadPage('20')" data-toggle="tab">已完成(<span id="ywcNum"></span>)</a>
                </li>
                <li>
                    <a href="#tab15" onclick="reLoadPage('30')" data-toggle="tab">已关闭(<span id="ygbNum"></span>)</a>
                </li <c:if test="${po.tStatus==30}"> class="active"</c:if>>
            </ul>
            <div class="table-container">
                <div class="tab-pane active" id="tab11">
						<div class="form horizontal-form">
							<div class="form-body col-md-12">
								<div class="form-group col-md-3">
									<label class="control-label col-md-4">输入搜索：</label>
									<div class="col-md-8">
										<input type="text" name="policyNum" id="policyNum" value="${po.policyNum}" placeholder="订单编号/保单号"
											class="form-control form-filter" />
									</div>
								</div>
								<div class="form-group col-md-3">
									<label class="control-label col-md-4">投保人：</label>
									<div class="col-md-8">
										<input type="text" name="cName" id="cName" value="${po.cName}" placeholder="投保人姓名/手机号码"
											class="form-control form-filter" />
									</div>
								</div>
								<div class="form-group col-md-3" style="margin-right:30px">
	                                   <label class="control-label col-md-4">提交时间：</label>
	                                   <div class="col-md-2">
	                                       <div class="input-group input-large date-picker input-daterange" data-date-format="yyyy-mm-dd">
	                                           <input type="text" class="form-control form-filter" name="createdTime" id="createdTime" placeholder="请选择时间" readonly value="${po.createdTime}">
	                                       </div>
	                                   </div>
	                            </div>					
								<div class="form-group col-md-3" id="orderStatus">
									<label class="control-label col-md-4">订单状态：</label>
									<div class="col-md-8">
		                                <select class="form-control input-medium form-filter" placeholder="全部" name="tStatus" id="tStatus">
		                                    <option value="">全部订单</option>
		                                    <option value="-1" <c:if test="${po.tStatus==10}">selected</c:if>>已删除</option>
		                                    <option value="10" <c:if test="${po.tStatus==10}">selected</c:if>>待付款</option>
		                                    <option value="20" <c:if test="${po.tStatus==20}">selected</c:if>>已支付</option>
		                                    <option value="30" <c:if test="${po.tStatus==30}">selected</c:if>>出单中</option>
		                                    <option value="40" <c:if test="${po.tStatus==40}">selected</c:if>>已出单</option>
		                                    <option value="50" <c:if test="${po.tStatus==50}">selected</c:if>>退保中</option>
		                                    <option value="60" <c:if test="${po.tStatus==60}">selected</c:if>>已退保</option>
		                                    <option value="80" <c:if test="${po.tStatus==80}">selected</c:if>>已关闭</option>
		                                    <option value="90" <c:if test="${po.tStatus==90}">selected</c:if>>已失效</option>
		                                </select>
									</div>
								</div>
							</div>
							<div class="form-actions right">
								<div class="row">
									<div class="col-md-offset-3 col-md-9">
										<button
											class="btn btn-sm green btn-outline filter-submit margin-bottom">
											<i class="fa fa-search"></i>查询
										</button>
										<button class="btn btn-sm red btn-outline filter-cancel">
											<i class="fa fa-times"></i> 重置
										</button>
									</div>
								</div>
							</div>
							<!-- END FORM-->
							<div class="table-container">
							<table class="table table-striped table-bordered table-hover table-checkable" id="datatable_ajax">
								<thead>
									<tr role="row" class="heading">
										<th width="15%">订单编号</th>
										<th width="15%">提交时间</th>
										<th width="10%">手机号</th>
										<th width="15%">订单金额</th>
										<th width="10%">支付方式</th>
										<th width="13%">订单来源</th>
										<th width="10%">订单状态</th>
										<th >操作</th>
									</tr>
								</thead>
								<tbody></tbody>
							</table>						
						</div>                    
	                </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-------------------------------------------------------- 关闭订单--------------------------------------start---------------------------------------->
<div class="modal fade" id="closeProductOrder" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h3 class="modal-title" id="myModalLabel">关闭订单</h3>
      </div>
      <div class="modal-body">

		<form action="" id="prodOrderInfo" class="form-horizontal" method="post">
			<!-- END PAGE TITLE-->
			<div class="row">
				<div class="col-md-12">
					<div class="portlet light portlet-fit portlet-datatable bordered">
						<div class="portlet-body">
							<div class="form-body">
								<input type="text" name="id" id="prodOrderId" style="display:none"/>
								<div class="form-group">
									<label class="control-label col-xs-3">操作备注<span class="required">*</span>
									</label>
									<div class="col-xs-7">
										<textarea rows="5" cols="100" name="remark" class="col-xs-12"></textarea>
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
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn green-meadow" onclick="updateOrderStatus()">确定</button>
      </div>
    </div>
  </div>
</div>
<!--------------------------------------------------------关闭订单---------------------------end------------------------------------------------------>	
</body>
<content tag="pageJs"> 
        <script src="${staticBase}/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
        <script src="${staticBase}/assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="${staticBase}/assets/global/plugins/js.cookie.min.js" type="text/javascript"></script>
        <script src="${staticBase}/assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
        <script src="${staticBase}/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
        <script src="${staticBase}/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
        <script src="${staticBase}/assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
        <script src="${staticBase}/assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
        <!-- END CORE PLUGINS -->
        <!-- BEGIN PAGE LEVEL PLUGINS -->
        <script src="${staticBase}/assets/global/scripts/datatable.js" type="text/javascript"></script>
        <script src="${staticBase}/assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
        <script src="${staticBase}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
        <!-- END PAGE LEVEL PLUGINS -->
        <!-- BEGIN THEME GLOBAL SCRIPTS -->
        <script src="${staticBase}/assets/global/scripts/app.min.js" type="text/javascript"></script>
        <!-- END THEME GLOBAL SCRIPTS -->
        <!-- BEGIN PAGE LEVEL SCRIPTS -->
        <!-- END PAGE LEVEL SCRIPTS -->
        <!-- BEGIN THEME LAYOUT SCRIPTS -->
        <script src="${staticBase}/assets/layouts/layout/scripts/layout.min.js" type="text/javascript"></script>
        <script src="${staticBase}/assets/layouts/layout/scripts/demo.min.js" type="text/javascript"></script>
        <script src="${staticBase}/assets/layouts/global/scripts/quick-sidebar.min.js" type="text/javascript"></script>



<%-- <script
	src="${base}/assets/global/scripts/datatable.js" type="text/javascript"></script>
<script
	src="${staticBase}/assets/global/plugins/jquery-validation/js/jquery.validate.min.js"
	type="text/javascript"></script> <script
	src="${staticBase}/assets/global/plugins/jquery-validation/js/additional-methods.min.js"
	type="text/javascript"></script> <script
	src="${staticBase}/assets/global/plugins/datatables/datatables.min.js"
	type="text/javascript"></script> <script
	src="${staticBase}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js"
	type="text/javascript"></script> --%><script
	src="${staticBase}/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"
	type="text/javascript"></script>  <script
	src="${base}/assets/pages/system/tOrder/list.js"
	type="text/javascript"> 
	</script> 
	<script type="text/javascript">
	    var initPickers = function () {
	        //init date pickers
	        $('.date-picker').datepicker({
	            rtl: App.isRTL(),
	            autoclose: true
	        });
	    }
	
	    var handleRecords = function (tStatus) {
	        var grid = new Datatable();
	        grid.init({
	            src: $("#datatable_ajax"),
	            onSuccess: function (grid, response) {
	                // grid:        grid object
	                // response:    json object of server side ajax response
	                // execute some code after table records loaded
	            },
	            onError: function (grid) {
	                // execute some code on network or other general error  
	            },
	            onDataLoad: function(grid) {
	                // execute some code on ajax data load
	            },
	            loadingMessage: 'Loading...',
	            dataTable: {
	                "bStateSave": true, // save datatable state(pagination, sort, etc) in cookie.
	                "lengthMenu": [
	                    [10, 20, 50, 150, -1],
	                    [10, 20, 50, 150, "All"] // change per page values here
	                ],
	                "pageLength": 10, // default record count per page
	                "ajax": {
	                    "url": BASE_PATH+'tOrder/list?tStatus='+tStatus, // ajax source
	                },
	    			 "columns": [
	    				 { "data": "transNo" },
	    				 { "data": "createdTime" },
	    				 { "data": "mobile" },
	    				 { "data": "payAmount" },
	    				 { "data": "gateWay" },
	    				 { "data": "" },
	                     { "data": "tStatus" },
	    				 { "data": "id" }
	                 ],
	                 "retrieve":true,
	                 "destroy":true,
	                "orderCellsTop": true,
	                "columnDefs": [
	                	{
	                		'orderable': false,
	                		'targets': [3],
	                		"render": function ( data, type, row ) {
	                			return '¥'+data;
	                		}
	                	},
	                	{
	                		'orderable': false,
	                		'targets': [4],
	                		"render": function ( data, type, row ) {
	                			if(data==100){
	                				return '微信';
	                			}else if(data==200){
	                				return '支付宝';
	                			}
	                		}
	                	},
	                	{
	                        'orderable': false,
	                        'targets': [5],
	                        "render": function ( data, type, row ) {
	                            return '微商城订单';
	                        }
	                    },
	                    {
	                    	'orderable': false,
	                    	'targets': [6],
	                    	"render": function ( data, type, row ) {
	                    		if(data==-1){
	                    			return "已删除";
	                    		}else if(data==0){
	                    			return "新建";
	                    		}else if(data==10){
	                    			return "待付款";
	                    		}else if(data==20){
	                    			return "已支付";
	                    		}else if(data==30){
	                    			return "出单中";
	                    		}else if(data==40){
	                    			return "已出单";
	                    		}else if(data==50){
	                    			return "退保中";
	                    		}else if(data==60){
	                    			return "已退保";
	                    		}else if(data==80){
	                    			return "已关闭";
	                    		}else if(data==90){
	                    			return "已失效";
	                    		}
	                    	}
	                    },
	                    {
	                        'orderable': false,
	                        'targets': [7],
	                        "render": function ( data, type, row ) {
	                            return '<a href=\"'+BASE_PATH+'tOrder/orderDetail/'+data+'\" class=\"btn btn-sm btn-outline grey-salsa\">查看订单</a>'+'<a href=\"'+BASE_PATH+'tOrder/orderDetail/'+data+'\" class=\"delete\">订单跟踪</a>';
                       		}
	                    }
	                ],
	                "pagingType": "bootstrap_extended",
	                "order": [
	                    [1, "asc"]
	                ]// set first column as a default sort by asc
	            }
	        });
	
	        grid.getTableWrapper().on('click', '.delete', function(e) {
				e.preventDefault();
				if (confirm("是否确定删除?") == false) {
					return;
				}
				var nRow = $(this).parents('tr')[0];
				if (nEditing !== null && nEditing != nRow) {
					/*
					 * Currently editing - but not this row - restore the old before
					 * continuing to edit mode
					 */
					restoreRow(oTable, nEditing);
				}
				nEditing = nRow;
				ajaxDel();
			});
	        // handle group actionsubmit button click
	        grid.getTableWrapper().on('click', '.table-group-action-submit', function (e) {
	            e.preventDefault();
	            var action = $(".table-group-action-input", grid.getTableWrapper());
	            if (action.val() != "" && grid.getSelectedRowsCount() > 0) {
	                grid.setAjaxParam("customActionType", "group_action");
	                grid.setAjaxParam("customActionName", action.val());
	                grid.setAjaxParam("id", grid.getSelectedRows());
	                grid.getDataTable().ajax.reload();
	                grid.clearAjaxParams();
	            } else if (action.val() == "") {
	                App.alert({
	                    type: 'danger',
	                    icon: 'warning',
	                    message: 'Please select an action',
	                    container: grid.getTableWrapper(),
	                    place: 'prepend'
	                });
	            } else if (grid.getSelectedRowsCount() === 0) {
	                App.alert({
	                    type: 'danger',
	                    icon: 'warning',
	                    message: 'No record selected',
	                    container: grid.getTableWrapper(),
	                    place: 'prepend'
	                });
	            }
	        });
	
	        grid.setAjaxParam("customActionType", "group_action");
	        grid.getDataTable().ajax.reload();
	        grid.clearAjaxParams();
	    }	

		function reLoadPage(tStatus) {
			if (tStatus != null&&tStatus!='') {
				$("#orderStatus").css("display", "none");
			}else{
				$("#orderStatus").css("display", "block");
			}
			initPickers();
			handleRecords(tStatus);
			$("#tStatus").val(tStatus);
		};

		$(function() {
			$.ajax({
				type : 'POST',
				url : BASE_PATH + 'tOrder/getNum',
				data : '',
				dataType : 'json',
				success : function(data) {
					$("#dfkNum").text(data.dfkNum);
					$("#ycdNum").text(data.ycdNum);
					$("#ywcNum").text(data.ywcNum);
					$("#ygbNum").text(data.ygbNum);
				},
				error : function() {
					alert("错误！");
				}
			});
		});
	</script>
	</content>	
</html>
