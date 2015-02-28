<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: tan
  Date: 14-9-12
  Time: 下午7:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>

<html>
<head>
    <jsp:include page="snap/head.html"/>

    <title>后台管理-流量汇管家</title>
</head>
<body>

订阅者总数：${mSubscribCount} <br/>
订阅朋友摇奖总数: ${mSubscribForFriendCount} <br/>
订阅自动领取总数: ${mSubscribAutoReceiveCount} <br/>


<form method="post" action="/admin/adminLogin.action">
    <input type="password" name="password">

    <input type="submit" value="登录">
</form>


<%--管理员的一些权利--%>

<c:if test="${sessionScope.admin}">
    线程池信息: <br/>
    ${executorInfo} <br/>

    <br/>

    已摇奖总人数: ${shakeCount}; <br/>

    朋友摇奖状态: ${statusForFriend} <br/>

    <%--启动自己摇奖功能--%>
    <button id="startForSelf">开启摇奖任务</button>
    <button id="startForFriend">开启朋友摇奖任务</button>

    <%--操作 historyVersion --%>

    <%--登录之后,在admin.jsp可以发布 history--%>

    <script type="text/javascript" src="/js/jquery-1.9.1.min.js"></script>

    <script>
        var startForSelf = document.getElementById("startForSelf");
        var startForFriend = document.getElementById("startForFriend");

        startForSelf.onclick = function () {
            $.post("/ajax/startForSelfTask.action?r="+Math.random(), {}, function (data) {
                alert(JSON.stringify(data));
                console.log(data);
            });
        };

        startForFriend.onclick = function () {
            $.post("/ajax/startForFriendTask.action?r="+Math.random(), {}, function (data) {
                alert(JSON.stringify(data));
                console.log(data);
            });
        };

    </script>

</c:if>

</body>
</html>
