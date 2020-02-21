package cn.edu.cup.tanyao.networksimulator.util;

import java.util.List;

/**
 * @author tanyao
 * @date 2020/2/12
 */
public class DataProcess {
    /**
     * 清除标题列
     * @param list 数据源
     * @param rowTitle 是否清除行标题
     * @param columnTitle 是否清除列标题
     * @return
     */
    public static List process(List<List<String>> list, boolean rowTitle, boolean columnTitle) {

        //处理空表
        if (list == null) {
            return null;
        }

        //去掉行标题
        if (rowTitle) {
            list.remove(0);
        }

        //去掉列标题
        if (columnTitle) {
            int var1 = list.size();
            for (int i = 0; i < var1; i++) {
                List var2 = list.get(i);
                var2.remove(0);
            }
        }

        return list;
    }
}
