package cn.edu.cup.tanyao.networksimulator.optimize;

import cn.edu.cup.tanyao.networksimulator.network.Bwrs;
import cn.edu.cup.tanyao.networksimulator.network.Constant;
import cn.edu.cup.tanyao.networksimulator.network.Well;
import org.ejml.simple.SimpleMatrix;

/**
 * @author tanyao
 * @date 2020/2/19
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
     * 节点载荷
     */
    private SimpleMatrix simpleLoad;

    private NPLNetwork nplNetwork;

    public EstimatedFunctions(NPLNetwork nplNetwork){
        this.nplNetwork = nplNetwork;
    }

    public void calculate(double[][] pressure){
        //节点数
        int nodeCount = nplNetwork.getNodes().length;
        //管段数
        int pipeCount = nplNetwork.getPipes().length;
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
            int start = nplNetwork.getPipes()[i].getStartNode().getUid();
            //管段终点
            int end = nplNetwork.getPipes()[i].getEndNode().getUid();
            //起点压力
            startPressure[i][0] = pressure[start-1][0];
            //终点压力
            endPressure[i][0] = pressure[end-1][0];

            Bwrs bwrs = new Bwrs(nplNetwork.getGas().getComponent());
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

        //根据压力计算节点载荷
        //未知压力,可计算载荷的节点个数
        int simpleNode = 0;
        for (int i = 0; i < nplNetwork.getNodes().length; i++) {
            if (!nplNetwork.getNodes()[i].getPressureState()) {
                simpleNode ++;
            }
        }
        //简化的节点载荷
        double[][] simpleNodes = new double[simpleNode][1];
        //根据节点压力计算节点载荷
        int flagNode = 0;
        for (int i = 0; i < nplNetwork.getNodes().length; i++) {
            //排除压力已知的节点
            if (nplNetwork.getNodes()[i].getPressureState()) {
                continue;
            }
            //节点没有载荷的点载荷为0
            if (!nplNetwork.getNodes()[i].getNodeState()) {
                simpleNodes[flagNode][0] = 0;
                flagNode ++;
                continue;
            }
            //其余节点根据产量曲线计算载荷
            // 节点编号
            int uid = nplNetwork.getNodes()[i].getUid();
            //根据节点编号寻找当前节点的Well对象
            Well well = null;
            for (int j = 0; j < nplNetwork.getWells().length; j++) {
                if (nplNetwork.getWells()[j].getUid() == uid) {
                    well = nplNetwork.getWells()[j];
                }
            }
            //设置节点载荷
            simpleNodes[flagNode][0] = well.getIpr().fit(pressure[flagNode][0]);
            flagNode ++;
        }
        this.simpleLoad = new SimpleMatrix(simpleNodes);
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

    public SimpleMatrix getSimpleLoad() {
        return simpleLoad;
    }
}
