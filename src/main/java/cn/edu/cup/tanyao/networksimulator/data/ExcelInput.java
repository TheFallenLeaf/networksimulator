package cn.edu.cup.tanyao.networksimulator.data;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

/**
 * @author tanyao
 * @date 2019/10/3
 */
public class ExcelInput {
    /**
     * 文件名
     */
    private String fileName = "networksimulator.xlsx";

    /**
     * 文件绝对路径
     */
    private String absolutePath;

    /**
     * 空构造方法
     */
    public ExcelInput () {}

    /**
     *
     * @param fileName
     */
    public ExcelInput (String fileName) {
        this.fileName = fileName;
    }

    public XSSFWorkbook getWorkbook () {
        XSSFWorkbook workbook = null;
        File file = new File(fileName);
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(file);
            workbook = new XSSFWorkbook(inputStream);
        } catch (FileNotFoundException e1) {
            System.out.println("找不到文件！");
        } catch (IOException e2) {
            System.out.println("文件打开出错！");
        }
        return workbook;
    }

    public Map<String, List> getData (XSSFWorkbook workbook) {
        int sheetCount = workbook.getNumberOfSheets();
        Map<String, List> data = new HashMap<>();
        for (int i = 0; i < sheetCount; i++) {
            //列数
            int columnCount = workbook.getSheetAt(i).getRow(0).getPhysicalNumberOfCells();
            //行数
            int rowCount = workbook.getSheetAt(i).getLastRowNum();
            //工作簿名称
            String string = workbook.getSheetName(i);

            List<List<String>> var1 = new LinkedList<>();
            List<String> var2 = new LinkedList<>();
            String var3 = null;

            //去掉标题行
            for (int j = 1; j <= rowCount; j++) {
                for (int k = 0; k < columnCount; k++) {
                    //获取单元格的值
                    var3 = String.valueOf(workbook.getSheetAt(i).getRow(j).getCell(k));
                    var2.add(k, new String(var3));
                }
                var1.add(j-1, new ArrayList<>(var2));
                var2.clear();
            }
            data.put(new String(string), new ArrayList<>(var1));
            var1.clear();
        }
        return data;
    }


    public void setFileName (String fileName) {
        this.fileName = fileName;
    }

    public void setAbsolutePath (String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getFileName () {
        return this.fileName;
    }

    public String getAbsolutePath () {
        return this.absolutePath;
    }

    public static void main(String[] args) {
        ExcelInput excelInput = new ExcelInput("networksimulator.xlsx");
        System.out.println(excelInput.getData(excelInput.getWorkbook()));;
    }
}
