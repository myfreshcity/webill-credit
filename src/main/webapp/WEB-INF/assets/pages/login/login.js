var Login = function() {

    var handleLogin = function() {

        $('.login-form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            rules: {
                username: {
                    required: true
                },
                password: {
                    required: true
                },
                remember: {
                    required: false
                }
            },

            messages: {
                username: {
                    required: "请填写用户名"
                },
                password: {
                    required: "请填写密码"
                }
            },

            invalidHandler: function(event, validator) { //display error alert on form submit   
                $('#input-required', $('.login-form')).show();
            },

            highlight: function(element) { // hightlight error inputs
                $(element)
                    .closest('.form-group').addClass('has-error'); // set error class to the control group
            },

            success: function(label) {
                label.closest('.form-group').removeClass('has-error');
                label.remove();
            },

            errorPlacement: function(error, element) {
                error.insertAfter(element.closest('.input-icon'));
            },

            submitHandler: function(form) {
                login(); // form validation success, call ajax form submit
            }
        });

        $('.login-form input').keypress(function(e) {
            if (e.which == 13) {
                if ($('.login-form').validate().form()) {
                    login(); //form validation success, call ajax form submit
                }
                return false;
            }
        });

        $('.forget-form input').keypress(function(e) {
            if (e.which == 13) {
                if ($('.forget-form').validate().form()) {
                    $('.forget-form').submit();
                }
                return false;
            }
        });

        $('#forget-password').click(function(){
            $('.login-form').hide();
            $('.forget-form').show();
        });

        $('#back-btn').click(function(){
            $('.login-form').show();
            $('.forget-form').hide();
        });

        function login() {
            $.ajax({
                type: 'POST',
                url: BASE_PATH+'/loginPost',
                data: $('.login-form').serialize(),
                dataType: 'json',
                success: function (data) {

                    $('.alert-danger', $('.login-form')).hide();
                    console.debug(data.msg);
                    if (data.success) {
                        window.location.href = BASE_PATH+'/';
                    }else{
                        $('#input-error', $('.login-form')).show();
                    }
                },
                error: function () {
                    alert("出错了,请重试!");
                }
            });
        }
    }

 
  

    return {
        //main function to initiate the module
        init: function() {

            handleLogin();

            // init background slide images
            $('.login-bg').backstretch([
                BASE_PATH+"/assets/pages/login/img/login/bg1.png",
                BASE_PATH+"/assets/pages/login/img/login/bg2.png",
                BASE_PATH+"/assets/pages/login/img/login/bg3.png",
                BASE_PATH+"/assets/pages/login/img/login/bg4.png",
                ], {
                  fade: 1000,
                  duration: 8000
                }
            );

            $('.forget-form').hide();

        }

    };

}();

jQuery(document).ready(function() {
    Login.init();
});