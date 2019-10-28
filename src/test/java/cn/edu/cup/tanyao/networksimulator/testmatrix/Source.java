package cn.edu.cup.tanyao.networksimulator.testmatrix;

/**
 * 计算需要的源数据
 * @author tanyao
 * @date 2019/10/18
 */
public class Source {
    /**
     * 维度
     */
    private int dimension;

    /**
     * 稀疏矩阵数据
     */
    private double[][] sparse;

    /**
     * 列向量1
     */
    private double[][] vector1;

    /**
     * 列向量2
     */
    private double[][] vector2;

    public Source (int dimension) {
        this.dimension = dimension;
    }

    /**
     * 初始化
     */
    public void init () {
        double[][] sparse = new double[dimension][dimension];
        double[][] vector1 = new double[dimension][1];
        double[][] vector2 = new double[dimension][1];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int flag = (int) (Math.random() * 100);
                if (flag % 4 == 0) {
                    sparse[i][j] = 1;
                }
            }
            vector1[i][0] = 500 + Math.random() * 1000;
            vector2[i][0] = 1000+ Math.random() * 3000;
        }
        this.sparse = sparse;
        this.vector1 = vector1;
        this.vector2 = vector2;
    }

    public int getDimension() {
        return dimension;
    }

    public double[][] getSparse() {
        return sparse;
    }

    public double[][] getVector1() {
        return vector1;
    }

    public double[][] getVector2() {
        return vector2;
    }

    public static void main(String[] args) {
    }
}
