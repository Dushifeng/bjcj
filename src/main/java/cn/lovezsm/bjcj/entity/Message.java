package cn.lovezsm.bjcj.entity;

public class Message {
    private String type;
    private String devMac;
    private String frameCtrl;
    private int frequency;
    private String snFirst;
    private String snLast;
    private int rssi;
    private String apMac;
    private Long time;

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getApMac() {
        return apMac;
    }

    public void setApMac(String apMac) {
        this.apMac = apMac;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDevMac() {
        return devMac;
    }

    public void setDevMac(String devMac) {
        this.devMac = devMac;
    }

    public String getFrameCtrl() {
        return frameCtrl;
    }

    public void setFrameCtrl(String frameCtrl) {
        this.frameCtrl = frameCtrl;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getSnFirst() {
        return snFirst;
    }

    public void setSnFirst(String snFirst) {
        this.snFirst = snFirst;
    }

    public String getSnLast() {
        return snLast;
    }

    public void setSnLast(String snLast) {
        this.snLast = snLast;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public Message() {
    }

    public Message(Long time,String type, String devMac, int frequency, int rssi, String apMac) {
        this.time = time;
        this.type = type;
        this.devMac = devMac;
        this.frequency = frequency;
        this.rssi = rssi;
        this.apMac = apMac;
    }

    @Override
    public String toString() {
        return "Message{" +
                "type='" + type + '\'' +
                ", devMac='" + devMac + '\'' +
                ", frameCtrl='" + frameCtrl + '\'' +
                ", frequency=" + frequency +
                ", snFirst='" + snFirst + '\'' +
                ", snLast='" + snLast + '\'' +
                ", rssi=" + rssi +
                ", apMac='" + apMac + '\'' +
                ", time=" + time +
                '}';
    }
}
