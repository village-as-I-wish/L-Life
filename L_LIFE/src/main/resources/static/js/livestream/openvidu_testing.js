var OV;
var session;
var publisher;
var test_count = 0;

$(document).ready(() => {

    var webComponent = document.querySelector('openvidu-webcomponent');

    webComponent.addEventListener('onSessionCreated', (event) => {
        var session = event.detail;
    });
    webComponent.addEventListener('onJoinButtonClicked', (event) => {});
    webComponent.addEventListener('onToolbarLeaveButtonClicked', (event) => {});
    webComponent.addEventListener('onToolbarCameraButtonClicked', (event) => {});
    webComponent.addEventListener('onToolbarMicrophoneButtonClicked', (event) => {});
    webComponent.addEventListener('onToolbarScreenshareButtonClicked', (event) => {});
    webComponent.addEventListener('onToolbarParticipantsPanelButtonClicked', (event) => {});
    webComponent.addEventListener('onToolbarChatPanelButtonClicked', (event) => {});
    webComponent.addEventListener('onToolbarFullscreenButtonClicked', (event) => {});
    webComponent.addEventListener('onParticipantCreated', (event) => {});
});




async function joinSession() {
    var mySessionId = 'test';
    var myUserName = 'sj';

    test_count += 1;
    console.log("test_count", test_count);

    OV = new OpenVidu();
    session = OV.initSession();

    if (test_count <= 1) {
        session.on('streamCreated', event => {
            var subscriber = session.subscribe(event.stream, 'video-container');
            subscriber.on('videoElementCreated', event => {
                appendUserData(event.element, subscriber.stream.connection);
                event.element['muted'] = true;
            });
        });
    }

    try {
        var token = await getToken(mySessionId);
        session.connect(token, { clientData: myUserName });

            publisher = OV.initPublisher('video-container', {
                audioSource: undefined,
                videoSource: undefined,
                publishAudio: true,
                publishVideo: true,
                resolution: '640x480',
                frameRate: 30,
                insertMode: 'PREPEND',
                mirror: false
            });

            publisher.on('videoElementCreated', function (event) {
                initMainVideo(event.element, myUserName);
                event.element['muted'] = true;
            });

            session.publish(publisher);

    } catch (error) {
        console.log('There was an error:', error);
    }
}

function appendUserData(videoElement, connection) {
    var userData;
    var nodeId;
    if (typeof connection === "string") {
        userData = connection;
        nodeId = connection;
    } else {
        userData = JSON.parse(connection.data).clientData;
        nodeId = connection.connectionId;
    }
    var dataNode = document.createElement('div');
    dataNode.className = "data-node";
    dataNode.id = "data-" + nodeId;
    dataNode.innerHTML = "<p>" + userData + "</p>";
    // videoElement.parentNode.insertBefore(dataNode, videoElement.nextSibling);
    // addClickListener(videoElement, userData);
}

var APPLICATION_SERVER_URL = "http://localhost:8080/l-life/";

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
    document.querySelector('#video-container video').srcObject = videoElement.srcObject;
}
