(function() {
    'use strict';
   $("input[name='pValue']").keyup(function(){
    	var pValue =$(this).val();
    	var detailId =$(this).prev("input[name='detailId']").val();
    	$(this).next("input[name='prmValueStr']").val(detailId+"|"+pValue);
    });
   
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
            dataTable: { // here you can define a typical datatable settings from http://datatables.net/usage/options 

                // Uncomment below line("dom" parameter) to fix the dropdown overflow issue in the datatable cells. The default datatable layout
                // setup uses scrollable div(table-scrollable) with overflow:auto to enable vertical scroll(see: assets/global/scripts/datatable.js). 
                // So when dropdowns used the scrollable div should be removed. 
                //"dom": "<'row'<'col-md-8 col-sm-12'pli><'col-md-4 col-sm-12'<'table-group-actions pull-right'>>r>t<'row'<'col-md-8 col-sm-12'pli><'col-md-4 col-sm-12'>>",
                
                "bStateSave": true, // save datatable state(pagination, sort, etc) in cookie.

                "lengthMenu": [
                    [10, 20, 50, 150, -1],
                    [10, 20, 50, 150, "All"] // change per page values here
                ],
                "pageLength": 10, // default record count per page
                "ajax": {
                    "url": BASE_PATH+'premiumOrder/premiumList/', // ajax source
                },
                "columns": [
                	{ "data": "id" },
                    { "data": "prmTypeStr" },
                    { "data": "prmName" }
                ],
                "orderCellsTop": true,
                "columnDefs": [
                    {
                        'orderable': false,
                        'targets': [0],
                        "render": function ( data, type, row ) {
                            return '<input type=\"checkbox\" name=\"premiumId\" class=\"checkboxes\" value=\"'+data+'\" />';
                            //return data +' ('+ row[3]+')';
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

    initPickers();
    handleRecords();
    function save() {
        $.ajax({
            type: 'POST',
            url: BASE_PATH+'illegal-order/confirmPay',
            data: form3.serialize(),
            dataType: 'json',
            success: function (data) {
                console.debug(data.msg);
                if (data.success) {
                    //window.location.href = BASE_PATH+'staff/edit/'+data.msg;
                    if (data.success) {
                    /*	Orgnization.tagOrgization().then(function(data){
                          $('#form_sample_3 #orgId').select({
                              data: Orgnization.getOragnization()
                          });
                      });*/
                        App.alert({
                            type: 'danger',
                            icon: 'warning',
                            message: '保存成功',
                            container: $('#form_sample_3'),
                            place: 'prepend'
                        });
                    }
                    
                }else{
	                App.alert({
	                    type: 'danger',
	                    icon: 'warning',
	                    message:data.msg,
	                    container: $('#form_sample_3'),
	                    place: 'prepend'
	                });
                }
            },
            error: function () {
                alert("出错了,请重试!");
            }
        });
    }    

})()
