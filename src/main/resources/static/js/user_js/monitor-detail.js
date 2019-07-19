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

$(function () {

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
        window.location.href = "/login";
    })


    instrumentInfo.bootstrapTable();
    oredersInfo.bootstrapTable();



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
