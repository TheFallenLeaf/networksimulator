package cn.edu.cup.tanyao.networksimulator.network;

/**
 * 管网中的流体
 * @author tanyao
 * @date 2019/9/26
 */
public class Gas {
    /**
     * 气体组分, 组分分别为C1, C2, C3, iC4, nC4, iC5, nC5, C6, N2, CO2，总和等于1
     */
    private double[] component;

    /**
     * 气体相对密度
     */
    private double relativeDensity;

    /**
     * 空构造方法
     */
    public Gas () {}

    /**
     * 设置气体组分
     * @param component
     */
    public Gas (double[] component) {
        this.component = component;
    }

    public void setRelativeDensity () {
        Bwrs bwrs = new Bwrs(this.component);
        bwrs.init(Constant.standardPressure * 1000, Constant.temperature);
        this.relativeDensity = bwrs.getRou_weight() / Constant.airDensity;
    }

    public double getRelativeDensity () {
        return this.relativeDensity;
    }

    public double[] getComponent() {
        return component;
    }

    public void setComponent(double[] component) {
        this.component = component;
    }
}
