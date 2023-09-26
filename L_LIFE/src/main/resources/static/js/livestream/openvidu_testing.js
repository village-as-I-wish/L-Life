var OV;
var session;
var publisher;
var test_count = 0;

$(document).ready(() => {

    var webComponent = document.querySelector('openvidu-webcomponent');


});

function closeSession(){
    session.disconnect()

    Swal.fire({
        title: '방송이 종료되었습니다.',
        text: '이전 화면으로 돌아갑니다.',
    })

    $('.livestream-sharebtn').attr('disabled', true);
    $('.livestream-sharebtn').css("background-color", "#db0d36");
    $('.share-btn-txt').text("방송 종료")

}

function joinSession(isAdmin) {
    console.log($('#openSession'))
    $('#openSession').removeAttr('onclick')

    $('#openSession').on("click", closeSession);
    $('.share-btn-txt').text("방송 종료하기");

    var mySessionId = "livestream" + lStreamId
    var myUserName = $('#existingMember').text()

    // Check if the user is an admin

    // --- 1) Get an OpenVidu object ---

    OV = new OpenVidu();

    // --- 2) Init a session ---

    session = OV.initSession();

    // --- 3) Specify the actions when events take place in the session ---

    // On every new Stream received...
    session.on('streamCreated', event => {

        var subscriber = session.subscribe(event.stream, 'video-container');
        var videoContainer = document.querySelector('.video-container-inner');

        // 요소가 존재하면 숨깁니다.
        if (videoContainer) {
            videoContainer.style.display = 'none';
        }
        // When the HTML video has been appended to DOM...
        subscriber.on('videoElementCreated', event => {

        });
    });

    // On every Stream destroyed...
    session.on('streamDestroyed', event => {
        console.log("사용자 세션 끊김!")
        Swal.fire({
            title: '방송이 종료되었습니다.',
            text: '이전 화면으로 돌아갑니다.',
            // imageUrl: baseUrl + '/l-life/img/header/logo_l_life_b.png',
        }).then((result) => {
            if (result.isConfirmed) {
                window.location.href = "https://livart-life.com/l-life/standard/main";
            }
        })
        removeUserData(event.stream.connection);


    });

    // On every asynchronous exception...
    session.on('exception', (exception) => {
        console.warn(exception);
    });

    // --- 4) Connect to the session with a valid user token ---
    if (isAdmin == 1) {

        // Get a token from the OpenVidu deployment
        getToken(mySessionId).then(token => {

            // First param is the token got from the OpenVidu deployment. Second param can be retrieved by every user on event
            // 'streamCreated' (property Stream.connection.data), and will be appended to DOM as the user's nickname
            session.connect(token, {clientData: myUserName})
                .then(() => {

                    // --- 5) Set page layout for active call ---


                    // --- 6) Get your own camera stream with the desired properties ---
                    if (isAdmin == 1) {
                        var publisher = OV.initPublisher('video-container', {
                            audioSource: undefined, // The source of audio. If undefined default microphone
                            videoSource: undefined, // The source of video. If undefined default webcam
                            publishAudio: true,     // Whether you want to start publishing with your audio unmuted or not
                            publishVideo: true,     // Whether you want to start publishing with your video enabled or not
                            resolution: '640x480',  // The resolution of your video
                            frameRate: 30,         // The frame rate of your video
                            insertMode: 'PREPEND',   // How the video is inserted in the target element 'video-container'
                            mirror: false          // Whether to mirror your local video or not
                        });

                        session.publish(publisher);
                        appendUserData(event.element, myUserName);

                    } else {
                        console.log("testingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtesting")

                    }

                })
                .catch(error => {
                    console.log('There was an error connecting to the session:', error.code, error.message);
                });

        });
    } else {
        createToken(mySessionId).then(token => {

            $('.livestream-sharebtn').attr('disabled', true);
            $('.livestream-sharebtn').css("background-color", "#db0d36");
            $('.share-btn-txt2').text("ON-AIR")

            // First param is the token got from the OpenVidu deployment. Second param can be retrieved by every user on event
            // 'streamCreated' (property Stream.connection.data), and will be appended to DOM as the user's nickname
            session.connect(token, {clientData: myUserName})
                .then(() => {

                });

        });
    }

}


function appendUserData(videoElement, connection) {
    console.log("new ")
    var userData;
    var nodeId;
    if (typeof connection === "string") {
        userData = connection;
        nodeId = connection;
    } else {
        userData = JSON.parse(connection.data).clientData;
        nodeId = connection.connectionId;
    }
    var videoContainer = document.querySelector('.video-container-inner');

    // 요소가 존재하면 숨깁니다.
    if (videoContainer) {
        videoContainer.style.display = 'none';
    }
    var dataNode = document.createElement('div');
    dataNode.className = "data-node";
    dataNode.id = "data-" + nodeId;
    dataNode.innerHTML = "<p>" + userData + "</p>";
    videoElement.parentNode.insertBefore(dataNode, videoElement.nextSibling);
    addClickListener(videoElement, userData);


}

var APPLICATION_SERVER_URL = "https://livart-life.com/l-life/";

function getToken(mySessionId) {
    return createSession(mySessionId).then(sessionId => createToken(sessionId));
}

function createSession(sessionId) {
    return new Promise((resolve, reject) => {
        $.ajax({
            type: "POST",
            url: APPLICATION_SERVER_URL + "api/sessions",
            data: JSON.stringify({ customSessionId: sessionId }),
            headers: { "Content-Type": "application/json" },
            success: response => resolve(response), // The sessionId
            error: (error) => reject(error)
        });
    });
}

function createToken(sessionId) {
    return new Promise((resolve, reject) => {
        $.ajax({
            type: 'POST',
            url: APPLICATION_SERVER_URL + 'api/sessions/' + sessionId + '/connections',
            data: JSON.stringify({}),
            headers: { "Content-Type": "application/json" },
            success: (response) => resolve(response), // The token
            error: (error) => reject(error)
        });
    });
}
function initMainVideo(videoElement, userData) {
    // 비디오 들어오는 코드 -> 버튼 바꾸기 필요
    var videoContainer = document.querySelector('.video-container-inner');

    // 요소가 존재하면 숨깁니다.
    if (videoContainer) {
        videoContainer.style.display = 'none';
    }
    document.querySelector('#video-container video').srcObject = videoElement.srcObject;
}
