<%--
  Created by IntelliJ IDEA.
  User: tan
  Date: 14-9-2
  Time: 下午6:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="css/css.css" rel="stylesheet" type="text/css">

    <link href="css/my.css" rel="stylesheet" type="text/css">

    <link rel="apple-touch-icon-precomposed" sizes="512x512" href="image/icon.png">
    <link rel="shortcut icon" href="image/icon.png">

    <title>领取</title>

    <style>
        .pwd {font-size:12px;}
        .mask {
            display: none;
            position: absolute;
            top: 0px;
            width: 100%;
            height: 120%;
            line-height: 100%;
            background: none repeat scroll 0% 0% #000;
            opacity: 0.5;
            z-index: 101;
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
    <!--列表-->
    <div class="lingqu_head">
        未领取流量币：<span class="rednum" id="totalCredit">?</span>
    </div>

    <div class="lingqu_list" style="background-color:#efefef;">
        <div>
            <label class="label_check" for="checkbox-a">
                <input name="sample-checkbox-a"  id="checkbox-a" value="a" type="checkbox" checked="false" />
                全选

                <div class="but_r">合计：<span class="rednum" id="totalCredit_">?</span>流量币</div>
            </label>
        </div>
    </div>
    <div id = "lingquList" ></div>
    <div class="lingqu_list hide" id="receiceDiv">
        <div class="zhuanzeng_but" onclick="receiveFun();"><a href="#">领取<a></div>
    </div>
    <div class="lingqu_list">
        <div class="text">
            *朋友为您摇奖获赠的流量币，超过3天未领取将失效；朋友转赠的流量币，超过3天未领取将退回；请亲们及时来领取^_^
        </div>
    </div>
    <div height="1143"><br/>&nbsp;<br/>&nbsp;<br/>&nbsp;<br/>&nbsp;<br/>&nbsp;<br/>&nbsp;<br/>&nbsp;<br/>&nbsp;<br/>&nbsp;<br/></div>

    <div style="height:55px;"></div>
</div>
<div id="mask" class="mask hide"></div>

<div id="buying_tip" class="hide" style="z-index: 101;color:#fff;text-align:center;position: absolute;">我们正在努力处理中，请稍候！</div>

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/tip.js"></script>
<script type="text/javascript" src="js/eventMan.js"></script>

<script>
    var $mask = $("#mask");
    var $buyingTip = $("#buying_tip");
    initBuyingTip();

    function initBuyingTip() {
        var $screen = $(".jia_layout");
        $mask.css({"line-height":$screen.height() + "px",height:window.height,width:window.width});
        $buyingTip.css({"line-height":$screen.height() + "px",height:window.height,width:$screen.width()});
    }

    function showBuyingTip() {
        $buyingTip.css({top : $(window).scrollTop()});
        $mask.css({top : $(window).scrollTop()});
        $mask.show();
        $buyingTip.show();
        $("html").css({overflow:"hidden"});
        window.ontouchmove = function(e){
            e.defaultPrevented && e.defaultPrevented();
            e.returnValue=false;
            e.stopPropagation && e.stopPropagation();
            return false;
        };
    }

    function hideBuyingTip() {
        $mask.hide();
        $buyingTip.hide();
        $("html").css({overflow:"auto"});
        window.ontouchmove = null;
    }

    function showErrorResult(data){
        if (data.status != "ok") {
            var message = data.message;
            alert(message);
            return;
        }
    }

    //未领取流量币数据加载
    function getFlowScoreTransferGiftsInfo(){
        var lazySeconds = 1;
        $.post("/ajax/getTransferGiftsList.action?queryType=all&type=others&status=2",{}, function(data) {
            setTimeout(function () {
                if (data.status != "ok") {
                    showErrorResult(data);
                    return;
                }else{
                    var result = data.result;
                    $("#totalCredit").html(result.totalCredit);
                    $("#totalCredit_").html(result.totalCredit);
                    var list = result.list;
                    var lingquList = $("#lingquList");
                    var htmls=new Array();
                    if(list != null && list.length > 0){
                        for(var i = 0;i<list.length;i++){
                            var obj = list[i];
                            htmls[i] =[
                                        '<div class="lingqu_list"><div><label class="label_check" for="checkbox-'+obj.handselID+'">',
                                        '<input name="sample-checkbox-01" id="checkbox-'+obj.handselID+'" value="'+obj.handselID+'" type="checkbox"  checked="false" />',
                                        obj.typeName+'<font class="smallfont">（'+obj.handselMsisdn+'）</font> ',
                                        '<div class="but_r"><span class="rednum">'+obj.credit+'</span>流量币</div>',
                                        '</label><div class="grayfont">'+obj.handselDate+'</div></div></div>'].join('');
                        }
                        var htmlStr = htmls.join('');
                        lingquList.html(htmlStr);
                        $("#receiceDiv").show();
                        $("input[name='sample-checkbox-01']").click(function(){
                            var checkValue = this.checked;
                            $(this).parent("label").toggleClass("c_on");
                            this.checked = checkValue;

                            var allCheckBox = $("input[name='sample-checkbox-01']");
                            var allBoxLength = $("input[name='sample-checkbox-01']").length;
                            var checkedBoxLength = 0;
                            allCheckBox.each(function(){
                                if(this.checked){
                                    checkedBoxLength++;
                                }
                            });
                            console.log('abc',checkedBoxLength);
                            if(allBoxLength == checkedBoxLength){
                                $("#checkbox-a").parent('label').addClass('c_on');
                                $("#checkbox-a")[0].checked = true;
                            }else{
                                $("#checkbox-a").parent('label').removeClass('c_on');
                                $("#checkbox-a")[0].checked = false;
                            }
                        });
                        checkAll(true);
                    }
                }
            }, lazySeconds * 1000);
        },"json");
    }


    eventMan.checkLogin(function() {
        if(eventMan.isLogin()) {
            //加载待领取列表
            getFlowScoreTransferGiftsInfo();
        }
    });

    function checkAll (checked){
        var checkValue = checked;
        $("input[name='sample-checkbox-01']").each(function() {
            if (checkValue) {
                $(this).parent("label").addClass("c_on");
            }
            else {
                $(this).parent("label").removeClass("c_on");
            }
            this.checked = checkValue;
        });
        $("#checkbox-a").parent("label").toggleClass("c_on");
        $("#checkbox-a")[0].checked = checkValue;
        return;
    }

    function receiveFun(){
        var idList = new Array();
        var i = 0;
        $("input[name='sample-checkbox-01']").each(function(){
            if(this.checked){
                idList[i] =  $(this).attr("value");
                i++;
            }
        })
        if(idList.length <= 0 ){
            alert("您没有选中需要领取的转赠流量币！");
            return;
        }
        var ids = idList.join(",");
        showBuyingTip();
        $.post("/ajax/transferGiftsReceive.action",{"id":ids}, function(data) {
            hideBuyingTip();
            if (data.status != "ok") {
                var message = data.message;
                alert(message);
                return;
            }else{
                var result = data.result;
                alert("您已成功领取选中的流量币！");
                location.href = "/";
            }
        },"json");
    }

    $(function() {
        $("body").addClass("has-js");
        $("#checkbox-a").click(function() {
            var checkValue = this.checked;
            checkAll(checkValue);
        });
    });


</script>



</body>
</html>
