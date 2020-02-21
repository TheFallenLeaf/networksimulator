package cn.edu.cup.tanyao.networksimulator.optimize;

/**
 * @author tanyao
 * @date 2020/2/19
 */
public class NetworkFunctions {
    /**
     * 管网对象
     */
    private NPLNetwork nplNetwork;

    /**
     * 管网方程中的变量部分
     */
    private EstimatedFunctions estimatedFunctions;

    /**
     * 管网方程中的固定部分
     */
    private FixedFunctions fixedFunctions;

    public NetworkFunctions(NPLNetwork nplNetwork){
        this.nplNetwork = nplNetwork;
    }

    public void init(){
        FixedFunctions fixedFunctions = new FixedFunctions();
        fixedFunctions.init(nplNetwork);
        this.fixedFunctions = fixedFunctions;

        EstimatedFunctions estimatedFunctions = new EstimatedFunctions(nplNetwork);
        this.estimatedFunctions = estimatedFunctions;
    }

    public EstimatedFunctions getEstimatedFunctions() {
        return estimatedFunctions;
    }

    public FixedFunctions getFixedFunctions() {
        return fixedFunctions;
    }
}
