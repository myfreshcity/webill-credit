(function() {
    'use strict';

    var initPickers = function () {
        //init date pickers
        $('.date-picker').datepicker({
            rtl: App.isRTL(),
            autoclose: true
        });
    }

    var handleRecords = function () {

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
                    "url": BASE_PATH+'product/list', // ajax source
                },
    			 "columns": [
    				 { "data": "caseCode" },
                     { "data": "imgUrlShow" },
                     { "data": "prodName"},
                     { "data": "planName"},
                     { "data": "companyName" },
                     { "data": "catName"},
                     { "data": "defaultPrice" },
                     { "data": "offShelf" },
                     { "data": "offShelf" },
                     { "data": "autoPay" },
                     { "data": "id" }
                 ],
                "orderCellsTop": true,
                "columnDefs": [
                    {
                        'orderable': false,
                        'targets': [1],
                        "render": function ( data, type, row ) {
                            return '<div style=\"text-align: center;\"><img alt="" src="'+data+'" width="60" height="60"></div>';
                        }
                    },
                    {
                    	'orderable': false,
                    	'targets': [2],
                    	"render": function ( data, type, row ) {
                    		return '<div style=\"text-align: center;\">'+data+'</div>';
                    	}
                    },
                    {
                    	'orderable': false,
                    	'targets': [3],
                    	"render": function ( data, type, row ) {
                    		return '<div style=\"text-align: center;\">'+data+'</div>';
                    	}
                    },
                    {
                    	'orderable': false,
                    	'targets': [4],
                    	"render": function ( data, type, row ) {
                    		return '<div style=\"text-align: center;\">'+data+'</div>';
                    	}
                    },
                    {
                    	'orderable': false,
                    	'targets': [5],
                    	"render": function ( data, type, row ) {
                    		return '<div style=\"text-align: center;\">'+data+'</div>';
                    	}
                    },
                    {
                    	'orderable': false,
                    	'targets': [6],
                    	"render": function ( data, type, row ) {
                    		return '<div style=\"text-align: center;\">'+data+'</div>';
                    	}
                    },
                    {
                    	'orderable': false,
                    	'targets': [7],
                    	"render": function ( data, type, row ) {
                    		if(data == 0){ //是否上架 0：是 1：否
                    			return '<div style=\"text-align: center;\">已上架</div>';;
                    		} else if (data == 1){
                    			return '<div style=\"text-align: center;\">未下架</div>';;
                    		}
                    	}
                    },
                    {
                    	'orderable': false,
                    	'targets': [8],
                    	"render": function ( data, type, row ) {
                    		if(data == 0){ //是否上架 0：是 1：否
                    			return '<div style=\"text-align: center;\">' +  
                    			'<button type="button" class=\"btn btn-sm btn-outline grey-salsa\" onclick="offShelf('+row.id+","+row.defaultPrice+',\''+row.caseCode+'\')\">否</button>'+
                    			'</div>';
                    		} else if (data == 1){
                    			return '<div style=\"text-align: center;\">' +  
                    			'<button type="button" class=\"btn btn-sm btn-outline-close grey-salsa\" onclick="onShelf('+row.id+","+row.defaultPrice+',\''+row.caseCode+'\')\">是</button>'+
                    			'</div>';
                    		}
                    	}
                    },
                    {
                    	'orderable': false,
                    	'targets': [9],
                    	"render": function ( data, type, row ) {
                    		if(data == 0){
                    			return '<div style=\"text-align: center;\">手动支付</div>';
                    		}else if(data ==1){
                    			return '<div style=\"text-align: center;\">银行代扣</div>';
                    		}
                    	}
                    },
                    {
                    	'orderable': false,
                    	'targets': [10],
                    	"render": function ( data, type, row ) {
                    		return '<div style=\"text-align: center;\">' + 
                    		'<a href=\"'+BASE_PATH+'product/prodDetail/'+data+'\" class=\"btn btn-sm btn-outline grey-salsa\">查看</a>'+
                    		'<a href=\"'+BASE_PATH+'productLog/productLogList/'+data+'\" class=\"btn btn-sm btn-outline grey-salsa\">日志</a>'+
                    		'<button id="deleteProd" class="btn btn-sm btn-outline grey-salsa" onclick="deleteProd('+row.id+')\">删除</button>'+
                    		'</div>';
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

    function getDateTimeStamp(dateStr){
    	return Date.parse(dateStr.replace(/-/gi,"/"));
	}    
    initPickers();
    handleRecords();

})()
