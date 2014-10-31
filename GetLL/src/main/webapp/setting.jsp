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

    <%--<link href="css/my.min.css" rel="stylesheet" type="text/css">--%>

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

<jsp:include page="nav_simple.html"/>

<div class="container">

    朋友为我摇奖时通知我: <input class="checkbox" type="checkbox" data-toggle="switch" id="fdShakeNotify" />


</div>


<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="js/flat-ui.min.js"></script>
<script type="text/javascript" src="js/application.min.js"></script>

<script type="text/javascript" src="js/eventMan.min.js"></script>
<script type="text/javascript" src="js/common.min.js"></script>

<script>
    // 登录检查
    eventMan.checkLogin(function() {
        if(eventMan.isLogin()) {
            // 显示个人短信提醒设置
//            loadSmsSystemSet();
        }
    });

    var onStyle = "but_visible";
    var offStyle = "but_disable";
    function loadSmsSystemSet(){
        var lazySeconds = 1;
        $.post("/ajax/smsNoticeSetQuery",{}, function(data) {
            if (data.status != "ok") {
                alert(data.message);
            }else{
                var result = data.result;

                /*流量汇各类活动提醒状态

                 var otherActivityNotify = result.otherActivityNotify;
                 updateDivStyle("otherActivityNotify",otherActivityNotify);
                 */

                //朋友为我摇奖时提醒
                var fdShakeNotify = result.fdShakeNotify;
                updateDivStyle("fdShakeNotify",fdShakeNotify);

                //秒杀时提醒

               /* var seckillNotify = result.seckillNotify;
                updateDivStyle("seckillNotify",seckillNotify);*/

                //摇奖提醒状态


               /* var shakeNotify = result.shakeNotify;
                updateDivStyle("shakeNotify",shakeNotify);*/
            }
        },"json");
    }

    function updateDivStyle(type,value){
        var styles = "";
        if(value == "1"){
            styles = onStyle;
        }else{
            styles = offStyle;
        }

        //根据不同的提醒类型获取对应的修改div
        var  divId = "";
        if(type=="otherActivityNotify"){
            //divId = "shakeNewRemindDiv";
        }else if(type=="fdShakeNotify"){
            divId = "fdToMeShakeDiv";
        }else if(type=="seckillNotify"){
            divId = "spikeActivityDiv";
        }else if(type=="shakeNotify"){
            divId = "dayRemindShakeDiv";
        }
        if(divId != ""){
            $("#"+divId).html([
                        '<div class="'+styles+'" onclick="javascript:smsNotifyUpdate(\''+type+'\','+value+');"><div class="btn"></div></div>'
            ].join(''));
        }
    }

    function smsNotifyUpdate(type,value){
        if(value=="0"){value = "1";}else{value = "0";}
        var lazySeconds = 1;
        $.post("/ajax/smsNoticeSet.action?r=" + new Date().getTime(),{"type":type,"value":value}, function(data) {
            if (data.status != "ok") {
                alert(data.message);
            }else{
                updateDivStyle(type,value);
            }
        },"json");
    }


</script>


<jsp:include page="foot.html"/>

</body>
</html>
