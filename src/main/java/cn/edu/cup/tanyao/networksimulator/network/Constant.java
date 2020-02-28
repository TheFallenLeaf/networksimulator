package cn.edu.cup.tanyao.networksimulator.network;


/**
 * 计算用到的常数接口
 * @author tanyao
 * @date 2019/9/26
 */
public interface Constant {
    /**
     * 重力常数
     */
    double gravity = 9.81;

    /**
     * 通用气体常数
     */
    double R = 8.314;

    /**
     * 气体常数, J/(Kg·K)
     */
    double Ra = 287.1;

    /**
     * 标况压力, MPa
     */
    double standardPressure = 0.101325;

    /**
     * 石油标况温度, 20℃
     */
    double standardTemperature = 293.15;

    /**
     * C0 = 0.03848 × 24 × 3600 × 10^6
     */
    double C0 = 3324672000.0;

    /**
     * 计算温度，K
     */
    double temperature = 293.15;

    /**
     * 空气摩尔质量
     */
    double airMolarMass = 28.966;

    /**
     * 20℃空气密度，kg/m³
     */
    double airDensity = 1.29;

    /**
     * 理想气体绝热指数
     */
    double k = 1.29;
}
