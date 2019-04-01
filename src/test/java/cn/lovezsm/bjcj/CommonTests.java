package cn.lovezsm.bjcj;

import cn.lovezsm.bjcj.entity.Record;
import cn.lovezsm.bjcj.utils.AlgorithmUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommonTests {
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
//        AlgorithmUtils.VectorStandardization(vals);
//        System.out.println(Arrays.toString(vals));
//        System.out.println(AlgorithmUtils.getVectorAvgVal(vals));
//        System.out.println(AlgorithmUtils.getVectorScalingVal(vals));
        System.out.println(AlgorithmUtils.getVectorScalingVal(new Double[]{10.61357945523998, 8.633434034486251, 9.097353818777124, 7.637703765764377, 8.97288753518689, 10.285441071229362}));
    }
    @Test
    public void testClass(){
        System.out.println(new Record().getClass().getName());
    }

    @Test
    public void testFormat(){
        System.out.println(String.format("(%s,%d,'%s','%s',%d,%d,%d),","1553679616395",2,"70476002a30d","8844771b6789",125,42,-85));
    }
}
