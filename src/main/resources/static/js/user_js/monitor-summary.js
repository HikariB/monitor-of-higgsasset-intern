function idFormatter() {
    return '统计'
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


function sumFormatter(data) {
    let field = this.field;
    let total_sum = data.reduce(function (sum, row) {
        return (sum) + (row[field] || 0)
    }, 0);

    return formatCurrency(total_sum)
    // return total_sum.toFixed(2);
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
        return '国泰君安-文思捷'
    if (value === '83925105')
        return '国泰君安-李春晓'
    if (value === '118933')
        return '宝城-徐媛静'
    if (value === '83925087')
        return '国泰君安-赵鲲'
    if (value === '11803017')
        return '兴证-周永敏'
    if (value === '20087058')
        return '华泰-杨建委'
    if (value === '20092132')
        return '华泰-祁先桥'
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
    return (((sign) ? '' : '-') + num + '.' + cents);
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
