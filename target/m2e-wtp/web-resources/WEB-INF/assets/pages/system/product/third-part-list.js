$(function() {
    //dataTable初始化对象  
    var initTable = function (queryData) {
    	var table= $('#bigDataList').DataTable({  
             "paging": true,  
             "lengthChange": true,  
             "searching": true,  
             "ordering": false,  
             "info": true,  
             "autoWidth": false,  
             "destroy":true,  
             "processing":true, 
             //"serverSide":true,    //true代表后台处理分页，false代表前台处理分页 
             "bStateSave": true, // save datatable state(pagination, sort, etc) in cookie.
             "pagingType": "bootstrap_extended",
             "lengthMenu": [
                 [10, 20, 50, 150, -1],
                 [10, 20, 50, 150, "All"] // change per page values here
             ],
             // set the initial value
             "pageLength": 10,
             "order": [
                 [1, "asc"]
             ], // set first column as a default sort by asc
             //当处理大数据时，延迟渲染数据，有效提高Datatables处理能力  
             "deferRender": true, 
             "ajax":{  
                 url:BASE_PATH+'product/tpProdList',  
                 dataSrc:  
                         function(data){  
                           if(data.callbackCount==null){  
                             data.callbackCount=0;  
                           }  
                           //抛出异常  
                           if(data.sqlException){  
                             alert(data.sqlException);  
                           }  
                           //查询结束取消按钮不可用  
                           $("#queryDataByParams").removeAttr("disabled");  
                           return data.records;             //自定义数据源，默认为data  
                         },     //dataSrc相当于success，在datatable里面不能用success方法，会覆盖datatable内部回调方法  
                 type:"post",  
                 data:queryData  
               },  
               columns:[  
                 { data: 'caseCode' },  
                 { data: 'prodName'},  
                 { data: 'planName' },  
                 { data: 'companyName'},  
                 { data: 'thirdPUrl' },  
                 { data: 'fristCatName'},  
                 { data: 'secondCatName' },  
                 { data: 'caseCode'},  
               ],  
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
                		  var param = '\''+row.caseCode+'\','+row.fristCategory+','+row.secondCategory+',\''+row.companyName+'\',\''+row.planName+'\'';
                		  return '<div style=\"text-align: center;\">' + 
                		  '<button id="deleteOne" class="btn btn-sm btn-outline grey-salsa" data-id='+data+' data-toggle="modal" onclick="publish('+param+')\">发布商品</button>'+
                		  '</div>';
                	  }
                  }
               ],
               "language": {
                   "aria": {
                       "sortAscending": ": activate to sort column ascending",
                       "sortDescending": ": activate to sort column descending"
                   },
                   "processing" : "正在获取数据，请稍后...",
                   "emptyTable": "No data available in table",
                   "info": "从 _START_ 到 _END_ |共 _TOTAL_ 条数据",
                   "infoEmpty": "没有数据",
                   "infoFiltered": "(从 _MAX_ 条数据中检索)",
                   "lengthMenu": "每页 _MENU_ 条记录",
                   "search": "Search:",
                   "zeroRecords": "没有检索到数据",
                   "paginate": {
                       "previous":"Prev",
                       "next": "Next",
                       "last": "Last",
                       "first": "First"
                   }
               }
        });
        
        $('#global').on( 'keyup', function () {
    		table.search( this.value ).draw();
    	} );    
    	$('#prodName').on( 'keyup', function () {
            table.column(1).search( this.value ).draw();
        } );    
    	$('#companyName').on( 'change', function () {
    		var companyName = $("#companyName").val();
            table.column(3).search( companyName ).draw();
    	} );
    	$('#thirdPartName').on( 'change', function () {
    		var companyName = $("#thirdPartName").val();
    		table.column(4).search( companyName ).draw();
    	} );
    }
    initTable();
});