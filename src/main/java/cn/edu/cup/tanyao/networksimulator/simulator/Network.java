package cn.edu.cup.tanyao.networksimulator.simulator;

import cn.edu.cup.tanyao.networksimulator.network.Element;
import cn.edu.cup.tanyao.networksimulator.network.Gas;
import cn.edu.cup.tanyao.networksimulator.network.Node;
import cn.edu.cup.tanyao.networksimulator.network.Pipe;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 纯管网对象模型
 * @author tanyao
 * @date 2019/9/26
 */
public class Network {
    /**
     * 管网中的节点
     */
    private Node[] nodes;

    /**
     * 管网中的元件
     */
    private Element[] elements;

    /**
     * 管网中的流体
     */
    private Gas gas;

    /**
     * 空构造方法
     */
    public Network() {}

    /**
     * 导入管网中的节点
     * @param nodesSource
     */
    public void setNodes (List<List<String>> nodesSource) {
        Node[] nodes = new Node[nodesSource.size()];
        for (int i = 0; i < nodes.length; i++) {
            //获取节点参数对象
            List<String> nodeSource = nodesSource.get(i);
            //节点编号
            int uid = (int) Double.parseDouble(nodeSource.get(0));
            //创建新的节点对象
            Node node = new Node(uid);
            //设置节点压力
            node.setPressure(Double.parseDouble(nodeSource.get(1)));
            //设置节点压力状态
            node.setPressureState(((int) Double.parseDouble(nodeSource.get(2)) == 1));
            //设置节点载荷
            node.setLoad(Double.parseDouble(nodeSource.get(3)));
            //设置节点载荷状态
            node.setLoadState(((int) Double.parseDouble(nodeSource.get(4)) == 1));
            //设置节点海拔
            node.setElevation(Double.parseDouble(nodeSource.get(5)));
            //
            nodes[i] = node;
        }
        this.nodes = nodes;
    }

    /**
     * 导入管网中的元件
     * @param elementsSource
     */
    public void setElements (List<List<String>> elementsSource) {
        Element[] elements = new Element[elementsSource.size()];
        for (int i = 0; i < elements.length; i++) {
            //创建管网元件参数对象
            List<String> elementSource = elementsSource.get(i);
            //管段编号
            int uid = (int) Double.parseDouble(elementSource.get(0));
            //元件起点编号
            int startNumber = (int) Double.parseDouble(elementSource.get(1));
            //元件终点编号
            int endNumber = (int) Double.parseDouble(elementSource.get(2));
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
            //创建新的管网元件
            Pipe pipe = new Pipe(startNode, endNode, uid);
            //设置管段长度
            pipe.setLength(Double.parseDouble(elementSource.get(3)));
            //设置管段内径
            pipe.setDiameter(Double.parseDouble(elementSource.get(4)));
            //设置管壁粗糙度
            pipe.setRoughness(Double.parseDouble(elementSource.get(5)));
            //设置管段摩阻
            pipe.setLambda();

            elements[i] = pipe;
        }
        this.elements = elements;
    }

    /**
     * 导入管网中的流体
     * @param components 流体组分
     */
    public void setGas (List<List<String>> components) {
        double[] component = new double[components.size()];
        //设置气体组分
        for (int i = 0; i < component.length; i++) {
            component[i] = Double.parseDouble(components.get(i).get(1));
        }
        Gas gas = new Gas(component);
        gas.setRelativeDensity();
        this.gas = gas;
    }

    /**
     * 初始化管网
     * @param nodesSource 节点参数
     * @param elementsSource 元件参数
     * @param component 气体组分
     */
    public void init (List<List<String>> nodesSource,
                      List<List<String>> elementsSource,
                      List<List<String>> component) {
        //必须先设置节点
        setNodes(nodesSource);
        setElements(elementsSource);
        setGas(component);
    }

    public Node[] getNodes () {
        return this.nodes;
    }

    public Element[] getElements () {
        return this.elements;
    }

    public Gas getGas () {
        return this.gas;
    }

    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("##.####");

        String pressure = "节点压力:";
        for (int i = 0; i < nodes.length; i++) {
            pressure += "\n";
            pressure += decimalFormat.format(nodes[i].getPressure());
        }
        return pressure;
    }
}
