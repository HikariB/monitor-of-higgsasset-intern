function fractionDigits2(value) {
    return value.toFixed(2);
}

function getParam(pname) {
    var params = location.search.substr(1);
    var ArrParam = params.split('&');
    if (ArrParam.length == 1) {
        return params.split('=')[1];
    } else {
        //多个参数参数的情况
        for (var i = 0; i < ArrParam.length; i++) {
            if (ArrParam[i].split('=')[0] == pname) {
                return ArrParam[i].split('=')[1];
            }
        }
    }
}

var MarketDataNoTValidid = 0;
var MDValidFlag = true;

function responseHandler(res) {
    // console.log(res[0].profitSum);
    res.forEach(function (e) {
        if (!e.marketDataValid) {
            MarketDataNoTValidid++;
            if (MarketDataNoTValidid > 9) {
                console.log("连续出现10次以上无效市场标志！")
                MarketDataNoTValidid = 0;
                MDValidFlag = false;
            }
        } else {
            MarketDataNoTValidid = 0;
            MDValidFlag = true;
        }
    });


    return res;
}

function warnStyle(value, row, index) {
    var classes = [
        'bg-blue',
        'bg-green',
        'bg-orange',
        'bg-yellow',
        'bg-red',
        'bg-danger',
        'bg-info'
    ];
    if (!MDValidFlag) {
        return {
            classes: classes[6]
        }
    } else {
        return {
            // classes: classes[1]
        };
    }

}


$(function () {

    console.log("当前合同更新频率:1Hz, 订单更新频率:0.25Hz，出现10次以上无效市场数据标志时将报警，当利润低于-20000时将变为绿色背景")

    let instrumentInfo = $('#instrumentInfo');
    let oredersInfo = $('#ordersInfo');
    let accountId = getParam("id");

    if (accountId !== undefined) {
        $('#' + accountId).attr("selected", true);
        oredersInfo.bootstrapTable('refreshOptions', {
            url: 'orders/' + accountId,
            autoRefresh: true,
            autoRefreshStatus: true
        });
        instrumentInfo.bootstrapTable('refreshOptions', {
            url: 'instruments/' + accountId,
            autoRefresh: true,
            autoRefreshStatus: true
        });
        oredersInfo.bootstrapTable();
        instrumentInfo.bootstrapTable();

    }


    $('#disconnect').click(btnlogout);


    $('#account').change(function () {
        let selectVal = $(this).val();
        console.log(selectVal);
        if (selectVal === 'default') {
            alert("Select an User Account")
        } else {
            window.location.replace(`monitor-detail?id=${selectVal}`);
            // instrumentInfo.bootstrapTable('refreshOptions', {
            //     url: 'instruments/' + selectVal,
            //     autoRefreshStatus: true
            //
            // });
            // oredersInfo.bootstrapTable('refreshOptions', {
            //     url: 'orders/' + selectVal,
            //     autoRefreshStatus: true
            // });
        }
    });
});
