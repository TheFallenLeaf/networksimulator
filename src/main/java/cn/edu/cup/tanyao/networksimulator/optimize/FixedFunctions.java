package cn.edu.cup.tanyao.networksimulator.optimize;

import org.ejml.simple.SimpleMatrix;

/**
 * @author tanyao
 * @date 2020/2/19
 */
public class FixedFunctions {
    /**
     * 管段内径
     */
    private SimpleMatrix diameter;

    /**
     * 管段摩阻
     */
    private SimpleMatrix lambda;

    /**
     * 管段长度
     */
    private SimpleMatrix length;

    /**
     * 管段起伏
     */
    private SimpleMatrix elevation;

    /**
     * 节点-管段关联矩阵
     */
    private SimpleMatrix incidence;

    /**
     * 简化的节点-管段关联矩阵
     */
    private SimpleMatrix simpleIncidence;

    public void init(NPLNetwork nplNetwork){
        //管段数
        int pipeCount = nplNetwork.getPipes().length;
        //管段内径数组
        double[][] diameter = new double[pipeCount][1];
        //管段摩阻数组
        double[][] lambda = new double[pipeCount][1];
        //管段长度数组
        double[][] length = new double[pipeCount][1];
        //管段起伏
        double[][] elevation = new double[pipeCount][1];

        for (int i = 0; i < pipeCount; i++) {
            //管段内径
            diameter[i][0] = nplNetwork.getPipes()[i].getDiameter();
            //摩阻系数
            lambda[i][0] = nplNetwork.getPipes()[i].getLambda();
            //管段长度
            length[i][0] = nplNetwork.getPipes()[i].getLength();
            //管段起伏
            elevation[i][0] = nplNetwork.getPipes()[i].getEndNode().getElevation()
                    - nplNetwork.getPipes()[i].getStartNode().getElevation();
        }
        this.diameter = new SimpleMatrix(diameter);
        this.elevation = new SimpleMatrix(elevation);
        this.lambda = new SimpleMatrix(lambda);
        this.length = new SimpleMatrix(length);

        //节点数
        int nodeCount = nplNetwork.getNodes().length;
        //节点-管段关联数组
        double[][] pipes = new  double[nodeCount][pipeCount];
        for (int i = 0; i < pipeCount; i++) {
            //管段起点编号
            int start = nplNetwork.getPipes()[i].getStartNode().getUid();
            //管段终点编号
            int end = nplNetwork.getPipes()[i].getEndNode().getUid();
            //节点start在管段i的始端
            pipes[start-1][i] = 1;
            //节点end在管段i的终端
            pipes[end-1][i] = -1;
        }
        //根绝节点-管段关联数组生成节点-管段关联矩阵
        this.incidence = new SimpleMatrix(pipes);

        //未知压力节点的个数
        int simpleNode = 0;
        for (int i = 0; i < nplNetwork.getNodes().length; i++) {
            if (!nplNetwork.getNodes()[i].getPressureState()) {
                simpleNode ++;
            }
        }
        double[][] simplePipes = new double[simpleNode][pipeCount];
        int flagPipe = 0;
        for (int i = 0; i < nodeCount; i++) {
            if (!nplNetwork.getNodes()[i].getPressureState()) {
                simplePipes[flagPipe] = pipes[i];
                flagPipe ++;
            }
        }
        this.simpleIncidence = new SimpleMatrix(simplePipes);
    }

    public SimpleMatrix getDiameter() {
        return diameter;
    }

    public SimpleMatrix getLambda() {
        return lambda;
    }

    public SimpleMatrix getLength() {
        return length;
    }

    public SimpleMatrix getElevation() {
        return elevation;
    }

    public SimpleMatrix getIncidence() {
        return incidence;
    }

    public SimpleMatrix getSimpleIncidence() {
        return simpleIncidence;
    }
}
