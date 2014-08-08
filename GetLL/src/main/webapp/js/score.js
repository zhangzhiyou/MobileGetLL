/**
 * Created by tan on 14-7-29.
 */

function Score() {
    // 获取用户积分信息请求路径
    this.queryScorePath_ = "/ajax/queryScore.action?r=";

    // 剩余摇奖次数
    this.remainTimes_ = 0;
    // 流量币总数
    this.creditSum_ = 0;
}

Score.prototype.showMyScore = function() {
    var that = this;

    // TODO: 不知道干啥的, 注释掉
   /* $("#jia_head").show();*/

    $.post(that.queryScorePath_+Math.random(), {}, function(data) {
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
        $.post("/ajax/getTTL.action?r="+Math.random(), {}, function (data) {
            if (data.status != "ok") {
                alert("获取个人服务信息失败")
            } else {
                $("#leavesDay").text(data.result.ttl);
            }
        })

        //更新 总汇信息
        flowScore.loadCreditSum();
    });
};


var score = new Score();

