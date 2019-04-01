package cn.lovezsm.bjcj.data;

import cn.lovezsm.bjcj.config.APConf;
import cn.lovezsm.bjcj.repository.APConfRepository;
import cn.lovezsm.bjcj.utils.AlgorithmUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.List;


@Component
public class FingerPrint {

    @Autowired
    APConfRepository apConfRepository;

    private String avgPath;
    private String stdPath;

    private List<String> in;
    private List<String> out;

    public String getAvgPath() {
        return avgPath;
    }

    public void setAvgPath(String avgPath) {
        this.avgPath = avgPath;
    }

    public String getStdPath() {
        return stdPath;
    }

    public void setStdPath(String stdPath) {
        this.stdPath = stdPath;
    }

    public List<String> getIn() {
        return in;
    }

    public void setIn(List<String> in) {
        this.in = in;
    }

    public List<String> getOut() {
        return out;
    }

    public void setOut(List<String> out) {
        this.out = out;
    }

    private int apInfoNum;
    public Double[][] avg;
    public Double[][] std;
    int gridNum;
    double[][] posGrid;
    double[] areaGrid;
    double[] posX;
    double[] posY;

    public  Logger logger = LoggerFactory.getLogger(FingerPrint.class);


    public void init(){
        APConf apConf = apConfRepository.findAll().get(0);
        apInfoNum = apConf.getInfo().size();
        gridNum = in.size()+out.size();
        System.out.println("ap数目："+apInfoNum);
        System.out.println("晶格数目:"+gridNum);
        buildFingerPrint(avgPath,stdPath);

        posGrid = new double[gridNum][2];
        areaGrid = new double[gridNum];
        posX = new double[gridNum];
        posY = new double[gridNum];

        for(int i=0;i<in.size();i++){
            String[] split = in.get(i).split("-");
            initParameter(split);
        }
        for(int i=0;i<out.size();i++){
            String[] split = out.get(i).split("-");
            initParameter(split);
        }
    }




    public void buildFingerPrint(String avgPath, String stdPath){
        avg = new Double[gridNum][apInfoNum];
        std = new Double[gridNum][apInfoNum];
        if(avgPath==null||stdPath==null){
            logger.error("指纹库文件路径错误,路径为空");
            return;
        }
        try {
            BufferedReader avgFile = new BufferedReader(new FileReader(avgPath));
            BufferedReader stdFile = new BufferedReader(new FileReader(stdPath));
            logger.info("指纹库创建中..");

            for(int ln = 0;ln<gridNum;ln++){
                setAvgAndStdValue(avgFile, ln, avg);
                double scalingVal = standardAvgMatrix(avg, ln);
                setAvgAndStdValue(stdFile, ln, std);
                standardStdMatrix(std,ln,scalingVal);
            }
            logger.info("指纹库创建成功");

        } catch (FileNotFoundException e) {
            logger.error("指纹库文件路径错误，文件不存在");
        } catch (IOException e) {
            logger.error("数据文件格式错误");
        }
//        logger.info("STD FingerPrintConf:");
//        //打印晶格点:
//        for(int ln = 0;ln<gridNum;ln++){
//            System.out.println(Arrays.toString(std[ln]));
//        }
//
//        logger.info("AVG FingerPrintConf:");
//        for(int ln = 0;ln<gridNum;ln++){
//            System.out.println(Arrays.toString(avg[ln]));
//        }

    }

    private void standardStdMatrix(Double[][] std, int ln, double scalingVal) {
        Double[] vals = std[ln];
        for (int i = 0;i<vals.length;i++){
            if(vals[i]!=null){
                vals[i] = vals[i]/scalingVal;
            }
        }
//        AlgorithmUtils.VectorStandardization(std[ln]);
    }

    private double standardAvgMatrix(Double[][] avg, int ln) {
        double avgVal = AlgorithmUtils.getVectorAvgVal(avg[ln]);
        double sum = 0d;
        int i = 0;
        for(Double f:avg[ln]){
            if(f!=null){
                sum += Math.pow(f-avgVal,2);
                i++;
            }
        }
        if(i==0){
            return 0;
        }
        double scalingVal = Math.sqrt(sum/i);

        AlgorithmUtils.VectorStandardization(avg[ln]);

        return scalingVal;
    }

    private void setAvgAndStdValue(BufferedReader stdFile, int ln, Double[][] std) throws IOException {
        String[] sstd = stdFile.readLine().split(" ");
        for(int i=0;i<apInfoNum;i++){

            std[ln][i] = Double.parseDouble(sstd[i]);

        }
    }


    public Double[][] getAvg() {
        return avg;
    }

    public void setAvg(Double[][] avg) {
        this.avg = avg;
    }

    public Double[][] getStd() {
        return std;
    }

    public void setStd(Double[][] std) {
        this.std = std;
    }

    public int getApInfoNum() {
        return apInfoNum;
    }

    public double[][] getPosGrid() {
        return posGrid;
    }

    public double[] getAreaGrid() {
        return areaGrid;
    }

    public double[] getPosX() {
        return posX;
    }

    public double[] getPosY() {
        return posY;
    }

    private void initParameter(String[] split) {
        int index = Integer.parseInt(split[0])-1;
        posGrid[index][0] = Double.parseDouble(split[1]);
        posGrid[index][1] = Double.parseDouble(split[2]);
        areaGrid[index] = Double.parseDouble(split[5]);
        posX[index] = Double.parseDouble(split[1]);
        posY[index] = Double.parseDouble(split[2]);
    }
    public int getGridNum() {
        return this.gridNum;
    }
}
