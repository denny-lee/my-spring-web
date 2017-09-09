<%@page isELIgnored="false" %>
<html>
<body>
<h2>dak index!</h2>
<form>
    <input type="text" id="token" value="${_csrf.token}" />
    <input type="text" id="headerName" value="${_csrf.headerName}" />
    <input type="text" id="msg" />
    <input type="button" id="send" value="send" />
    <input type="button" id="connect" value="connect" />
    <input type="button" id="disconnect" value="disconnect" />
    <!--<input type="text" id="ses" />-->
    <!--<input type="button" id="sendToClient" value="sendToClient" />-->
</form>
</body>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script>
<script type="text/javascript" src="/static/js/sockjs.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.js"></script>
<script type="text/javascript">
    var stompClient = null;

    function setConnected(connected) {
        $("#connect").prop("disabled", connected);
        $("#disconnect").prop("disabled", !connected);

    }

    function connect() {
        var socket = new SockJS('/war/dak/msgcenter');
        stompClient = Stomp.over(socket);
        var headers = {};
        headers[$('#headerName').val()] = $('#token').val();
        stompClient.connect(headers, function (frame) {
            setConnected(true);
            console.log('Connected: ' + frame);
            stompClient.subscribe('/user/queue/hello', function (greeting) {
                console.log(greeting);
                showGreeting(JSON.parse(greeting.body).orderNo);
            });
        });
    }
    function showGreeting(message) {
        console.log(message)
    }

    function disconnect() {
        if (stompClient !== null) {
            stompClient.disconnect();
        }
        setConnected(false);
        console.log("Disconnected");
    }

    function sendName() {
        stompClient.send("/app/hello/xxx", {}, JSON.stringify({'orderNo': $("#msg").val(),'orderStatus': $("#msg").val()}));
    }

    function sendToClient() {
        var url = "sendMsg";
        var session = $('#ses').val();
        if (session) {
            url += "?user="+session;
        }
        $.get(url);
    }


    $(function () {

        $( "#connect" ).click(function() { connect(); });
        $( "#disconnect" ).click(function() { disconnect(); });
        $( "#send" ).click(function() { sendName(); });
//        $( "#sendToClient" ).click(function() { sendToClient(); });
    })
</script>
</html>
