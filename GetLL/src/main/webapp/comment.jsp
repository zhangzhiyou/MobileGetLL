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

    <title>留言-流量汇管家</title>
</head>
<body>

<jsp:include page="snap/nav_simple.html"/>

<div class="container">

    <div class="row">
        <div class="col-xs-12">
            <!-- 留言模块-->
            <div>
                <!-- 多说评论框 start -->
                <div class="ds-thread" data-thread-key="index" data-url="http://xiayule.net"></div>

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
        </div>
    </div>
</div>

<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>

<script type="text/javascript" src="js/eventMan.min.js"></script>
<script type="text/javascript" src="js/common.min.js"></script>

<jsp:include page="snap/foot.html"/>

</body>
</html>
