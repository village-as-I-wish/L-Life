$(document).ready(function(){
    var lStreamId = $('#lStreamId').val();
    var memberName = $('#memberName').val();

    console.log("확인해보기" + lStreamId);

    var sockJs = new SockJS("/l-life/stomp/chat");
    //1. SockJS를 내부에 들고있는 stomp를 내어줌
    var stomp = Stomp.over(sockJs);

    //2. connection이 맺어지면 실행
    stomp.connect({}, function (){
        console.log("STOMP Connection")

        //4. subscribe(path, callback)으로 메세지를 받을 수 있음
        stomp.subscribe("/sub/chat/room/" + lStreamId, function (chat) {
            var content = JSON.parse(chat.body);

            var writer = content.writer;
            var str = '';

                //로그인 한 클라이언트와 타 클라이언트를 분류하기 위함
                if(writer === memberName){ // 본인 챗
                    str = "<p class='chat'>";
                    str += "<span class='chat-user' style='color:blue;'>" + writer + "</span>" + " : " + content + "</p>";
                    $("#msgArea").append(str);
                }
                else{ // 타 챗
                    str = "<p class='chat'>";
                    str += "<span class='chat-user'>" + writer + "</span>" + " : " + content + "</p>";
                    $("#msgArea").append(str);
                }

            $("#msgArea").append(str);
        });

        //3. send(path, header, message)로 메세지를 보낼 수 있음
        stomp.send('/pub/chat/enter', {}, JSON.stringify({liveStreamId: lStreamId, writer: memberName}))
    });

    $("#button-send").on("click", (e) => {
        var msg = document.getElementById("msg");

        console.log(memberName + ":" + msg.value);
        stomp.send('/pub/chat/message', {}, JSON.stringify({liveStreamId: lStreamId, message: msg.value, writer: memberName}));
        msg.value = '';
    });
});
