package cn.edu.cup.tanyao.networksimulator.util;

import cn.edu.cup.tanyao.networksimulator.data.NPLData;

import java.util.List;
import java.util.Map;

/**
 * @author tanyao
 * @date 2020/2/18
 */
public class DataSetting {
    public static NPLData set(Map data){
        //检查数据完整性
        String[] strings = {"气体组分", "节点数据", "管段数据", "产量数据", "井口对应表"};
        for (int i = 0; i < strings.length; i++) {
            if (!data.containsKey(strings[i])){
                System.out.println("数据不完整！");
                return null;
            }
        }

        NPLData nplData = new NPLData();
        nplData.setComponent((List<List<String>>) data.get(strings[0]));
        nplData.setNodes((List<List<String>>) data.get(strings[1]));
        nplData.setPipes((List<List<String>>) data.get(strings[2]));
        nplData.setProductions((List<List<String>>) data.get(strings[3]));
        nplData.setWells((List<List<String>>) data.get(strings[4]));
        nplData.process();

        return nplData;
    }
}
