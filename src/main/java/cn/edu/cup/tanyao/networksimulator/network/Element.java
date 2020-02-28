package cn.edu.cup.tanyao.networksimulator.network;

/**
 * @author tanyao
 * @date 2019/9/26
 */
public abstract class Element {
    /**
     * 元件编号
     */
    private int uid;

    /**
     * 元件起始节点
     */
    private Node startNode;

    /**
     * 元件终止节点
     */
    private Node endNode;

    /**
     * 构造方法
     * @param startNode 起始节点
     * @param endNode 终止节点
     */
    public Element (Node startNode, Node endNode, int uid) {
        this.uid = uid;
        this.startNode = startNode;
        this.endNode = endNode;
    }

    /**
     * 流量计算方法
     */
    public abstract void fluxion();

    public int getUid () {
        return uid;
    }

    public Node getStartNode () {
        return startNode;
    }

    public Node getEndNode () {
        return endNode;
    }
}
