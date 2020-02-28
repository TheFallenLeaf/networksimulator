package cn.edu.cup.tanyao.networksimulator.optimize;

import cn.edu.cup.tanyao.networksimulator.network.Constant;
import org.ejml.simple.SimpleMatrix;

/**
 * @author tanyao
 * @date 2020/2/13
 */
public class NPLSolve {

    /**
     *
     */
    private NPLNetwork nplNetwork;

    /**
     *
     */
    private NetworkFunctions networkFunctions;

    public NPLSolve(NPLNetwork nplNetwork) {
        this.nplNetwork = nplNetwork;
        NetworkFunctions networkFunctions = new NetworkFunctions(nplNetwork);
        networkFunctions.init();
        this.networkFunctions = networkFunctions;
    }

    /**
     * 获取节点压力未知个数
     * @return 未知数个数
     */
    private int getPressureState() {
        int count = 0;

        for (int i = 0; i < nplNetwork.getNodes().length; i++) {
            if (!nplNetwork.getNodes()[i].getPressureState()) {
                count ++;
            }
        }

        return count;
    }

    private double[][] getPressure(SimpleMatrix unknown) {
        int nodeCount = nplNetwork.getNodes().length;
        double[][] pressure = new double[nodeCount][1];
        int flag = 0;
        for (int i = 0; i < nodeCount; i++) {
            if (nplNetwork.getNodes()[i].getPressureState()) {
                pressure[i][0] = nplNetwork.getNodes()[i].getPressure();
            }
            else {
                pressure[i][0] = unknown.get(flag, 0);
                flag ++;
            }
        }
        return pressure;
    }

    /**
     * 初始化未知数
     * @return
     */
    private double[][] initPressure() {
        int count = 0;
        double averagePressure = 0;
        double[][] pressure = new double[getPressureState()][1];
        for (int i = 0; i < nplNetwork.getNodes().length; i++) {
            if (nplNetwork.getNodes()[i].getPressureState()) {
                averagePressure += nplNetwork.getNodes()[i].getPressure();
                count ++;
            }
        }
        averagePressure /= count;
        for (int i = 0; i < pressure.length; i++) {
            pressure[i][0] = averagePressure + Math.random() * (averagePressure * 0.1);
        }
        return pressure;
    }

    private SimpleMatrix calculateJacobi(SimpleMatrix unknownPressure) {
        SimpleMatrix jacobi = new SimpleMatrix(unknownPressure.numRows(), unknownPressure.numRows());
        //F(x1)
        SimpleMatrix fx1 = getFunction(unknownPressure);
        //dx = 0.0000001
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

    /**
     * 计算方程函数值
     * @param unknownPressure
     * @return
     */
    private SimpleMatrix getFunction(SimpleMatrix unknownPressure) {
        //节点压力
        double[][] var0 = getPressure(unknownPressure);
        //更新未知数相关的量
        networkFunctions.getEstimatedFunctions().calculate(var0);
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
        double var8 = nplNetwork.getGas().getRelativeDensity();
        //管段长度
        SimpleMatrix var9 = networkFunctions.getFixedFunctions().getLength();
        //(P1^2-P2^2(1+aS))*d^5
        SimpleMatrix var10 = var1.elementPower(2).minus(var2.elementPower(2)
                .elementMult(var3.elementMult(var4).plus(1)))
                .elementMult(var5.elementPower(5));
        // λZL(1+aS)ΔT
        SimpleMatrix var11 = var6.elementMult(var7).elementMult(var9).elementMult(var3.elementMult(var4)
                .plus(1)).scale(var8* Constant.temperature);
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
        SimpleMatrix var17 = networkFunctions.getEstimatedFunctions().getSimpleLoad();
        //AQ-q
        return var16.mult(var15).minus(var17);
    }

    private void setResult(SimpleMatrix unknownPressure) {
        //计算结果赋值
        int flag = 0;
        for (int i = 0; i < nplNetwork.getNodes().length; i++) {
            if (!nplNetwork.getNodes()[i].getPressureState()) {
                nplNetwork.getNodes()[i].setPressure(unknownPressure.get(flag, 0));
                flag ++;
            }
        }

        //计算井口流量并赋值
        for (int i = 0; i < nplNetwork.getNodes().length; i++) {
            //节点有载荷且节点压力未知的节点
            if (nplNetwork.getNodes()[i].getNodeState() && !nplNetwork.getNodes()[i].getPressureState()) {
                //节点编号
                int uid = nplNetwork.getNodes()[i].getUid();
                //节点压力
                double pressure = nplNetwork.getNodes()[i].getPressure();
                //节点Well对象
                for (int j = 0; j < nplNetwork.getWells().length; j++) {
                    if (uid == nplNetwork.getWells()[j].getUid()) {
                        double load = nplNetwork.getWells()[j].getIpr().fit(pressure);
                        nplNetwork.getNodes()[i].setLoad(load);
                        break;
                    }
                }
            }
        }

        //集气站编号
        int uid = 0;
        for (int i = 0; i < nplNetwork.getNodes().length; i++) {
            if (nplNetwork.getNodes()[i].getPressureState()) {
                uid = nplNetwork.getNodes()[i].getUid();
            }
        }
        double load = 0;
        for (int i = 0; i < nplNetwork.getNodes().length; i++) {
            if (nplNetwork.getNodes()[i].getUid() != uid) {
                load -= nplNetwork.getNodes()[i].getLoad();
            }
        }
        for (int i = 0; i < nplNetwork.getNodes().length; i++) {
            if (nplNetwork.getNodes()[i].getPressureState()) {
                nplNetwork.getNodes()[i].setLoad(load);
            }
        }
    }

    public void run() {
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

        //SimpleMatrix var1 = getFunction(unknownPressure);
        //保留计算结果
        setResult(unknownPressure);
    }
}
