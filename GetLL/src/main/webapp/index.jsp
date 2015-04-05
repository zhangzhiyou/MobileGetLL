<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.xiayule.getll.utils.TimeUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!DOCTYPE html>
<html>
<head>
    <jsp:include page="snap/head.html"/>

    <link href="/css/jquery.circliful.css" rel="stylesheet" type="text/css" />

    <%--检测到是电脑,显示小人--%>
    <c:if test="${model.isMobile eq false}">
        <link href="/css/animation.css" rel="stylesheet" type="text/css"/>
    </c:if>

    <title>流量汇管家</title>
</head>

<body>

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
                <li><a href="#" title="90天内的流量币获取消费明细" onclick="locationPage('/scoreDetails.jsp')">流量币明细</a></li>
                <li><a href="#" title="一起来见证我们的成长" onclick="locationPage('/historyVersion/show.action')">历程
                    <%--<span class="badge" style="background: #e80029">new</span>--%></a></li>
                <li><a href="#" title="有什么想对我们说的，可以留言哦" onclick="locationPage('/comment.jsp')">留言</a></li>
                <li><a href="#" title="如果我们的服务对您有用，可以请我们喝杯茶" onclick="locationPage('/donate.jsp')">赞助</a></li>
            </ul>

            <ul class="nav navbar-nav navbar-right">
                <li><a href="#" title="您想知道的，都在这里" onclick="locationPage('/help.jsp')">帮助</a></li>
                <li><a href="#" title="注销登录后，我们依旧为您赚取流量而努力" onclick="logoutSystem()" id="logoutSystem">注销</a></li>
            </ul>
        </div>
    </div>
    <!--todo: 没有加入立即摇奖功能-->
</nav>

