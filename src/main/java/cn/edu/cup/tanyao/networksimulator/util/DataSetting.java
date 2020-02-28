package cn.edu.cup.tanyao.networksimulator.util;

import cn.edu.cup.tanyao.networksimulator.data.ENPLData;
import cn.edu.cup.tanyao.networksimulator.data.NPLData;

import java.util.List;
import java.util.Map;

/**
 * @author tanyao
 * @date 2020/2/18
 */
public class DataSetting {

    private static String[] npl = {"气体组分", "节点数据", "管段数据", "产量数据", "井口对应表"};

    private static String[] enpl = {"气体组分", "节点数据", "管段数据", "产量数据", "井口对应表", "引射器方案1"};

    public static NPLData setNPL(Map data){
        //检查数据完整性
        for (int i = 0; i < npl.length; i++) {
            if (!data.containsKey(npl[i])){
                System.out.println("数据不完整！");
                return null;
            }
        }

        NPLData nplData = new NPLData();
        nplData.setComponent((List<List<String>>) data.get(npl[0]));
        nplData.setNodes((List<List<String>>) data.get(npl[1]));
        nplData.setPipes((List<List<String>>) data.get(npl[2]));
        nplData.setProductions((List<List<String>>) data.get(npl[3]));
        nplData.setWells((List<List<String>>) data.get(npl[4]));
        //处理标题
        nplData.process();

        return nplData;
    }

    public static ENPLData setENPL(Map data) {
        //检查数据完整性
        for (int i = 0; i < enpl.length; i++) {
            if (!data.containsKey(enpl[i])){
                System.out.println("数据不完整！");
                return null;
            }
        }
        ENPLData enplData = new ENPLData();
        enplData.setComponent((List<List<String>>) data.get(enpl[0]));
        enplData.setNodes((List<List<String>>) data.get(enpl[1]));
        enplData.setPipes((List<List<String>>) data.get(enpl[2]));
        enplData.setProductions((List<List<String>>) data.get(enpl[3]));
        enplData.setWells((List<List<String>>) data.get(enpl[4]));
        enplData.setEjector((List<List<String>>) data.get(enpl[5]));
        //处理标题
        enplData.process();

        return enplData;
    }
}
