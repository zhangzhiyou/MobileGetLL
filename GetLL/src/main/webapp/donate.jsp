<%--
  Created by IntelliJ IDEA.
  User: tan
  Date: 14-10-15
  Time: 下午3:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>

<html>
<head>
    <jsp:include page="snap/head.html"/>

    <title>赞助-流量汇管家</title>
</head>
<body>

<jsp:include page="snap/nav_simple.html"/>

<div class="container">
    <div class="row">
        <div class="col-xs-12">
            <div class="jumbotron col-xs-12">
                <div class="col-md-8 col-xs-12">
                    <h3>赞助方式</h3>
                    <p>
                    <ul>
                        <li>1. 使用支付宝客户端扫描二维码</li>
                        <li>2. 使用支付宝直接付款至 xiayule148@gmail.com</li>
                        <li>3. 使用支付宝直接支付至 18369905136</li>
                    </ul>
                    </p>
                    <p>只要您的赞助 > 0, 就是对我们的理解与支持</p>
                    <p>如果您不想出现在我们的赞助名单，请在赞助时说明</p>
                    <p>非常欢迎在赞助时说些想说的,我会添加到赠言中</p>
                    <p>赞助过的可以发送赠言到我的手机号18369905136,补加赠言</p>
                    <p>谢谢</p>
                </div>

                <div class="col-md-4 col-xs-12">
                    <a href="image/alipay_large.png" title="支付宝收款二维码" target="_blank">
                        <img src="image/alipay.png" alt="支付宝收款二维码"/>
                    </a>
                </div>
            </div>
        </div>

        <div class="col-xs-12">
            <table class="col-xs-12 col-xs-12 table table-striped table-bordered table-condensed table-hover" style="text-align: center; border: 1px">
                <caption style="text-align: center"><h3><span style="color: #ff0000">2015</span>赞助名单</h3></caption>

                <tr>
                    <th style="text-align: center">姓名</th>
                    <th style="text-align: center">金额(元)</th>
                    <th style="text-align: center">赠言</th>
                </tr>
                <tr>
                    <td>张加东</td>
                    <td>1.00</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>杨家乐</td>
                    <td>1.00</td>
                    <td>Never give up</td>
                </tr>
                <tr>
                    <td>光风(昵称)</td>
                    <td>1.00</td>
                    <td>感谢</td>
                </tr>
                <tr>
                    <td>王文彬</td>
                    <td>0.80</td>
                    <td>&nbsp;</td>
                </tr>
            </table>
        </div>

        <div class="col-xs-12">
            <table class="col-xs-12 col-xs-12 table table-striped table-bordered table-condensed table-hover" style="text-align: center; border: 1px">
                <caption style="text-align: center"><h3><span style="color: #ff0000">2014</span>赞助名单</h3></caption>

                <tr>
                    <th style="text-align: center">姓名</th>
                    <th style="text-align: center">金额(元)</th>
                    <th style="text-align: center">赠言</th>
                </tr>

                <tr>
                    <td>刘培吉</td>
                    <td>100.00</td>
                    <td>&nbsp;</td>
                </tr>

                <tr>
                    <td>**南</td>
                    <td>100.00</td>
                    <td>&nbsp;</td>
                </tr>

                <tr>
                    <td>刘春辉</td>
                    <td>20.00</td>
                    <td>&nbsp;</td>
                </tr>

                <tr>
                    <td>赵巍</td>
                    <td>10.00</td>
                    <td>&nbsp;</td>
                </tr>

                <tr>
                    <td>韩素珍</td>
                    <td>10.00</td>
                    <td>加油!</td>
                </tr>

                <tr>
                    <td>**凤</td>
                    <td>5.00</td>
                    <td>匿名的哦!加油加油.</td>
                </tr>

                <tr>
                    <td>李敏成</td>
                    <td>1.00</td>
                    <td>&nbsp;</td>
                </tr>

                <tr>
                    <td>严晓声</td>
                    <td>0.50</td>
                    <td>&nbsp;</td>
                </tr>
            </table>
        </div>

        <div class="col-xs-12">
            <!-- 留言模块-->
            <div>
                <!-- 多说评论框 start -->
                <div class="ds-thread" data-thread-key="donate" data-url="http://xiayule.net"></div>

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
<script type="text/javascript" src="js/common.min.js"></script>

<jsp:include page="snap/foot.html"/>

</body>
</html>
