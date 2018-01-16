$(function() {
    //dataTable初始化对象  
    var initTable = function (queryData) {
    	var table= $('#example').DataTable({  
             "paging": true,  
             "lengthChange": true,  
             "searching": false,  
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
    }
    initTable();
});