package cn.edu.cup.tanyao.networksimulator;

import cn.edu.cup.tanyao.networksimulator.data.ENPLData;
import cn.edu.cup.tanyao.networksimulator.data.Input;
import cn.edu.cup.tanyao.networksimulator.optimize.ENPLNetwork;
import cn.edu.cup.tanyao.networksimulator.optimize.ENPLSolve;
import cn.edu.cup.tanyao.networksimulator.util.DataSetting;

import java.util.Map;

/**
 * @author tanyao
 * @date 2020/2/21
 */
public class Application3 {
    public static void main(String[] args) {
        System.out.println("--------------------");
        System.out.println("读取Excel文档...");
        //从excel读取数据
        Input input = new Input();
        //管网数据
        Map data = input.read();

        //管网数据对象
        ENPLData enplData = DataSetting.setENPL(data);

        //管网优化对象
        System.out.println("创建管网对象...");
        ENPLNetwork enplNetwork = new ENPLNetwork();
        enplNetwork.init(enplData);

        //计算
        ENPLSolve enplSolve = new ENPLSolve(enplNetwork);
        enplSolve.run();

        System.out.println(enplNetwork);
        System.out.println("--------------------");

    }
}
