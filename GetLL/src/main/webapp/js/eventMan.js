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

    this.nickName_ = "";

    // 获取登陆动态密码请求路径
    this.getPasswordPath_ = "/ajax/getPasswordDo.action";

    this.getOtherPasswordPath_ = "/ajax/getOtherPassword.action";

    this.refreshNickName_ = "/ajax/refreshNickName.action"


    // 重发动态密码倒计时线程ID
    this.pwdIntervalIndex_ = -1;
    // 重发动态密码倒计时线程ID
    this.pwdIntervalIndex2_ = -1;

}

EventMan.prototype.init = function() {
    var that = this;

    $("#mobile").keydown(function(e) {
        var curKey = e.which;
        if (curKey == 13) {
            $("#loginDo").click();
        }
    });

    $("#password").keydown(function (e) {
        var curKey = e.which;
        if (curKey == 13) {
            $("#loginDo").click();
        }
    })

    // 去掉非法字符
    $("#mobile").keyup(function() {
        $(this).val(that.parseMobileFormat($(this).val()))
    });

    // 登录
    $("#loginDo").click(function (){
        that.loginDo(function() {
            // 登录成功，转到home页面
            locationPage("/home.action")
        });
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

        that.shakeNow();
    });

    // 免费续期
    $("#leavesDay").click(function() {
        if ($("#leavesDay").text().indexOf("点我续期") > 0) {
            $.post("/ajax/freshRegisterCode.action?r="+Math.random(), {}, function (data) {
                if (data.status != "ok") {
                    alert("续期失败，请刷新后重试");
                } else {
                    $("#leavesDay").text(data.result.ttl);
                    alert("恭喜，续期成功")
                }
            });
        }
    });

    $("#getPassword").unbind("click");
    // 点击事件:获取动态密码
    $("#getPassword").click(function() {
        that.getPassword();
    });
};

