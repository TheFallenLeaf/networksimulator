package cn.edu.cup.tanyao.networksimulator.testmatrix;

import org.apache.commons.math3.linear.*;

/**
 * @author tanyao
 * @date 2019/10/19
 */
public class TestMath3 {
    public void run (Source source,
                     int times) {
        long[] time = calculate(source, times);
        System.out.print("CommonMath: ");
        System.out.print("稀疏矩阵计算时间：" + time[0] + "  ");
        System.out.println("列向量计算时间：" + time[1]);
    }

    /**
     *
     * @param source 源数据
     * @param times 计算次数
     * @return 计算时间，分别是稀疏矩阵乘法，列向量对应项相乘
     */
    private long[] calculate (Source source,
                              int times) {
        long [] time = new long[2];

        SparseRealMatrix sparse = new OpenMapRealMatrix(source.getDimension(), source.getDimension());
        RealVector vector1 = new OpenMapRealVector(source.getDimension());
        RealVector vector2 = new OpenMapRealVector(source.getDimension());
        for (int i = 0; i < source.getDimension(); i++) {
            for (int j = 0; j < source.getDimension(); j++) {
                sparse.setEntry(i, j, source.getSparse()[i][j]);
            }
            vector1.setEntry(i, source.getVector1()[i][0]);
            vector2.setEntry(i, source.getVector2()[i][0]);
        }

        long start = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            RealVector temp = sparse.operate(vector1);
        }
        long end = System.currentTimeMillis();

        time[0] = end - start;

        start = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            RealVector temp = vector1.ebeMultiply(vector2);
        }
        end = System.currentTimeMillis();

        time[1] = end - start;
        return time;
    }
}
