<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<link href="${base}/assets/pages/login/css/login-5.css" rel="stylesheet" type="text/css"/>
    <%-- <link href="${base}/assets/pages/login/css/login-5.min.css" rel="stylesheet" type="text/css"/> --%>
    <!-- BEGIN PAGE LEVEL PLUGINS -->
    <script src="${staticBase}/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${staticBase}/assets/global/plugins/jquery-validation/js/additional-methods.min.js"
            type="text/javascript"></script>
    <script src="${staticBase}/assets/global/plugins/select2/js/select2.full.min.js" type="text/javascript"></script>
    <script src="${staticBase}/assets/global/plugins/backstretch/jquery.backstretch.min.js" type="text/javascript"></script>
    <!-- END PAGE LEVEL PLUGINS -->
    <!-- BEGIN PAGE LEVEL SCRIPTS -->
    <script>
        var BASE_PATH = '${base}';
    </script>
    <script src="${base}/assets/pages/login/login.js" type="text/javascript"></script>
    <!-- END PAGE LEVEL SCRIPTS -->
</head>
<body>
<div class="user-login-5">
    <div class="row bs-reset">
        <div class="col-md-6 bs-reset">
            <div class="login-bg" style="background-image:url(${base}/assets/pages/login/img/login/bg1.png)">
                <%-- <img class="login-logo" src="${base}/assets/pages/login/img/login/logo.png"/></div> --%>
        </div>
        <div class="col-md-6 login-container bs-reset">
            <div class="login-content">
                <h1>慢慢花后台管理登录</h1>
                <p> 有钱不任性，我要慢慢花... </p>
                <form action="#" class="login-form" method="post">
                    <div class="alert alert-danger display-hide" id="input-required">
                        <button class="close" data-close="alert"></button>
                        <span>请输入用户名或密码 </span>
                    </div>
                    <div class="alert alert-danger display-hide" id="input-error">
                        <button class="close" data-close="alert"></button>
                        <span>错误的用户名或密码 </span>
                    </div>
                    <div class="row">
                        <div class="col-xs-6">
                            <input class="form-control form-control-solid placeholder-no-fix form-group" type="text"
                                   autocomplete="off" placeholder="用户名" name="username"/></div>
                        <div class="col-xs-6">
                            <input class="form-control form-control-solid placeholder-no-fix form-group" type="password"
                                   autocomplete="off" placeholder="密码" name="password"/></div>
                    </div>
                    <div class="row">
                        <div class="col-sm-4">
                            <div class="rem-password">
                                <p>记住我
                                    <input type="checkbox" class="rem-checkbox"/>
                                </p>
                            </div>
                        </div>
                        <div class="col-sm-8 text-right">
                            <div class="forgot-password">
                                <a href="javascript:;" id="forget-password" class="forget-password">密码忘记了?</a>
                            </div>
                            <button class="btn blue" type="submit">登录</button>
                        </div>
                    </div>
                </form>
                <!-- BEGIN FORGOT PASSWORD FORM -->
                <form class="forget-form" action="javascript:;" method="post">
                    <h3 class="font-green">密码忘记了?</h3>
                    <p> 请输入邮箱地址以重置密码 </p>
                    <div class="form-group">
                        <input class="form-control placeholder-no-fix form-group" type="text" autocomplete="off"
                               placeholder="Email" name="email"/></div>
                    <div class="form-actions">
                        <button type="button" id="back-btn" class="btn grey btn-default">返回</button>
                        <button type="submit" class="btn blue btn-success uppercase pull-right">提交</button>
                    </div>
                </form>
                <!-- END FORGOT PASSWORD FORM -->
            </div>
        </div>
    </div>
</div>
</body>
</html>
