package cn.lovezsm.bjcj.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@Data
public class LogConf{

    private String rawPath;
    private String locResultPath;
    private String recordPath;
    private String dicPath;
    private boolean isOpen;
    private int gridId;


}
