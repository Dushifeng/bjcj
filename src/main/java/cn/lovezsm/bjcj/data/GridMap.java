package cn.lovezsm.bjcj.data;

import cn.lovezsm.bjcj.utils.FileUtil;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

@Data
public class GridMap {

    private String name;
    private double[] posW;
    private double[] posH;
    private double[] posX;
    private double[] posY;

    private int gridNum;

    /**
     *     - 1-34-6.3-1-1
     * @param file
     * @return
     */
    public static GridMap buildByFile(File file, String name){
        GridMap gridMap = null;
        Logger logger = LoggerFactory.getLogger(GridMap.class);
        try {
            int gridNum = FileUtil.getTotalLines(file);
            double[] posW = new double[gridNum];
            double[] posH = new double[gridNum];
            double[] posX = new double[gridNum];
            double[] posY = new double[gridNum];
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);


            for(int i =0;i<gridNum;i++){
                String line = reader.readLine();
                String[] split = line.trim().split("-");
                if(split.length<5){
                    logger.error("文件数据有误，行参数不足5个");
                    return null;
                }
                int index = Integer.parseInt(split[0])-1;
                posX[index] = Double.parseDouble(split[1]);
                posY[index] = Double.parseDouble(split[2]);
                posW[index] = Double.parseDouble(split[3]);
                posH[index] = Double.parseDouble(split[4]);
            }
            gridMap = new GridMap(name,posW,posH,posX,posY,gridNum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridMap;
    }

    private GridMap(String name, double[] posW, double[] posH, double[] posX, double[] posY, int gridNum) {
        this.name = name;
        this.posW = posW;
        this.posH = posH;
        this.posX = posX;
        this.posY = posY;
        this.gridNum = gridNum;
    }


}
