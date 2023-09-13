$(document).ready(function(){
    var lStreamId = $('#lStreamId').val();
    var memberName = $('#memberName').val();

    var sockJs = new SockJS("/l-life/stomp/chat");
    //1. SockJS를 내부에 들고있는 stomp를 내어줌
    var stomp = Stomp.over(sockJs);

    //2. connection이 맺어지면 실행
    stomp.connect({}, function (){
        console.log("STOMP Connection")

        //4. subscribe(path, callback)으로 메세지를 받을 수 있음
        stomp.subscribe("/sub/chat/room/" + lStreamId, function (chat) {
            var content = JSON.parse(chat.body);

            var writer = content.mname;

            //로그인 한 클라이언트와 타 클라이언트를 분류하기 위함
            if(writer === memberName){ // 본인 챗
                var str = "<p class='chat'>";
                str += "<span class='chat-user' style='color:blue;'>" + writer + "</span>" + " : " + content.message + "</p>";
                $("#msgArea").append(str);
            }
            else{ // 타 챗
                var str = "<p class='chat'>";
                str += "<span class='chat-user'>" + writer + "</span>" + " : " + content.message+ "</p>";
                $("#msgArea").append(str);
            }
        });

        //3. send(path, header, message)로 메세지를 보낼 수 있음
        stomp.send('/pub/chat/enter', {}, JSON.stringify({lStreamId: lStreamId, mName: memberName}))
    });

    $("#button-send").on("click", function(e) {
        sendMessage();
    });

    // 엔터 키 이벤트 처리
    $("#msg").on("keyup", function(e) {
        if (e.key === "Enter") {
            sendMessage();
        }
    });

    function sendMessage() {
        var msg = document.getElementById("msg");

        var payload = JSON.stringify({lstreamId: lStreamId, message: msg.value, mname: memberName});
        console.log("Sending payload: ", payload);

        stomp.send('/pub/chat/message', {}, payload);
        msg.value = '';
    }
});