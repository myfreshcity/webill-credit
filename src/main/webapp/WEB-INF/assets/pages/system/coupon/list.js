(function() {
    'use strict';
    
    var initPickers = function () {
        //init date pickers
    };
    
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
                    "url": BASE_PATH+'coupon/list', // ajax source
                },
    			 "columns": [
                     { "data": "cpName" },
                     { "data": "sendWayStr" },
                     { "data": "validTimeStr" },
                     { "data": "sendStartTime"},
                     { "data": "sendEndTime" },
                     { "data": "ruleName"},
                     { "data": "useScopeStr" },
                     { "data": "amtLimitStr" },
                     { "data": "saleAmtStr" },
                     { "data": "sendPeopleNum" },
                     { "data": "planAmt" },
                     { "data": "obtainAmt" },
                     { "data": "useAmt" },
                     { "data": "createdTime" },
                     { "data": "statusStr" },
                     { "data": "id"}
                 ],
                "orderCellsTop": true,
                "columnDefs": [
                    {
                        'orderable': false,
                        'targets': [15],
                        "render": function ( data, type, row ) {
/*                        	return '<a href=\"'+BASE_PATH+'coupon/doCoupon/'+data+'/600\" class=\"btn btn-sm btn-outline grey-salsa\">停止发放</a>';
*/                            return '<button type="button" class=\"btn btn-sm btn-outline grey-salsa\" data-target=\"#sendCoupon\" onclick="showSendCoupon('+data+')\">发放</button>&nbsp;&nbsp;&nbsp;&nbsp;<button class=\"btn btn-sm btn-outline-close grey-salsa\" onclick="stopSendCoupon('+data+')\">停止</button>';
//                            return data +' ('+ row[3]+')';
                        }
                    }
                ],
                "pagingType": "bootstrap_extended",
                "order": [
                    [1, "asc"]
                ]// set first column as a default sort by asc
            }
        });

        grid.getTableWrapper().on( 'click', '.btn-outline-open', function () {
            var data = grid.rows().data()[0];
            alert(data);
            $.ajax({
                url: BASE_PATH+'doRuleAble',
                type: 'POST',
                dataType: 'json',
                data: {id: data[0]},
                success: function (data) {
                	if (data.status=="200") {
                    	alert(data.msg);
                    	grid.getDataTable().ajax.reload();
                    }else{
                    	alert(data.msg);
                    }
    	        }
            })
        });
    }

    function getDateTimeStamp(dateStr){
    	return Date.parse(dateStr.replace(/-/gi,"/"));
	}    
    initPickers();
    handleRecords();

})()
