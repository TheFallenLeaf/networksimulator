package cn.edu.cup.tanyao.networksimulator.simulator;

/**
 *
 * @author tanyao
 * @date 2019/10/17
 */
public class NetworkFunctions {
    /**
     * 管网对象
     */
    private Network network;

    /**
     * 管网方程中的变量部分
     */
    private EstimatedFunctions estimatedFunctions;

    /**
     * 管网方程中的固定部分
     */
    private FixedFunctions fixedFunctions;

    public NetworkFunctions (Network network) {
        this.network = network;
    }

    public void init () {
        FixedFunctions fixedFunctions = new FixedFunctions();
        fixedFunctions.init(network);
        this.fixedFunctions = fixedFunctions;

        EstimatedFunctions estimatedFunctions = new EstimatedFunctions(network);
        this.estimatedFunctions = estimatedFunctions;
    }

    public EstimatedFunctions getEstimatedFunctions() {
        return estimatedFunctions;
    }

    public FixedFunctions getFixedFunctions() {
        return fixedFunctions;
    }
}
