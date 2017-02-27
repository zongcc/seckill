//存放交互逻辑js代码

var seckill = {
    URL: {
        getCurrentTime: function () {
            return "/kill/time/now";
        },
        exposer: function (seckillId) {
            return "/kill/" + seckillId + "/exposer";
        },
        execution: function (seckillId, md5) {
            return "/kill/" + seckillId + "/" + md5 + "/execute";
        }
    },
    handlerSeckill: function (seckillId, node) {
        //处理秒杀逻辑
        node.hide().html("<btn class='btn btn-primary btn-lg' id='killBtn'>开始秒杀</btn>");
        $.post(seckill.URL.exposer(seckillId), {}, function (result) {
            //回调函数执行交互
            if (result && result['success']) {
                var exposer = result['data'];
                if (exposer['exposed']) {
                    //开始秒杀
                    var md5 = exposer['md5'];
                    var killUrl = seckill.URL.execution(seckillId, md5);
                    $("#killBtn").one('click', function () {
                        $(this).addClass("disabled");
                        //发送秒杀请求
                        $.post(killUrl, {}, function (result) {
                            console.log("result:" + result);
                            if (result && result['success']) {
                                var killResult = result['data'];
                                var state = killResult['state'];
                                var stateInfo = killResult['stateInfo'];
                                node.html('<span class="label label-success">' + stateInfo + '</span>');
                            }
                        })
                    })
                    node.show();
                } else {
                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];
                    seckill.countDown(seckillId, start, end, now);
                }
            } else {
                console.log("result:" + result);
            }
        });
    },
    countDown: function (seckillId, startTime, endTime, nowTime) {
        var seckillBox = $("#seckill-box");
        if (startTime > nowTime) {
            seckillBox.html("秒杀未开始");
            var killTime = new Date(startTime + 1000);
            seckillBox.countdown(killTime, function (event) {
                var format = event.strftime("秒杀倒计时: %D天 %H时 %M分 %S秒");
                seckillBox.html(format);
            }).on("finish.countdown", function () {
                seckill.handlerSeckill(seckillId, seckillBox);
            });
        } else if (nowTime > endTime) {
            seckillBox.html("秒杀已结束");
        } else {
            seckill.handlerSeckill(seckillId, seckillBox);
        }
    },
    validatePhone: function (phone) {
        if (phone && phone.length == 11 && !isNaN(phone)) {
            return true;
        } else {
            return false;
        }
    },
    detail: {
        init: function (params) {
            //用户手机验证 计时交互
            var killPhone = $.cookie("killPhone");
            var startTime = params.startTime;
            var endTime = params.endTime;
            var seckillId = params.seckillId;
            if (!seckill.validatePhone(killPhone)) {
                //显示弹出层
                var killPhoneModal = $("#killPhoneModal");
                killPhoneModal.modal({
                    show: true,
                    backdrop: 'static',
                    keyboard: false
                });

                $("#killPhoneBtn").click(function () {
                    var inputPhone = $("#killPhoneKey").val();
                    if (seckill.validatePhone(inputPhone)) {
                        $.cookie("killPhone", inputPhone, {expires: 7, path: "/kill"});
                        window.location.reload();
                    } else {
                        $("#killPhoneMessage").hide().html('<label class="label label-danger">手机号输入有误！</label>').show();
                    }
                });
            }

            //判断是否开始
            $.get(seckill.URL.getCurrentTime(), {}, function (result) {
                if (result && result['success']) {
                    //时间判断
                    var nowTime = result['data'];
                    seckill.countDown(seckillId, startTime, endTime, nowTime);
                } else {
                    console.log('result:' + result);
                }
            })
        }
    }
}
