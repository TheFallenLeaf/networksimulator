package cn.edu.cup.tanyao.networksimulator.network;

/**
 * 管段类
 * @author tanyao
 * @date 2019/9/26
 */
public class Pipe extends Element {
    /**
     * 管段内径, m
     */
    private double diameter;

    /**
     * 管段长度, m
     */
    private double length;

    /**
     * 管段粗糙度,m
     */
    private double roughness;

    /**
     * 管段截面流通量, Nm³/d
     */
    private double flow;

    /**
     * 管段摩阻
     */
    private double lambda;

    public Pipe(Node startNode, Node endNode) {
        super(startNode, endNode);
    }

    /**
     * 流量方法
     */
    @Override
    public void fluxion() {
        System.out.println("抽象实现");
    }

    /**
     * 威莫斯公式
     * 管段摩阻计算方法
     */
    public void setLambda () {
        lambda = 0.009407 / Math.pow(diameter, 1.0/3);
    }

    public void setFlow (double flow) {
        this.flow = flow;
    }

    public void setDiameter (double diameter) {
        this.diameter = diameter;
    }

    public void setLength (double length) {
        this.length = length;
    }

    public void setRoughness (double roughness) {
        this.roughness = roughness;
    }

    public double getDiameter () {
        return diameter;
    }

    public double getLength () {
        return length;
    }

    public double getRoughness () {
        return roughness;
    }

    public double getFlow () {
        return flow;
    }

    public double getLambda() {
        return lambda;
    }
}
