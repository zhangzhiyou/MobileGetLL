<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="com.xiayule.getll.db.model.VersionHistory" %>
<%@ page import="sun.misc.Version" %>
<%--
  Created by IntelliJ IDEA.
  User: tan
  Date: 14-11-25
  Time: 下午5:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <jsp:include page="snap/head.html"/>

  <title>更新历史-流量汇管家</title>

  <link href="css/history-log.css" rel="stylesheet" type="text/css">
</head>
<body>

<jsp:include page="snap/nav_simple.html"></jsp:include>

<div class="container">
  <div class="row">

    <div class="col-md-offset-3 col-md-9">

      <h5>流量汇管家<span style="font-size: 40px; color: #d34754;"> 历程</span></h5>

      <ul class="timeline">

        <c:forEach var="versionHistory" items="${model.versionHistories}">
          <li>
            <div class="time"><fmt:formatDate value="${versionHistory.time}" pattern="yyyy-MM-dd" /></div>
            <div class="version">${versionHistory.versionName}</div>
            <div class="number">&nbsp;</div>
            <div class="detail">
              <div class="content-title">${versionHistory.title}</div>
              <div class="content">${versionHistory.content}</div>
            </div>
          </li>
        </c:forEach>
      </ul>
    </div>
  </div>
</div>

<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="js/flat-ui.min.js"></script>

<script>

  $(function() {
    var ulNode = $('ul.timeline');

    function initLiNodes() {
      var liNodes = ulNode.find('li'), count = liNodes.length, i, liNode;
      for(i=0; i<count; i++) {
        liNode = $(liNodes.get(i));
        if(i % 3 == 0) {
//          liNode.removeClass('alt1');
        } else if (i % 3 == 1) {
          liNode.addClass('alt1');
        } else if (i % 3 == 2) {
          liNode.addClass('alt2');
        }

        liNode.find('.number').text(count - i);
      }
    }

    initLiNodes();
  });
</script>

<jsp:include page="snap/foot.html"></jsp:include>


</body>
</html>
