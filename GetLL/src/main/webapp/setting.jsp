<%--
  Created by IntelliJ IDEA.
  User: tan
  Date: 14-10-30
  Time: 下午8:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <link href="css/my.min.css" rel="stylesheet" type="text/css">

    <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="css/flat-ui.min.css" rel="stylesheet" type="text/css">

    <!-- 响应式设计 -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="keywords" content="流量,移动,流量汇,流量汇管家,赚取流量,免费流量,摇流量,兑换,流量币,转赠,短信">
    <meta name="description" content="流量汇,流量汇管家,免费赚取流量,兑换流量,兑换短信.">

    <title>设置-流量汇管家</title>

    <link rel="apple-touch-icon-precomposed" sizes="512x512" href="image/icon.png">
    <link rel="shortcut icon" href="image/icon.png">
</head>

<body>

<jsp:include page="snap/nav_simple.html"/>

<div class="container">

    <div class="row">

        <div class="col-xs-12">
            <div style="color: #ff0000" id="result_msg">123</div>
        </div>

        <div class="col-md-4 col-xs-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">设置昵称</h3>
                </div>
                <div class="panel-body">
                    <input value="xiayule" name="nickname" id="nickname"><br/>
                    4-10个字符，可由中英文、数字、"_" 、 "-"组成，不能全是数字
                </div>
                <div class="panel-footer" style="text-align: center"><button type="button" style="width: 100%;" class="btn btn-primary">保存</button></div>
            </div>
        </div>

        <div class="col-md-4 col-xs-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">朋友摇奖功能</h3>
                </div>
                <div class="panel-body">
                    开启朋友摇奖,每天18点会使用本站手机号再为您摇奖5次,只不过摇到的需要来本站领取,同时还会有移动的短信通知
                </div>
                <div class="panel-footer" style="text-align: center"><input class="checkbox" type="checkbox" data-toggle="switch" id="forFriend"></div>
            </div>
        </div>


        <div class="col-md-4 col-xs-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">摇奖短信通知</h3>
                </div>
                <div class="panel-body">
                    朋友摇奖会收到短信通知，开启该功能可以屏蔽短信通知，次日生效。<span style="color: #ff0000">注:该功能暂时不可用，请耐心等待修复</span>
                </div>
                <div class="panel-footer" style="text-align: center"><input class="checkbox" type="checkbox" data-toggle="switch" id="fdShakeNotify"/></div>
            </div>
        </div>

        <div class="col-md-4 col-xs-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">停止本站服务</h3>
                </div>
                <div class="panel-body">
                    停止本站服务后，本站将清除所有与您相关的信息。届时，您也无法使用本站的服务。如果想要再次使用本站服务，重新登录即可。
                </div>
                <div class="panel-footer" style="text-align: center"><button type="button" class="btn btn-danger" style="width: 100%;" onclick="deleteService()">停止服务</button></div>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="js/flat-ui.min.js"></script>

<script type="text/javascript" src="js/application.min.js"></script>

<script type="text/javascript" src="js/eventMan.min.js"></script>
<script type="text/javascript" src="js/common.min.js"></script>

<script>
    //todo: 使用data-toggle实现?
    $('input[id="fdShakeNotify"]').on({
        'init.bootstrapSwitch': function() {
            // 更新 移动好友摇奖短信通知状态
            $.post("/ajax/smsNoticeSetQuery",{}, function(data) {
                if (data.status != "ok") {
                    alert(data.message);
                }else{
                    var result = data.result;

                    //朋友为我摇奖时提醒
                    var fdShakeNotify = result.fdShakeNotify;
                    updateDivStyle("fdShakeNotify", fdShakeNotify);
                }
            },"json");
        },
        'switchChange.bootstrapSwitch': function(event, state) {

            if ($("#fdShakeNotify").is(":focus") == false) return;
            smsNotifyUpdate("fdShakeNotify", state);
        }
    });

    $('input[id="forFriend"]').on({
        'init.bootstrapSwitch': function() {
            // 更新朋友摇奖状态
            $.getJSON("/ajax/statusForFriend.action?r=" + new Date().getTime(), function(data) {
                if (data.status != "ok") {
                    alert(data.message);
                }else{
                    var result = data.result;

                    //朋友为我摇奖时提醒
                    var forFriendSuber = result.forFriendSuber;

                    updateDivStyle("forFriend", forFriendSuber);
                }
            },"json");
        },
        'switchChange.bootstrapSwitch': function(event, state) {
            //todo: 如果没有焦点，证明不是人控制的,　不做任何处理
            if ($("#forFriend").is(":focus") == false) return;

            forFriendUpdate(state);
        }
    });

    // 登录检查
    eventMan.checkLogin(function() {
        if(eventMan.isLogin()) {

        }
    }, function () {
        // 验证登录失败
        locationPage("/login.jsp")
    });


    function updateDivStyle(type,value){
        if(type != ""){
            $("#" + type).bootstrapSwitch("state", value == 1 ? true : false);
        }
    }

    function smsNotifyUpdate(type,bvalue){
        if (bvalue == true) {
            value = 1;
        } else {
            value = 0;
        }

        var lazySeconds = 1;
        $.post("/ajax/smsNoticeSet.action?r=" + new Date().getTime(),{"type":type,"value":value}, function(data) {
            if (data.status != "ok") {
                alert(data.message);
            }else{
                // 成功
            }
        },"json");
    }

    function forFriendUpdate(bvalue){
        if (bvalue == true) {
            value = 1;
        } else {
            value = 0;
        }

        $.post("/ajax/changeStatusForFriend.action?r=" + new Date().getTime(),{"status":value}, function(data) {
            if (data.status != "ok") {
                alert(data.message);
            }else{
                // 成功
            }
        },"json");
    }

    function deleteService() {
        var confirmMessage = "退订服务是不可撤销的,您确定要停用本站服务吗?";
        if (confirm(confirmMessage)) {
            $.post("/ajax/deleteService.action?r=" + new Date().getTime(), function(data) {
                if (data.status != "ok") {
                    alert(data.message);
                }else{
                    // 退订成功
                    locationPage("/home.action");
                }
            },"json");
        }
    }
</script>


<jsp:include page="snap/foot.html"/>

</body>
</html>
