package cn.lovezsm.bjcj.config;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;

@Data
@Document
public class MessageConf {

    //    private FloorPlan<String,String> type;
    private int rssiMin;
    private List<String> macAllow = new ArrayList<>();
    private List<String> macRefuse = new ArrayList<>();


}
