<%--
  Created by IntelliJ IDEA.
  User: tan
  Date: 14-10-2
  Time: 下午10:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">

    <link href="css/my.min.css" rel="stylesheet" type="text/css">

    <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="css/flat-ui.min.css" rel="stylesheet" type="text/css">

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <!-- 响应式设计 -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <meta name="keywords" content="流量,移动,流量汇,流量汇管家,赚取流量,免费流量,摇流量,兑换,流量币,转赠,短信">
    <meta name="description" content="流量汇,流量汇管家,免费赚取流量,兑换流量,兑换短信.">

    <title>登录-流量汇管家</title>

    <link rel="apple-touch-icon-precomposed" sizes="512x512" href="image/icon.png">
    <link rel="shortcut icon" href="image/icon.png">
</head>
<body style="height: 100%">

<jsp:include page="nav_simple.html"/>


<div class="container">
    <!-- 登录框 -->
    <div class="row" >
        <div class="col-xs-12 col-md-6">
            <div class="form-area">
                <div class="form-title">
                    <span>登录</span>
                </div>

                <div class="form-group">
                    <span id="message" style="color: red;"></span>
                </div>

                <div class="form-group">
                    <input type="text" class="form-control" name="mobile" id="mobile" placeholder="请输入手机号">
                </div>

                <div class="form-group hideme" id="passwordContent">
                    <div class="input-group">
                        <input type="text" class="form-control" id="password" name="password" placeholder="动态密码">

                        <span class="input-group-btn">
                            <button type="button" class="btn btn-default" id="getPassword">获取动态密码</button>
                            <button type="button" class="btn btn-default" style="display: none" id="sendStatus">已发送(<span
                                    id="seconds" style="display:inline;">0</span>秒)</button>
                        </span>
                    </div>
                </div>

                <div class="form-group">
                    <button type="button" title="登录, 开启神奇之旅" style="width: 100%" class="btn btn-primary" id="loginDo">登录</button>
                </div>
            </div>
        </div>

        <div class="col-xs-12 col-md-6">
            <div class="form-area">
                <div class="form-title"><span>说明:</span></div>

                <div class="text">1、登录即表示使用本站服务</div>
                <div class="text">2、每日6点准时为您摇奖</div>
                <div class="text">3、赚得流量币归手机号主所有</div>
                <div class="text">4、有不明白的地方查看<a href="#" onclick="locationPage('/help.jsp')" title="帮助说明">帮助</a></div>
            </div>
        </div>
    </div>


    <script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>

    <script type="text/javascript" src="js/bootstrap.min.js"></script>

    <script type="text/javascript" src="js/eventMan.min.js"></script>
    <script type="text/javascript" src="js/common.min.js"></script>

    <script>
        eventMan.checkLogin(function () {
            if (eventMan.isLogin()) {
                locationPage("/home.action")
            }
        });
    </script>
</div>

<jsp:include page="foot.html"></jsp:include>


</body>
</html>
