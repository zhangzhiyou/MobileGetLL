<%--
  Created by IntelliJ IDEA.
  User: tan
  Date: 14-7-22
  Time: 下午11:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="nav.html"/>

<html>
<head>
    <title></title>
</head>
<body>

<form action="search.action" method="post">
    <table>
        <tr>
            <td><span id="tip" style="color: red">${tip}</span></td>
        </tr>
        <tr>
            <td><label for="mobile">手机号:</label></td>
            <td><input id="mobile" type="text" name="mobile" value="${mobile}"/></td>
        </tr>
        <tr>
            <td><input id="sub_mobile" type="submit"  value="search"/></td>
        </tr>
    </table>
</form>

</body>
</html>
