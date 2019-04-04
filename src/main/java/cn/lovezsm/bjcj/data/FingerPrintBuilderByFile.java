package cn.lovezsm.bjcj.data;

import cn.lovezsm.bjcj.utils.AlgorithmUtils;
import cn.lovezsm.bjcj.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Scanner;

public class FingerPrintBuilderByFile extends FingerPrintBuilder{
   private String name;
   private File avgFile;
   private File stdFile;
   private int apNum;
    @Override
    public FingerPrint build(boolean isStandardization) {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.info("开始建立指纹库");

        FingerPrint fingerPrint =null;
        try {
            int gridNum = FileUtil.getTotalLines(avgFile);
            Double avg[][] = new Double[gridNum][apNum];
            Double std[][] = new Double[gridNum][apNum];
            BufferedReader avgBufferedReader = new BufferedReader(new FileReader(avgFile));
            BufferedReader stdBufferedReader = new BufferedReader(new FileReader(stdFile));
            Scanner avgSc = new Scanner(avgFile);
            Scanner stdSc = new Scanner(stdFile);
            for(int ln = 0;ln<gridNum;ln++){
                for(int j = 0;j<apNum;j++){
                    avg[ln][j] = avgSc.nextDouble();
                    std[ln][j] = stdSc.nextDouble();
                }
            }

            if(isStandardization == true){
                for(int ln = 0;ln<gridNum;ln++){
                    double scalingVal = standardAvgMatrix(avg, ln);
                    standardStdMatrix(std,ln,scalingVal);
                }
            }

            fingerPrint = new FingerPrint(name,avg,std);
            logger.info("指纹库创建成功");

        } catch (FileNotFoundException e) {
            logger.error("指纹库文件路径错误，文件不存在");
        } catch (IOException e) {
            logger.error("数据文件格式错误");
        }
        return fingerPrint;
    }

    public FingerPrintBuilderByFile(String name, File avgFile, File stdFile, int apNum) {
        this.name = name;
        this.avgFile = avgFile;
        this.stdFile = stdFile;
        this.apNum = apNum;
    }
}
