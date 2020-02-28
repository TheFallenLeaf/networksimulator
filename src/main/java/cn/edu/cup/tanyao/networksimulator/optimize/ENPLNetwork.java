package cn.edu.cup.tanyao.networksimulator.optimize;

import cn.edu.cup.tanyao.networksimulator.data.ENPLData;
import cn.edu.cup.tanyao.networksimulator.network.*;

import java.util.List;

/**
 * 引射器非线性规划
 * @author tanyao
 * @date 2020/2/12
 */
public class ENPLNetwork extends NPLNetwork{

    private Ejector[] ejectors;

    public ENPLNetwork(){
        super();
    }

    public void init(ENPLData enplData) {
        super.init(enplData);
        setEjectors(enplData.getEjector());

    }

    public void setEjectors(List<List<String>> ejectorsSource) {
        //引射器
        Ejector[] ejectors = new Ejector[ejectorsSource.size()];

        for (int i = 0; i < ejectors.length; i++) {
            //引射器数据
            List<String> ejectorSource = ejectorsSource.get(i);
            //创建引射器对象
            Ejector ejector = new Ejector();
            //中压节点
            int middle = (int) Double.parseDouble(ejectorSource.get(1));
            //高压节点
            int high = (int) Double.parseDouble(ejectorSource.get(2));
            //低压节点
            int low = (int) Double.parseDouble(ejectorSource.get(3));
            for (int j = 0; j < getNodes().length; j++) {
                if (getNodes()[j].getUid() == middle) {
                    ejector.setMiddleNode(getNodes()[j]);
                }

                if (getNodes()[j].getUid() == high) {
                    ejector.setHighNode(getNodes()[j]);
                }

                if (getNodes()[j].getUid() == low) {
                    ejector.setLowNode(getNodes()[j]);
                }
            }

            ejector.setEfficiency(Double.parseDouble(ejectorSource.get(4)));

            ejectors[i] = ejector;
        }

        this.ejectors = ejectors;
    }



    public Ejector[] getEjectors() {
        return ejectors;
    }
}
