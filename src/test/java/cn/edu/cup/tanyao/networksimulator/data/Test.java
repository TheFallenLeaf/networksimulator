package cn.edu.cup.tanyao.networksimulator.data;

import cn.edu.cup.tanyao.networksimulator.util.DataProcess;

import java.util.List;
import java.util.Map;

/**
 * @author tanyao
 * @date 2020/2/9
 */
public class Test {
    public static void main(String[] args) {
        Input input = new Input();
        Map temp = input.read();

        //管段数据
        List var1 = DataProcess.process((List<List<String>>) temp.get("管段数据"), true, false);
        //气体组分
        List var2 = DataProcess.process((List<List<String>>) temp.get("气体组分"), true, true);
        //空表测试
        List var3 = DataProcess.process((List<List<String>>) temp.get("测试表"), true, true);
        //产量数据
        List var4 = DataProcess.process((List<List<String>>) temp.get("产量数据"), true, false);

        System.out.println("管段数据");
        System.out.println(var1);
        System.out.println("气体组分");
        System.out.println(var2);
        System.out.println("空表测试");
        System.out.println(var3);
        System.out.println("产量数据");
        System.out.println(var4);
    }
}
