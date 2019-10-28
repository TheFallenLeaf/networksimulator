package cn.edu.cup.tanyao.networksimulator;

import cn.edu.cup.tanyao.networksimulator.data.ExcelInput;
import cn.edu.cup.tanyao.networksimulator.data.NetworkData;
import cn.edu.cup.tanyao.networksimulator.simulator.Network;
import cn.edu.cup.tanyao.networksimulator.simulator.NewtonMethod;

import java.util.List;
import java.util.Map;

/**
 * @author tanyao
 * @date 2019/10/21
 */
public class Test {
    public static void main(String[] args) {
        //excel数据源
        ExcelInput excelInput = new ExcelInput("data/networksimulator1.xlsx");
        Map temp = excelInput.getData(excelInput.getWorkbook());
        //管网数据对象
        NetworkData networkData = new NetworkData();
        networkData.init();

        List<List<String>> data1 = (List<List<String>>) temp.get("管段数据");
        List<List<String>> data2 = (List<List<String>>) temp.get("节点数据");
        networkData.setNodes(data2);
        networkData.setElements(data1);
        //管网对象
        Network network = new Network();
        network.init(networkData.getNodes(),
                     networkData.getElements(),
                     networkData.getComponent());
        //牛顿迭代
        NewtonMethod newtonMethod = new NewtonMethod(network);
        long start = System.currentTimeMillis();
        newtonMethod.run();
        long end = System.currentTimeMillis();
        System.out.print("计算时间：");
        System.out.println(end - start);
    }
}
