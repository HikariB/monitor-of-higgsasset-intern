function idFormatter() {
    return '总计'
}


function cellStyle(value, row, index) {


    var classes = [
        'bg-blue',
        'bg-green',
        'bg-orange',
        'bg-yellow',
        'bg-red'
    ]

    if (value > 20000) {
        return {
            classes: classes[1]
        }
    }
    return {
        css: {
            color: 'red'
        }
    }
}

// function idCellStyle(value, row, index) {
//     return {
//
//     }
// }


function sumFormatter(data) {
    let field = this.field;
    let total_sum = data.reduce(function (sum, row) {
        return (sum) + (row[field] || 0)
    }, 0);

    return formatCurrency(total_sum)
    // return total_sum.toFixed(2);
}

function sumFormatterFraction(data) {
    let field = this.field;
    let total_sum = data.reduce(function (sum, row) {
        return (sum) + (row[field] || 0)
    }, 0);
    return toPercent(total_sum)
}


function toPercent(point) {
    if (point > 1 || point === 0)
        return 'NaN';
    var str = Number(point * 100).toFixed(3);
    str += "%";
    return str;
}

function accountName(value) {
    if (value === '83925101')
        return '<a href="/monitor-detail?id=83925101">GTW</a>';
    if (value === '83925105')
        return '<a href="/monitor-detail?id=83925105">GTL</a>';
    if (value === '118933')
        return '<a href="/monitor-detail?id=118933">BCX</a>';
    if (value === '83925087')
        return '<a href="/monitor-detail?id=83925087">GTZ</a>';
    if (value === '11803017')
        return '<a href="/monitor-detail?id=11803017">XZY</a>';
    if (value === '20087058')
        return '<a href="/monitor-detail?id=20087058">WTY</a>';
    if (value === '20092132')
        return '<a href="/monitor-detail?id=20092132">WTQ</a>';
    return 'unknown'
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
}


$(function () {
    // let connectBTN = $('#connect');
    // let disconnectBTN = $('#disconnect');

    let summary = $('#summary')
    // disconnectBTN.attr("disabled","disabled")

    //
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

    summary.bootstrapTable();

    // $("td,th").addClass("text-center");


    //
    // $('#account').change(function () {
    //     let selectVal = $(this).val();
    //     console.log(selectVal);
    //
    //     instrumentInfo.bootstrapTable('refreshOptions', {
    //         url: 'instruments/' + selectVal,
    //         autoRefreshStatus: true
    //
    //     });
    //     oredersInfo.bootstrapTable('refreshOptions', {
    //         url: 'orders/' + selectVal,
    //         autoRefreshStatus: true
    //     });
    // })

});
