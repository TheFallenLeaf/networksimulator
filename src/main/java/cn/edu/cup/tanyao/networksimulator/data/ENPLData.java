package cn.edu.cup.tanyao.networksimulator.data;

import cn.edu.cup.tanyao.networksimulator.util.DataProcess;

import java.util.List;

/**
 * @author tanyao
 * @date 2020/2/12
 */
public class ENPLData extends NPLData{
    /**
     * 引射器数据
     */
    private List<List<String>> ejector;

    @Override
    public void process() {
        super.process();
        setEjector(DataProcess.process(ejector, true, false));
    }

    public List<List<String>> getEjector() {
        return ejector;
    }

    public void setEjector(List<List<String>> ejector) {
        this.ejector = ejector;
    }
}
