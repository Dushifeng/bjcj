package cn.lovezsm.bjcj.service;

import cn.lovezsm.bjcj.algorithm.CalculateDeviceDensity;
import cn.lovezsm.bjcj.algorithm.LocalizeByFingerPrint;

import cn.lovezsm.bjcj.utils.DataUtils;
import cn.lovezsm.bjcj.data.FingerPrint;
import cn.lovezsm.bjcj.entity.CalculateDeviceDensityReturnVal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LocationService {
    @Autowired
    LocalizeByFingerPrint lbfp;
    @Autowired
    DataUtils dataUtils;

    @Autowired
    FingerPrint fingerPrint;

}
