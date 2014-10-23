<%@ page import="com.xiayule.getll.utils.TimeUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">

    <link href="css/css.min.css" rel="stylesheet" type="text/css">

    <link href="css/my.min.css" rel="stylesheet" type="text/css">

    <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="css/flat-ui.min.css" rel="stylesheet">

    <meta name="keywords" content="流量,移动,流量汇,流量汇管家,赚取流量,免费流量,摇流量,兑换,流量币,转赠,短信">
    <meta name="description" content="流量汇,流量汇管家,免费赚取流量,兑换流量,兑换短信.">

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <!-- 响应式设计 -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>流量汇自动管家</title>

    <link rel="apple-touch-icon-precomposed" sizes="512x512" href="image/icon.png">
    <link rel="shortcut icon" href="image/icon.png">
</head>


<nav class="navbar navbar-default" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse"
                    data-target="#navbar-collapse">
                <span class="sr-only">切换导航</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" title="立志做您的流量汇管家" href="/">流量汇管家</a>
        </div>

        <div class="collapse navbar-collapse" id="navbar-collapse">
            <ul class="nav navbar-nav">
                <li class="dropdown">
                    <a href="javascript:;" title="流量不够用了？兑换流量吧" class="dropdown-toggle" data-toggle="dropdown">
                        兑换流量
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu" id="convertList"></ul>
                </li>

                <li class="dropdown">
                    <a href="#" title="短信不够用了？兑换短信吧" class="dropdown-toggle" data-toggle="dropdown">
                        兑换短信
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu" id="convertSmsList"></ul>
                </li>

                <li><a href="javascript:;" title="可以将流量币转赠给亲人、好友" id="bt_zhuanzeng" onclick="locationPage('/transferGifts.jsp')">转赠</a></li>
                <li><a href="#" id="bt_mingxi"  title="90天内的流量币获取消费明细" onclick="locationPage('/scoreDetails.jsp')">流量币明细</a></li>
                <li><a href="#" id="bt_comment" title="有什么想对我们说的，可以留言哦" onclick="locationPage('/comment.jsp')">留言</a></li>
                <li><a href="#" id="bt_comment" title="如果我们的服务对您有用，可以请我们喝杯茶" onclick="locationPage('/donate.jsp')">赞助</a></li>
            </ul>

            <ul class="nav navbar-nav navbar-right">
                <li><a href="#" title="注销登录后，依旧为您赚取流量而努力" onclick="javascript:logoutSystem();" id="logoutSystem">注销</a></li>
            </ul>
        </div>
    </div>
    <!--todo: 没有加入立即摇奖功能-->
</nav>

