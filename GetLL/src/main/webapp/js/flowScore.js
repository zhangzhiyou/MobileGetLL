/**
 * Created by tan on 14-7-30.
 */

function FlowScore() {

    // 加载收支总和信息请求路径
    this.querCreditSumPath_ =  "/ajax/queryCreditSum.action?r=";

    // 加载收支明细请求路径
    this.queryCreditDetailPath_ = "/ajax/queryCreditDetail.action?type=";

    // 加载收支明细请求类型-加载下方数据（历史数据）
    this.downType_ = "down";
    // 加载收支明细请求类型-加载上方数据（最新数据）
    this.upType_ = "up";
}


FlowScore.prototype.loadCreditSum = function () {
    var that = this;
    $.ajax( {
        type: "POST",
        url:that.querCreditSumPath_+Math.random(),
        cache: false,
        dataType: "json",
        timeout: 25000,
        success: function(data){
            if (data.status != "ok") {
                that.showErrorResult(data);
                return;
            }else{
                var result = data.result;
                var days_div = $("#days");
                if(days_div){
                    days_div.html("流量币 | 最近"+result.days+"天 |");
                }
                var incomeDiv = $("#incomeDiv");
                if(incomeDiv){
                    incomeDiv.html(result.income);
                }
                var expensesDiv = $("#expensesDiv");
                if(expensesDiv){
                    expensesDiv.html(result.consumes);
                }
            }

            that.loadFirstCreditDetail();
        },
        error: function(data){}
    });
}

//首次加载明细数据
FlowScore.prototype.loadFirstCreditDetail = function () {
    var that = this;
    that.stop_ = false;
    that.detailId_ = 0;
    var requertUrl = that.queryCreditDetailPath_+that.upType_+"&startNum=0&r="+Math.random();
    if(!that.isloading){
        that.isloading = true;
        $.ajax( {
            type: "POST",
            url:requertUrl,
            cache: false,
            dataType: "json",
            timeout: 25000,
            success: function(data){
                that.isloading = false;
                if (data.status != "ok") {
                    that.showErrorResult(data,"first");
                    return;
                }else{
                    var result = data.result;
                    var list = result.list;
                    /**
                     * 0.清空div内容，重新更新div内容
                     * 1.更新当前对象的detailId_属性值为本次加载最后一条数据ID
                     * 2.更新stop_值，确定是否还可以继续加载数据
                     */
                    if(list != null && list.length > 0){
                        var htmls = that.getDetailHtml(data);
                        $("#mingxiListDiv").show();
                        $("#mingxiListDiv").html(htmls);
                        that.detailId_ = list[list.length-1].accessId;
                        that.stop_ = false;
                    }else{
                        that.stop_= true;
                    }
                }
            },
            error: function(data){
                that.isloading = false;
            }
        });
    }
}

//获取明细展示dom内容
FlowScore.prototype.getDetailHtml = function(data){
    var result = data.result;
    var list = result.list;
    var htmls=new Array();
    for(var i = 0;i<list.length;i++){
        var obj = list[i];
        var accessTypeHtml = "";
        var operType = '';
        var imageName = obj.operImg;
        var operTypeId = obj.operTypeId;
        var operResultDesc = obj.operResultDesc == "null" ?"":obj.operResultDesc;
        var accessType = obj.accessType//出入帐类型（1：收入、2：支出）
        if( accessType== "1"){
            accessTypeHtml = 'myaccount_mingxi_green';
            operType= "+";
        }else if(accessType == "2"){
            accessTypeHtml = 'myaccount_mingxi_blue';
            operType= "-";
            if(operTypeId == "TIGER_GIVE"){
                operResultDesc = "投币成功";
            }
        }
        if(operTypeId == "CREDIT_EXCHANGE_GIVE"){//操作类型--兑换流量包获赠
            imageName = "dh_1.png";//  成功
            if(obj.operResult != '0'){//0：兑换成功	1：兑换失败	2：处理中
                imageName = "dh_zs.png";//失败
            }
        }
        var operResultDescStyle = "myaccount_mingxi_gray";
        if(obj.operResult != '0'){//0：兑换成功	1：兑换失败	2：处理中
            operResultDescStyle = 'myaccount_mingxi_red';
        }

        //如果流量币为0 ，那么则不需要展示正负符号
        var credit = obj.credit;
        if(credit == '0' || credit == 0){
            operType= "";
        }

        htmls[i] =[
            '<div class="myaccount_mingxi_shouzhi_list">',
                '<div class="myaccount_mingxi_shouzhi_logo"><img src="/image/'+imageName+'" width="60" height="60" /></div>',
            '<div class="text_m">',
                '<div>'+obj.operDesc+'</div>',
                '<div class="myaccount_mingxi_gray">'+obj.accessTime+'</div>',
            '</div>',
            '<div class="text_r">',
                '<div class="'+accessTypeHtml+'">'+operType+' '+credit+'</div>',
                '<div class="'+operResultDescStyle+'">'+operResultDesc+'</div>',
            '</div>',
            '</div>'].join('');
    }
    var htmlStr = htmls.join('');
    return htmlStr;
}

// 第一次使用流量汇
FlowScore.prototype.showErrorResult = function (data,isFirst){
    if (data.status != "ok") {
        var message = data.message;
		if(isFirst == "first"){
            if(data.code == "EMPTY"){
//            if (confirm(message)) {
//                window.location.href="/"
//            }else{
//                window.location.href="/"
//            }
            }else{
                alert(message);
            }
        }else{
			alert(message);
        }
        return;
    }
};


//非第一次加载明细内容
FlowScore.prototype.loadNoFirstCreditDetail = function () {
    var that = this;
    var requertUrl = that.queryCreditDetailPath_+that.downType_+"&startNum="+that.detailId_ + "&r="+Math.random();
    if(!that.stop_){
        if(!that.isloading){
            that.isloading = true;
            $.ajax( {
                type: "POST",
                url:requertUrl,
                cache: false,
                dataType: "json",
                timeout: 25000,
                success: function(data){
                    that.isloading = false;
                    if (data.status != "ok") {
                        //alert(data.message);
                        return;
                    }else{
                        var result = data.result;
                        var list = result.list;
                        /**
                         * 0.追加div内容展示
                         * 1.更新当前对象的detailId_属性值为本次加载最后一条数据ID
                         * 2.更新stop_值，确定是否还可以继续加载数据

                         */
                        if(list != null && list.length > 0){
                            var htmls = that.getDetailHtml(data);
                            $("#mingxiListDiv").append(htmls);
                            that.detailId_ = list[list.length-1].accessId;
                            that.stop_ = false;
                        }else{
                            that.stop_= true;
                        }
                    }
                },
                error: function(data){
                    that.isloading = false;
                }
            });
        }
    }
}

//非第一次加载明细内容
FlowScore.prototype.loadCreditDetail = function (type) {
    var that = this;
    if(type == "up"){
        that.loadFirstCreditDetail();
    }else{
        that.loadNoFirstCreditDetail();
    }
}

var flowScore = new FlowScore();