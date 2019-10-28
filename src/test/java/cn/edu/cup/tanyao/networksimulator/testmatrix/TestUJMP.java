package cn.edu.cup.tanyao.networksimulator.testmatrix;

import org.ujmp.core.DenseMatrix;
import org.ujmp.core.Matrix;
import org.ujmp.core.SparseMatrix;

/**
 * @author tanyao
 * @date 2019/10/19
 */
public class TestUJMP {
    public void run (Source source,
                     int times) {
        long[] time = calculate(source, times);
        System.out.print("UJMP: ");
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
        long[] time = new long[2];


        Matrix sparse = SparseMatrix.Factory.zeros(source.getDimension(), source.getDimension());
        Matrix vector1 = DenseMatrix.Factory.zeros(source.getDimension(), 1);
        Matrix vector2 = DenseMatrix.Factory.zeros(source.getDimension(), 1);

        for (int i = 0; i < sparse.getRowCount(); i++) {
            for (int j = 0; j < sparse.getColumnCount(); j++) {
                sparse.setAsInt((int)source.getSparse()[i][j], i, j);
            }
            vector1.setAsDouble(source.getVector1()[i][0], i, 0);
            vector2.setAsDouble(source.getVector2()[i][0], i, 0);
        }

        long start = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            Matrix temp = sparse.mtimes(vector1);
        }
        long end = System.currentTimeMillis();

        time[0] = end - start;

        start = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            Matrix temp = vector1.times(vector2);
            int a = 0;
        }
        end = System.currentTimeMillis();

        time[1] = end - start;

        return time;
    }

    public static void main(String[] args) {
        Source source = new Source(1000);
        source.init();

        TestUJMP testUJMP = new TestUJMP();
        testUJMP.run(source, 1000);
    }
}