EventMan.prototype.parseMobile = function(mobile) {
    mobile = mobile.replace(/\-/g, "");
    if (mobile.indexOf("+86") == 0) {
        mobile = mobile.substr(3);
    } else if (mobile.indexOf("86") == 0) {
        mobile = mobile.substr(2);
    }
    return mobile;
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
 * 获取动态密码
 * @param type
 */
EventMan.prototype.getPassword =  function(type) {
    var that = this;
    var mobile = that.parseMobile($.trim($("#mobile").val()));
    if (!mobile) {
        $("#message").html("手机号不能为空");
        return;
    } else if (!that.mobileReg_.test(mobile)) {
        $("#message").html("必须输入山东移动手机号");
        return;
    }
    var postUrl = that.getPasswordPath_ + "?";

    if(type == "other")
    {
        postUrl = that.getOtherPasswordPath_+"?isLogin=true"+"&";
    }

    $.post(postUrl + "r=" + Math.random(), {
        mobile : mobile
    }, function(data) {
        var msg = "";
        if (data.status != "ok") {
            msg = data.message;
        }
        $("#message").html(msg);
        var seconds = parseInt(data.result.seconds);
        if (seconds > 0) {
            $("#getPassword").hide();
            $("#sendStatus").show();
            that.pwdInterval(seconds);
        }
    }, "json");
};


/**
 * 允许再次获取动态密码倒计时
 * @param seconds 秒
 */
EventMan.prototype.pwdInterval = function(seconds) {
    var that = this;
    var $seconds = $("#seconds");
    $seconds.html(seconds);
    var index = setInterval(function() {
        var second = parseInt($seconds.html());
        second = second - 1;
        $seconds.html(second);
        if (second <= 0) {
            clearInterval(that.pwdIntervalIndex_);
            $("#getPassword").show();
            $("#sendStatus").hide();
        }
    }, 1000);
    that.pwdIntervalIndex_ = parseInt(index);
};

/**
 * 允许再次获取动态密码倒计时

 * @param seconds 秒

 */
EventMan.prototype.pwdInterval2 = function(seconds,type) {
    var that = this;
    var $seconds = $("#seconds"+type);
    $seconds.html(seconds);
    var index = setInterval(function() {
        var second = parseInt($seconds.html());
        second = second - 1;
        $seconds.html(second);
        if (second <= 0) {
            clearInterval(that.pwdIntervalIndex2_);
            $("#getPassword"+type).show();
            $("#sendStatus"+type).hide();
        }
    }, 1000);
    that.pwdIntervalIndex2_ = parseInt(index);
};

/**
 * 检测是否登录
 * 如果已经登录,刷新页面，隐藏登录框，如果没有登录，显示登录框
 */
EventMan.prototype.checkLogin = function(loginCallback, notLoginCallBack) {
    var that = this;

    $.getJSON("/ajax/loadLoginedMobile.action?r=" + new Date().getTime(), {}, function(data) {
        if (data.status != "ok") {
            //todo: 登录失败跳转到 login?
            alert("登录失败");
            return;
        }

        // 已经登录的号码(存在则表示已经登录,否则未登录)
        that.loginMobile_ = data.result.loginMobile;
        that.nickName_ = data.result.nickName;

        if (that.loginMobile_) {
            if (loginCallback) {
                loginCallback(data);
            }
        } else {
            if (notLoginCallBack) {
                notLoginCallBack(data);
            }
        }
    });
};

/**
 * 登录
 * 登录成功后，刷新页面
 */
EventMan.prototype.loginDo = function(callback) {
    var that = this;

    // 取得参数
    var mobile = that.parseMobileFormat($.trim($("#mobile").val()));
    var password = $.trim($("#password").val());

    var $message = $("#message");
    if (!mobile) {
        $message.html("手机号不能为空哦");
        return ;
    } else if(!that.mobileReg_.test(mobile)) {
        $message.html("要输入山东移动手机号哦");
        return;
    }

    var params = {
        "mobile" : mobile,
        "password": password
        //"registerCode" : registerCode
    };

    $.post("/ajax/login.action?r=" + Math.random(), params, function (data) {
        if (data.status != "ok") {
            if (data.errorId == 0) {
                $("#passwordContent").show();
            }

            $message.html(data.message);

            return;

//            $message.html("该手机号没有订购业务");

            // 没有订购业务
//            if (data.errorId == 0) {
                // 登录窗口隐藏
//                $("#loginContent").hide();
//                显示注册窗口
//                $("#registerCodeContent").show();
        /*    } else if (data.errorId == 1) {//不是山东移动号码
                // 登录窗口隐藏
                $("#loginContent").show();
                // 显示注册窗口
                $("#registerCodeContent").hide();
                $message.html("<span style=\"color: red;\">" + data.errorDesc + "</span>")
            }*/

        } else {
//            that.refresh();

            if (callback) {
                callback(data);
            }
        }
    })
};

EventMan.prototype.getPassword2 = function(type, isLogin, isVeriMobile) {
    var that = this;

    // 找不到就为空
    var mobile = that.parseMobile($.trim($("#mobile"+type).val()));

    if(isVeriMobile){
        if (!mobile) {
            $("#message"+type).html(that.tip_.emptyMobile);
            $("#message"+type).show();
            return;
        } else if (!that.mobileReg_.test(mobile)) {
            $("#message"+type).html(that.tip_.notShangDongMobile);
            $("#message"+type).show();
            return;
        }
    }


    var postUrl = that.getOtherPasswordPath_+"?isLogin="+isLogin+"&mobile="+mobile+"&r=" + new Date().getTime();
    $.post(postUrl, {"type":type}, function(data) {
        var msg = "";
        if (data.status != "ok") {
            msg = data.message;
        }
        $("#message"+type).html(msg);
        var seconds = parseInt(data.result.seconds);
        if (seconds > 0) {
            if($("#getPassword"+type)){
                $("#getPassword"+type).hide();
            }
            if($("#sendStatus"+type)){
                $("#sendStatus"+type).show();
            }
            that.pwdInterval2(seconds,type);
        }
    }, "json");
};

EventMan.prototype.refreshNickName = function() {
    var that = this;
    var postUrl = that.refreshNickName_;

    $.post(postUrl + "?r=" + Math.random(), {}, function(data) {
        if (data.status == "ok") {
            that.nickName_ = data.result.nickname;
        }
    }, "json");
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
            alert("正在为您摇奖，请稍等刷新查看\n提示: 系统每天6点准时为您摇奖")
            that.refresh();
        }
    })
}

/**
 * 获取剩余摇奖次数
EventMan.prototype.getRemainTimes = function () {
    var that = this;

    $.getJSON("/ajax/getRemainTimes.action", function (data) {
        if (data.status != "ok") {
            alert("网络错误");
            return ;
        } else {
            that.remainTimes_ = data.result.remainTimes;
        }

        return that.remainTimes_;
    })
};*/

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