<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: tan
  Date: 14-7-20
  Time: 下午11:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="nav.html"/>

<html>
<head>
    <title></title>
</head>

<body>
<form action="subscribe.action" name="subscribe" method="post">
    <table>
        <tr>
            <td><span id="tip" style="color: red">${tip}</span></td>
        </tr>
        <tr>
            <td><input type="text" id="mobile" name="mobile" size="20"></td>
        </tr>

        <tr>
            <td><input id="sub_mobile" onsubmit="checkMobile(this)" type="submit" value="订购"></td>
        </tr>
    </table>

    <!--英雄榜-->
    <div class="bangdan" style="width: 30%">
        <div class="bangdan-title">流量汇英雄榜</div>
        <div class="juci03"></div>
        <div class="bangdan-content">
            <div class="first" id="bigWiner">排行</div>
            <div id="winList" style="overflow: hidden;clear:both;">
                <div class="mingdan">
                    <table cellspacing="0" cellpadding="0" style="border-collapse:collapse;">
                        <thead>
                        <tr>
                            <th>排行</th>
                            <th>手机号</th>
                            <th>流量</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>
                                <!--                            <div><span class="mingdan-name">1</span><span>山东18369905136获得0.3个流量币</span></div>-->
                                1
                            </td>
                            <td>
                                2
                            </td>
                            <td>3</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</form>
</body>
</html>
