package cn.lovezsm.bjcj.data;


import lombok.Data;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;



@Component
@Document
@Data
public class FingerPrint {
    private String name;
    public Double[][] avg;
    public Double[][] std;

    public FingerPrint() {
    }

    public FingerPrint(String name, Double[][] avg, Double[][] std) {
        this.name = name;
        this.avg = avg;
        this.std = std;
    }
}
