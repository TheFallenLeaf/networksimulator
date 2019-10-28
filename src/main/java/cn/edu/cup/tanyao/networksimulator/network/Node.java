package cn.edu.cup.tanyao.networksimulator.network;

/**
 * 节点类
 * @author tanyao
 * @date 2019/9/26
 */
public class Node {
    /**
     * 节点编号
     */
    private int uid;

    private static int id = 0;

    /**
     * 节点海拔, m
     */
    private double elevation;

    /**
     * 节点压力, Pa
     */
    private double pressure;

    /**
     * 节点载荷, m³/s
     */
    private double load;

    /**
     * 节点压力是否已知
     */
    private boolean pressureState;

    /**
     * 节点载荷是否已知
     */
    private boolean loadState;

    public Node () {
        id ++;
        this.uid = id;
    }

    public void setElevation (double elevation) {
        this.elevation = elevation;
    }

    public void setPressure (double pressure) {
        this.pressure = pressure;
    }

    public void setLoad (double load) {
        this.load = load;
    }

    public double getPressure () {
        return pressure;
    }

    public double getLoad () {
        return load;
    }

    public double getElevation () {
        return elevation;
    }

    public int getUid () {
        return uid;
    }

    public boolean getPressureState() {
        return this.pressureState;
    }

    public boolean getLoadState() {
        return this.loadState;
    }

    public void setPressureState (boolean state) {
        this.pressureState = state;
    }

    public void setLoadState (boolean state) {
        this.loadState = state;
    }
}
