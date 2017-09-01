var stompClient = null;

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#send").click(function () {
        sendName();
    });
    $("#connectToBidding").click(function () {
        connectToBidding();
    });
    $("#send_rate").click(function () {
        sendRate();
    });
});

function sendName() {
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#connectToBidding").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

function connectToBidding() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnectedB(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/field/gold', function (resource) {
            showRate(JSON.parse(resource.body).message);
        });
    });
}

function setConnectedB(connected) {
    $("#connect").prop("disabled", connected);
    $("#connectToBidding").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#bidding").show();
    }
    else {
        $("#bidding").hide();
    }
    $("#rates").html("Starting price: 5");
}

function sendRate() {
    stompClient.send("/app/bidding", {}, JSON.stringify({
        'login': $("#login").val(),
        'password': $("#password").val(), 'rate': $("#rate").val()
    }));
}

function showRate(message) {
    $("#rates").append("<tr><td>" + message + "</td></tr>");
}
