<%--
  Created by IntelliJ IDEA.
  User: tan
  Date: 14-9-1
  Time: 下午8:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>

    <link href="css/my.min.css" rel="stylesheet" type="text/css">

    <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="css/flat-ui.min.css" rel="stylesheet" type="text/css">

    <link rel="apple-touch-icon-precomposed" sizes="512x512" href="image/icon.png">
    <link rel="shortcut icon" href="image/icon.png">

    <meta name="keywords" content="流量,移动,流量汇,流量汇管家,赚取流量,免费流量,摇流量,兑换,流量币,转赠,短信">
    <meta name="description" content="流量汇,流量汇管家,免费赚取流量,兑换流量,兑换短信.">

    <!-- 响应式设计 -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>转赠-流量汇管家</title>

    <style type="text/css">
        .mask {
            display: none;
            position: absolute;
            top: 0px;
            width: 100%;
            height: 120%;
            line-height: 100%;
            background: none repeat scroll 0% 0% #000;
            opacity: 0.5;
            z-index: 101;
        }
    </style>
</head>

<body>

<jsp:include page="snap/nav_simple.html"/>

<div class="container">
    <div class="row">
        <div class="col-xs-12 col-md-6">
            <div class="form-area form-horizontal">
                <p class="form-title">转赠流量币</p>

                <div class="form-group">
                    <div id="message" style="color: #ff0000"></div>
                </div>


                <div class="form-group">
                    <input type="text" name="textfield" id="mobile" class="form-control" placeholder="朋友手机号:(必须为本省手机号)">
                </div>

                <div class="form-group">
                    <div class="input-group">
                        <input type="text" class="form-control" name="textfield2" id="transferGiftsText" placeholder="转赠金额数">

                        <span class="input-group-addon">共 <span style="color: #ff0000" id="credit">0</span> 流量币</span>
                    </div>
                </div>

                <div class="form-group">
                    <select name="remarkSelect" id="remarkSelect" class="form-control">
                        <option value="" disabled="disabled" selected="true">装土豪也要显摆几句(点击选择)</option>
                        <option value="千里送流量，礼轻情谊深。">千里送流量，礼轻情谊深。</option>
                        <option value="流量都去哪了？不够用咱给你补上！">流量都去哪了？不够用咱给你补上！</option>
                        <option value="流量币送上，马上有流量！">流量币送上，马上有流量！</option>
                        <option value="你问我爱你有多深，流量代表我的心。">你问我爱你有多深，流量代表我的心。</option>
                        <option value="补充你的上网能量，我一直都这么够意思！">补充你的上网能量，我一直都这么够意思！</option>
                    </select>
                </div>

                <div class="form-group">
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
                    <button type="button" class="btn btn-primary" onclick="submitFun()" style="width: 100%">确认</button>
                </div>
            </div>
        </div>

        <div class="col-xs-12 col-md-6">
            <div class="form-area">
            <div class="text">1、转赠后，流量币将直接进入对方帐户，无法退回，请先核对亲友的手机号码。</div>
            <div class="text">2、如亲友3天内未登录流量汇领取，流量币将自动退回您的帐户。</div>
            <div class="text">3、每个用户每天最多能转赠 3 次，最多被赠予 3 次，每月最多向 10 位朋友赠送。</div>
            <div class="text">4、每次转赠限额 1000 流量币。</div>
            </div>
        </div>
    </div>



</div>

<div id="mask" class="mask hide"></div>
<div id="buying_tip" class="hide" style="z-index: 101;color:#fff;text-align:center;position: absolute;">我们正在努力处理中，请稍候！</div>

<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>

<script type="text/javascript" src="js/tip.min.js"></script>
<script type="text/javascript" src="js/eventMan.min.js"></script>
<script type="text/javascript" src="js/score.min.js"></script>
<script type="text/javascript" src="js/common.min.js"></script>


<jsp:include page="snap/foot.html"/>

