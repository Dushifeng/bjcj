package cn.lovezsm.bjcj.repository;


import cn.lovezsm.bjcj.config.AlgorithmConf;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlgorithmRepository extends MongoRepository<AlgorithmConf, ObjectId> {
}
