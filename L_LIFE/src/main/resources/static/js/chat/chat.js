$(document).ready(function(){
    var roomId = $('#roomId').val();
    var roomName = '${chat.lStreamName}';
    var username = $('#memberName').val();

    var sockJs = new SockJS("/l-life/stomp/chat");
    //1. SockJS를 내부에 들고있는 stomp를 내어줌
    var stomp = Stomp.over(sockJs);

    //2. connection이 맺어지면 실행
    stomp.connect({}, function (){
        console.log("STOMP Connection")

        //4. subscribe(path, callback)으로 메세지를 받을 수 있음
        stomp.subscribe("/sub/chat/room/" + roomId, function (chat) {
            var content = JSON.parse(chat.body);

            var writer = content.writer;

                //로그인 한 클라이언트와 타 클라이언트를 분류하기 위함
                if(writer === username){ // 본인 챗
                    var str = "<p class='chat'>";
                    str += "<span class='chat-user' style='color:blue;'>" + writer + "</span>" + " : " + message + "</p>";
                    $("#msgArea").append(str);
                }
                else{ // 타 챗
                    var str = "<p class='chat'>";
                    str += "<span class='chat-user'>" + writer + "</span>" + " : " + message + "</p>";
                    $("#msgArea").append(str);
                }
        });

        //3. send(path, header, message)로 메세지를 보낼 수 있음
        stomp.send('/pub/chat/enter', {}, JSON.stringify({roomId: roomId, writer: username}))
    });

    $("#button-send").on("click", (e) => {
        var msg = document.getElementById("msg");

        console.log(username + ":" + msg.value);
        stomp.send('/pub/chat/message', {}, JSON.stringify({roomId: roomId, message: msg.value, writer: username}));
        msg.value = '';
    });

    // sockJs.onmessage = onMessage;
    // sockJs.onopen = onOpen;
    // sockJs.onclose = onClose;
    //
    // function send() {
    //     let msg = document.getElementById("msg");
    //
    //     console.log(username + ":" + msg.value);
    //     sockJs.send(username + ":" + msg.value);
    //
    //     msg.value = '';
    //
    // }
    //
    // //채팅창에서 나갔을 때
    // function onClose(evt) {
    //     var str = username + ": 님이 방을 나가셨습니다.";
    //     sockJs.send(str);
    // }
    //
    // //채팅창에 들어왔을 때
    // function onOpen(evt) {
    //     var str = username + ": 님이 입장하셨습니다.";
    //     sockJs.send(str);
    // }
    //
    // function onMessage(msg) {
    //     var data = msg.data;
    //     var sessionId = null;
    //     //데이터를 보낸 사람
    //     var message = null;
    //     var arr = data.split(":");
    //     // var username = parseInt($('#memberName').val());
    //
    //     for(var i=0; i<arr.length; i++){
    //         console.log('arr[' + i + ']: ' + arr[i]);
    //     }
    //
    //     var cur_session = username;
    //
    //     //현재 세션에 로그인 한 사람
    //     console.log("cur_session : " + cur_session);
    //     sessionId = arr[0];
    //     message = arr[1];
    //
    //     console.log("sessionID : " + sessionId);
    //     console.log("cur_session : " + cur_session);
    //
    //     //로그인 한 클라이언트와 타 클라이언트를 분류하기 위함
    //     if(sessionId === cur_session){ // 본인 챗
    //         var str = "<p class='chat'>";
    //         str += "<span class='chat-user' style='color:blue;'>" + sessionId + "</span>" + " : " + message + "</p>";
    //         $("#msgArea").append(str);
    //     }
    //     else{ // 타 챗
    //         var str = "<p class='chat'>";
    //         str += "<span class='chat-user'>" + sessionId + "</span>" + " : " + message + "</p>";
    //         $("#msgArea").append(str);
    //     }
    // }
});
