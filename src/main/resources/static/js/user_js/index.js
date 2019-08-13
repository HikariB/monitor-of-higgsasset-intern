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

$('#newWs-btn').click(function () {
    let wsUrl = $('#wsUrl').val();
    let loginAccount = $('#loginAccount').val();
    let loginPassword = $('#loginPassword').val();
    let subscribeAccount = $('#subscribeAccount').val();
    if (wsUrl === null || wsUrl === "") {
        alert("Url Can't be Null");
        return false;
    }
    if (loginAccount === null || loginAccount === "") {
        alert("loginAccount Can't be Null");
        return false;
    }
    if (loginPassword === null || loginPassword === "") {
        alert("loginPassword Can't be Null");
        return false;
    }
    if (subscribeAccount === null || subscribeAccount === "") {
        alert("subscribeAccount Can't be Null");
        return false;
    }
    $.ajax({
        type: "POST",
        contentType: "application/x-www-form-urlencoded",
        url: "new/wsClient",
        data: {
            "wsUrl":wsUrl,
            "loginAccount":loginAccount,
            "loginPassword":loginPassword,
            "subscribeAccount":subscribeAccount
        },
        success:function (data) {
            alert(data)
        },
        error:function (data) {
            alert("failed")
        }
    });
});