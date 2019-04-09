package cn.lovezsm.bjcj.entity;

import lombok.Data;

@Data
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
