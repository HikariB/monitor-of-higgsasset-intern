function idFormatter() {
    return '总计'
}

function profitStyle(value, row, index) {
    var classes = [
        'bg-blue',
        'bg-green',
        'bg-orange',
        'bg-yellow',
        'bg-red'
    ];
    if (value >= 0) {
        return {
            css: {
                color: 'white'
            }
        }
    } else if (value > -20000 && value < 0) {
        return {
            css: {
                color: 'red'
            }
        }
    } else {
        return {
            classes: classes[1]
        }
    }
    // return {
    //     css: {
    //         color: 'red'
    //     }
    // }
}

function sumFormatter(data) {
    let field = this.field;
    let total_sum = data.reduce(function (sum, row) {
        return (sum) + (row[field] || 0)
    }, 0);

    return formatCurrency(total_sum)
    // return total_sum.toFixed(2);
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
    //不保留小数的部分
    return (((sign) ? '' : '-') + num);
    // return (((sign) ? '' : '-') + num + '.' + cents);
}

window.icons = {
    refresh: 'ion-md-refresh',
    columns: 'ion-md-menu',
    fullscreen: 'ion-md-expand',
    autoRefresh: 'ion-md-time'
};


function btnlogout() {
    $.ajax({
        type: "get",
        url: "logout"
    });
    window.location.href = "login";
}

// $.extend($.fn.bootstrapTable.columnDefaults, {
//     align: 'center',
//     valign: 'middle'
// });