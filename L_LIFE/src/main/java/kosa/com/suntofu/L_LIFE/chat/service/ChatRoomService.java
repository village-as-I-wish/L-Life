package kosa.com.suntofu.L_LIFE.chat.service;

import kosa.com.suntofu.L_LIFE.chat.vo.ChatRoomVo;

import java.util.List;

public interface ChatRoomService {
    List<ChatRoomVo> findAllRooms();
    ChatRoomVo findRoomById(int lStreamId);
}
