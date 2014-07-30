/**
 * Created by tan on 14-7-27.
 */

function EventMan() {

    // 登录号码
    this.loginMobile_ = "";

    // 移动号码正则式
    this.mobileReg_ = /^1\d{10}$/;

    // 剩余摇奖次数
    this.remainTimes_ = 0;
}

EventMan.prototype.init = function() {
    var that = this;

    // 去掉非法字符
    $("#mobile").keyup(function() {
        $(this).val(that.parseMobileFormat($(this).val()))
    });

    // 登录
    $("#loginDo").click(function (){
        that.loginDo();
    });

    // 返回
    $("#cancelSetting").click(function(){
        that.refresh();
    });

    // 获得注册码
    $("#getRegisterCode").click(function() {
        $.getJSON("/ajax/getRegisterCode.action", function(data) {
            var registerCode = data.result.registerCode;

            $("#registerCode").val(registerCode);
        });
    });

    // 提交注册码
    $("#setRegisterCodeDo").click(function() {
       that.loginDo();
    });

    $("#btShakeNow").click(function() {
        // 如果剩余次数不足，则返回

        if (score.remainTimes_ <= 0) {
            alert("您今天剩余摇奖次数为0,\n提示: 系统每天6点准时为您摇奖")
            return;
        }

        alert("正在为您摇奖，请稍等刷新查看\n提示: 系统每天6点准时为您摇奖")
        that.shakeNow();
    });
};

EventMan.prototype.parseMobileFormat = function (mobile) {
    var that = this;
    mobile = mobile.replace(/[^0-9]+/g, "");
    if (!/^[0-9]*$/g.test(mobile)) {
        mobile = that.parseMobileFormat(mobile);
    }
    return mobile;
}

/**
 * 检测是否登录
 * 如果已经登录,刷新页面，隐藏登录框，如果没有登录，显示登录框
 */
EventMan.prototype.checkLogin = function(callback) {
    var that = this;

    $.post("/ajax/loadLoginMobile.action", {}, function(data) {
        if (data.status != "ok") {
            $("#loginContent").show();
            return;
        }

        that.loginMobile_ = data.result.loginMobile;

        if (that.loginMobile_) {
            $("#loginContent").hide();
        } else {
            $("#loginContent").show();
        }

        if (callback) {
            callback(data);
        }
    });
};


/**
 * 登录
 * 登录成功后，刷新页面
 */
EventMan.prototype.loginDo = function() {
    var that = this;

    // 取得参数
    var mobile = that.parseMobileFormat($.trim($("#mobile").val()));
    var registerCode = $.trim($("#registerCode").val());

    var $message = $("#message");
    if (!mobile) {
        $message.html("手机号不能为空哦");
        return ;
    } else if(!that.mobileReg_.test(mobile)) {
        $message.html("要输入山东移动手机号哦");
        return;
    };

    var params = {
        "mobile" : mobile,
        "registerCode" : registerCode
    };

    $.post("/ajax/login.action", params, function (data) {
        if (data.status != "ok") {
//            $message.html("该手机号没有订购业务");
            // 登录窗口隐藏
            $("#loginContent").hide();
            // 显示注册窗口
            $("#registerCodeContent").show();
        } else {
            // 如果是第一次登录
            if (data.result.firstLogin) {
                //alert("您是第一次使用本站服务\n正在为您准备数据，请稍等约30秒后刷新即可")
            }
            // 登录成功就刷新 本页面
            that.refresh();
        }
    })
};

/**
 * 立即摇奖
 */
EventMan.prototype.shakeNow = function() {
    var that = this;

    // 获取剩余摇奖次数

    $.post("/ajax/shakeNow.action", {}, function (data) {
        if (data.status != "ok") {
            alert("网络错误");
        } else {
            //TODO: 多少秒后自动刷新
            that.refresh();
        }
    })
}

/**
 * 获取剩余摇奖次数
 */
EventMan.prototype.getRemainTimes = function () {
    var that = this;

    $.getJSON("/ajax/getRemainTimes.action", function (data) {
        if (data.status != "ok") {
            alert("网络错误");
            return ;
        } else {
            that.remainTimes_ = data.result.remainTimes;
        }

        alert(that.remainTimes_);

        return that.remainTimes_;
    })
};

/**
 * 刷新页面
 */
EventMan.prototype.refresh = function() {
    location.href = location.href;
};

/**
 * 获取登录状态
 */
EventMan.prototype.isLogin = function() {
    return this.loginMobile_ ? true : false;
}

var eventMan = new EventMan();
eventMan.init();