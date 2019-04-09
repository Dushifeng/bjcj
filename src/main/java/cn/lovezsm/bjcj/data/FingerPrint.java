package cn.lovezsm.bjcj.data;


import lombok.Data;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;



@Component
@Document
@Data
public class FingerPrint {
    private String name;
    private Double[][] avg;
    private Double[][] std;
    private boolean isStandardization;

    public FingerPrint() {
    }

    public FingerPrint(String name, Double[][] avg, Double[][] std,boolean isStandardization) {
        this.name = name;
        this.avg = avg;
        this.std = std;
        this.isStandardization = isStandardization;
    }
}
