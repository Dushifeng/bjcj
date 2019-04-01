package cn.lovezsm.bjcj.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

public class Record {
    private String companyName;
    private String protocolControl;
    private String encryption;
    private Long scanTime;
    private Long updateTime;
    private Double[] rssi;
    private String devMac;
    private LocalizeReturnVal localizeReturnVal;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getProtocolControl() {
        return protocolControl;
    }

    public void setProtocolControl(String protocolControl) {
        this.protocolControl = protocolControl;
    }

    public String getEncryption() {
        return encryption;
    }

    public void setEncryption(String encryption) {
        this.encryption = encryption;
    }

    public Long getScanTime() {
        return scanTime;
    }

    public void setScanTime(Long scanTime) {
        this.scanTime = scanTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Double[] getRssi() {
        return rssi;
    }

    public void setRssi(Double[] rssi) {
        this.rssi = rssi;
    }

    public String getDevMac() {
        return devMac;
    }

    public void setDevMac(String devMac) {
        this.devMac = devMac;
    }

    public LocalizeReturnVal getLocalizeReturnVal() {
        return localizeReturnVal;
    }

    public void setLocalizeReturnVal(LocalizeReturnVal localizeReturnVal) {
        this.localizeReturnVal = localizeReturnVal;
    }

    @Override
    public String toString() {
        Double[] out = Arrays.copyOf(rssi, rssi.length);

        for(int i =0;i<out.length;i++){
            if(out[i]!=null)
                out[i] = new BigDecimal(out[i]).setScale(2, RoundingMode.UP).doubleValue();
        }
        return "Record{" +
                "scanTime=" + scanTime +
                ", rssi=" + Arrays.toString(out) +
                ", devMac='" + devMac + '\'' +
                '}';
    }
}
