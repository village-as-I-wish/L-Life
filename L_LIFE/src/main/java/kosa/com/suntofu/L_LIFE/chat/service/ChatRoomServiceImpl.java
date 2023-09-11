package kosa.com.suntofu.L_LIFE.chat.service;

import kosa.com.suntofu.L_LIFE.chat.dao.ChatRoomDao;
import kosa.com.suntofu.L_LIFE.chat.vo.ChatRoomVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomDao chatRoomDao;
    @Override
    public List<ChatRoomVo> findAllRooms() {

        return chatRoomDao.findAllRooms();
    }

    @Override
    public ChatRoomVo findRoomById(int lStreamId) {
        return chatRoomDao.findRoomById(lStreamId);
    }
}
