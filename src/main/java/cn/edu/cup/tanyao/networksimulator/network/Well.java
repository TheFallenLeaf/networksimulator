package cn.edu.cup.tanyao.networksimulator.network;

/**
 * @author tanyao
 * @date 2020/2/12
 */
public class Well {
    /**
     * 井口编号
     */
    private int uid;

    /**
     * 井口名称
     */
    private String name;

    /**
     * 产量曲线
     */
    private IPR ipr;

    public Well(int uid, String name){
        this.name = name;
        this.uid = uid;
    }

    public int getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IPR getIpr() {
        return ipr;
    }

    public void setIpr(IPR ipr) {
        this.ipr = ipr;
    }
}
