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
  <meta charset="UTF-8">

  <link href="css/my.min.css" rel="stylesheet" type="text/css">

  <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
  <link href="css/flat-ui.min.css" rel="stylesheet" type="text/css">

  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

  <!-- 响应式设计 -->
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <meta name="keywords" content="流量,移动,流量汇,流量汇管家,赚取流量,免费流量,摇流量,兑换,流量币,转赠,短信">
  <meta name="description" content="流量汇,流量汇管家,免费赚取流量,兑换流量,兑换短信.">

  <link rel="apple-touch-icon-precomposed" sizes="512x512" href="image/icon.png">
  <link rel="shortcut icon" href="image/icon.png">


  <title>更新历史-流量汇管家</title>

  <link href="css/history-log.css" rel="stylesheet" type="text/css">


</head>
<body>

<div class="container">
  <div class="row history-date">
    <div class="col-xs-offset-3 col-xs-3" style="text-align: right;">
      3月26日
    </div>

    <div class="col-xs-6" style="border-left: 1px #cccccc solid; text-align: left">
      <p>微俱聚硬件微美图正式推出</p>
      <p>12</p>
      <p>34</p>
    </div>
  </div>

  <div class="row">
    <div class="col-xs-offset-3 col-xs-3" style="text-align: right;">
      3月26日
    </div>

    <div class="col-xs-6" style="border-left: 1px #cccccc solid; text-align: left">
      <p>微俱聚硬件微美图正式推出</p>
      <p>12</p>
      <p>34</p>
    </div>
  </div>

  <div class="row">
    <div class="col-xs-offset-1 col-xs-10">
      <ul class="timeline">
        <li>
          <div class="time">时间</div>
          <div class="version">V3.7.7</div>
          <div class="number">第几个版本</div>
          <div class="detail">

            <div class="content-title">title</div>

            <div class="content">
              更新记录
              122112
              fwelfjwe
              wef
              wef
              wef
              we
              f
            </div>
          </div>
        </li>

        <li>
          <div class="time">时间</div>
          <div class="version">V3.7.6</div>
          <div class="number">&nbsp;</div>
          <div class="detail">
            <div class="content-title">title</div>

            <div class="content">
              更新记录
              122112
              fwelfjwe
              wef
              wef
              wef
              we
              f
            </div>
          </div>
        </li>
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
        if(i % 2 !== 0) {
          liNode.addClass('alt');
        } else {
          liNode.removeClass('alt');
        }

        liNode.find('.number').text(count - i);
      }
    }

    initLiNodes();
  });
</script>


</body>
</html>
