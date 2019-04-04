package cn.lovezsm.bjcj.data;


import cn.lovezsm.bjcj.utils.AlgorithmUtils;

public abstract class FingerPrintBuilder {

    public abstract FingerPrint build(boolean isStandardization);

    public void standardStdMatrix(Double[][] std, int ln, double scalingVal) {
        Double[] vals = std[ln];
        for (int i = 0;i<vals.length;i++){
            if(vals[i]!=null){
                vals[i] = vals[i]/scalingVal;
            }
        }
    }

    public double standardAvgMatrix(Double[][] avg, int ln) {
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

}
