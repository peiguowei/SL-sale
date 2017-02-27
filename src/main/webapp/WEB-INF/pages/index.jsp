<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>SL会员商城</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Charisma, a fully featured, responsive, HTML5, Bootstrap admin template.">
    <meta name="author" content="Amao">

    <!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="/cdn.bootcss.com/html5shiv/r29/html5.js"></script>
    <![endif]-->
    <link href="/statics/css/bootstrap-responsive.css" rel="stylesheet">
    <link href="/statics/css/bootstrap-cerulean.css" rel="stylesheet">
    <link href="/statics/css/charisma-app.css" rel="stylesheet">
    <!-- The styles -->
    <style type="text/css">
        body {
            padding-bottom: 40px;
        }
    </style>
    <link rel="shortcut icon" href="/statics/img/favicon.ico">
</head>

<body>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="row-fluid">
            <div class="span12 center login-header">
                <h2>SL会员商城</h2>
            </div>
        </div>
        <div class="row-fluid">
            <div class="well span5 center login-box">
                <div class="alert alert-info">
                    请输入登陆账号和密码...
                </div>
                <div class="form-horizontal">
                    <fieldset>
                        <div class="input-prepend" title="登录账号" data-rel="tooltip">
                            <span class="add-on"><i class="icon-user"></i></span>
                            <input autofocus class="input-large span10" name="loginCode" id="logincode" type="text" value=""/>
                        </div>
                        <div class="clearfix"></div>

                        <div class="input-prepend" title="登陆密码" data-rel="tooltip">
                            <span class="add-on"><i class="icon-lock"></i></span>
                            <input class="input-large span10" name="password" id="password" type="password" value=""/>
                        </div>
                        <div class="clearfix"></div>

                        <div class="clearfix"></div>
                        <ul id="formtip"></ul>
                        <p class="center span5">
                            <button type="submit" class="btn btn-primary" id="loginBtn">登录</button>
                        </p>
                    </fieldset>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="../statics/js/jquery-1.7.2.min.js"></script>
<script src="..//statics/localjs/index.js"></script>
</body>
</html>


