package cn.lovezsm.bjcj.repository;

import cn.lovezsm.bjcj.config.APConf;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface APConfRepository extends MongoRepository<APConf, ObjectId> {
}
