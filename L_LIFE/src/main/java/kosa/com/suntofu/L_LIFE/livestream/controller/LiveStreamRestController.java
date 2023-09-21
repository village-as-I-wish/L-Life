package kosa.com.suntofu.L_LIFE.livestream.controller;


import kosa.com.suntofu.L_LIFE.chat.service.MessageService;
import kosa.com.suntofu.L_LIFE.chat.vo.ChatMessageVo;
import kosa.com.suntofu.L_LIFE.common.util.CartReturn;
import kosa.com.suntofu.L_LIFE.common.vo.BasicResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.openvidu.java.client.Connection;
import io.openvidu.java.client.ConnectionProperties;
import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.openvidu.java.client.Session;
import io.openvidu.java.client.SessionProperties;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
public class LiveStreamRestController {

    @Value("${OPENVIDU_URL}")
    private String OPENVIDU_URL;

    @Value("${OPENVIDU_SECRET}")
    private String OPENVIDU_SECRET;

    private OpenVidu openvidu;
    private final MessageService messageService;

    @PostConstruct
    public void init() {
        this.openvidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
    }

    /**
     * @param params The Session properties
     * @return The Session ID
     */
    @PostMapping("/api/sessions")
    public ResponseEntity<String> initializeSession(@RequestBody(required = false) Map<String, Object> params)
            throws OpenViduJavaClientException, OpenViduHttpException {
        System.out.println("initalize session controller");
        SessionProperties properties = SessionProperties.fromJson(params).build();
        Session session = openvidu.createSession(properties);
        return new ResponseEntity<>(session.getSessionId(), HttpStatus.OK);
    }

    /**
     * @param sessionId The Session in which to create the Connection
     * @param params    The Connection properties
     * @return The Token associated to the Connection
     */
    @PostMapping("/api/sessions/{sessionId}/connections")
    public ResponseEntity<String> createConnection(@PathVariable("sessionId") String sessionId,
                                                   @RequestBody(required = false) Map<String, Object> params)
            throws OpenViduJavaClientException, OpenViduHttpException {
        Session session = openvidu.getActiveSession(sessionId);
        if (session == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ConnectionProperties properties = ConnectionProperties.fromJson(params).build();
        Connection connection = session.createConnection(properties);
        return new ResponseEntity<>(connection.getToken(), HttpStatus.OK);
    }

    @GetMapping("/api/v1/livestream/{lStreamId}")
    public ResponseEntity<BasicResponse> findBylStreamId(@PathVariable int lStreamId) {
        List<ChatMessageVo> chats =  messageService.findBylStreamId(lStreamId);
        log.info("chat from mongo {} ", chats);
        return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(200).message("채팅 목록").result(CartReturn.CART_ADD_SUCCESS).result(chats).build(), HttpStatus.OK);
    }
}
