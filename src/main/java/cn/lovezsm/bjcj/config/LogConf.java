package cn.lovezsm.bjcj.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;


@Component
@Scope("singleton")
@ConfigurationProperties("log")
public class LogConf{

    private String rawPath;
    private String locResultPath;
    private String recordPath;
    private String dicPath;
    private boolean isOpen;
    private int gridId;

    public String getRawPath() {
        return rawPath;
    }

    public void setRawPath(String rawPath) {
        this.rawPath = rawPath;
    }

    public String getLocResultPath() {
        return locResultPath;
    }

    public void setLocResultPath(String locResultPath) {
        this.locResultPath = locResultPath;
    }

    public String getRecordPath() {
        return recordPath;
    }

    public void setRecordPath(String recordPath) {
        this.recordPath = recordPath;
    }

    public String getDicPath() {
        return dicPath;
    }

    public void setDicPath(String dicPath) {
        this.dicPath = dicPath;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public int getGridId() {
        return gridId;
    }

    public void setGridId(int gridId) {
        this.gridId = gridId;
    }
}
