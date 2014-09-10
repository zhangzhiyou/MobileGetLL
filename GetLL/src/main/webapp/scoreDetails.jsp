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

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <link rel="apple-touch-icon-precomposed" sizes="512x512" href="image/icon.png">
    <link rel="shortcut icon" href="image/icon.png">

    <title>账户明细</title>

    <!-- 回顶部按钮样式 -->
    <style type="text/css">
        /** 返回顶部CSS **/
        #gotop {
            position: fixed;
            bottom: 90px;
            right: 50px;
            top: auto;
            display: none;
            cursor: pointer;
            z-index: 999;
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

    <!-- 页脚 -->
    <div class="foot">
        <div class="smallfont">Auth:<a href="mailto:xiayule148@gmail.com">tanhe123</a> Version:0.9.2</div>

        <div style="display: none">
            <script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");
            document.write(unescape("%3Cspan id='cnzz_stat_icon_5678078'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s9.cnzz.com/stat.php%3Fid%3D5678078' type='text/javascript'%3E%3C/script%3E"));</script>
        </div>
    </div>
</div>


<script type="text/javascript" src="js/jquery.min.js"></script>

<script type="text/javascript" src="js/flowScore.js"></script>

<script type="text/javascript">

    //流量币总汇信息
    flowScore.loadCreditSum(function () {
        // 加载明细第一页
        flowScore.loadFirstCreditDetail();
    });

    /*$(window).scroll(function(){
        var scrolltop = $(document).scrollTop();
        var winHeight = $(document).height() - $(window).height();

        alert(document.documentElement.clientHeight);

        alert($(document).height()  + " " +  $(window).height())

        *//*f(scrolltop == 0){
         flowScore.loadFirstCreditDetail();
         flowScore.loadCreditSum();
         }else*//*
        if(scrolltop >= winHeight-410){
            alert(scrolltop + " " + winHeight)
            flowScore.loadCreditDetail("down");
        }
    });*/

    // todo: $(window).height() 获取的高度不对
    //隐藏滚动动态加载流量信息
     $(window).scroll(function(){

         alert($(window).height());
         // 可见的情况下，才加载数据
//         var scrolltop = $(document).scrollTop();
//         var winHeight = $(document).height() - $(window).height();

//         if(scrolltop >= winHeight-50){
//             flowScore.loadCreditDetail("down");
//         }
     });

</script>

<!-- 返回顶部按钮 -->
<div id="gotop"><img src="image/top.png"/></div>
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
</script>

</body>
</html>
