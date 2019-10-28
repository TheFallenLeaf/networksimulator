package cn.edu.cup.tanyao.networksimulator.data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 纯管网对象模型数据
 * @author tanyao
 * @date 2019/9/27
 */
public class NetworkData {
    /**
     * 节点数据
     * 压力单位统一使用MPa
     * 流量单位统一使用Nm³/d
     * 海拔单位使用m
     */
    private List<List<String>> nodes;

    /**
     * 管网元件数据
     */
    private List<List<String>> elements;

    /**
     * 气体组分数据
     */
    private List<List<String>> component;

    /**
     * 空构造方法
     */
    public NetworkData () {
    }

    /**
     * 直接初始化管网数据
     * 用于测试程序
     */
    public void init () {
        //节点数据
        double[][] nodesData = {
                {1, 2, 1, 0, 0, 212},
                {2, 2, 0, 0, 1, 50},
                {3, 2, 0, 0, 1, 98},
                {4, 2, 0, 0, 1, 23},
                {5, 2, 0, 12354, 1, 0},
                {6, 2, 0, 8964, 1, -54},
                {7, 2, 0, 2354, 1, 68},
                {8, 2, 0, 3698, 1, 19},
                {9, 2, 0, 8889, 1, 37},
        };
        //管段数据
        double[][] pipesData = {
                {1, 2, 1, 1522, 0.1, 0.0000457},
                {2, 3, 2, 3958, 0.1, 0.0000457},
                {3, 4, 2, 2594, 0.1, 0.0000457},
                {4, 5, 4, 1789, 0.1, 0.0000457},
                {5, 6, 4, 3492, 0.1, 0.0000457},
                {6, 7, 3, 1236, 0.1, 0.0000457},
                {7, 8, 3, 2893, 0.1, 0.0000457},
                {8, 9, 3, 1259, 0.1, 0.0000457},
        };
        //气体组分
        String[] componentPart = {"C1", "C2", "C3", "iC4", "nC4", "iC5", "nC5", "C6", "N2", "CO2"};
        double[] componentData = {0.9496, 0.0088, 0.0018, 0.0003, 0.0004, 0, 0, 0, 0.0095, 0.0296};

        //
        List<List<String>> var1 = new LinkedList<>();
        List<String> var2 = new LinkedList<>();

        //初始化节点数据
        for (int i = 0; i < nodesData.length; i++) {
            for (int j = 0; j < nodesData[0].length; j++) {
                var2.add(j, String.valueOf(nodesData[i][j]));
            }
            var1.add(i, new ArrayList<>(var2));
            var2.clear();
        }
        this.nodes = new ArrayList<>(var1);
        var1.clear();
        var2.clear();

        //初始化管段数据
        for (int i = 0; i < pipesData.length; i++) {
            for (int j = 0; j < pipesData[0].length; j++) {
                var2.add(j, String.valueOf(pipesData[i][j]));
            }
            var1.add(i, new ArrayList<>(var2));
            var2.clear();
        }
        this.elements = new ArrayList<>(var1);
        var1.clear();
        var2.clear();

        //初始化气体组分
        for (int i = 0; i < componentData.length; i++) {
            var2.add(0, componentPart[i]);
            var2.add(1, String.valueOf(componentData[i]));
            var1.add(i, new ArrayList<>(var2));
            var2.clear();
        }
        this.component = new ArrayList<>(var1);
        var1.clear();
        var2.clear();
    }

    public void setNodes (List<List<String>> nodes) {
        this.nodes = nodes;
    }

    public void setElements (List<List<String>> elements) {
        this.elements = elements;
    }

    public void setComponent (List<List<String>> component) {
        this.component = component;
    }

    public List<List<String>> getNodes () {
        return this.nodes;
    }

    public List<List<String>> getElements () {
        return this.elements;
    }

    public List<List<String>> getComponent () {
        return this.component;
    }
}
