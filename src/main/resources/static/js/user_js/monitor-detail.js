function idFormatter() {
    return 'Total'
}

function sumFormatter(data) {
    let field = this.field;
    let total_sum = data.reduce(function (sum, row) {
        return (sum) + (row[field] || 0)
    }, 0);

    return formatCurrency(total_sum);
}

function fractionDigits2(value) {
    return value.toFixed(2);
}

/**
 *  将数值四舍五入(保留2位小数)后格式化成金额形式
 *
 * @param num 数值(Number或者String)
 * @return 金额格式的字符串,如'1,234,567.45'
 * @type String
 */
function formatCurrency(num) {
    num = num.toString().replace(/[^\d\.-]/g, ''); //转成字符串并去掉其中除数字, . 和 - 之外的其它字符。
    if (isNaN(num)) num = "0";  //是否非数字值
    var sign = (num == (num = Math.abs(num)));
    num = Math.floor(num * 100 + 0.50000000001); //下舍入
    var cents = num % 100;  //求余 余数 = 被除数 - 除数 * 商
    cents = (cents < 10) ? "0" + cents : cents; //小于2位数就补齐
    num = Math.floor(num / 100).toString();
    for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++) { //每隔三位小数分始开隔
        //4 ==> 三位小数加一个分隔符，
        num = num.substring(0, num.length - (4 * i + 3)) + ',' + num.substring(num.length - (4 * i + 3));
    }
    return (((sign) ? '' : '-') + num);
    // return (((sign) ? '' : '-') + num + '.' + cents);
}


window.icons = {
    refresh: 'ion-md-refresh',
    columns: 'ion-md-menu',
    fullscreen: 'ion-md-expand',
    autoRefresh: 'ion-md-time'
};

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


$(function () {
    // let connectBTN = $('#connect');
    // let disconnectBTN = $('#disconnect');
    let instrumentInfo = $('#instrument-info');
    let oredersInfo = $('#ordersInfo');
    let accountId = getParam("id");

    if (accountId !== undefined) {
        $('#' + accountId).attr("selected", true)
        oredersInfo.bootstrapTable('refreshOptions', {
            url: 'orders/' + accountId,
            autoRefreshStatus: true
        });
        instrumentInfo.bootstrapTable('refreshOptions', {
            url: 'instruments/' + accountId,
            autoRefreshStatus: true

        });
    }

    $('#disconnect').click(function () {
        $.ajax({
            type: "get",
            url: "/logout"
        })
        window.location.href="/login";
    })
    // $('#118933').attr();

    // $("#account").each(function (i, j) {
    //     $(j).find("option:selected").attr("selected", false);
    //     $(j).find("option").first().attr("selected", true);
    // });


    // disconnectBTN.attr("disabled","disabled")


    // $.ajax({
    //     type: "GET",
    //     url: "/connect",
    //     success: function (data) {
    //         console.log(data);
    //     }
    // });


    // connectBTN.click(function () {
    //
    //     connectBTN.attr("disabled","disabled");
    //     disconnectBTN.removeAttr("disabled");
    //     // alert("connect")
    //     $.ajax({
    //         type: "GET",
    //         url: "/connect",
    //         success: function (data) {
    //             console.log(data);
    //         }
    //     });
    //     instrumentInfo.bootstrapTable('refreshOptions', {
    //         autoRefreshStatus: true
    //     });
    //
    // });


    // disconnectBTN.click(function () {
    //     disconnectBTN.attr("disabled","disabled");
    //     connectBTN.removeAttr("disabled");
    //     // alert("disconnect")
    //     $.ajax({
    //         type: "GET",
    //         url: "/close",
    //         success: function (data) {
    //             console.log(data);
    //         }
    //     });
    //     // instrumentInfo.bootstrapTable('refreshOptions', {
    //     //     // autoRefresh: false,
    //     //     autoRefreshStatus: false,
    //     //     // search: false
    //     // });
    //
    // });

    // $.extend($.fn.bootstrapTable.columnDefaults, {
    //     align: 'center',
    //     valign: 'middle'
    // });
    instrumentInfo.bootstrapTable();
    oredersInfo.bootstrapTable();

    // $("td,th").addClass("text-center");


    $('#account').change(function () {
        let selectVal = $(this).val();
        console.log(selectVal);
        if (selectVal === 'default') {
            alert("Select an User Account")
        } else {
            instrumentInfo.bootstrapTable('refreshOptions', {
                url: 'instruments/' + selectVal,
                autoRefreshStatus: true

            });
            oredersInfo.bootstrapTable('refreshOptions', {
                url: 'orders/' + selectVal,
                autoRefreshStatus: true
            });
        }
    });
});
