package cn.lovezsm.bjcj;

import cn.lovezsm.bjcj.data.*;
import cn.lovezsm.bjcj.entity.Message;
import cn.lovezsm.bjcj.entity.Record;
import cn.lovezsm.bjcj.utils.AlgorithmUtil;
import cn.lovezsm.bjcj.utils.DataUtil;
import cn.lovezsm.bjcj.utils.FileUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.junit.Test;

import java.io.*;
import java.lang.annotation.Target;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class CommonTests {

    @Test
    public void testDoubleArray(){
        Double[] d1 = new Double[5];
        Double[] d2 = new Double[5];
        for (int i =0;i<5;i++){
            d1[i] = 5d;
            d2[i] = d1[i];
        }

        System.out.println(Arrays.toString(d1));
        d2[0] = 10d;
        System.out.println(Arrays.toString(d1));

    }

    @Test
    public void testFileUtil() throws IOException {
//        System.out.println(FileUtil.getTotalLines(new File("C:\\data\\Fingerprint_avg.dat")));
        System.out.println(new Date());
    }

    @Test
    public void testGrid(){
        GridMap gridMap = GridMap.buildByFile(new File("C:\\data\\grid.txt"),"测试晶格");
        System.out.println(gridMap);
    }
//    @Test
//    public void testFingerPrint(){
//        FingerPrintBuilder fingerPrintBuilder = new FingerPrintBuilderByFile("测试指纹库",new File("C:\\data\\Fingerprint_avg.dat"),new File("C:\\data\\Fingerprint_std.dat"),6);
//        FingerPrint finger = fingerPrintBuilder.build(true);
//        System.out.println(finger);
//    }

    @Test
    public void test1(){
        System.out.println(0xaa-256);
    }
    private static final int PORT = 16661;
    @Test
    public void nettyTest() throws InterruptedException {
            EventLoopGroup group = new NioEventLoopGroup();
            System.out.println("QuoteOfTheMomentServer" + " + start");
            try {
                Bootstrap b = new Bootstrap();
                b.group(group)
                        .channel(NioDatagramChannel.class)
                        .option(ChannelOption.SO_BROADCAST, true)
                        .handler(new QuoteOfTheMomentServerHandler());

                b.bind(PORT).sync().channel().closeFuture().await();
            } finally {
                group.shutdownGracefully();
            }
    }

    @Test
    public void test2(){
        System.out.println("3747000000000000000000000000000000000000ff000670476002a30a".length());
    }

    @Test
    public void test3(){
        int s = Integer.parseInt("AA",16);
        System.out.println(s);
    }
    @Test
    public void test4(){
        int t = 0;
        for(int i=319;i<=350;i++){
            System.out.println("- "+i+"- -1-1-1");
            t++;
        }
    }

    @Test
    public void testsort(){
        int[] idx = new int[7];
        double[] fFusion = {5.5,1.2,3,9,4,1,4.1};
        for(int i=0;i<7;i++)
        {
            idx[i]=i;
        }
        double temp;
        int t;
        for(int i=0;i<fFusion.length;i++)
        {
            for(int j=0;j<fFusion.length-i-1;j++)
            {
                if(fFusion[j]<fFusion[j+1])
                {
                    temp = fFusion[j];
                    fFusion[j] = fFusion[j+1];
                    fFusion[j+1] = temp;

                    t=idx[j];
                    idx[j] = idx[j+1];
                    idx[j+1] = t;
                }
            }
        }

        System.out.println(idx);
        System.out.println(fFusion);
    }
    @Test
    public void  test5(){
        Float[] f = new Float[5];
        System.out.println(f[3]);
    }
    @Test
    public void test6(){
        Record r1 = new Record();
        r1.setDevMac("123");
        Record r2 = new Record();
        r1.setDevMac("456");
        List<Record> records = new ArrayList<>();
        records.add(r1);
        records.add(r2);
        List<Record> temp = new ArrayList<>();
        for (Record record:records){
            temp.add(record);
        }

        Record record = temp.get(0);
        record.setDevMac("111");
        System.out.println(records.get(0).getDevMac());

    }

    @Test
    public void testMath(){
//        Double[] vals = new Double[]{1d, 2d, 3d, 6d, 3d};
//        AlgorithmUtil.VectorStandardization(vals);
//        System.out.println(Arrays.toString(vals));
//        System.out.println(AlgorithmUtil.getVectorAvgVal(vals));
//        System.out.println(AlgorithmUtil.getVectorScalingVal(vals));
        System.out.println(AlgorithmUtil.getVectorScalingVal(new Double[]{10.61357945523998, 8.633434034486251, 9.097353818777124, 7.637703765764377, 8.97288753518689, 10.285441071229362}));
    }
    @Test
    public void testClass(){
        System.out.println(new Record().getClass().getName());
    }

    @Test
    public void testFormat(){
        System.out.println(String.format("(%s,%d,'%s','%s',%d,%d,%d),","1553679616395",2,"70476002a30d","8844771b6789",125,42,-85));
    }
    @Test
    public void testDouble(){
        Double d1 = null;
        Double d2 = 1d;
        System.out.println(d1+d2);
    }
    @Test
    public void testE1(){
        System.out.println( new BigDecimal(0.9884710505995267d).setScale(2, RoundingMode.UP).doubleValue());
    }

    @Test
    public void funcationCount() throws Exception{
        String dic = "C:/log/3-26--1576840985/";
        File ap1 = new File(dic+"70476002a30b.log");
        File ap2 = new File(dic+"70476002a30c.log");
        File ap3 = new File(dic+"70476002a30d.log");
        File ap4 = new File(dic+"70476002a30e.log");
        File ap5 = new File(dic+"70476002a30f.log");
        File ap6 = new File(dic+"70476002a30a.log");
        System.out.println("AP 1的条目数："+FileUtil.getTotalLines(ap1));
        System.out.println("AP 2的条目数："+FileUtil.getTotalLines(ap2));
        System.out.println("AP 3的条目数："+FileUtil.getTotalLines(ap3));
        System.out.println("AP 4的条目数："+FileUtil.getTotalLines(ap4));
        System.out.println("AP 5的条目数："+FileUtil.getTotalLines(ap5));
        System.out.println("AP 6的条目数："+FileUtil.getTotalLines(ap6));

        Map<String,Integer> counts = new HashMap<>();
        counts.put("心跳包",0);
        String line;
        Date firstTime = null;
        Date lastTime = null;
        BufferedReader br_ap1 = new BufferedReader(new FileReader(ap1));
        BufferedReader br_ap2 = new BufferedReader(new FileReader(ap2));
        BufferedReader br_ap3 = new BufferedReader(new FileReader(ap3));
        BufferedReader br_ap4 = new BufferedReader(new FileReader(ap4));
        BufferedReader br_ap5 = new BufferedReader(new FileReader(ap5));
        BufferedReader br_ap6 = new BufferedReader(new FileReader(ap6));


        while ((line = br_ap1.readLine())!=null){
            String[] split = line.split(":");
            Date cur = new Date(Long.parseLong(split[0]));
            if(firstTime==null||firstTime.getTime()>cur.getTime()){
                firstTime = cur;
            }
            if(lastTime==null||lastTime.getTime()<cur.getTime()){
                lastTime = cur;
            }
            line = split[1];
            if(line.length()<=58){
                counts.put("心跳包",counts.get("心跳包")+1);
                continue;
            }
            List<Message> messages = this.analyzeData(line);
            for (Message message:messages){
                int frequency = message.getFrequency();
                if (!counts.containsKey(frequency+"")){
                    counts.put(frequency+"",0);
                }
                counts.put(frequency+"",counts.get(frequency+"")+1);
            }
        }
        System.out.println("第一条记录时间："+firstTime);
        System.out.println("最后一条记录时间:"+lastTime);
        for (Map.Entry<String,Integer> entry:counts.entrySet()){
            System.out.println(entry.getKey()+" : "+entry.getValue());
        }

        counts = new HashMap<>();
        counts.put("心跳包",0);
        firstTime = null;
        lastTime = null;
        while ((line = br_ap2.readLine())!=null){
            String[] split = line.split(":");
            Date cur = new Date(Long.parseLong(split[0]));
            if(firstTime==null||firstTime.getTime()>cur.getTime()){
                firstTime = cur;
            }
            if(lastTime==null||lastTime.getTime()<cur.getTime()){
                lastTime = cur;
            }
            line = split[1];
            if(line.length()<=58){
                counts.put("心跳包",counts.get("心跳包")+1);
                continue;
            }
            List<Message> messages = this.analyzeData(line);
            for (Message message:messages){
                int frequency = message.getFrequency();
                if (!counts.containsKey(frequency+"")){
                    counts.put(frequency+"",0);
                }
                counts.put(frequency+"",counts.get(frequency+"")+1);
            }
        }
        System.out.println("第一条记录时间："+firstTime);
        System.out.println("最后一条记录时间:"+lastTime);
        for (Map.Entry<String,Integer> entry:counts.entrySet()){
            System.out.println(entry.getKey()+" : "+entry.getValue());
        }

        counts = new HashMap<>();
        counts.put("心跳包",0);
        firstTime = null;
        lastTime = null;
        while ((line = br_ap3.readLine())!=null){
            String[] split = line.split(":");
            Date cur = new Date(Long.parseLong(split[0]));
            if(firstTime==null||firstTime.getTime()>cur.getTime()){
                firstTime = cur;
            }
            if(lastTime==null||lastTime.getTime()<cur.getTime()){
                lastTime = cur;
            }
            line = split[1];
            if(line.length()<=58){
                counts.put("心跳包",counts.get("心跳包")+1);
                continue;
            }
            List<Message> messages = this.analyzeData(line);
            for (Message message:messages){
                int frequency = message.getFrequency();
                if (!counts.containsKey(frequency+"")){
                    counts.put(frequency+"",0);
                }
                counts.put(frequency+"",counts.get(frequency+"")+1);
            }
        }
        System.out.println("第一条记录时间："+firstTime);
        System.out.println("最后一条记录时间:"+lastTime);
        for (Map.Entry<String,Integer> entry:counts.entrySet()){
            System.out.println(entry.getKey()+" : "+entry.getValue());
        }
        counts = new HashMap<>();
        counts.put("心跳包",0);
        firstTime = null;
        lastTime = null;
        while ((line = br_ap4.readLine())!=null){
            String[] split = line.split(":");
            Date cur = new Date(Long.parseLong(split[0]));
            if(firstTime==null||firstTime.getTime()>cur.getTime()){
                firstTime = cur;
            }
            if(lastTime==null||lastTime.getTime()<cur.getTime()){
                lastTime = cur;
            }
            line = split[1];
            if(line.length()<=58){
                counts.put("心跳包",counts.get("心跳包")+1);
                continue;
            }
            List<Message> messages = this.analyzeData(line);
            for (Message message:messages){
                int frequency = message.getFrequency();
                if (!counts.containsKey(frequency+"")){
                    counts.put(frequency+"",0);
                }
                counts.put(frequency+"",counts.get(frequency+"")+1);
            }
        }
        System.out.println("第一条记录时间："+firstTime);
        System.out.println("最后一条记录时间:"+lastTime);
        for (Map.Entry<String,Integer> entry:counts.entrySet()){
            System.out.println(entry.getKey()+" : "+entry.getValue());
        }
        counts = new HashMap<>();
        counts.put("心跳包",0);
        firstTime = null;
        lastTime = null;
        while ((line = br_ap5.readLine())!=null){
            String[] split = line.split(":");
            Date cur = new Date(Long.parseLong(split[0]));
            if(firstTime==null||firstTime.getTime()>cur.getTime()){
                firstTime = cur;
            }
            if(lastTime==null||lastTime.getTime()<cur.getTime()){
                lastTime = cur;
            }
            line = split[1];
            if(line.length()<=58){
                counts.put("心跳包",counts.get("心跳包")+1);
                continue;
            }
            List<Message> messages = this.analyzeData(line);
            for (Message message:messages){
                int frequency = message.getFrequency();
                if (!counts.containsKey(frequency+"")){
                    counts.put(frequency+"",0);
                }
                counts.put(frequency+"",counts.get(frequency+"")+1);
            }
        }
        System.out.println("第一条记录时间："+firstTime);
        System.out.println("最后一条记录时间:"+lastTime);
        for (Map.Entry<String,Integer> entry:counts.entrySet()){
            System.out.println(entry.getKey()+" : "+entry.getValue());
        }
        counts = new HashMap<>();
        counts.put("心跳包",0);
        firstTime = null;
        lastTime = null;
        while ((line = br_ap6.readLine())!=null){
            String[] split = line.split(":");
            Date cur = new Date(Long.parseLong(split[0]));
            if(firstTime==null||firstTime.getTime()>cur.getTime()){
                firstTime = cur;
            }
            if(lastTime==null||lastTime.getTime()<cur.getTime()){
                lastTime = cur;
            }
            line = split[1];
            if(line.length()<=58){
                counts.put("心跳包",counts.get("心跳包")+1);
                continue;
            }
            List<Message> messages = this.analyzeData(line);
            for (Message message:messages){
                int frequency = message.getFrequency();
                if (!counts.containsKey(frequency+"")){
                    counts.put(frequency+"",0);
                }
                counts.put(frequency+"",counts.get(frequency+"")+1);
            }
        }
        System.out.println("第一条记录时间："+firstTime);
        System.out.println("最后一条记录时间:"+lastTime);
        for (Map.Entry<String,Integer> entry:counts.entrySet()){
            System.out.println(entry.getKey()+" : "+entry.getValue());
        }

    }


    public List<Message> analyzeData(String rawData){

        Long scanTime=System.currentTimeMillis();
        List<Message> messages = new ArrayList<>();
        String apMac= rawData.substring(46, 58);
        int messageIndex = 58;
        while (messageIndex < rawData.length() - 1) {
            String tag = rawData.substring(messageIndex, messageIndex + 2);
            if (!tag.equals("00")) {
                int len = Integer.parseInt(rawData.substring(messageIndex + 2, messageIndex + 6), 16);
                messageIndex += (len * 2 + 6);
//                System.out.println("出现了一条异常信息:"+tag);
                continue;
            }
            messageIndex += 6;
            String devMac = rawData.substring(messageIndex, messageIndex + 12);
            int f = Integer.parseInt(rawData.substring(messageIndex + 16, messageIndex + 18), 16);
            messageIndex += 32;
            int rssiVal = Integer.parseInt(rawData.substring(messageIndex, messageIndex + 2), 16) - 256;
            messageIndex += 2;
            Message message = new Message(scanTime,tag, devMac, f, rssiVal, apMac);
            messages.add(message);
        }

        return messages;

    }

    @Test
    public void genGridFile() throws IOException {
        FileWriter fileWriter = new FileWriter("C:\\data\\DS0517\\grid.txt");
        int index = 1;
        for(int i=0;i<=18;i++){
            for(int j=0;j<=8;j++){

                fileWriter.append(index+"_"+(j+0.5)+"_"+(i+0.5)+"_1_1_1");
                fileWriter.append("\r\n");
                index++;
            }
        }
        fileWriter.close();
    }

}
