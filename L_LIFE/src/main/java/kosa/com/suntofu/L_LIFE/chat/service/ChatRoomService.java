package kosa.com.suntofu.L_LIFE.chat.service;

import kosa.com.suntofu.L_LIFE.chat.vo.ChatRoomVo;

public interface ChatRoomService {
    ChatRoomVo findRoomById(int lStreamId);
}
