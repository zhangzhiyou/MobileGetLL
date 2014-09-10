<%--
  Created by IntelliJ IDEA.
  User: tan
  Date: 14-9-10
  Time: 下午4:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="css/css.css" rel="stylesheet" type="text/css">

    <link href="css/my.css" rel="stylesheet" type="text/css">

    <link rel="apple-touch-icon-precomposed" sizes="512x512" href="image/icon.png">
    <link rel="shortcut icon" href="image/icon.png">

    <title>账户明细</title>

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

<div class="jia_layout">
    <div id="myaccount_mingxi_shouzhi">
        <div class="jia_nav2">
            <div class="jia_back"><img src="/image/go_back.png" width="21" height="19" align="absmiddle" /> <a href="/">返回</a></div>
        </div>
        <div class="myaccount_mingxi_shouzhi">
            <div class="myaccount_mingxi_title" id="days">流量币 | 最近0天 |</div>
            <div class="myaccount_mingxi_title2">
                <div>收入：<span class="myaccount_mingxi_yellow" id="incomeDiv">0</span></div>
                <div>支出：<span class="myaccount_mingxi_blue" id="expensesDiv">0</span></div>
            </div>
        </div>
    </div>


    <!--列表-->
    <div id= "mingxiListDiv"></div>
</div>


<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>

<script type="text/javascript" src="js/flowScore.js"></script>

<script type="text/javascript">

    //流量币总汇信息
    flowScore.loadCreditSum(function () {
        // 加载明细第一页
        flowScore.loadFirstCreditDetail();
    });

    $(window).scroll(function(){
        var scrolltop = $(document).scrollTop();
        var winHeight = $(document).height() - $(window).height();

        alert(document.documentElement.clientHeight);

        alert($(document).height()  + " " +  $(window).height())

        /*f(scrolltop == 0){
         flowScore.loadFirstCreditDetail();
         flowScore.loadCreditSum();
         }else*/
        if(scrolltop >= winHeight-410){
            alert(scrolltop + " " + winHeight)
            flowScore.loadCreditDetail("down");
        }
    });

    /**
     * 隐藏滚动动态加载流量信息
     /*$(window).scroll(function(){
        var detailVisible = $("#mingxiListDiv").is(":visible")
        // 可见的情况下，才加载数据
        if (detailVisible) {
            var scrolltop = $(document).scrollTop();
            var winHeight = $(document).height() - $(window).height();

            if(scrolltop >= winHeight-50 && eventMan.isLogin()){
                flowScore.loadCreditDetail("down");
            }
        }
    });*/

</script>

<!-- 返回顶部按钮 -->
<%--<div id="gotop"><img src="image/top.png"/></div>
<script>
    /*返回顶部JS*/
    backTop = function (btnId) {
        var btn = document.getElementById(btnId);
        var d = document.documentElement;
        var b = document.body;
        window.onscroll = btnDisplay;
        btn.onclick = function () {
            btn.style.display = "none";
            window.onscroll = null;
            this.timer = setInterval(function () {
                d.scrollTop -= Math.ceil((d.scrollTop + b.scrollTop) * 0.1);
                b.scrollTop -= Math.ceil((d.scrollTop + b.scrollTop) * 0.1);
                if ((d.scrollTop + b.scrollTop) == 0)
                    clearInterval(btn.timer, window.onscroll = btnDisplay);
            }, 10);
        };
        function btnDisplay() {
            btn.style.display = (d.scrollTop + b.scrollTop > 200) ? 'block' : "none";
        }
    };
    backTop('gotop');//返回顶部调用
</script>--%>

</body>
</html>
