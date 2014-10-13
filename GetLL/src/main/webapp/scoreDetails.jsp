<%--
  Created by IntelliJ IDEA.
  User: tan
  Date: 14-9-10
  Time: 下午4:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>

<html>
<head>
    <link href="css/css.css" rel="stylesheet" type="text/css">

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <link href="css/my.css" rel="stylesheet" type="text/css">

    <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="css/flat-ui.min.css" rel="stylesheet">

    <!-- 响应式设计 -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="keywords" content="流量,移动,流量汇,流量汇管家,赚取流量,免费流量,摇流量,兑换,流量币,转赠,短信">
    <meta name="description" content="流量汇,流量汇管家,免费赚取流量,兑换流量,兑换短信.">

    <title>账户明细</title>

    <link rel="apple-touch-icon-precomposed" sizes="512x512" href="image/icon.png">
    <link rel="shortcut icon" href="image/icon.png">

</head>

<body>

<jsp:include page="nav_simple.html"/>

<div class="container">
    <div class="row">
        <div class="col-xs-12">
            <div class="jumbotron">
                <div id="days">流量币 | 最近0天 |</div>
                <div>
                    <div>收入：<span id="incomeDiv" style="color: #ff0000">0</span></div>
                    <div>支出：<span id="expensesDiv" style="color: #ff0000">0</span></div>
                </div>
            </div>
        </div>

        <div class="col-xs-12">
            <!--列表-->
            <div id = "mingxiListDiv" style="border: thick solid #e8e8e8"></div>
        </div>
    </div>

    <script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
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

            if (scrolltop >= winHeight - 50) {
                flowScore.loadCreditDetail("down");
            }
        });

    </script>
</div>

<jsp:include page="foot.html"/>


</body>
</html>
