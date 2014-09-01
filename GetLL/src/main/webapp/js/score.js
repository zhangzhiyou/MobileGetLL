/**
 * Created by tan on 14-7-29.
 */

function Score() {
    // 获取用户积分信息请求路径
    this.queryScorePath_ = "/ajax/queryScore.action";

    // 获取积分兑换列表请求路径
    this.queryPrizePath_ = "/ajax/queryPrize.action";

    //TODO:
    // 积分兑换请求路径
    this.exchangePrizePath_ = "/ajax/exchangePrize.action";

    // 剩余摇奖次数
    this.remainTimes_ = 0;
    // 流量币总数
    this.creditSum_ = 0;
    // 兑换列表数据
    this.convertList_ = {};

    // 积分信息
    this.scoreData_ = {};
}

Score.prototype.showMyScore = function() {
    var that = this;

    // TODO: 不知道干啥的, 注释掉
   /* $("#jia_head").show();*/

//    $.post(that.queryScorePath_+Math.random(), {}, function(data) {
    $.getJSON(that.queryScorePath_, {}, function(data) {
        if (data.status != "ok") {
            alert("流量币信息获取失败!");
            return;
        }

        var scoreData = data.result.list[0];
        that.scoreData_	= scoreData;

        // 更新剩余摇奖次数
        that.remainTimes_ = data.result.list[0].times;

        // 连续登录天数
        if($("#continueDay")){
            $("#continueDay").html(scoreData.count_1);
        }

        // 今天获取赠送积分数
        if($("#todayCredit")){
            var todayCredit_temp = scoreData.todayCredit
            if(Number(todayCredit_temp)<0){
                todayCredit_temp = 0;
            }
            $("#todayCredit").html(todayCredit_temp);
        }

        // 积分总数
        if($("#credit")){
            var credit_temp = scoreData.credit
            if(Number(credit_temp)<0){
                credit_temp = 0;
            }
            $("#credit").html(credit_temp);
            that.creditSum_ = credit_temp;
        }
        //今天登陆了用户已赠送次数
        if($("#transferGiftsNum")){
            $("#transferGiftsNum").html(scoreData.usedCount);
        }
        //今天登陆了用户剩余转赠次数
        if($("#handlesCreditNumber")){
            $("#handlesCreditNumber").html(scoreData.handlesCreditNumber);
        }
        //今天登陆了用户剩余被转赠次数
        if($("#isHandlesCreditNumber")){
            $("#isHandlesCreditNumber").html(scoreData.isHandlesCreditNumber);
        }

        // 获得注册码有效期
        $.getJSON("/ajax/getTTL.action", {}, function (data) {
            if (data.status != "ok") {
                alert("获取个人服务信息失败")
            } else {
                $("#leavesDay").text(data.result.ttl);
            }

            // 显示兑换列表
            score.showConvertList();
        })
    });
};

Score.prototype.showConvertList = function() {
    var that = this;

    //TODO: 其他兑换
    var $convertList = $("#convertList").html("");//流量兑换列表
    var $convertSmsList = $("#convertSmsList").html("");//点对点短信兑换列表


    //todo: 其他 tmp
    var itemTmp = $("#jiaitem_tmp").html();
    var itemTmpSms = $("#jiaitem_tmp_sms").html();


    $.getJSON(that.queryPrizePath_, {}, function(data) {
        if (data.status != "ok") {
            alert("兑换列表获取失败!");
            return;
        }

        //兑换列表整理
        var list = data.result.list;
        var len = list.length;

        for ( var i = 0; i < len; i++) {
            var convertData = list[i];
            var appendObj = null;
            var itemObj = "";
            var productType = convertData.productType;
            var prizeName = convertData.prizeName;

            if (productType == "1") {
                appendObj = $convertList;
                itemObj = itemTmp;
                prizeName = prizeName.replace(/省内流量/, "流量");
            } else if (productType == "3") {
                appendObj = $convertSmsList;
                itemObj = itemTmpSms;
                var dddIndex = prizeName.indexOf("点对点");
                prizeName = prizeName.substring(0, dddIndex);
            } /*else if (productType == "6") {
                appendObj = $convertShakeList;
                itemObj = itemTmpShake;
                var dddIndex = prizeName.indexOf("摇奖");
                prizeName = prizeName.substring(0, dddIndex);
            } else if (productType == "9") {
                appendObj = $convertTigerList;
                itemObj = itemTmpTiger;
                var dddIndex = prizeName.indexOf("幸运投");
                prizeName = prizeName.substring(0, dddIndex);
            }*/
            else
                continue;

            // 获得奖品名称
            var showPrizeName=convertData.prizeName;
            if(parseInt(showPrizeName.substring(0,2))>=10){
                showPrizeName=showPrizeName.substring(0,2);
            }else{
                showPrizeName=showPrizeName.substring(0,1);
            }

            that.convertList_[convertData.exchangeID] = convertData;
            var itemStr = itemObj.replace(/#id#/g, convertData.exchangeID).replace(/#prizeName#/g, prizeName).replace(/#giveName#/g, convertData.giveCredit?convertData.giveCredit: "0");
            itemStr = itemStr.replace(/#nowLostCredit#/g, convertData.nowLostCredit);//实际所需流量币
            itemStr=itemStr.replace(/#showPrizeName#/g, showPrizeName);//
            appendObj.append(itemStr);
        }

        // 兑换事件声明
        $("[id^='convert_']").click(function() {
            var id = $(this).attr("id").replace(/convert_/g, "");
            var productType =  $(this).attr("productType");
            that.exchange(id);
        });

        //更新 总汇信息
        flowScore.loadCreditSum();
    });
}

//兑换方法
Score.prototype.exchange = function(id,productType) {
    var that = this;
    var converData = that.convertList_[id];
    if (Number(converData.nowLostCredit) > Number(that.scoreData_.credit)) {
        alert("流量币不足！您还需要继续积攒哦！加油！加油！嘿呦！嘿呦！");
        return;
    }

    $("#productType").val(converData.productType);

    $("#id").val(id);
    $("#gc").val(converData.giveCredit);
    $("#pn").val(converData.prizeName);
    $("#nc").val(converData.nowLostCredit);

    //todo： 兑换摇奖次数, 暂时不考虑
    if(converData.productType == "6"){//兑换摇奖次数的，需要验证是否在可兑换时间范围内
        alert("执行到不应该被执行的地方了（exchange 摇奖次数）")
        $.getJSON(that.exchangeTimesPath_, {}, function(data) {
            if (data.status != "ok") {
                alert(data.message);
                return;
            }
            $("#flowScoreCfPage").submit();
        });
    }else{
        $("#flowScoreCfPage").submit();
    }
};

var score = new Score();

