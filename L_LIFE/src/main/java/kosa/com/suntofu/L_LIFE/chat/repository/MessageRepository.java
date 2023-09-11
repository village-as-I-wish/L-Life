package kosa.com.suntofu.L_LIFE.chat.repository;

import kosa.com.suntofu.L_LIFE.chat.vo.MessageVo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends MongoRepository<MessageVo, String> {
}
