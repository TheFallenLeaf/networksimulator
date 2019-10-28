package cn.edu.cup.tanyao.networksimulator.testmatrix;

import org.ejml.simple.SimpleMatrix;

/**
 * @author tanyao
 * @date 2019/10/19
 */
public class TestEJML {
    public void run (Source source,
                     int times) {
        long[] time = calculate(source, times);
        System.out.print("EJML: ");
        System.out.print("稀疏矩阵计算时间：" + time[0] + "  ");
        System.out.println("列向量计算时间：" + time[1]);
    }

    private long[] calculate (Source source,
                              int times) {
        long[] time = new long[2];

        SimpleMatrix sparse = new SimpleMatrix(source.getSparse());
        SimpleMatrix vector1 = new SimpleMatrix(source.getVector1());
        SimpleMatrix vector2 = new SimpleMatrix(source.getVector2());

        long start = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            SimpleMatrix temp = sparse.mult(vector1);
        }
        long end = System.currentTimeMillis();

        time[0] = end - start;

        start = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            SimpleMatrix temp = vector1.elementMult(vector2);
        }
        end = System.currentTimeMillis();

        time[1] = end - start;

        return time;
    }
}
