package cn.edu.cup.tanyao.networksimulator;

import cn.edu.cup.tanyao.networksimulator.data.Input;
import cn.edu.cup.tanyao.networksimulator.data.NPLData;
import cn.edu.cup.tanyao.networksimulator.optimize.NPLNetwork;
import cn.edu.cup.tanyao.networksimulator.optimize.NPLSolve;
import cn.edu.cup.tanyao.networksimulator.util.DataSetting;

import java.util.Map;

/**
 * @author tanyao
 * @date 2020/1/30
 */
public class Application2 {
    public static void main(String[] args) {
        System.out.println("--------------------");
        System.out.println("读取Excel文档...");
        //从excel读取数据
        Input input = new Input();
        //管网数据
        Map data = input.read();
        System.out.println("--------------------");

        //管网数据对象
        NPLData nplData = DataSetting.setNPL(data);

        //管网优化对象
        System.out.println("创建管网对象...");
        NPLNetwork nplNetwork = new NPLNetwork();

        nplNetwork.init(nplData);
        System.out.println("--------------------");
        //管网计算
        NPLSolve nplSolve = new NPLSolve(nplNetwork);
        nplSolve.run();
        System.out.println(nplNetwork);
        System.out.println("--------------------");
    }
}
