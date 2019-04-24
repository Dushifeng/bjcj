package cn.lovezsm.bjcj;

import cn.lovezsm.bjcj.config.APConf;
import cn.lovezsm.bjcj.config.AlgorithmConf;
import cn.lovezsm.bjcj.entity.AP;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DsfApplicationTests {

    @Test
    public void contextLoads() {

    }
    private static final Logger log= LoggerFactory.getLogger(DsfApplicationTests.class);

//    @Autowired
//    AlgorithmConf algorithmConf;
//    @Autowired
//    APConf apConf;
//    @Test
//    public void testMongo(){
//        System.out.println("ok");
//        APConf apconf = new APConf();
//        List<AP> aps = new ArrayList<>();
//        aps.add(new AP("70476002A30B",35,6.3,0));
//        aps.add(new AP("70476002A30C",39,9.3,1));
//        aps.add(new AP("70476002A30D",33,16.3,2));
//        aps.add(new AP("70476002A30E",17,14.3,3));
//        aps.add(new AP("70476002A30F",22,19.3,4));
//        aps.add(new AP("70476002A30A",27,13.8,5));
//        apconf.setInfo(aps);

//    }

    @Autowired
    MongoOperations operations;

    @Test
    public void testMongo1(){
        Map<String, String> map1 = new HashMap<>();
        map1.put("444", "1123");
        map1.put("555", "4553");
        Document document = new Document(new BasicDBObject(map1));
        MongoCollection<Document> collection = operations.getCollection("123");
        collection.insertOne(document);
    }
    @Test
    public void testMongo2(){
        MongoCollection<Document> collection = operations.getCollection("123");
        FindIterable<Document> documents = collection.find();
        for(Document document:documents){
            for (Map.Entry entry:document.entrySet()){
                if(entry.getKey().equals("_id")){
                    continue;
                }
                System.out.println(entry.getKey()+"  "+entry.getValue());
            }
        }

    }

}
