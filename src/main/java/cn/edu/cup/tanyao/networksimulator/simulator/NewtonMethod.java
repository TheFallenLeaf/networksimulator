package cn.edu.cup.tanyao.networksimulator.simulator;

import cn.edu.cup.tanyao.networksimulator.network.Constant;
import org.ejml.simple.SimpleMatrix;

/**
 * @author tanyao
 * @date 2019/9/29
 */
public class NewtonMethod {
    /**
     * 管网对象
     */
    private Network network;

    /**
     * 管网方程
     */
    private NetworkFunctions networkFunctions;

    public NewtonMethod (Network network) {
        this.network = network;
        NetworkFunctions networkFunctions = new NetworkFunctions(network);
        networkFunctions.init();
        this.networkFunctions = networkFunctions;
    }

    /**
     * 获取节点压力未知个数
     * @return 未知数压力个数
     */
    private int getPressureState () {
        int count = 0;
        for (int i = 0; i < network.getNodes().length; i++) {
            if (!network.getNodes()[i].getPressureState()) {
                count ++;
            }
        }
        return count;
    }

    /**
     * 根据未知数求完整节点压力矩阵
     * @param unknown 未知数x
     * @return
     */
    private double[][] getPressure (SimpleMatrix unknown) {
        int nodeCount = network.getNodes().length;
        double[][] pressure = new double[nodeCount][1];
        int flag = 0;
        for (int i = 0; i < nodeCount; i++) {
            if (network.getNodes()[i].getPressureState()) {
                pressure[i][0] = network.getNodes()[i].getPressure();
            }
            else {
                pressure[i][0] = unknown.get(flag, 0);
                flag ++;
            }
        }
        return pressure;
    }

    /**
     * 计算函数值
     * @return
     */
    private SimpleMatrix getFunction (SimpleMatrix unknownPressure) {
        //节点压力
        double[][] var = getPressure(unknownPressure);
        //更新未知数相关的量
        networkFunctions.getEstimatedFunctions().calculate(var);
        //起点压力
        SimpleMatrix var1 = networkFunctions.getEstimatedFunctions().getStartPressure();
        //终点压力
        SimpleMatrix var2 = networkFunctions.getEstimatedFunctions().getEndPressure();
        //a=2g/zrt
        SimpleMatrix var3 = networkFunctions.getEstimatedFunctions().getCoefficientA();
        //管段起伏
        SimpleMatrix var4 = networkFunctions.getFixedFunctions().getElevation();
        //管径
        SimpleMatrix var5 = networkFunctions.getFixedFunctions().getDiameter();
        //摩阻系数
        SimpleMatrix var6 = networkFunctions.getFixedFunctions().getLambda();
        //压缩因子
        SimpleMatrix var7 = networkFunctions.getEstimatedFunctions().getCompressionFactor();
        //相对密度
        double var8 = network.getGas().getRelativeDensity();
        //管段长度
        SimpleMatrix var9 = networkFunctions.getFixedFunctions().getLength();
        //(P1^2-P2^2(1+aS))*d^5
        SimpleMatrix var10 = var1.elementPower(2).minus(var2.elementPower(2)
                            .elementMult(var3.elementMult(var4).plus(1)))
                            .elementMult(var5.elementPower(5));
        // λZL(1+aS)ΔT
        SimpleMatrix var11 = var6.elementMult(var7).elementMult(var9).elementMult(var3.elementMult(var4)
                            .plus(1)).scale(var8*Constant.temperature);
        //var10/var11
        SimpleMatrix var12 = var10.elementDiv(var11);
        //var12绝对值
        SimpleMatrix var13 = var12.elementPower(2).elementPower(0.5);
        //var12符号函数
        SimpleMatrix var14 = var12.elementDiv(var13);
        //管段流量
        SimpleMatrix var15 = var13.elementPower(0.5).elementMult(var14).scale(Constant.C0);
        //简化的节点-管段关联矩阵
        SimpleMatrix var16 = networkFunctions.getFixedFunctions().getSimpleIncidence();
        //简化的节点载荷
        SimpleMatrix var17 = networkFunctions.getFixedFunctions().getSimpleLoad();
        //AQ-q
        return var16.mult(var15).minus(var17);
    }

    /**
     * 初始化压力
     * @return
     */
    private double[][] initPressure () {
        int count = 0;
        double averagePressure = 0;
        double[][] pressure = new double[getPressureState()][1];
        for (int i = 0; i < network.getNodes().length; i++) {
            if (network.getNodes()[i].getPressureState()) {
                averagePressure += network.getNodes()[i].getPressure();
                count ++;
            }
        }
        averagePressure /= count;
        for (int i = 0; i < pressure.length; i++) {
            pressure[i][0] = averagePressure + Math.random() * (averagePressure * 0.1);
        }
        return pressure;
    }

    public SimpleMatrix calculateJacobi (SimpleMatrix unknownPressure) {
        SimpleMatrix jacobi = new SimpleMatrix(unknownPressure.numRows(), unknownPressure.numRows());
        //F(x1)
        SimpleMatrix fx1 = getFunction(unknownPressure);
        //dx = 0.0000000001
        double dx = 0.0000001;
        for (int i = 0; i < unknownPressure.numRows(); i++) {
            SimpleMatrix x2 = new SimpleMatrix(unknownPressure);
            //x2
            x2.setRow(i, 0, unknownPressure.get(i, 0)+dx);
            //F(x2)
            SimpleMatrix fx2 = getFunction(x2);
            //F(x2)-F(x1) / dx
            SimpleMatrix temp = fx2.minus(fx1).divide(dx);
            jacobi.insertIntoThis(0, i, temp);
        }
        return jacobi;
    }

    public void run () {
        //初始化未知数
        SimpleMatrix unknownPressure = new SimpleMatrix(initPressure());
        //初始化未知数相关的量
        SimpleMatrix jacobi = calculateJacobi(unknownPressure);
        SimpleMatrix deltaPressure = jacobi.solve(getFunction(unknownPressure).scale(-1));
        for (int i = 0; i < 100; i++) {
            if (deltaPressure.elementMaxAbs() < 0.000000001) {
                System.out.println("迭代次数：" + i);
                break;
            }
            unknownPressure = unknownPressure.plus(deltaPressure.scale(0.5));
            jacobi = calculateJacobi(unknownPressure);
            deltaPressure = jacobi.solve(getFunction(unknownPressure).scale(-1));
        }

//        SimpleMatrix var1 = getFunction(unknownPressure);

        //计算结果赋值
        int flag = 0;
        for (int i = 0; i < network.getNodes().length; i++) {
            if (!network.getNodes()[i].getPressureState()) {
                network.getNodes()[i].setPressure(unknownPressure.get(flag, 0));
                flag ++;
            }
        }
    }

}
