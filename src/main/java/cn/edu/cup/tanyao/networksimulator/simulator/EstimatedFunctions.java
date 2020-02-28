package cn.edu.cup.tanyao.networksimulator.simulator;

import cn.edu.cup.tanyao.networksimulator.network.Bwrs;
import cn.edu.cup.tanyao.networksimulator.network.Constant;
import org.ejml.simple.SimpleMatrix;

/**
 * 管网方程中的变量部分
 * @author tanyao
 * @date 2019/10/21
 */
public class EstimatedFunctions {
    /**
     * 管段压缩因子
     */
    private SimpleMatrix compressionFactor;

    /**
     * 管段起始压力
     */
    private SimpleMatrix startPressure;

    /**
     * 管段终点压力
     */
    private SimpleMatrix endPressure;

    /**
     * 管段起伏计算因子
     */
    private SimpleMatrix coefficientA;

    /**
     * 管网对象
     */
    private Network network;

    public EstimatedFunctions (Network network) {
        this.network = network;
    }

    public void calculate (double[][] pressure) {
        //节点数
        int nodeCount = network.getNodes().length;
        //管段数
        int pipeCount = network.getElements().length;
        //管段压缩因子
        double[][] compressionFactor = new double[pipeCount][1];
        //管段起点压力
        double[][] startPressure = new double[pipeCount][1];
        //管段终点压力
        double[][] endPressure = new double[pipeCount][1];
        //管段起伏计算因子
        double[][] coefficientA = new double[pipeCount][1];

        for (int i = 0; i < pipeCount; i++) {
            //管段起点
            int startNumber = network.getElements()[i].getStartNode().getUid();
            //管段终点
            int endNumber = network.getElements()[i].getEndNode().getUid();
            //根据节点编号寻找序列
            int start = 0;
            int end = 0;
            for (int j = 0; j < network.getNodes().length; j++) {
                if (network.getNodes()[j].getUid() == startNumber) {
                    start = j;
                }
                if (network.getNodes()[j].getUid() == endNumber) {
                    end = j;
                }
            }
            //起点压力
            startPressure[i][0] = pressure[start][0];
            //终点压力
            endPressure[i][0] = pressure[end][0];

            Bwrs bwrs = new Bwrs(network.getGas().getComponent());
            bwrs.init((startPressure[i][0] + endPressure[i][0]) * 500, Constant.temperature);
            //压缩因子
            compressionFactor[i][0] = bwrs.getZ();
            //地形起伏因子
            coefficientA[i][0] = 2 * Constant.gravity / compressionFactor[i][0]
                    / Constant.Ra / Constant.temperature;
        }

        this.compressionFactor = new SimpleMatrix(compressionFactor);
        this.startPressure = new SimpleMatrix(startPressure);
        this.endPressure = new SimpleMatrix(endPressure);
        this.coefficientA = new SimpleMatrix(coefficientA);
    }

    public SimpleMatrix getCompressionFactor() {
        return compressionFactor;
    }

    public SimpleMatrix getStartPressure() {
        return startPressure;
    }

    public SimpleMatrix getEndPressure() {
        return endPressure;
    }

    public SimpleMatrix getCoefficientA() {
        return coefficientA;
    }
}
