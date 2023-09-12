package kosa.com.suntofu.L_LIFE.chat.repository;

import kosa.com.suntofu.L_LIFE.chat.vo.ChatMessageVo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

// 카프카에서 소비한 메시지를 MongoDB에 저장하기 위한 과정
// 1. MongoDB와의 통신을 위한 Repository 설정
@Repository
public interface MessageRepository extends MongoRepository<ChatMessageVo, String> {
}
