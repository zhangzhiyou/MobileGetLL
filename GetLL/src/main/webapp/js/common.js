function getNumTip(index) {	
	var charObj = ['一','二','三','四','五','六','七','八','九','十','十一','十二','十三','十四','十五','十六','十七','十八','十九','二十','二十一','二十二','二十三','二十四','二十五','二十六','二十七','二十八','二十九','三十'];
	var value = charObj[index];
	return value ? value : "";
}

// 重定向网页
function locationPage(url) {
    //todo:
//    if (eventMan.isLogin()) {
        location.href = url;
//    }
}

function logoutSystem() {
    var confirmMessage = "下次来将重新登录，确定退出吗？";
    if (confirm(confirmMessage)) {
        $.post("/ajax/logout.action?r=" + Math.random(), {}, function (data) {
            if (data.status != "ok") {
                alert(data.message);
            } else {
                location.href = "/";
            }
        });
    }
}