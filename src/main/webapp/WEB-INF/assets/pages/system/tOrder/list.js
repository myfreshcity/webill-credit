(function() {
    'use strict';

    var initPickers = function () {
        //init date pickers
        $('.date-picker').datepicker({
            rtl: App.isRTL(),
            autoclose: true
        });
    }

    var handleRecords = function (flag) {
    	console.info(flag);
        var grid = new Datatable();
        grid.init({
            src: $("#datatable_ajax"),
            paging: true,
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
                "pageLength": 20, // default record count per page
                "ajax": {
                    "url": BASE_PATH+'tOrder/list', // ajax source
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
                			console.info(data);
                			if(data==1){
                				return '支付宝';
                			}else if(data==3){
                				return '银联';
                			}else if(data==21){
                				return '微信';
                			}else{
                				return '默认';
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
                        	if (row.tStatus != 80) {
                        		return '<a href=\"'+BASE_PATH+'tOrder/orderDetail/'+data+'\" class=\"btn btn-sm btn-outline grey-salsa\">查看订单</a>'+'&nbsp;&nbsp;<i type="button" class=\"btn btn-sm btn-outline grey-salsa\" data-target=\"#sendCoupon\" onclick="updateOrder('+data+')\">删除订单</i>'+
                        		'<button id="closeProdOrder" class=\"btn btn-sm btn-outline grey-salsa\" data-id='+data+' data-toggle="modal" data-target="#closeProductOrder" onclick="closeProdtOrder('+data+')\">关闭订单</button>';
							}else {
								return '<a href=\"'+BASE_PATH+'tOrder/orderDetail/'+data+'\" class=\"btn btn-sm btn-outline grey-salsa\">查看订单</a>'+'&nbsp;&nbsp;<i type="button" class=\"btn btn-sm btn-outline grey-salsa\" data-target=\"#sendCoupon\" onclick="updateOrder('+data+')\">删除订单</i>';
							}
                            /* return '<i type="button" class=\"btn btn-sm btn-outline grey-salsa\" data-target=\"#sendCoupon\" onclick="stopSendCoupon('+data+')\">删除订单</i>;
                            return '<a href=\"'+BASE_PATH+'tOrder/orderDetail/'+data+'\" class=\"btn btn-sm btn-outline grey-salsa\">订单跟踪</a>';
                            return '<a href=\"'+BASE_PATH+'tOrder/orderDetail/'+data+'\" class=\"btn btn-sm btn-outline grey-salsa\">删除订单</a>';
                            return '<a href=\"'+BASE_PATH+'tOrder/orderDetail/'+data+'\" class=\"btn btn-sm btn-outline grey-salsa\">关闭订单</a>';
                            //return data +' ('+ row[3]+')';
*/                        }
                    }
                ],
                "pagingType": "bootstrap_extended",
                "serverSide" : true,
                "buttons": [{
    				extend: "print",
    				className: "btn default"
    			},
    			{
    				extend: "pdf",
    				className: "btn default"
    			},
    			{
    				extend: "csv",
    				className: "btn default"
    			}],
                "order": [
                    [1, "asc"]
                ]// set first column as a default sort by asc
            }
        });

        grid.getTableWrapper().on('click', '.delete', function(e) {
        	console.log("删除"+e);
			e.preventDefault();
			if (confirm("是否确定删除?") == false) {
				$.ajax({
					type : 'POST',
					url : BASE_PATH + 'tOrder/update',
					data : {"tStatus":-1,"id":e},
					dataType : 'json',
					success : function(data) {
						
					},
					error : function() {
						alert("错误！");
					}
				});
				return;
			}
			var nRow = $(this).parents('tr')[0];
			if (nEditing !== null && nEditing != nRow) {
				
				 // Currently editing - but not this row - restore the old before
				 // continuing to edit mode
				 
				restoreRow(oTable, nEditing);
			}
			nEditing = nRow;
			ajaxDel();
		});
        // handle group actionsubmit button click
        grid.getTableWrapper().on('click', '.table-group-action-submit', function (e) {
        	console.info("点击");
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

    function getDateTimeStamp(dateStr){
    	return Date.parse(dateStr.replace(/-/gi,"/"));
	} 
    initPickers();
    handleRecords();

})()