<script>
    var $mask = $("#mask");
    var $buyingTip = $("#buying_tip");
    initBuyingTip();

    // 登录检查
    eventMan.checkLogin(function () {
        if (eventMan.isLogin()) {
            // 显示个人积分
            score.showMyScore();
        }
    });

    //输入手机号码格式化

    $("#mobile").keyup(function () {
        $("#message").html("");
        $(this).val(eventMan.parseMobileFormat($(this).val()));
    });

    //手机验证码

    $("#getPassword").unbind("click");
    $("#getPassword").bind("click", function () {
        eventMan.getPassword("other");
    });

    // 输入事件:赠送流量币金额
    var transferGiftsText = $("#transferGiftsText");
    transferGiftsText.keyup(function () {
        $("#message").html("");
        $(this).val($(this).val().replace(/[^\d.]/g, "").replace(/^\./g, "").replace(/\.{2,}/g, ".").replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'));
    });

    //选择短信内容
    var remarkSelect = $("#remarkSelect");
    //remarkSelect.val("AAAAAA");
    //输入手机号码有效性验证

    function verifyMobile() {
        var mobile = eventMan.parseMobile($.trim($("#mobile").val()));
        if (!mobile) {
            return tip.transferGiftsEmptyMobile;
        } else if (!eventMan.mobileReg_.test(mobile)) {
            return tip.notShangDongMobile;
        }
        return true;
    }

    var submitBut = $("#submitBut");
    var messageDiv = $("#message");
    function submitFun() {
        var mobile = $.trim($("#mobile").val());
        //手机号码的验证

        var flag = verifyMobile();
        if (flag != true) {
            messageDiv.html(flag);
            return;
        }
        //流量币金额为空


        var transferGifts = $.trim($("#transferGiftsText").val());
        if (!transferGifts) {
            messageDiv.html(tip.transferGiftsEmpty);
        }
        if (Number(transferGifts) < 0.01) {
            messageDiv.html("流量币数额不正确（必须大于0.1）！");
            return;
        }
        if (Number(transferGifts) > 1000) {
            messageDiv.html("亲，每次转赠限额 1000 流量币，请修改转赠数量！");
            return;
        }
        var credit = score.scoreData_.credit;
        if (Number(transferGifts) > Number(credit)) {
            messageDiv.html("你要转赠的流量币不能超过您当前的流量币余额。");//，快去<a href='/portal/app/flowReChange.jsp'>充值</a>吧！
            return;
        }

        //验证码的验证
        var password = $.trim($("#password").val());
        if (!password) {
            messageDiv.html(tip.emptyPwd);
            return;
        }
        var remarkSelectText = remarkSelect.find("option:selected").val();

        var params = {
            "mobile": mobile,
            "password": password,
            "smsContext": remarkSelectText,
            "transferGifts": transferGifts
        };

        var confirmMessage = "您将向" + mobile + "赠送" + transferGifts + "个流量币，请核对转赠信息：";
        if (confirm(confirmMessage)) {
            //showBuyingTip();
            $.post("/ajax/transferGifts?r=" + Math.random(), params, function (data) {
                //hideBuyingTip();
                if (data.status != "ok") {
                    if (data.code != "NOLOGIN" && data.code != "10005" && data.code != "1") {
                        eventMan.pwdInterval(0);
                    }
                    alert(data.message);
                } else {
                    var result = data.result;

                    alert("成功向" + mobile + "赠送" + transferGifts + "个流量币，快通知TA登录本站领取吧！");
                    location.href = "/";
                }
            }, "json");
        } else {

        }
    }


    function initBuyingTip() {
        var $screen = $(".jia_layout .layout");
        $mask.css({"line-height": $screen.height() + "px", height: window.height, width: window.width});
        $buyingTip.css({"line-height": $screen.height() + "px", height: window.height, width: $screen.width()});
    }

    function showBuyingTip() {
        $buyingTip.css({top: $(window).scrollTop()});
        $mask.css({top: $(window).scrollTop()});
        $mask.show();
        $buyingTip.show();
        $("html").css({overflow: "hidden"});
        window.ontouchmove = function (e) {
            e.defaultPreventffed && e.defaultPrevented();
            e.returnValue = false;
            e.stopPropagation && e.stopPropagation();
            return false;
        };
    }

    function hideBuyingTip() {
        $mask.hide();
        $buyingTip.hide();
        $("html").css({overflow: "auto"});
        window.ontouchmove = null;
    }
</script>

</body>
</html>
