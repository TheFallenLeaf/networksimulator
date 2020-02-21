package cn.edu.cup.tanyao.networksimulator.data;

import cn.edu.cup.tanyao.networksimulator.util.DataProcess;

import java.util.List;

/**
 * @author tanyao
 * @date 2020/2/12
 */
public class NPLData {
    /**
     * 管网中的节点数据
     */
    private List<List<String>> nodes;

    /**
     * 管网中的管段数据
     */
    private List<List<String>> pipes;

    /**
     * 气体组分数据
     */
    private List<List<String>> component;

    /**
     * 产量数据
     */
    private List<List<String>> productions;

    /**
     * 井口对照表
     */
    private List<List<String>> wells;

    public void process(){
        //设置节点,去除行标题
        setNodes(DataProcess.process(nodes, true, false));
        //设置管段,清除行标题
        setPipes(DataProcess.process(pipes, true, false));
        //设置组分,清除行标题和列标题
        setComponent(DataProcess.process(component, true, false));
        //设置产量数据,不清除标题
        setProductions(DataProcess.process(productions, false, false));
        //设置井口对应表,清除行标题
        setWells(DataProcess.process(wells, true, false));
    }

    public List<List<String>> getNodes() {
        return nodes;
    }

    public void setNodes(List<List<String>> nodes) {
        this.nodes = nodes;
    }

    public List<List<String>> getPipes() {
        return pipes;
    }

    public void setPipes(List<List<String>> pipes) {
        this.pipes = pipes;
    }

    public List<List<String>> getComponent() {
        return component;
    }

    public void setComponent(List<List<String>> component) {
        this.component = component;
    }

    public List<List<String>> getProductions() {
        return productions;
    }

    public void setProductions(List<List<String>> productions) {
        this.productions = productions;
    }

    public List<List<String>> getWells() {
        return wells;
    }

    public void setWells(List<List<String>> wells) {
        this.wells = wells;
    }
}
