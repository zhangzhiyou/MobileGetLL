/**
 * Created by tan on 14-7-27.
 */

function EventMan() {

    // 登录号码
    this.loginMobile_ = "";

    // 移动号码正则式
    this.mobileReg_ = /^1\d{10}$/;
}

EventMan.prototype.init = function() {
    var that = this;
    $("#mobile").keyup(function() {
        $(this).val(that.parseMobileFormat($(this).val()))
    });

    $("#loginDo").click(function (){
        that.loginDo();
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

    $.getJSON("/ajax/loadLoginMobile.action", function(data) {
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
    var mobile = that.parseMobileFormat($.trim($("#mobile").val()));
    var $message = $("#message");
    if (!mobile) {
        $message.html("手机号不能为空哦");
        return ;
    } else if(!that.mobileReg_.test(mobile)) {
        $message.html("要输入山东移动手机号哦")
        return;
    };

    var params = {
        "mobile" : mobile
    };

    $.post("/ajax/login.action", params, function (data) {
        if (data.status != "ok") {
            $message.html("该手机号没有订购业务");
        } else {
            // 登录成功就刷新 本页面
            that.refresh();
        }
    })
}

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

