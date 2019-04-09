package cn.lovezsm.bjcj.service;

import cn.lovezsm.bjcj.algorithm.LocalizeByFingerPrint;

import cn.lovezsm.bjcj.utils.DataUtil;
import cn.lovezsm.bjcj.data.FingerPrint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LocationService {
    @Autowired
    LocalizeByFingerPrint lbfp;
    @Autowired
    DataUtil dataUtil;


}
