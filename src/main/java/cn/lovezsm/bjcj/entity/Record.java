package cn.lovezsm.bjcj.entity;

import cn.lovezsm.bjcj.utils.FileUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Record {
    private int frequency;
    private Long scanTime;
    private Long updateTime;
    private Double[] rssi;
    private String devMac;
    private LocalizeReturnVal localizeReturnVal;


    @Override
    public String toString() {
        Double[] out = null;

        if(rssi!=null){
            out = Arrays.copyOf(rssi, rssi.length);
            for(int i =0;i<out.length;i++){
                if(out[i]!=null)
                    out[i] = new BigDecimal(out[i]).setScale(2, RoundingMode.UP).doubleValue();
            }
        }

        return  scanTime + ","
                + devMac + ","
                + frequency +","
                + FileUtil.toString(out);

    }
}
