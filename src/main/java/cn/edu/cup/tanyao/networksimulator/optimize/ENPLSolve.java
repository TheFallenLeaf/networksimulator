package cn.edu.cup.tanyao.networksimulator.optimize;

import cn.edu.cup.tanyao.networksimulator.network.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tanyao
 * @date 2020/2/26
 */
public class ENPLSolve {
    /**
     *
     */
    private ENPLNetwork enplNetwork;

    public ENPLSolve(ENPLNetwork enplNetwork) {
        this.enplNetwork = enplNetwork;
    }

    /**
     * 只在井口安装引射器的求解
     */
    public void run() {
        NPLNetwork nplNetwork = generateNetwork();
        NPLSolve nplSolve = new NPLSolve(nplNetwork);
        nplSolve.run();
    }

    /**
     * 生成新的管网
     * @return
     */
    private NPLNetwork generateNetwork() {
        NPLNetwork nplNetwork = new NPLNetwork();

        //引射器计数
        int ejectorCount = enplNetwork.getEjectors().length;
        //需要舍弃的节点
        List<Integer> abandoned = new ArrayList<>();
        //新的井口节点
        List<Integer> newWell = new ArrayList<>();
        for (int i = 0; i < ejectorCount; i++) {
            //低压节点
            int low = (int) enplNetwork.getEjectors()[i].getLowNode().getUid();
            //高压节点
            int high = (int) enplNetwork.getEjectors()[i].getHighNode().getUid();
            //中压节点
            int middle = (int) enplNetwork.getEjectors()[i].getMiddleNode().getUid();
            abandoned.add(low);
            abandoned.add(high);
            newWell.add(middle);
        }
        //新管网节点,每个引射器使管网减少两个节点
        Node[] nodes = new Node[enplNetwork.getNodes().length-ejectorCount*2];
        int flag = 0;
        for (int i = 0; i < enplNetwork.getNodes().length; i++) {
            //节点编号
            int uid = enplNetwork.getNodes()[i].getUid();
            if (!abandoned.contains(uid)) {
                nodes[flag] = enplNetwork.getNodes()[i];
                flag ++;
            }
        }

        //新管网管段
        //包含舍弃的节点的管段也需要舍弃
        //新管网管段,每个引射器减少两个管段
        Pipe[] pipes = new Pipe[enplNetwork.getPipes().length-ejectorCount*2];
        flag = 0;
        for (int i = 0; i < enplNetwork.getPipes().length; i++) {
            //管段起始节点编号
            int startUid = enplNetwork.getPipes()[i].getStartNode().getUid();
            //管段终止节点编号
            int endUid = enplNetwork.getPipes()[i].getEndNode().getUid();
            if (!abandoned.contains(startUid) && !abandoned.contains(endUid)) {
                pipes[flag] = enplNetwork.getPipes()[i];
                flag ++;
            }
        }

        //新管网井口
        //舍弃高压井,低压井
        //新管网井，每个引射器减少两个井
        Well[] wells = new Well[enplNetwork.getWells().length-ejectorCount];
        flag = 0;
        //生成新井
        for (int i = 0; i < enplNetwork.getEjectors().length; i++) {
            //新井节点编号
            int middle = enplNetwork.getEjectors()[i].getMiddleNode().getUid();
            //生成新井
            EjectorWell ejectorWell = new EjectorWell(middle, null);
            //高压节点编号
            int high = enplNetwork.getEjectors()[i].getHighNode().getUid();
            //低压节点编号
            int low = enplNetwork.getEjectors()[i].getLowNode().getUid();
            //新井的产量曲线
            EjectorIPR ejectorIPR = new EjectorIPR();
            Well highWell = null;
            Well lowWell = null;
            for (int j = 0; j < enplNetwork.getWells().length; j++) {
                if (enplNetwork.getWells()[j].getUid() == high) {
                    highWell = enplNetwork.getWells()[j];
                }
                if (enplNetwork.getWells()[j].getUid() == low) {
                    lowWell = enplNetwork.getWells()[j];
                }
            }
            ejectorIPR.init(lowWell, highWell, enplNetwork.getEjectors()[i]);
            ejectorWell.setIpr(ejectorIPR);
            wells[flag] = ejectorWell;
            flag ++;
        }
        //保留跟引射器无关的井
        for (int i = 0; i < enplNetwork.getWells().length; i++) {
            //井的节点编号
            int uid = enplNetwork.getWells()[i].getUid();
            if (!abandoned.contains(uid) && !newWell.contains(uid)) {
                wells[flag] = enplNetwork.getWells()[i];
                flag ++;
            }
        }

        //改变引射器节点的参数
        for (int i = 0; i < newWell.size(); i++) {
            int uid = newWell.get(i);
            for (int j = 0; j < nodes.length; j++) {
                if (nodes[j].getUid() == uid) {
                    nodes[j].setNodeState(true);
                }
            }
        }

        nplNetwork.setNodes(nodes);
        nplNetwork.setPipes(pipes);
        nplNetwork.setGas(enplNetwork.getGas());
        nplNetwork.setWells(wells);

        return nplNetwork;
    }
}
