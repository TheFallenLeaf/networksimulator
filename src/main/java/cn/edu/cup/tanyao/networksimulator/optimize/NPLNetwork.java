package cn.edu.cup.tanyao.networksimulator.optimize;

import cn.edu.cup.tanyao.networksimulator.data.NPLData;
import cn.edu.cup.tanyao.networksimulator.network.*;

import java.text.DecimalFormat;
import java.util.List;

/**
 * @author tanyao
 * @date 2020/2/18
 */
public class NPLNetwork {
    private Node[] nodes;
    private Pipe[] pipes;
    private Gas gas;
    private Well[] wells;

    public void setNodes(List<List<String>> nodesSource){
        Node[] nodes = new Node[nodesSource.size()];
        for (int i = 0; i < nodes.length; i++) {
            //获取节点参数对象
            List<String> nodeSource = nodesSource.get(i);
            //节点编号
            int uid = (int) Double.parseDouble(nodeSource.get(0));
            //创建新的节点对象
            Node node = new Node(uid);
            //设置节点海拔
            node.setElevation(Double.parseDouble(nodeSource.get(1)));
            //节点是否有载荷
            node.setNodeState(((int) Double.parseDouble(nodeSource.get(2)) == 1));
            //节点压力
            node.setPressure(Double.parseDouble(nodeSource.get(3)));
            //节点压力是否已知
            node.setPressureState(((int) Double.parseDouble(nodeSource.get(4)) == 1));

            nodes[i] = node;
        }
        this.nodes = nodes;
    }

    public void setPipes(List<List<String>> pipesSource){
        Pipe[] pipes = new Pipe[pipesSource.size()];
        for (int i = 0; i < pipes.length; i++) {
            //创建管段数据对象
            List<String> pipeSource = pipesSource.get(i);
            //管段编号
            int uid = (int) Double.parseDouble(pipeSource.get(0));
            //管段起点编号
            int startNumber = (int) Double.parseDouble(pipeSource.get(1));
            //管段终点编号
            int endNumber = (int) Double.parseDouble(pipeSource.get(2));
            //根据节点编号寻找节点
            Node startNode = null;
            Node endNode = null;
            for (int j = 0; j < this.nodes.length; j++) {
                if (this.nodes[j].getUid() == startNumber) {
                    startNode = this.nodes[j];
                }
                if (this.nodes[j].getUid() == endNumber) {
                    endNode = this.nodes[j];
                }
            }
            //创建管段对象
            Pipe pipe = new Pipe(startNode, endNode, uid);
            //设置管段长度
            pipe.setLength(Double.parseDouble(pipeSource.get(3)));
            //设置管段内径
            pipe.setDiameter(Double.parseDouble(pipeSource.get(4)));
            //设置管壁粗糙度
            pipe.setRoughness(Double.parseDouble(pipeSource.get(5)));
            //设置管段摩阻
            pipe.setLambda();

            pipes[i] = pipe;
        }
        this.pipes = pipes;
    }

    public void setGas(List<List<String>> components){
        double[] component = new double[components.size()];
        //设置气体组分
        for (int i = 0; i < component.length; i++) {
            component[i] = Double.parseDouble(components.get(i).get(1)) / 100;
        }
        Gas gas = new Gas(component);
        gas.setRelativeDensity();
        this.gas = gas;
    }

    public void setWells(List<List<String>> productions,
                         List<List<String>> wellInfo){
        //井
        Well[] wells = new Well[wellInfo.size()];
        for (int i = 0; i < wellInfo.size(); i++) {
            //创建气井对象
            Well well = new Well((int) Double.parseDouble(wellInfo.get(i).get(0)), wellInfo.get(i).get(1));
            //跳过集气站
            if (wellInfo.get(i).get(1).equals("集气站")){
                wells[i] = well;
                continue;
            }
            //产量数据
            double[] xi = new double[productions.size()-1];
            double[] yi = new double[productions.size()-1];
            //井口所在列
            int flag = productions.get(0).indexOf(well.getName());
            for (int j = 0; j < xi.length; j++) {
                xi[j] = Double.parseDouble(productions.get(j+1).get(0));
                yi[j] = Double.parseDouble(productions.get(j+1).get(flag));
            }
            IPR ipr = new IPR();
            ipr.init(xi, yi, 3);
            well.setIpr(ipr);

            wells[i] = well;
        }
        this.wells = wells;
    }

    public void init(NPLData nplData) {
        setNodes(nplData.getNodes());
        setPipes(nplData.getPipes());
        setGas(nplData.getComponent());

        setWells(nplData.getProductions(), nplData.getWells());
    }

    public void setNodes(Node[] nodes) {
        this.nodes = nodes;
    }

    public void setPipes(Pipe[] pipes) {
        this.pipes = pipes;
    }

    public void setGas(Gas gas) {
        this.gas = gas;
    }

    public void setWells(Well[] wells) {
        this.wells = wells;
    }

    public Node[] getNodes() {
        return nodes;
    }

    public Pipe[] getPipes() {
        return pipes;
    }

    public Gas getGas() {
        return gas;
    }

    public Well[] getWells() {
        return wells;
    }

    @Override
    public String toString() {
        //压力格式
        DecimalFormat decimalFormat1 = new DecimalFormat("##.####");
        //流量格式
        DecimalFormat decimalFormat2 = new DecimalFormat("#####.##");

        String value = "节点压力:";
        for (int i = 0; i < nodes.length; i++) {
            value += "\n";
            value += decimalFormat1.format(nodes[i].getPressure());
        }
        value += "\n";

        value += "节点载荷:";
        for (int i = 0; i < nodes.length; i++) {
            value += "\n";
            value += decimalFormat2.format(nodes[i].getLoad());
        }

        return value;
    }
}
