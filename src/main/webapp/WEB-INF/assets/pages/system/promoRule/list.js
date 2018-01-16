(function() {
    'use strict';

    var initPickers = function () {
        //init date pickers
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
                    "url": BASE_PATH+'promoRule/list', // ajax source
                },
    			 "columns": [
                     { "data": "ruleName" },
                     { "data": "areaLimit" },
                     { "data": "useScopeStr" },
                     { "data": "insurerLimit" },
                     { "data": "amtLimitStr"},
                     { "data": "saleResultStr" },
                     { "data": "saleAmtStr"},
                     { "data": "userLevelStr" },
                     { "data": "transferTypeStr" },
                     { "data": "activityTypeStr" },
                     { "data": "statusStr" },
                     { "data": "id" }
                 ],
                "orderCellsTop": true,
                "columnDefs": [
                    {
                        'orderable': false,
                        'targets': [11],
                        "render": function ( data, type, row ) {
                            return '<i class=\"btn btn-sm btn-outline-open grey-salsa\" onclick="doRule('+data+','+1+')\">启用</i><i class=\"btn btn-sm btn-outline-close grey-salsa\" onclick="doRule('+data+','+2+')\">禁用</i>';
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
    }

    function getDateTimeStamp(dateStr){
    	return Date.parse(dateStr.replace(/-/gi,"/"));
	}    
    initPickers();
    handleRecords();

})()