<div class="container">
    <div class="row">

        <jsp:include page="snap/notification.html"/>

        <%--检测到是电脑,显示小人--%>
        <c:if test="${model.isMobile eq false}">
            <div class="col-xs-12">
                <div class="wrapper" style="-webkit-transform: scale(0.8);">
                    <div class="border-circle" id="one"></div>
                    <div class="border-circle" id="two"></div>
                    <div class="background-circle">
                        <div class="triangle-light"></div>
                        <div class="body"></div>
                        <span class="shirt-text">流</span>
                        <span class="shirt-text">量</span>
                        <span class="shirt-text">汇</span>
                        <span class="shirt-text">管</span>
                        <span class="shirt-text">家</span>

                        <div class="triangle-dark"></div>
                    </div>
                    <div class="head">
                        <div class="ear" id="left"></div>
                        <div class="ear" id="right"></div>
                        <div class="hair-main">
                            <div class="sideburn" id="left"></div>
                            <div class="sideburn" id="right"></div>
                            <div class="hair-top"></div>
                        </div>
                        <div class="face">
                            <div class="hair-bottom"></div>
                            <div class="nose"></div>
                            <div class="eye-shadow" id="left">
                                <div class="eyebrow"></div>
                                <div class="eye"></div>
                            </div>
                            <div class="eye-shadow" id="right">
                                <div class="eyebrow"></div>
                                <div class="eye"></div>
                            </div>
                            <div class="mouth"></div>
                            <div class="shadow-wrapper">
                                <div class="shadow"></div>
                            </div>
                        </div>
                    </div>
                    <span class="music-note" id="one">&#9835;</span>
                    <span class="music-note" id="two">&#9834;</span>
                </div>
            </div>
        </c:if>

        <!-- 主要内容 -->
        <div id="mainContainer" class="col-md-9 col-xs-12">
            <!-- 个人信息 -->
            <div class="jumbotron col-xs-12">
                <!-- 欢迎语 -->
                <div class="col-xs-12">
                    <h3>
                        <!-- 问候语 -->
                        <span style="font-size: 25px"> <%= TimeUtils.getGreetings() %> &nbsp;<span id="userNick" style="font-size: 45px;"></span>&nbsp;</span>

                        <div class="dropdown">
                            <a data-toggle="dropdown" href="#"><small style="color: #b09fc9" id="userMobile">&nbsp;</small><b class="caret"></b></a>

                            <ul class="dropdown-menu" id="mobileGroups"></ul>
                        </div>
                    </h3>
                </div>

                <div class="col-xs-12">
                    <!-- 个人详情 -->
                    <div class="col-xs-8">
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
                            <span class="rednum" id="totalCredit">?</span>流量币未领 &nbsp &nbsp
                            <%--<span class="but_lingqu" onclick="javascript:location.href='/receiveFlowScore.jsp'">领取</span>--%>
                            <button type="button" class="btn btn-danger" onclick="locationPage('/receiveFlowScore.jsp')">领取</button>
                        </div>

                        <div>
                            <span>上次登录：${model.lastLoginTime}</span>
                            <%--<span>是否过期：${model.expired}</span>--%>
                            <%--<span style="cursor: help" title="少于30天时，可以点击免费延长至30天">有效期：</span>--%>
                            <%--<span id="leavesDay">${model.ttl}</span>--%>
                        </div>
                    </div>

                    <div class="col-xs-4">
                        <button type="button" class="btn btn-primary" style="width: 100%;" onclick="locationPage('/setting.jsp')">设置</button>
                        <!--<button class="btn button" id="btShakeNow" type="button" onclick="this.blur()">立即摇奖</button>-->
                    </div>
                </div>
            </div>

            <!-- 其他信息 -->

            <div class="col-xs-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">温馨提示</h3>
                    </div>
                    <div class="panel-body">
                        <div class="col-xs-12 col-md-6" style="text-align:center; font-size: 20px">距结算日<span class="rednum" id="distanceDays" style="font-size: 30px">0</span>天</div>

                        <div class="col-xs-12 col-md-6" style="text-align:center; font-size: 20px">日均可用<span class="rednum" id="dayNum" style="font-size: 30px">0</span>MB</div>
                    </div>
                </div>
            </div>
            
            <c:if test="${!empty model.mobileRank}">
                <div class="col-xs-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">昨日摇取概况(第一摇得<span style="color: #ff0000; font-size: large">${model.firstRank.creditToString}</span>个流量币, 只统计摇取信息)</h3>
                        </div>
                        <div class="panel-body">
                            <div class="col-xs-12" style="text-align:center; font-size: 20px">
                                昨日共摇得<span class="bigrednum">${model.mobileRank.creditToString}</span>个流量币,
                                击败了全站 <span class="bigrednum">${model.beatPercent}</span>用户
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>


            <div class="col-xs-12 col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">流量概况</h3>
                    </div>
                    <div class="panel-body">
                        <div class="col-xs-12" id="totalDiv"></div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 侧边栏 -->
        <div class="col-md-3 col-xs-12">
            <!-- 网站公告 -->
            <div class="col-xs-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">网站公告</h3>
                    </div>
                    <div class="panel-body">
                        1. 如果您在官方登录并点击了官方的退出登录，那么也会导致本站的服务不可用，这时可以重新登录本站，便可恢复服务<br/>
                        2. 朋友摇奖已可用，需要重新手动开启该功能
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

            <div class="col-xs-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">最新留言 <small style="float: right;"><a href="comment.jsp" style=" color: #398439">更多</a></small></h3>
                    </div>
                    <div class="panel-body">
                        <div>
                            <ul class="ds-recent-comments" data-num-items="5" data-show-avatars="1" data-show-time="1" data-show-admin="1" data-excerpt-length="10"></ul>
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
        </div>
    </div>

    <!-- 临时模板 -->
    <div id="jiaitem_tmp" class="hideme">
        <li><a href="javascript:;" id="convert_#id#">#prizeName#</a></li>
    </div>

    <div id="jiaitem_tmp_sms" class="hideme">
        <li><a href="javascript:;" id="convert_#id#">#prizeName#(需#nowLostCredit#流量币)</a></li>
    </div>

    <%--套餐明细临时模板--%>
    <div id="tmp_ll_detail" class="hideme">
        <div class='col-xs-12 col-md-4'>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">套餐明细</h3>
                </div>
                <div class="panel-body">
                    <div class="listDiv" style="text-align: center"></div>
                </div>
            </div>
        </div>
    </div>


    <!-- 图表临时模板 -->
    <div id="jiaitem_tmp_tubiao" class="hideme">
        <!--图表-->
        <table>
            <%--上方文字说明--%>
            <tr><td class="center" style="color: gray">#top_desc#</td></tr>
            <%--图表--%>

            <%--<tr><td><div id="tubiao0" data-dimension="200" data-info="剩余量" data-width="8" data-fontsize="15" data-fgcolor="#61a9dc" data-bgcolor="#eee"  data-total="200" data-part="20"></div></tr>--%>
            <tr>
                <td class="center">
                    <div id="taocan#id#" data-dimension="200" data-info="剩余量" data-width="8" data-fontsize="30" data-fgcolor="#fgcolor#"
                                                       data-bgcolor="#eee"  data-total="#data-total#" data-part="#data-part#" data-text="#data-text#M" data-animationstep="0"></div>
                </td>
            </tr>
            <%--下方文字说明--%>
            <tr><td class="center"><div style="color: #1198c8">#botton_desc#</div></td></tr>
        </table>
    </div>

    <%--绑定手机号--%>
    <div class="modal fade" id="modal-mobile-group" tabindex="-1" role="dialog"
         aria-labelledby="modal-mobile-group" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close"
                            data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="modal-forFriend-label">
                        添加手机号
                    </h4>
                </div>
                <div class="modal-body" style="text-align: center">
                    请输入需要绑定的手机号: <input type="text" name="mobile" id="groupMobile" class="form-control">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default btn-cancel"
                            data-dismiss="modal">返回
                    </button>
                    <button type="button" class="btn btn-primary action-add-group-mobile"
                            data-dismiss="modal">
                        添加
                    </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
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
    <%--<script type="text/javascript" src="js/bootstrap.min.js"></script>--%>
    <script type="text/javascript" src="js/flat-ui.min.js"></script>

    <script type="text/javascript" src="js/eventMan.min.js"></script>
    <script type="text/javascript" src="js/tip.min.js"></script>
    <script type="text/javascript" src="js/score.min.js"></script>
    <script type="text/javascript" src="js/flowScore.min.js"></script>

    <script type="text/javascript" src="js/jquery.circliful.min.js"></script>

    <script type="text/javascript" src="js/common.min.js"></script>

    <script>

        $(function () {
            $(".action-add-group-mobile").click(function () {
                var mobile = $("#groupMobile").val();

                var params = {mobile: mobile};

                $.post("/ajax/addMobileGroup.action?r=" + Math.random(), params, function (data) {
                    if (data.status != "ok") {

                        alert("绑定手机号失败");

                        return;
                    } else {
                        var mobileGroups = data.mobileGroups;
                        eventMan.renderMobileGroups(mobileGroups);

                        alert("绑定成功");
                    }
                })
            });

            $(".btn-cancel").click(function () {

            });

//            关闭模态框后，自动清空内容
            $('#modal-mobile-group').on('hide.bs.modal', function () {
                        $("#groupMobile").val("");
                    }
            );

//             动态绑定事件, 删除手机组
            $("#mobileGroups").delegate(".delete-group-mobile", "click", function () {

                var mobile = $(this).data("mobile");

                var params = {mobile: mobile};

                $.post("/ajax/deleteMobileGroup.action?r=" + Math.random(), params, function (data) {
                    if (data.status != "ok") {

                        alert("删除绑定的手机号失败");

                        return;
                    } else {
                        var mobileGroups = data.mobileGroups;
                        eventMan.renderMobileGroups(mobileGroups);

                        alert("解绑成功")
                    }
                })
            });

            eventMan.checkLogin(function () {
                if (eventMan.isLogin()) {
                    $("#userMobile").html(eventMan.loginMobile_);

                    // 显示 手机组
                    eventMan.renderMobileGroups();

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
        });

    </script>
</div>

<jsp:include page="snap/foot.html"></jsp:include>

</body>
</html>