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
    //Getting form inputvalue
    var mySessionId = 'test' // document.getElementById("sessionId").value;
    var myUserName = 'sj' // document.getElementById("userName").value;

    // var sessionName = document.getElementById('sessionName').value;
    // var participantName = document.getElementById('user').value;

    OV = new OpenVidu();
    session = OV.initSession();


    session.on('streamCreated', event => {

        // Subscribe to the Stream to receive it. HTML video will be appended to element with 'video-container' id
        var subscriber = session.subscribe(event.stream, 'video-container');

        // When the HTML video has been appended to DOM...
        subscriber.on('videoElementCreated', event => {

            // Add a new <p> element for the user's nickname just below its video
            appendUserData(event.element, subscriber.stream.connection);
        });
    });
    getToken(mySessionId).then(token => {

        // First param is the token got from the OpenVidu deployment. Second param can be retrieved by every user on event
        // 'streamCreated' (property Stream.connection.data), and will be appended to DOM as the user's nickname
        session.connect(token, { clientData: myUserName })
            .then(() => {

                // --- 5) Set page layout for active call ---

                // document.getElementById('session-title').innerText = mySessionId;
                // document.getElementById('join').style.display = 'none';
                // document.getElementById('session').style.display = 'block';

                // --- 6) Get your own camera stream with the desired properties ---

                var publisher = OV.initPublisher('video-container', {
                    audioSource: undefined, // The source of audio. If undefined default microphone
                    videoSource: undefined, // The source of video. If undefined default webcam
                    publishAudio: true,  	// Whether you want to start publishing with your audio unmuted or not
                    publishVideo: true,  	// Whether you want to start publishing with your video enabled or not
                    resolution: '640x480',  // The resolution of your video
                    frameRate: 30,			// The frame rate of your video
                    insertMode: 'PREPEND',	// How the video is inserted in the target element 'video-container'
                    mirror: false       	// Whether to mirror your local video or not
                });

                // --- 7) Specify the actions when events take place in our publisher ---



                // 분기 처리 -> Sub / Pub
                publisher.on('videoElementCreated', function (event) {
                      initMainVideo(event.element, myUserName); // subscriber ?
                     // appendUserData(event.element, myUserName); // 생성하는 애
                    event.element['muted'] = true;
                });

                // --- 8) Publish your stream ---

                session.publish(publisher);

            })
            .catch(error => {
                console.log('There was an error connecting to the session:', error.code, error.message);
            });
    });
    // // Requesting tokens
    // var promiseResults = await Promise.all([getToken(sessionName), getToken(sessionName)]);
    // var tokens = {webcam: promiseResults[0], screen: promiseResults[1]};
    //
    // //Getting the webcomponent element
    // var webComponent = document.querySelector('openvidu-webcomponent');
    //
    // hideForm();
    //
    // // Displaying webcomponent
    // webComponent.style.display = 'block';
    //
    // // Setting up our name and tokens
    // webComponent.participantName = participantName;
    // webComponent.tokens = tokens;
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
