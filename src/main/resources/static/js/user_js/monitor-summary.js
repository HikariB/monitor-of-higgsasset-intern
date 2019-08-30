function footerStyle(column) {
    return {
        id: {
            classes: 'uppercase'
        },
        name: {
            css: {'font-weight': 'normal'}
        },
        price: {
            css: {color: 'red'}
        }
    }[column.field]
}

function cellStyle(value, row, index) {
    var classes = [
        'bg-blue',
        'bg-green',
        'bg-orange',
        'bg-yellow',
        'bg-red'
    ];

    if (value > 0) {
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

function delayWarnStyle(value, row, index) {
    var classes = [
        'bg-blue',
        'bg-green',
        'bg-orange',
        'bg-yellow',
        'bg-red'
    ];
    if (value > 10) {
        return {
            css: {
                color: 'red'
            }
        }
    }
    // -1000 表示初始化
    // -2000 表示已连接
    // -3000 表示关闭
    // -4000 表示Error
    if (value === -3000 || value === -4000) {
        return classes[4];
    }
    return {
        css: {
            color: 'green'
        }
    }

}

function delayDF(value) {
    if (value === -1)
        return "Init...";
    if (value < -10)
        return '<span style="color: #85144B">异常</span>';
    return value;
}


function sumFormatterFraction(data) {
    let field = this.field;
    let total_sum = data.reduce(function (sum, row) {
        return (sum) + (row[field] || 0)
    }, 0);
    return toPercent(total_sum)
}


function toPercent(point) {
    if (point > 1 || point < 0)
        return 'NaN';
    var str = Number(point * 100).toFixed(3);
    str += "%";
    return str;
}

function accountName(value) {

    let oldVal = value;
    value = value.replace('_', '').toUpperCase();
    if (oldVal === "fz_t"){
        value = "SHFE";
    }
    return `<a href="monitor-detail?did=${oldVal}">${value}</a>`
}


var MDValidFlag = true;


function responseHandler(res) {
    // console.log(res[0].profitSum);
    MDValidFlag = true;
    res.forEach(function (e) {
        if (!e.marketDataValid) {
            MDValidFlag = false;
        }
    });

    if (MDValidFlag) {

    } else {

    }

    return res;
}


$(function () {


    let summary = $('#summary')

    $('#disconnect').click(btnlogout);

    summary.bootstrapTable();
});
