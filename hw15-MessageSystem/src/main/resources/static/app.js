let stompClient = null;

const setConnected = (connected) => {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#userLine").show();
    }
    else {
        $("#userLine").hide();
    }
    $("#user").html("");
}

const connect = () => {
    stompClient = Stomp.over(new SockJS('/gs-guide-websocket'));
    stompClient.connect({}, (frame) => {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/response', (user) => showUser(JSON.parse(user.body)));
    });
}

const disconnect = () => {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

const sendUser = () => stompClient.send("/app/create", {}, JSON.stringify({
'name': $("#name").val(),
'age': $("#age").val(),
'address': $("#address").val(),
'phone': $("#phone").val()
}))


const showUser = (user) => $("#userLine").append("<tr><td>" + user.name + "&nbsp" + user.age + "&nbsp" + user.address + "&nbsp" + user.phone + "</td></tr>")


$(function () {
    $("form").on('submit', (event) => {
        event.preventDefault();
    });
    $("#connect").click(connect);
    $("#disconnect").click(disconnect);
    $("#send").click(sendUser);
});