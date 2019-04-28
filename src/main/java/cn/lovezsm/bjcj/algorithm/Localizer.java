package cn.lovezsm.bjcj.algorithm;

import cn.lovezsm.bjcj.data.FingerPrint;
import cn.lovezsm.bjcj.data.GridMap;
import cn.lovezsm.bjcj.entity.LocalizeReturnVal;
import cn.lovezsm.bjcj.utils.AlgorithmUtil;
import cn.lovezsm.bjcj.utils.FileUtil;
import lombok.val;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class Localizer {
    Double[] rssi;
    int k;
    FingerPrint fingerPrint;
    GridMap gridMap;
    LocalizeReturnVal returnVal;
    String mac;
    String vision;
    public Localizer(String mac, Double[] rssi, int k, FingerPrint fingerPrint, GridMap gridMap,String vision) {
        this.mac = mac;
        this.rssi = rssi;
        this.k = k;
        this.fingerPrint = fingerPrint;
        this.gridMap = gridMap;
        this.vision = vision;
    }

    public abstract LocalizeReturnVal doCalculate();

    public String log(Double x, Double y, Long time) {
        StringBuffer content = new StringBuffer();
        Object[] pro = returnVal.getProbCandidate().toArray();
        for (int i = 0; i < pro.length; i++) {
            pro[i] = new BigDecimal((Double) pro[i]).setScale(2, RoundingMode.UP).doubleValue();
        }
        double error = AlgorithmUtil.euclideanDistance(returnVal.getX(), returnVal.getY(), x, y);
        if (Double.isNaN(error) || Double.isInfinite(error)) {
            error = 0d;
        } else {
            error = new BigDecimal(error).setScale(2, RoundingMode.UP).doubleValue();
        }
        error = new BigDecimal(error).setScale(2, RoundingMode.UP).doubleValue();
        content.append(time);
        content.append(",");
        content.append(mac);
        content.append(",");
        content.append(new BigDecimal(returnVal.getX()).setScale(2, RoundingMode.UP).doubleValue());
        content.append(",");
        content.append(new BigDecimal(returnVal.getY()).setScale(2, RoundingMode.UP).doubleValue());
        content.append(",");
        content.append(FileUtil.toString(returnVal.getIdxCandidate().toArray()));
        content.append(",");
        content.append(FileUtil.toString(pro));
        content.append(",");
        content.append(new BigDecimal(Math.abs(x - returnVal.getX())).setScale(2, RoundingMode.UP).doubleValue());
        content.append(",");
        content.append(new BigDecimal(Math.abs(y - returnVal.getY())).setScale(2, RoundingMode.UP).doubleValue());
        content.append(",");
        content.append(error);

        return content.toString();
    }

    ;
}
