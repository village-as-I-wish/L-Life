var OV;
var session;
var publisher;
var test_count = 0;

$(document).ready(() => {

    var webComponent = document.querySelector('openvidu-webcomponent');

    // webComponent.addEventListener('onSessionCreated', (event) => {
    //     var session = event.detail;
    // });
    // webComponent.addEventListener('onJoinButtonClicked', (event) => {});
    // webComponent.addEventListener('onToolbarLeaveButtonClicked', (event) => {});
    // webComponent.addEventListener('onToolbarCameraButtonClicked', (event) => {});
    // webComponent.addEventListener('onToolbarMicrophoneButtonClicked', (event) => {});
    // webComponent.addEventListener('onToolbarScreenshareButtonClicked', (event) => {});
    // webComponent.addEventListener('onToolbarParticipantsPanelButtonClicked', (event) => {});
    // webComponent.addEventListener('onToolbarChatPanelButtonClicked', (event) => {});
    // webComponent.addEventListener('onToolbarFullscreenButtonClicked', (event) => {});
    // webComponent.addEventListener('onParticipantCreated', (event) => {});
    // //
    // $('.create-btn-txt').click(function() {
    //     joinSession('admin')
    // });
    //
    // $('.share-btn-txt').click(function() {
    //     joinSession('user')
    // });


});


function joinSession(isAdmin) {
    var mySessionId = "dev-sskong2"
    var myUserName = "sskong"

    // Check if the user is an admin

    // --- 1) Get an OpenVidu object ---

    OV = new OpenVidu();

    // --- 2) Init a session ---

    session = OV.initSession();

    // --- 3) Specify the actions when events take place in the session ---

    // On every new Stream received...
    session.on('streamCreated', event => {

        var subscriber = session.subscribe(event.stream, 'video-container');

        // When the HTML video has been appended to DOM...
        subscriber.on('videoElementCreated', event => {
           //  initMainVideo(event.element, myUserName);
           //
           //  // Add a new <p> element for the user's nickname just below its video
           // appendUserData(event.element, subscriber.stream.connection);
        });
    });

    // On every Stream destroyed...
    session.on('streamDestroyed', event => {

        // Delete the HTML element with the user's nickname. HTML videos are automatically removed from DOM
        removeUserData(event.stream.connection);
    });

    // On every asynchronous exception...
    session.on('exception', (exception) => {
        console.warn(exception);
    });

    // --- 4) Connect to the session with a valid user token ---

    // Get a token from the OpenVidu deployment
    getToken(mySessionId).then(token => {

        // First param is the token got from the OpenVidu deployment. Second param can be retrieved by every user on event
        // 'streamCreated' (property Stream.connection.data), and will be appended to DOM as the user's nickname
        session.connect(token, { clientData: myUserName })
            .then(() => {

                // --- 5) Set page layout for active call ---
                //
                // document.getElementById('session-title').innerText = mySessionId;
                // document.getElementById('join').style.display = 'none';
                // document.getElementById('session').style.display = 'block';

                // --- 6) Get your own camera stream with the desired properties ---
                if(isAdmin == 1){
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

                }else{
                    console.log("testingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtesting")

                }
                // var publisher = OV.initPublisher('video-container', {
                //     audioSource: undefined, // The source of audio. If undefined default microphone
                //     videoSource: undefined, // The source of video. If undefined default webcam
                //     publishAudio: true,     // Whether you want to start publishing with your audio unmuted or not
                //     publishVideo: true,     // Whether you want to start publishing with your video enabled or not
                //     resolution: '640x480',  // The resolution of your video
                //     frameRate: 30,         // The frame rate of your video
                //     insertMode: 'PREPEND',   // How the video is inserted in the target element 'video-container'
                //     mirror: false          // Whether to mirror your local video or not
                // });

                // --- 7) Specify the actions when events take place in our publisher ---

                // When our HTML video has been added to DOM...
                // publisher.on('videoElementCreated', function (event) {
                //     // if (isAdmin !=1) {
                //     //     event.element.style.display = 'none'; // Hide video for non-admin users
                //     // }
                //     if(isAdmin ==1 ){
                //         initMainVideo(event.element, myUserName);
                //         appendUserData(event.element, myUserName);
                //         event.element['muted'] = true;
                //     }else{
                //
                //     }
                //     // initMainVideo(event.element, myUserName);
                //     // appendUserData(event.element, myUserName);
                //
                // });

                // --- 8) Publish your stream ---
                // if(isAdmin ==1){
                //     console.log("Admin 영역 : ", "testing")
                //     session.publish(publisher);
                //
                // }

            })
            .catch(error => {
                console.log('There was an error connecting to the session:', error.code, error.message);
            });
    });
}


//
// async function joinSession(isAdmin) {
//     var mySessionId = 'hong';
//
//     OV = new OpenVidu();
//     session = OV.initSession();
//
//     try {
//         var token = await getToken(mySessionId);
//         session.connect(token, { clientData: isAdmin });
//
//         if (isAdmin === 'admin'){
//             console.log("admin")
//
//             publisher = OV.initPublisher('video-container', {
//                 audioSource: undefined,
//                 videoSource: undefined,
//                 publishAudio: true,
//                 publishVideo: true,
//                 resolution: '640x480',
//                 frameRate: 30,
//                 insertMode: 'PREPEND',
//                 mirror: false
//             });
//
//             publisher.on('videoElementCreated', function (event) {
//                 // 사용자의 비디오를 표시하지 않도록 주석 처리
//                 // document.querySelector('#video-container video').srcObject = event.element.srcObject;
//                 event.element['muted'] = true;
//             });
//
//             session.publish(publisher);
//         }
//
//         else{
//             console.log("user")
//             session.on('streamCreated', event => {
//
//                 // Subscribe to the Stream to receive it. HTML video will be appended to element with 'video-container' id
//                 var subscriber = session.subscribe(event.stream, 'video-container');
//
//                 // When the HTML video has been appended to DOM...
//                 subscriber.on('videoElementCreated', event => {
//
//                     // Add a new <p> element for the user's nickname just below its video
//                     appendUserData(event.element, subscriber.stream.connection);
//                 });
//             });
//             session.on('streamCreated', event => {
//                 console.log("stream connect data" + event.stream.connection.data)
//                 console.log("!!!!!!!!!" + event.stream.connection.data)
//                 if (event.stream.connection.data === 'admin') {
//                     var subscriber = session.subscribe(event.stream, 'video-container');
//                     subscriber.on('videoElementCreated', event => {
//                         event.element['muted'] = true;
//                     });
//                 }
//             });
//         }
//
//
//     } catch (error) {
//         console.log('There was an error:', error);
//     }
// }

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
    var dataNode = document.createElement('div');
    dataNode.className = "data-node";
    dataNode.id = "data-" + nodeId;
    dataNode.innerHTML = "<p>" + userData + "</p>";
    videoElement.parentNode.insertBefore(dataNode, videoElement.nextSibling);
    addClickListener(videoElement, userData);


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
