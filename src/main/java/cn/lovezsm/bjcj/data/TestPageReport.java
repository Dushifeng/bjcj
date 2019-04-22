package cn.lovezsm.bjcj.data;

import cn.lovezsm.bjcj.entity.Message;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.data.annotation.Transient;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Data
public class TestPageReport {
    private int id;
    private String mac;
    private int allRawNum;
    private int allMessageNum;
    private int emptyMessageNum;
    private int independentMacNum;
    private Map<String,Integer> channelCount = new HashMap<>();


    @JSONField(serialize = false)
    @Transient
    private List<Message> messages = new ArrayList<>();
}
