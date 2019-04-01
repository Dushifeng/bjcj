package cn.lovezsm.bjcj.repository;

import cn.lovezsm.bjcj.config.LogConf;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends MongoRepository<LogConf, ObjectId> {
}
