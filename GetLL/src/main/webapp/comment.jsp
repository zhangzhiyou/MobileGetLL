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

    <link href="css/css.css" rel="stylesheet" type="text/css">

    <link href="css/my.css" rel="stylesheet" type="text/css">

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

<div class="jia_layout">
    <div id="myaccount_mingxi_shouzhi">
        <div class="jia_nav2">
            <a href="/"><div class="jia_back"><img src="/image/go_back.png" width="21" height="19" align="absmiddle" /> 返回</div></a>
        </div>
    </div>

    <div class="zhuanzeng_layout">
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
    </div>

    <!-- 页脚 -->
    <div class="foot">
        <div class="smallfont">Auth:<a href="mailto:xiayule148@gmail.com">tanhe123</a> Version:0.9.2</div>

        <div style="display: none">
            <script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");
            document.write(unescape("%3Cspan id='cnzz_stat_icon_5678078'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s9.cnzz.com/stat.php%3Fid%3D5678078' type='text/javascript'%3E%3C/script%3E"));</script>
        </div>
    </div>
</div>

<!-- 返回顶部按钮 -->
<div id="gotop"><img src="image/top.png"/></div>
<script>
    /*返回顶部JS*/
    backTop = function (btnId) {
        var btn = document.getElementById(btnId);
        var d = document.documentElement;
        var b = document.body;
        window.onscroll = btnDisplay;
        btn.onclick = function () {
            btn.style.display = "none";
            window.onscroll = null;
            this.timer = setInterval(function () {
                d.scrollTop -= Math.ceil((d.scrollTop + b.scrollTop) * 0.1);
                b.scrollTop -= Math.ceil((d.scrollTop + b.scrollTop) * 0.1);
                if ((d.scrollTop + b.scrollTop) == 0)
                    clearInterval(btn.timer, window.onscroll = btnDisplay);
            }, 10);
        };
        function btnDisplay() {
            btn.style.display = (d.scrollTop + b.scrollTop > 200) ? 'block' : "none";
        }
    };
    backTop('gotop');//返回顶部调用
</script>

</body>
</html>
