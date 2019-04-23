package cn.lovezsm.bjcj.algorithm;

import cn.lovezsm.bjcj.data.FingerPrint;
import cn.lovezsm.bjcj.data.GridMap;
import cn.lovezsm.bjcj.entity.LocalizeReturnVal;

public interface Localizer {
    LocalizeReturnVal doCalculate(Double[] rssi, int k, FingerPrint fingerPrint, GridMap gridMap);
}
