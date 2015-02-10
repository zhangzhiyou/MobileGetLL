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
    <jsp:include page="snap/head.html"/>
    <title>登录-流量汇管家</title>
</head>

<body style="height: 100%">

<jsp:include page="snap/nav_simple.html"/>

<div class="container">
    <!-- 登录框 -->
    <div class="row">

        <%--公告--%>
        <jsp:include page="snap/notification.html"/>

    </div>

    <div class="row">
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
                <div class="form-title"><span>本站功能:</span></div>
                <ol>
                    <li>每日凌晨6点,为您进行10次摇奖</li>
                    <li>每日18点,使用本站手机号,为您进行5次摇奖(需手动开启设置)</li>
                    <li>登录本站可以兑换、转赠、领取流量币</li>
                </ol>
            </div>
        </div>

    </div>


    <script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="js/flat-ui.min.js"></script>
    <script type="text/javascript" src="js/application.min.js"></script>


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

<jsp:include page="snap/foot.html"></jsp:include>


</body>
</html>
