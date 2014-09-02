<%--
  Created by IntelliJ IDEA.
  User: tan
  Date: 14-9-1
  Time: 下午8:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="css/css.css" rel="stylesheet" type="text/css">

    <link href="css/my.css" rel="stylesheet" type="text/css">

    <link rel="apple-touch-icon-precomposed" sizes="512x512" href="image/icon.png">
    <link rel="shortcut icon" href="image/icon.png">

    <title>兑换</title>

    <style>
        .pwd {font-size:12px;}
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
<div class="layout" style="background-color:#efeeec;">
    <div class="jia_nav2">
        <a href="/"><div class="jia_back"><img src="image/go_back.png" width="21" height="19" align="absmiddle" border="0" /> 返回</div></a>
    </div>
    <div class="zhuanfenggeblock"></div>
    <div class="zhuanzeng_layout">
        <div class="zhuanzeng_inputbg">
            <label>
                <input type="text" name="textfield" id="mobile" class="zhuanzeng_input1" placeholder="朋友手机号（必须为本省手机号）："  />
            </label>
        </div>
        <div class="zhuanzeng_title">转赠数额<span class="notes">（当前余额</span><span class="redfont" id="credit">0</span><span class="notes">流量币）</span>：</div>
        <div class="zhuanzeng_inputbg">
            <label>
                <input type="text" name="textfield2" id="transferGiftsText" class="zhuanzeng_input2" />
            </label>
            <span class="zhuanzeng_input_text">流量币</span></div>
        <div class="zhuanzeng_title">
            <select name="remarkSelect" id="remarkSelect">
                <option value="" disabled="disabled" selected="true">装土豪也要显摆几句(点击选择)</option>
                <option value="千里送流量，礼轻情谊深。">千里送流量，礼轻情谊深。</option>
                <option value="流量都去哪了？不够用咱给你补上！">流量都去哪了？不够用咱给你补上！</option>
                <option value="流量币送上，马上有流量！">流量币送上，马上有流量！</option>
                <option value="你问我爱你有多深，流量代表我的心。">你问我爱你有多深，流量代表我的心。</option>
                <option value="补充你的上网能量，我一直都这么够意思！">补充你的上网能量，我一直都这么够意思！</option>
            </select>
        </div>
        <div class="zhuanzeng_title">
            <div class="zhuanzeng_title2">动态密码：</div>
            <div class="zhuanzeng_inputbg2">
                <label>
                    <input type="text" id="password" name="password" class="pop_cinput zhuanzeng_input1" />
                </label>
            </div>
            <div class="zhuanzeng_title3">
                <a href="javascript:;" id="getPassword" class="pwd">免费获取动态密码</a>
                <a href="javascript:;" id="sendStatus" class="zhuanzeng_input_text" style="display:none;">已发送(<span id="seconds" style="display:inline;">0</span>秒)</a>
            </div>
        </div>

        <div class="zhuanzeng_but" onclick="submitFun();"><a href="#">确定</a></div>
        <div class="redfont" id="message"></div>
    </div>
    <div class="zhuanzeng_layout2">
        <div class="text">1、转赠后，流量币将直接进入对方帐户，无法退回，请先核对亲友的手机号码。</div>
        <div class="text">2、如亲友3天内未登录流量汇领取，流量币将自动退回您的帐户。</div>
        <div class="text">3、每个用户每天最多能转赠 3 次，最多被赠予 3 次，每月最多向 10 位朋友赠送。</div>
        <div class="text">4、每次转赠限额 1000 流量币。</div>
    </div>
    <div style="height:60px;"></div>


    <div style="height:55px;"></div>
</div>

<div id="mask" class="mask hide"></div>
<div id="buying_tip" class="hide" style="z-index: 101;color:#fff;text-align:center;position: absolute;">我们正在努力处理中，请稍候！</div>

<script type="text/javascript" src="js/jquery.min.js"></script>

<script type="text/javascript" src="js/eventMan.js"></script>
<script type="text/javascript" src="js/score.js"></script>

