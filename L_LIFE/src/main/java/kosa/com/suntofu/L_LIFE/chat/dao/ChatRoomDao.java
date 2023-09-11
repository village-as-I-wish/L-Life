package kosa.com.suntofu.L_LIFE.chat.dao;

import kosa.com.suntofu.L_LIFE.chat.vo.ChatRoomVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChatRoomDao {
    List<ChatRoomVo> findAllRooms();
    ChatRoomVo findRoomById(int lStreamId);
}
