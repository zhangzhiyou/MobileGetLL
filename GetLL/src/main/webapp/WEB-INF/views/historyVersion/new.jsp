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
  <jsp:include page="/snap/head.html"/>

  <link href="/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css">


</head>
<body>

<c:if test="${sessionScope.admin}">

  <form action="/historyVersion/create.action" method="post">
    Title: <input type="text" name="title">  <br/>
    VersionName: <input type="text" name="versionName"><br/>
    Content: <textarea name="content"></textarea> <br/>
    Time: <input type="datetime" id="datetimepicker" name="time" data-date-format="yyyy-mm-dd hh:ii">    <br/>


    <input type="submit" value="提交">

  </form>

</c:if>



<script type="text/javascript" src="/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/js/flat-ui.min.js"></script>
<script type="text/javascript" src="/js/bootstrap-datetimepicker.min.js" charset="UTF-8"></script>


<script>
  $('#datetimepicker').datetimepicker();

</script>

<jsp:include page="/snap/foot.html"></jsp:include>

</body>
</html>
