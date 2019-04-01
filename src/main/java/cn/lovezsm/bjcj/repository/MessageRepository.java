package cn.lovezsm.bjcj.repository;

import cn.lovezsm.bjcj.config.MessageConf;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends MongoRepository<MessageConf, ObjectId> {

}
