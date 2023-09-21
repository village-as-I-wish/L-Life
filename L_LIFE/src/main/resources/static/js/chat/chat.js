$(document).ready(function(){
    var lStreamId = $('#lStreamId').val();
    var memberName = $('#memberName').val();

    var sockJs = new SockJS("/l-life/stomp/chat");
    var stomp = Stomp.over(sockJs);

    stomp.connect({}, function (){
        console.log("STOMP Connection")

        stomp.subscribe("/sub/chat/room/" + lStreamId, function (chat) {
            var content = JSON.parse(chat.body);
            appendChatMessage(content);
        });

        stomp.send('/pub/chat/enter', {}, JSON.stringify({lstreamId: lStreamId, mname: memberName}));
    });

    $("#button-send").on("click", function(e) {
        sendMessage();
    });

    $("#msg").on("keyup", function(e) {
        if (e.key === "Enter") {
            sendMessage();
        }
    });

    function sendMessage() {
        var msg = document.getElementById("msg").value.trim();
        console.log("enter Test");

        if (msg.endsWith("\n")) {
            console.log("enter");
            msg = msg.slice(0, -1);
        }

        var payload = JSON.stringify({lstreamId: lStreamId, message: msg, mname: memberName});
        console.log("Sending payload: ", payload);

        stomp.send('/pub/chat/message', {}, payload);
        document.getElementById("msg").value ='';
    }

    function appendChatMessage(chat) {
        var writer = chat.mname;

        if(writer === memberName){ // 본인 챗
            var str = "<p class='chat'>";
            str += "<span class='chat-user' style='color:blue;'>" + writer + "</span>" + " : " + chat.message + "</p>";
        }
        else { // 타 챗
            var str = "<p class='chat'>";
            str += "<span class='chat-user'>" + writer + "</span>" + " : " + chat.message+ "</p>";
        }
        $("#msgArea").prepend(str);

    }

    // Fetch chat messages from the API and appends them to the chat display.
    function fetchChatMessages() {
        $.ajax({
            url: "https://livart-life.com/l-life/api/v1/livestream/" + lStreamId,
            method: "GET",
            success: function(response) {
                if (response.code === 200 && response.result && Array.isArray(response.result)) {
                    response.result.forEach(function(chat) {
                        appendChatMessage(chat);
                    });
                }

            },
            error: function(err) {
                console.error("Error fetching chat messages:", err);
            }
        });
    }

    // Initial call to fetch and display existing messages
    fetchChatMessages();
//     const scrollableDiv = document.getElementById("msgArea");
//     console.log("scroll", scrollableDiv.scrollHeight);
// // 스크롤이 맨 아래로 내려가도록 설정합니다.
//     scrollableDiv.scrollTop = scrollableDiv.scrollHeight;
});
