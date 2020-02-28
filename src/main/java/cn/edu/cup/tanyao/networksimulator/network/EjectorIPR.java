package cn.edu.cup.tanyao.networksimulator.network;

/**
 * @author tanyao
 * @date 2020/2/26
 */
public class EjectorIPR extends IPR {
    private Well lowWell;
    private Well highWell;
    private Ejector ejector;

    public void init(Well lowWell, Well highWell, Ejector ejector) {
        this.lowWell = lowWell;
        this.highWell = highWell;
        this.ejector = ejector;
        //计算名义引射率
        double value = ejector.getEfficiency()
                * (1-Math.pow(ejector.getCompressRate() / ejector.getExpandRate(), (Constant.k-1)/Constant.k))
                / (Math.pow(ejector.getCompressRate(), (Constant.k-1)/Constant.k)-1);
        ejector.setEjectorRate(value);
    }

    @Override
    public double fit(double x) {
        double lowFlow = lowWell.getIpr().fit(x);
        double highFlow = highWell.getIpr().fit(x);
        return Math.min(ejector.getEjectorRate() * highFlow + highFlow, lowFlow / ejector.getEjectorRate() + lowFlow);

    }

    public Well getLowWell() {
        return lowWell;
    }

    public void setLowWell(Well lowWell) {
        this.lowWell = lowWell;
    }

    public Well getHighWell() {
        return highWell;
    }

    public void setHighWell(Well highWell) {
        this.highWell = highWell;
    }

    public Ejector getEjector() {
        return ejector;
    }

    public void setEjector(Ejector ejector) {
        this.ejector = ejector;
    }
}
