package cn.edu.cup.tanyao.networksimulator.simulator;

import cn.edu.cup.tanyao.networksimulator.network.Ejector;

import java.util.List;

/**
 * 含引射器的管网对象模型
 * @author tanyao
 * @date 2019/9/29
 */
public class EjectorNetwork extends Network {
    /**
     * 管网中的引射器
     */
    private Ejector[] ejectors;

    public void setEjectors (List<List<String>> ejectors) {}

}
