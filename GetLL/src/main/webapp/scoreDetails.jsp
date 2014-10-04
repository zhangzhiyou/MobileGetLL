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
</head>

<body>
<div class="layout">
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

    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script type="text/javascript" src="js/flowScore.js"></script>


    <script type="text/javascript">

        //流量币总汇信息
        flowScore.loadCreditSum(function () {
            // 加载明细第一页
            flowScore.loadFirstCreditDetail();
        });


        $(window).scroll(function() {
            var scrolltop = $(document).scrollTop();
            var winHeight = $(document).height() - $(window).height();

            // todo: $(window).height() 获取的高度不对

            if (scrolltop >= winHeight - 50) {
                flowScore.loadCreditDetail("down");
            }
        });

    </script>
</div>

<jsp:include page="foot.html"/>


</body>
</html>
