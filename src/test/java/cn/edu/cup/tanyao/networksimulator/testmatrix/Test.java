package cn.edu.cup.tanyao.networksimulator.testmatrix;

/**
 * @author tanyao
 * @date 2019/10/18
 */
public class Test {
    public static void main(String[] args) {
        int[] var1 = {100, 200, 400, 600, 800, 1000};

        for (int i = 0; i < var1.length; i++) {
            System.out.println("矩阵维度："+ var1[i]);

            Source source = new Source(var1[i]);
            source.init();

            TestJama testJama = new TestJama();
            testJama.run(source, 10000);

            TestEJML testEJML = new TestEJML();
            testEJML.run(source, 10000);

            TestUJMP testUJMP = new TestUJMP();
            testUJMP.run(source, 10000);

            TestMath3 testMath3 = new TestMath3();
            testMath3.run(source, 10000);
        }
    }
}
