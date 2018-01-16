var Mgnt = function() {

    var tagCatsArray = null;
    var tagCatsMap = {};
    var OrgNameArray = null;
    var tagOrganizationMap = {};
    var UserRealNameArray = null;
    var tagUserMap = {};
    return {
        tagCatsReq: function() {
            return $.ajax({
                type: 'GET',
                url: BASE_PATH+'phoneTag/getAllCats',
                data: {
                },
                dataType: 'json',
                success: function (data) {
                    tagCatsArray = data;
                    for (var i=0;i<tagCatsArray.length;i++){
                        tagCatsMap[tagCatsArray[i].id] = tagCatsArray[i].text;
                    }
                },
                error: function () {
                    alert("出错了,请重试!");
                }
            });
        },
        tagOrgization: function() {
            return $.ajax({
                type: 'GET',
                url: BASE_PATH+'staff/getAllOrgnazition',
                data: {
                },
                dataType: 'json',
                success: function (data) {
                	OrgNameArray = data;
                    for (var i=0;i<OrgNameArray.length;i++){
                    	tagOrganizationMap[OrgNameArray[i].id] = OrgNameArray[i].text;
                    	
                    }
                },
                error: function () {
                    alert("出错了,请重试!");
                }
            });
        },
        tagUser: function() {
            return $.ajax({
                type: 'GET',
                url: BASE_PATH+'phone/getAllUser',
                data: {
                },
                dataType: 'json',
                success: function (data) {
                	UserRealNameArray = data;
                    for (var i=0;i<UserRealNameArray.length;i++){
                    	tagUserMap[UserRealNameArray[i].id] = UserRealNameArray[i].text;
                    	
                    }
                },
                error: function () {
                    alert("出错了,请重试!");
                }
            });
        },
        decodePhoneTagCat: function(val){
            return tagCatsMap[val];
        },
        getPhoneTagCats: function () {
            return tagCatsArray;
        },
        decodeOrgnName: function(val){
            return tagOrganizationMap[val];
        },
        getOrganizationName: function () {
            return OrgNameArray;
        },
        decodeUser:function(val){
        	return tagUserMap[val];
        },
        getUserRealName:function(){
        	return UserRealNameArray;
        },
    };

}();

jQuery(document).ready(function() {
     // init metronic core componets
});