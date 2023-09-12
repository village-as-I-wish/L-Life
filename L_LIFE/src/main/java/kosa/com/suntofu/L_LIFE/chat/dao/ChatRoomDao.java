package kosa.com.suntofu.L_LIFE.chat.dao;

import kosa.com.suntofu.L_LIFE.chat.vo.ChatRoomVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatRoomDao {
    ChatRoomVo findRoomById(int lStreamId);
}
