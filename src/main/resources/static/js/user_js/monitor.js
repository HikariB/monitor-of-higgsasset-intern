function idFormatter() {
    return 'Total'
}

function sumFormatter(data) {
    let field = this.field;
    let total_sum = data.reduce(function (sum, row) {
        return (sum) + (row[field] || 0)
    }, 0);

    return total_sum;
}

function profitFormatter(value){
    return value.toFixed(2);
}

window.icons = {
    refresh: 'ion-md-refresh',
    columns: 'ion-md-menu',
    fullscreen: 'ion-md-expand',
    autoRefresh: 'ion-md-time'
}


$(function () {
    let connectBTN = $('#connect');
    let disconnectBTN = $('#disconnect');
    let instrumentInfo = $('#instrument-info');
    let oredersInfo = $('#ordersInfo')

    connectBTN.click(function () {
        // alert("connect")
        $.ajax({
            type: "GET",
            url: "/connect",
            success: function (data) {
                console.log(data);
            }
        });
        instrumentInfo.bootstrapTable('refreshOptions', {
            autoRefreshStatus: true
        });

    });


    disconnectBTN.click(function () {
        // alert("disconnect")
        $.ajax({
            type: "GET",
            url: "/close",
            success: function (data) {
                console.log(data);
            }
        });
        // instrumentInfo.bootstrapTable('refreshOptions', {
        //     // autoRefresh: false,
        //     autoRefreshStatus: false,
        //     // search: false
        // });

    });

    $.extend($.fn.bootstrapTable.columnDefaults, {
        align: 'center',
        valign: 'middle'
    });
    instrumentInfo.bootstrapTable();
    oredersInfo.bootstrapTable();

    $('#account').find('select').change(function () {
        console.log('changed');
    })

})
