package cn.lovezsm.bjcj.config;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;


@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlgorithmConf {
    private String id;
    private int k;
    private int sliding_window_time;
    private int sliding_step_time;
    private int notNullValMinCount;
    private int offsetNotNullVal;
    private boolean offsetOpen;
}
