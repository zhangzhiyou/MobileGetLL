/**
 * Created by tan on 14-7-29.
 */

function Score() {
    // 获取用户积分信息请求路径
    this.queryScorePath_ = "/ajax/queryScore.action";
}

Score.prototype.showMyScore = function(callback) {
    var that = this;

    // TODO: 不知道干啥的, 注释掉
   /* $("#jia_head").show();*/

    $.getJSON(that.queryScorePath_, {}, function(data) {
        if (data.status != "ok") {
            alert("流量币信息获取失败!");
            return;
        }

        var scoreData = data.result.list[0];
        that.scoreData_	= scoreData;

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
        if(callback){
            callback();
        }
    });
};


var score = new Score();

