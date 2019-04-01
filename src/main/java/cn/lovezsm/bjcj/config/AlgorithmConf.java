package cn.lovezsm.bjcj.config;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.io.Serializable;


@Document
@Data
public class AlgorithmConf{

    @Id
    private ObjectId id;

    private int k;
    private int sliding_window_time;
    private int sliding_step_time;
}
