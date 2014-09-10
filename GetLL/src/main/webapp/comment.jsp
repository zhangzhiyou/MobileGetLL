<%--
  Created by IntelliJ IDEA.
  User: tan
  Date: 14-9-10
  Time: 下午4:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <title>留言</title>

    <link rel="apple-touch-icon-precomposed" sizes="512x512" href="image/icon.png">
    <link rel="shortcut icon" href="image/icon.png">

    <!-- 回顶部按钮样式 -->
    <style type="text/css">
        /** 返回顶部CSS **/
        #gotop {
            position: fixed;
            bottom: 90px;
            right: 50px;
            top: auto;
            display: none;
            cursor: pointer;
            z-index: 999;
        }
    </style>
</head>
<body>


<!-- 留言模块-->
<div id="duoshuoContent">
    <!-- 多说评论框 start -->
    <div class="ds-thread" id="duoshuoContent" data-thread-key="index" data-url="http://xiayule.net"></div>

    <!-- 多说评论框 end -->
    <!-- 多说公共JS代码 start (一个网页只需插入一次) -->
    <script type="text/javascript">
        var duoshuoQuery = {short_name: "xiayule"};
        (function () {
            var ds = document.createElement('script');
            ds.type = 'text/javascript';
            ds.async = true;
            ds.src = (document.location.protocol == 'https:' ? 'https:' : 'http:') + '//static.duoshuo.com/embed.js';
            ds.charset = 'UTF-8';
            (document.getElementsByTagName('head')[0]
                    || document.getElementsByTagName('body')[0]).appendChild(ds);
        })();
    </script>
    <!-- 多说公共JS代码 end -->
</div>

</body>
</html>