<script>
    var $mask = $("#mask");
    var $buyingTip = $("#buying_tip");
    initBuyingTip();

    // 登录检查
    eventMan.checkLogin(function() {
        if(eventMan.isLogin()) {
            // 显示个人积分
            score.showMyScore();
        }
    });

    //输入手机号码格式化

    $("#mobile").keyup(function() {
        $("#message").html("");
        $(this).val(eventMan.parseMobileFormat($(this).val()));
    });

    //手机验证码

    $("#getPassword").unbind("click");
    $("#getPassword").bind("click",function(){
        //TODO: 好像收不到
        eventMan.getPassword("other");
    });

    // 输入事件:赠送流量币金额
    var transferGiftsText = $("#transferGiftsText");
    transferGiftsText.keyup(function() {
        $("#message").html("");
        $(this).val($(this).val().replace(/[^\d.]/g,"").replace(/^\./g,"").replace(/\.{2,}/g,".").replace(".","$#$").replace(/\./g,"").replace("$#$",".").replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'));
    });

    //选择短信内容
    var remarkSelect = $("#remarkSelect");
    //remarkSelect.val("AAAAAA");
    //输入手机号码有效性验证

    function verifyMobile(){
        var mobile = eventMan.parseMobile($.trim($("#mobile").val()));
        if (!mobile) {
            return tip.transferGiftsEmptyMobile;
        }else if (!eventMan.mobileReg_.test(mobile)) {
            return tip.notShangDongMobile;
        }
        return true;
    }

    var submitBut = $("#submitBut");
    var messageDiv = $("#message");
    function submitFun(){
        var mobile = $.trim($("#mobile").val());
        //手机号码的验证

        var flag = verifyMobile();
        if(flag != true){
            messageDiv.html(flag);
            return ;
        }
        //流量币金额为空


        var transferGifts =$.trim($("#transferGiftsText").val());
        if (!transferGifts) {
            messageDiv.html(tip.transferGiftsEmpty);
        }
        if(Number(transferGifts)<0.01){
            messageDiv.html("流量币数额不正确（必须大于0.1）！");
            return ;
        }
        if(Number(transferGifts)>1000){
            messageDiv.html("亲，每次转赠限额 1000 流量币，请修改转赠数量！");
            return ;
        }
        var credit = score.scoreData_.credit;
        if(Number(transferGifts) > Number(credit)){
            messageDiv.html("你要转赠的流量币不能超过您当前的流量币余额。");//，快去<a href='/portal/app/flowReChange.jsp'>充值</a>吧！
            return ;
        }

        //验证码的验证
        var password = $.trim($("#password").val());
        if (!password) {
            messageDiv.html(tip.emptyPwd);
            return;
        }
        var remarkSelectText = remarkSelect.find("option:selected").val();

        var params = {
            "mobile" :  mobile,
            "password" : password,
            "smsContext":remarkSelectText,
            "transferGifts":transferGifts
        };

        //todo: 转赠
        var confirmMessage = "您将向" + mobile + "赠送" + transferGifts + "个流量币，请核对转赠信息：";
        if (confirm(confirmMessage)) {
            //showBuyingTip();
            $.post("/flowScore?method=transferGifts&r=" + Math.random(),params,function(data) {
                //hideBuyingTip();
                if (data.status != "ok") {
                    if(data.code != "NOLOGIN" && data.code != "10005" && data.code != "1"){
                        shakeCom.pwdInterval(0);
                    }
                    alert(data.message);
                }else{
                    var result = data.result;
                    //alert("您已成功给好友"+mobile+"转赠"+transferGifts+"流量币！");

                    alert("成功向"+mobile+"赠送"+transferGifts+"个流量币，快通知TA登录流量汇领取吧！");
                    location.href = "/portal/app/transferGifts.jsp";
                }
            },"json");
        }else{

        }
    }


    function initBuyingTip() {
        var $screen = $(".jia_layout .layout");
        $mask.css({"line-height":$screen.height() + "px",height:window.height,width:window.width});
        $buyingTip.css({"line-height":$screen.height() + "px",height:window.height,width:$screen.width()});
    }

    function showBuyingTip() {
        $buyingTip.css({top : $(window).scrollTop()});
        $mask.css({top : $(window).scrollTop()});
        $mask.show();
        $buyingTip.show();
        $("html").css({overflow:"hidden"});
        window.ontouchmove = function(e){
            e.defaultPrevented && e.defaultPrevented();
            e.returnValue=false;
            e.stopPropagation && e.stopPropagation();
            return false;
        };
    }

    function hideBuyingTip() {
        $mask.hide();
        $buyingTip.hide();
        $("html").css({overflow:"auto"});
        window.ontouchmove = null;
    }


</script>

</body>
</html>
