$('#disconnect').click(function () {
    $.ajax({
        type: "get",
        url: "logout"
    });
    window.location.href = "login";
});


$('#limit-btn').click(function () {
    let totalProfitLimit = $('#totalProfitLimit').val();
    let profitLimit = $('#profitLimit').val();
    let cancelWarnRatio = $('#cancelWarnRatio').val();
    let orderCancelLimit = $('#orderCancelLimit').val();
    let netPositionLimit = $('#netPositionLimit').val();
    let mDDelaySecLimit = $('#mDDelaySecLimit').val();

    if (totalProfitLimit === null || totalProfitLimit === "")
        totalProfitLimit = -30000;
    if (profitLimit === null || profitLimit === "")
        profitLimit = -20000;
    if (cancelWarnRatio === null || cancelWarnRatio === "")
        cancelWarnRatio = 0.98;
    if (orderCancelLimit === null || orderCancelLimit === "")
        orderCancelLimit = 390;
    if (netPositionLimit === null || netPositionLimit === "")
        netPositionLimit = 5;
    if (mDDelaySecLimit === null || mDDelaySecLimit === "")
        mDDelaySecLimit = 10;

    $.ajax({
        type: "POST",
        contentType: "application/x-www-form-urlencoded",
        url: "set/para",
        data: {
            "totalProfitLimit": totalProfitLimit,
            "profitLimit": profitLimit,
            "cancelWarnRatio": cancelWarnRatio,
            "orderCancelLimit": orderCancelLimit,
            "netPositionLimit": netPositionLimit,
            "mDDelaySecLimit": mDDelaySecLimit
        },
        success: function (data) {
            alert(data);
        },
        error: function (data) {
            alert("failed");
        }
    });
});

$('#edit-btn').click(function () {
    let cmdOpt = $('#cmdOpt').val();
    console.log(cmdOpt);
    let account = $('#account').val();
    let editContent = $('#edit-content').val();
    if (cmdOpt === null || cmdOpt === "") {
        alert("Option Can't be Null");
        return false;
    }
    if (account === null || account === "") {
        alert("Account Can't be Null");
        return false;
    }
    if (cmdOpt === "updCmd" && (editContent === null || editContent === "")) {
        alert("Update Content Can't be Null");
        return false;
    }
    $.ajax({
        type: "POST",
        contentType: "application/x-www-form-urlencoded",
        url: "account/subscribe/edit",
        data: {
            "cmdOpt": cmdOpt,
            "account": account,
            "editContent": editContent
        },
        success: function (data) {
            $('#time-stamp').text(new Date().toLocaleTimeString());
            $('#alertText').text(data);
        },
        error: function (data) {
            $('#time-stamp').text(new Date().toLocaleTimeString())
            $('#alertText').text("failed")
        }
    });
});

$('#edit-ac').click(function () {
    let cmdOpt = $('#cmdOpt-account').val();
    console.log(cmdOpt);
    let account = $('#account-ac').val();
    let editContent = $('#edit-content-ac').val();
    if (cmdOpt === null || cmdOpt === "") {
        alert("Option Can't be Null");
        return false;
    }
    if (account === null || account === "") {
        alert("Account Can't be Null");
        return false;
    }
    if (cmdOpt === "updCmd" && (editContent === null || editContent === "")) {
        alert("Update Content Can't be Null");
        return false;
    }
    $.ajax({
        type: "POST",
        contentType: "application/x-www-form-urlencoded",
        url: "account/config/edit",
        data: {
            "cmdOpt": cmdOpt,
            "account": account,
            "editContent": editContent
        },
        success: function (data) {
            $('#time-stamp').text(new Date().toLocaleTimeString());
            $('#alertText').text(data)
        },
        error: function (data) {
            $('#time-stamp').text(new Date().toLocaleTimeString());
            $('#alertText').text("failed")
        }
    });
});