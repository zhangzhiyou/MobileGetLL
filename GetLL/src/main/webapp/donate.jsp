<%--
  Created by IntelliJ IDEA.
  User: tan
  Date: 14-10-15
  Time: 下午3:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <link href="css/my.min.css" rel="stylesheet" type="text/css">

    <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="css/flat-ui.min.css" rel="stylesheet">

    <!-- 响应式设计 -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="keywords" content="流量,移动,流量汇,流量汇管家,赚取流量,免费流量,摇流量,兑换,流量币,转赠,短信">
    <meta name="description" content="流量汇,流量汇管家,免费赚取流量,兑换流量,兑换短信.">

    <title>赞助-流量汇管家</title>

    <link rel="apple-touch-icon-precomposed" sizes="512x512" href="image/icon.png">
    <link rel="shortcut icon" href="image/icon.png">
</head>
<body>

<jsp:include page="nav_simple.html"/>

<div class="container">
    <div class="row">
        <div class="col-xs-12">
            <div class="jumbotron col-xs-12">
                <div class="col-md-8 col-xs-12">
                    <h3>赞助方式</h3>
                    <p>
                    <ul>
                        <li>1. 使用支付宝客户端扫描二维码</li>
                        <li>2. 使用支付宝直接付款至 xiayule148@gmail.com</li>
                        <%--<linli>3. 使用支付宝直接支付至 18369905136</li>--%>
                    </ul>
                    </p>
                    <p>只要您的赞助 > 0, 就是对我们的理解与支持</p>
                    <p>如果您不想出现在我们的赞助名单，请在在赞助时说明</p>
                    <p>谢谢</p>
                </div>

                <div class="col-md-4 col-xs-12">
                    <a href="image/alipay_large.png" title="支付宝收款二维码" target="_blank">
                        <img src="image/alipay.png" alt="支付宝收款二维码"/>
                    </a>
                </div>
            </div>
        </div>

        <div class="col-xs-12">
            <table class="col-xs-12" style="text-align: center">
                <caption><h3>赞助名单</h3></caption>

                <tr>
                    <th style="text-align: center">姓名</th>
                    <th style="text-align: center">金额(元)</th>
                </tr>

                <tr>
                    <td>**南</td>
                    <td>100.00</td>
                </tr>

                <tr>
                    <td>赵巍</td>
                    <td>10.00</td>
                </tr>

                <tr>
                    <td>韩素珍</td>
                    <td>10.00</td>
                </tr>

                <tr>
                    <td>**凤</td>
                    <td>5.00</td>
                </tr>

                <tr>
                    <td>李敏成</td>
                    <td>1.00</td>
                </tr>
            </table>
        </div>

        <div class="col-xs-12" style="height: 200px">

        </div>
    </div>
</div>


<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/common.min.js"></script>

<jsp:include page="foot.html"/>

</body>
</html>
