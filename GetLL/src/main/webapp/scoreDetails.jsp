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

    <jsp:include page="snap/head.html"/>

    <!-- todo: 还需要 -->
    <link href="css/css.min.css" rel="stylesheet" type="text/css">

    <title>账户明细-流量汇管家</title>

</head>

<body>

<jsp:include page="snap/nav_simple.html"/>

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
    <script type="text/javascript" src="js/flowScore.min.js"></script>


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

<jsp:include page="snap/foot.html"/>


</body>
</html>
