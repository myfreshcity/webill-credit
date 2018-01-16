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
                    "url": BASE_PATH+'category/list', // ajax source
                },
    			 "columns": [
    				 { "data": "id" },
    				 { "data": "catName" },
    				 { "data": "level" },
    				 { "data": "sortIndex" },
    				 { "data": "isDisplay" },
    				 { "data": "isDisplay" },
    				 { "data": "id" },
                     { "data": "id" },
                 ],
                "orderCellsTop": true,
                "columnDefs": [
                    {
                        'orderable': false,
                        'targets': [1],
                        "render": function ( data, type, row ) {
                            return '<div style=\"text-align: center;\">'+data+'</div>';
                        }
                    },
                    {
                    	'orderable': false,
                    	'targets': [2],
                    	"render": function ( data, type, row ) {
                    		if(data == 1){
                    			return '<div style=\"text-align: center;\">一级</div>';
                    		} else if(data == 2){
                    			return '<div style=\"text-align: center;\">二级<div>';
                    		}
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
                    		if(data == 0){
                    			return '<div style=\"text-align: center;\">显示</div>';
                    		}else if(data == 1){
                    			return '<div style=\"text-align: center;\">不显示</div>';
                    		}
                    	}
                    },
                    {
                    	'orderable': false,
                    	'targets': [5],
                    	"render": function ( data, type, row ) {
                    		if(data == 0){
                    			return '<div style=\"text-align: center;\">' +  
                    			'<button type="button" class=\"btn btn-sm btn-outline-close grey-salsa\" onclick="stopCategory('+row.id+')\">否</button>'+
                    			'</div>';
                    		} else if (data == 1){
                    			return '<div style=\"text-align: center;\">' +  
                    			'<button type="button" class=\"btn btn-sm btn-outline grey-salsa\" onclick="showCategory('+row.id+')\">是</button>'+
                    			'</div>';
                    		}
                    		
                    	}
                    },
                    {
                    	'orderable': false,
                    	'targets': [6],
                    	"render": function ( data, type, row ) {
                    		if(row.level ==1 ){
                    			return '<div style=\"text-align: center;\">' + 
                    			'<a href=\"'+BASE_PATH+'category/childCatList/'+data+'\" class=\"btn btn-sm btn-outline grey-salsa\">查看下级</a>'+
                    			'</div>';
                    		}else if(row.level == 2){
                    			var parentCatName = row.parentCatName;
                    			return '<div style=\"text-align: center;\">' + 
                    			'<button id="deleteOne" class="btn btn-sm btn-outline grey-salsa" data-id='+parentCatName+' data-toggle="modal" data-target="#transferCat" onclick="transfer('+row.id+',this)\">转移分类</button>'+
                    			'</div>';
                    		}
                    	}
                    },
                    {
                    	'orderable': false,
                    	'targets': [7],
                    	"render": function ( data, type, row ) {
                    		return '<div style=\"text-align: center;\">' + 
                    			   '<button class="btn btn-sm btn-outline grey-salsa" data-toggle="modal" data-target="#updateCatModal" onclick="updateCatModal('+row.id+',\''+row.catName+'\','+row.sortIndex+' )\">编辑</button>'+
                    			   '<button id="deleteOne" class="btn btn-sm btn-outline grey-salsa" onclick="deleteCat('+data+')\">删除</button>'+
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
