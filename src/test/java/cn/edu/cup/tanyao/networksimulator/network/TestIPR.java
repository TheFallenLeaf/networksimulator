package cn.edu.cup.tanyao.networksimulator.network;

/**
 * @author tanyao
 * @date 2020/2/13
 */
public class TestIPR {
    public static void main(String[] args) {
        double[] xi = {-1, 0, 1, 2};
        double[] yi = {1, 0, 1, 4};
        IPR ipr = new IPR(xi, yi, 2);
        System.out.println(ipr.fit(1.3));
    }
}
