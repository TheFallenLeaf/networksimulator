package cn.edu.cup.tanyao.networksimulator.testmatrix;

import Jama.Matrix;

/**
 * @author tanyao
 * @date 2019/10/19
 */
public class TestJama {
    public void run (Source source,
                     int times) {
        long[] time = calculate(source, times);
        System.out.print("Jama: ");
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
        Matrix sparse = new Matrix(source.getSparse());
        Matrix vector1 = new Matrix(source.getVector1());
        Matrix vector2 = new Matrix(source.getVector2());

        long start = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            Matrix temp = sparse.times(vector1);
        }
        long end = System.currentTimeMillis();
        time[0] = end - start;

        start = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            Matrix temp = vector1.arrayTimes(vector2);
        }
        end = System.currentTimeMillis();
        time[1] = end - start;

        return time;
    }
}
