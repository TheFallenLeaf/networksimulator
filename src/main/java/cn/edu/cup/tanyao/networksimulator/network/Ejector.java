package cn.edu.cup.tanyao.networksimulator.network;

/**
 * @author tanyao
 * @date 2020/2/22
 */
public class Ejector {
    private static int id = 1;

    /**
     * 引射器编号
     */
    private int uid;

    /**
     * 膨胀比,Ph:Pl
     */
    private double expandRate = 1.6;

    /**
     * 压缩比,Pm:Pl
     */
    private double compressRate = 1.2;

    /**
     * 引射率
     */
    private double ejectorRate;

    /**
     * 等熵效率
     */
    private double efficiency;

    /**
     * 高压节点
     */
    private Node highNode;

    /**
     * 中压节点
     */
    private Node middleNode;

    /**
     * 低压节点
     */
    private Node lowNode;

    public Ejector() {
        uid = id;
        id ++;
    }

    public void setExpandRate(double expandRate) {
        this.expandRate = expandRate;
    }

    public void setCompressRate(double compressRate) {
        this.compressRate = compressRate;
    }

    public void setEjectorRate(double ejectorRate) {
        this.ejectorRate = ejectorRate;
    }

    public void setEfficiency(double efficiency) {
        this.efficiency = efficiency;
    }

    public void setHighNode(Node highNode) {
        this.highNode = highNode;
    }

    public void setMiddleNode(Node middleNode) {
        this.middleNode = middleNode;
    }

    public void setLowNode(Node lowNode) {
        this.lowNode = lowNode;
    }

    public int getUid() {
        return uid;
    }

    public double getExpandRate() {
        return expandRate;
    }

    public double getCompressRate() {
        return compressRate;
    }

    public double getEjectorRate() {
        return ejectorRate;
    }

    public double getEfficiency() {
        return efficiency;
    }

    public Node getHighNode() {
        return highNode;
    }

    public Node getMiddleNode() {
        return middleNode;
    }

    public Node getLowNode() {
        return lowNode;
    }
}
