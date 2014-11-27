<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: tan
  Date: 14-11-27
  Time: 上午11:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>

<html>
<head>
    <title></title>
</head>
<body>

<c:if test="${sessionScope.admin}">

  <form action="/admin/newHistoryVersion.action" method="post">
    Title: <input type="text" name="title">  <br/>
    VersionName: <input type="text" name="versionName"><br/>
    Content: <textarea name="content"></textarea> <br/>
    Time: <input type="time" name="time"> <br/>

    <input type="submit" value="提交">

  </form>

</c:if>

</body>
</html>
