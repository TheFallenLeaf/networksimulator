package cn.edu.cup.tanyao.networksimulator.network;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

/**
 * 多项式拟合产量曲线
 * @author tanyao
 * @date 2020/2/12
 */
public class IPR {
    /**
     * 多项式阶数
     */
    private int degree;

    /**
     * 多项式系数，从常数项开始
     */
    private double[] coefficient;

    public IPR(double[] xi, double[] yi, int degree){
        this.degree = degree;
        WeightedObservedPoints points = new WeightedObservedPoints();
        for (int i = 0; i < xi.length; i++) {
            points.add(xi[i], yi[i]);
        }

        PolynomialCurveFitter polynomialCurveFitter = PolynomialCurveFitter.create(degree);
        this.coefficient = polynomialCurveFitter.fit(points.toList());
    }

    public double fit(double x){
        double value = 0;
        for (int i = 0; i <= degree; i++) {
            value += coefficient[i] * Math.pow(x, i);
        }
        return value;
    }

    public static void main(String[] args) {
        double[] xi = {2.1, 2.2, 2.3, 2.4, 2.5};
        double[] yi = {2500, 2400, 2200, 2000, 1500};
        IPR ipr = new IPR(xi, yi, 2);
        double y = ipr.fit(2.05);
        System.out.println(y);
    }
}
