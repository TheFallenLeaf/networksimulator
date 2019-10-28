package cn.edu.cup.tanyao.networksimulator.network;

/**
 * @author tanyao
 * @date 2019/9/21
 */
public class TestBwrs {
    public static void main (String[] args) {
        double[] com = {0.9496, 0.0088, 0.0018, 0.0003, 0.0004, 0, 0, 0, 0.0095, 0.0296};
        Bwrs A = new Bwrs(com);
        double pressure = 0;
        double tem = 293.15;
        long start = System.currentTimeMillis();

        for (int i = 0; i < 300000; i++) {
            pressure = 1000 + Math.random() * (5000 - 1000);
            A.init(pressure, tem);
//            System.out.println(A.getZ());
        }

        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
