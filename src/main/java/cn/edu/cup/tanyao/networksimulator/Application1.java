package cn.edu.cup.tanyao.networksimulator;

import cn.edu.cup.tanyao.networksimulator.data.NetworkData;
import cn.edu.cup.tanyao.networksimulator.simulator.Network;
import cn.edu.cup.tanyao.networksimulator.simulator.NewtonMethod;

/**
 * @author tanyao
 * @date 2019/12/2
 */
public class Application1 {
    public static void main(String[] args) {
//        //excel数据源
//        Input excelInput = new Input("data/networksimulator.xlsx");
//        Map temp = excelInput.read();
//
        //管网数据对象
        NetworkData networkData = new NetworkData();

        //初始化数据
        networkData.init();

//        List<List<String>> data1 = (List<List<String>>) temp.get("管段数据");
//        List<List<String>> data2 = (List<List<String>>) temp.get("节点数据");

//        networkData.setNodes(data2);
//        networkData.setElements(data1);
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

        //输出结果
        System.out.println(network);
    }
}
