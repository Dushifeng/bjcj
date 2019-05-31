package cn.lovezsm.bjcj.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class LocalizeReturnVal implements Cloneable,Serializable {
    private double x;
    private double y;
    private List<Integer> idxCandidate = new ArrayList<>();
    private List<Double> probCandidate = new ArrayList<>();
    private Long updateTime;
    private String devMac;
    private boolean valid = true;

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    private int frequency;

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public List<Integer> getIdxCandidate() {
        return idxCandidate;
    }

    public void setIdxCandidate(int[] idxCandidate) {
        for(int a:idxCandidate){
            this.idxCandidate.add(a);
        }
    }

    public List<Double> getProbCandidate() {
//        for (int i =0;i<probCandidate.size();i++){
//            probCandidate.set(i,new BigDecimal(probCandidate.get(i)).setScale(2, RoundingMode.UP).doubleValue());
//        }

        return probCandidate;
    }

    public void setProbCandidate(double[] probCandidate) {
        for(double a:probCandidate){
            this.probCandidate.add(a);
        }
    }

    public String getDevMac() {
        return devMac;
    }

    public void setDevMac(String devMac) {
        this.devMac = devMac;
    }

    @Override
    public String toString() {
        return "LocalizeReturnVal{" +
                "x=" + x +
                ", y=" + y +
                ", idxCandidate=" + idxCandidate +
                ", probCandidate=" + probCandidate +
                '}';
    }

    public LocalizeReturnVal clone() {
        LocalizeReturnVal o = null;
        try {
            o = (LocalizeReturnVal) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }
}
