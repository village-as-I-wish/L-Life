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
            str += "<span class='chat-user' style='color:blue;'>" + writer + "</span>" + "   " + chat.message + "</p>";
        }
        else { // 타 챗
            var str = "<p class='chat'>";
            str += "<span class='chat-user'>" + writer + "</span>" + "   " + chat.message+ "</p>";
        }
        $("#msgArea").prepend(str);

    }

    // Fetch chat messages from the API and appends them to the chat display.
    function fetchChatMessages() {
        $.ajax({
            url: "http://localhost:8080/l-life/api/v1/livestream/" + lStreamId,
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

    // 1. 사전 정의된 사용자 및 메시지
    const users = ["김설희", "김우원", "노유민", "김진우", "박보선", "서명현", "송민진", "양석진", "오혁진", "이지호", "장서윤", "조상원", "조재룡", "최승열", "최시언", "홍진욱"];
    const messages = [
        "안녕하세요!",
        "지난번에 이 제품 구독 했었는데 너무 좋았어요.",
        "라이브방송 너무 좋아요!",
        "패키지에 어떤 가구들이 포함되나요?",
        "안녕하세요~~~!",
        "이번에 이사가려고 하는데 패키지 구매 딱 좋겠어요! 🏠",
        "배송이나 설치는 어떻게 되는 건가요?",
        "코인 할인 외에 다른 프로모션은 없나요?",
        "패키지 구성품의 색상이나 디자인 변경도 가능한가요?",
        "오늘 주문하면 언제쯤 배송받을 수 있나요?",
        "환불이나 교환 정책은 어떻게 되나요?",
        "코인 할인 이벤트는 언제까지인가요?",
        "우와 소파 편해보인다..",
        "핑크색 내 원픽!",
        "패키지 구매로 코인 할인이라니 좋은 아이디어네요!",
        "가구구독 너무 좋은 서비스인 것 같아요"
    ];
    const md= ["리바트MD"];
    const md_messages = [
        "🛍️퀄리티 높은 가구 패키지 + 코인 1개 할인! 지금 바로 구매하세요!🛍️",
        "🚀최고의 딜! 패키지 구매하면 코인 1개 할인!🚀",
        "❤️품격 있게 집 꾸미기! 패키지 구매시 코인 할인 받으세요!❤️",
        "🏡드림 홈을 만드세요! 패키지 구매시 코인 할인 받아가세요!🏡",
        "⏰시간 제한! 패키지 구매시 코인 1개 할인 혜택!⏰",
        "📣한정 수량! 패키지 구매하면 코인 1개를 할인해드려요!📣"
    ];

    // 2. 매크로 기능
    let chatCount = 0;

    function chatMacro() {
        const isMdMessage = Math.random() <= 0.2; // 20% 확률로 MD 메시지 출력

        if (isMdMessage) {
            const randomMdMessage = md_messages[Math.floor(Math.random() * md_messages.length)];
            addChatMessage(md[0], randomMdMessage);
        } else {
            const randomUser = users[Math.floor(Math.random() * users.length)];
            const randomMessage = messages[Math.floor(Math.random() * messages.length)];
            addChatMessage(randomUser, randomMessage);
        }

        chatCount++;
    }

    // 채팅 메시지 추가 함수 (이 부분은 기존의 appendChatMessage와 유사한 기능을 합니다.)
    function addChatMessage(username, message) {
        var str = "<p class='chat'>";
        if(username === "리바트MD") {
            str += "<span class='chat-user md-color'>" + username + "</span>" + "   " + "<span class='md-color'>" + message + "</span>" + "</p>";
        } else {
            str += "<span class='chat-user'>" + username + "</span>" + "   " + message+ "</p>";
        }
        $("#msgArea").prepend(str);
    }

    // 매 0.4초마다 채팅 매크로 실행
    setInterval(chatMacro, 400);
});