<div class="container">
    <div class="row">
        <!-- 主要内容 -->
        <div class="col-md-9 col-xs-12">
            <!-- 个人信息 -->
            <div class="jumbotron col-xs-12">
                <!-- 欢迎语 -->
                <div class="col-xs-12">
                    <h3>
                        <!-- 问候语 -->
                        <span> <%= TimeUtils.getGreetings() %> !</span><br />

                        <span id="userMobile">&nbsp;</span>
                        <small style="color: #b09fc9" id="userNick"></small>
                    </h3>
                </div>

                <!-- 个人详情 -->
                <div class="col-xs-12">
                    <div>
                        <span style="cursor: help" title="剩余的流量币数量">帐户余额：</span>
                        <span id="credit" class="rednum">&nbsp;</span><img src="image/mb.png" width="25" height="23">
                    </div>

                    <div>
                        <span style="cursor: help" title="今日获得的收益">今日收益：</span>
                        <span class="yellowfont rednum" id="todayCredit">0.0</span>流量币
                    </div>

                    <!--未领取的流量币 -->
                    <div class="hideme" id="receiveDiv">
                        <span class="rednum" id="totalCredit">?</span>流量币未领
                        <span class="but_lingqu" onclick="javascript:location.href='/receiveFlowScore.jsp'">领取</span>
                    </div>

                    <div>
                        <span style="cursor: help" title="少于30天时，可以点击免费延长至30天">有效期：</span>
                        <span id="leavesDay">&nbsp;</span>
                    </div>
                </div>
            </div>

            <!-- 其他信息 -->
            <div class="col-xs-12">
                <!-- 流量图表 -->
                <div class="col-xs-12 col-sm-6 col-md-4">
                    <!--图表-->
                    <div class="jiafengge"><span class="line" style="color:#F33">流量图表</span></div>

                    <div class="myaccount_chart">
                        <div id="tipDiv" style="padding-bottom:5px;"></div>
                        <div id="ReportDiv"></div>
                        <div class="tishi2">计费数据有延时，仅供参考，请以月结数据为准。</div>
                    </div>
                </div>


                <div class="col-xs-12 col-sm-6 col-md-4">
                    <!--明细-->
                    <div id="listDiv"></div>
                </div>

                <div class="col-xs-12 col-sm-6 col-md-4">
                    <!--套餐详情-->
                    <div class="col-xs-12">
                        <div class="jiafengge"><span class="line" style="color:#F33">温馨提醒</span></div>

                        <p>本月总流量<span id="TotalFlowDiv">0</span>MB</p>

                        <p>剩余流量<span id="LeftFlowDiv">0</span>MB</p>

                        <p>距结算日<span id="distanceDays">0</span>天</p>

                        <p>日均可用<span id="dayNum">0</span>MB</p>
                    </div>

                    <!-- todo:会引发错误，暂时关闭该功能-->
                    <!--<button class="btn button" id="btShakeNow" type="button" onclick="this.blur()">立即摇奖</button>-->
                </div>
            </div>
        </div>

        <!-- 侧边栏 -->
        <div class="col-md-3 col-xs-12">
            <!-- 时钟 -->
            <div class="col-xs-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">时间</h3>
                    </div>
                    <div class="panel-body">
                        <div id="clock"></div>
                    </div>
                </div>
            </div>

            <div class="col-xs-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">最新留言 <small style="float: right;"><a href="comment.jsp" style=" color: #398439">更多</a></small></h3>
                    </div>
                    <div class="panel-body">
                        <div>
                            <ul class="ds-recent-comments" data-num-items="5" data-show-avatars="1" data-show-time="1" data-show-admin="1" data-excerpt-length="50"></ul>
                            <!--多说js加载开始，一个页面只需要加载一次 -->
                            <script type="text/javascript">
                                var duoshuoQuery = {short_name:"xiayule"};
                                (function() {
                                    var ds = document.createElement('script');
                                    ds.type = 'text/javascript';ds.async = true;
                                    ds.src = 'http://static.duoshuo.com/embed.js';
                                    ds.charset = 'UTF-8';
                                    (document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(ds);
                                })();
                            </script>
                            <!--多说js加载结束，一个页面只需要加载一次 -->
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-xs-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">网站赞助</h3>
                    </div>
                    <div class="panel-body">
                        如果我们的服务对您有用，可以请我们喝杯茶
                    </div>

                    <div class="panel-footer">
                        <a href="donate.jsp" class="btn btn-default btn-block" target="_blank">老板，上茶!!!</a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- 临时模板 -->
    <div id="jiaitem_tmp" class="hideme">
        <li><a href="javascript:;" id="convert_#id#">#prizeName#</a></li>
    </div>

    <div id="jiaitem_tmp_sms" class="hideme">
        <li><a href="javascript:;" id="convert_#id#">#prizeName#(需#nowLostCredit#流量币)</a></li>
    </div>

    <!-- 兑换申请表单 -->
    <form action="/score" method="POST" name="flowScoreCfPage" id="flowScoreCfPage" class='hide'>
        <input type="text" value="flowScoreCfPage" name="redirect"/>
        <input type="text" value="" name="productType_n" id="productType"/>
        <input type="text" value="" name="id_n" id="id"/>
        <input type="text" value="" name="gc_n" id="gc"/>
        <input type="text" value="" name="pn_n" id="pn"/>
        <input type="text" value="" name="nc_n" id="nc"/>
    </form>


    <%--<script type="text/javascript" src="js/jquery.min.js"></script>--%>
    <script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>

    <script type="text/javascript" src="js/bootstrap.min.js"></script>

    <script type="text/javascript" src="js/eventMan.min.js"></script>
    <script type="text/javascript" src="js/tip.min.js"></script>
    <script type="text/javascript" src="js/score.min.js"></script>
    <script type="text/javascript" src="js/flowScore.min.js"></script>
    <script type="text/javascript" src="js/highcharts.js"></script>
    <script type="text/javascript" src="js/common.min.js"></script>
    <script type="text/javascript" src="js/jquery.MyDigitclock.js"></script>

    <script>
        eventMan.checkLogin(function () {
            if (eventMan.isLogin()) {
                $("#userMobile").html(eventMan.loginMobile_);

                if (eventMan.nickName_ != null && eventMan.nickName_ != "") {
                    $("#userNick").html(eventMan.nickName_);
                }

                // 显示个人积分
                score.showMyScore(function () {
                    // 获得套餐详情, 包括绘制饼状图
                    flowScore.getTotalFlow(function () {
                        // 显示 兑换列表
                        score.showConvertList(function () {
                            // 未领取的流量币信息
                            flowScore.getFlowScoreTransferGiftsInfo();
                        });
                    });
                });
            }
        }, function () {
            // 验证登录失败
            locationPage("/login.jsp")
        });

        <!-- 显示时钟 -->
        $("#clock").MyDigitClock({
            fontSize:40,
            fontFamily:"Century gothic",
            fontColor: "#56ba98",
            fontWeight:"bold",
            bAmPm:false,
            background:'#fff',
            bShowHeartBeat:true
        });
    </script>
</div>

<jsp:include page="foot.html"></jsp:include>


</body>
</html>