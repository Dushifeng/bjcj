package cn.lovezsm.bjcj.entity;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestOutputCase implements Serializable {
    private int fingerPrintNo = 1;
    private int step = 10;
    private int gridId = 0;
    private Pos girdPos;
    private List<Pos> culPoslist;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String startTime;
    private String endTime;
    private float error;
    private String devMac;

    public String getDevMac() {
        return devMac;
    }

    public void setDevMac(String devMac) {
        this.devMac = devMac;
    }

    public float getError() {
        return error;
    }

    public void setError(float error) {
        this.error = error;
    }

    public int getFingerPrintNo() {
        return fingerPrintNo;
    }

    public void setFingerPrintNo(int fingerPrintNo) {
        this.fingerPrintNo = fingerPrintNo;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getGridId() {
        return gridId;
    }

    public void setGridId(int gridId) {
        this.gridId = gridId;
    }

    public Pos getGirdPos() {
        return girdPos;
    }

    public void setGirdPos(Pos girdPos) {
        this.girdPos = girdPos;
    }

    public List<Pos> getCulPoslist() {
        return culPoslist;
    }

    public void setCulPoslist(List<Pos> culPoslist) {
        this.culPoslist = culPoslist;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = dateFormat.format(startTime);
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = dateFormat.format(endTime);
    }

    public TestOutputCase(int fingerPrintNo, int step, int gridId, Pos girdPos, List<Pos> culPoslist, Date startTime, Date endTime,float error,String devMac) {
        this.fingerPrintNo = fingerPrintNo;
        this.step = step;
        this.gridId = gridId;
        this.girdPos = girdPos;
        this.culPoslist = culPoslist;
        this.startTime = dateFormat.format(startTime);
        this.endTime = dateFormat.format(endTime);
        this.error = error;
        this.devMac = devMac;
    }

    public TestOutputCase() {
    }
}
