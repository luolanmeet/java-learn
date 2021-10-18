package com.pers.demo1;

import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;

import javax.swing.*;
import java.util.Random;

/**
 * @auther ken.ck
 * @date 2021/10/17 23:15
 */
public class Main {

    public static void main(String[] args) throws Exception {

        /* 加载数据 */
        // 读取数据
        ConverterUtils.DataSource source = new ConverterUtils.DataSource("demo1/zoo.arff");
        Instances data = source.getDataSet();

        // 移走animal属性
        Remove remove = new Remove();
        String[] opts = {"-R", "1"};
        remove.setOptions(opts);
        remove.setInputFormat(data);
        data = Filter.useFilter(data, remove);

        /* 特征选择（属性选择） */
        // 信息增益 评价器
        InfoGainAttributeEval eval = new InfoGainAttributeEval();
        // 排行器
        Ranker search = new Ranker();

        AttributeSelection attSelect = new AttributeSelection();
        attSelect.setEvaluator(eval);
        attSelect.setSearch(search);
        attSelect.SelectAttributes(data);

        // 输出最有价值的属性，无价值的属性可以剔除
        int[] indices = attSelect.selectedAttributes();
//        System.out.println(Arrays.toString(indices));

        /* 学习算法 */
        // J48决策树 学习器
        J48 tree = new J48();
        // 未剪枝树
        String[] options = new String[1];
        options[0] = "-U";
        tree.setOptions(options);
        tree.buildClassifier(data);
//        System.out.println(tree);
//        showTree(tree);

        /* 对新数据进行分类 */
//        testClassify(data, tree);

        /* 评估与预测误差度量 */
        // 交叉验证技术
        Classifier cl = new J48();
        Evaluation eval_roc = new Evaluation(data);
        eval_roc.crossValidateModel(cl, data, 10, new Random(1), new Object[] {});
//        System.out.println(eval_roc.toSummaryString());

        /* 混淆矩阵 */
        // 查看特定的错误分类出现在什么地方
        double[][] confusionMatrix = eval_roc.confusionMatrix();
        System.out.println(eval_roc.toMatrixString());
    }

    /**
     * 对数据进行分类
     * @param data
     * @param tree
     */
    private static void testClassify(Instances data, J48 tree) throws Exception {

        // 一只马  mammal 哺乳动物
        double[] vals = new double[data.numAttributes()];
        vals[0] = 1.0; // hair {false, true}
        vals[1] = 0.0; // feathers {false, true}
        vals[2] = 0.0; // eggs {false, true}
        vals[3] = 1.0; // milk {false, true}
        vals[4] = 0.0; // airborne {false, true}
        vals[5] = 0.0; // aquatic {false, true}
        vals[6] = 0.0; // predator {false, true}
        vals[7] = 1.0; // toothed {false, true}
        vals[8] = 1.0; // backbone {false, true}
        vals[9] = 1.0; // breathes {false, true}
        vals[10] = 1.0; // venomous {false, true}
        vals[11] = 0.0; // fins {false, true}
        vals[12] = 4.0; // legs INTEGER [0,9]
        vals[13] = 1.0; // tail {false, true}
        vals[14] = 1.0; // domestic {false, true}
        vals[15] = 0.0; // catsize {false, true}
        Instance myUnicorn = new DenseInstance(1.0, vals);
        //Assosiate your instance with Instance object in this case dataRaw
        myUnicorn.setDataset(data);

        double label = tree.classifyInstance(myUnicorn);
        System.out.println(data.classAttribute().value((int) label));
    }

    /**
     * 图像形式展示
     * @param tree
     * @throws Exception
     */
    private static void showTree(J48 tree) throws Exception {
        TreeVisualizer tv = new TreeVisualizer(null, tree.graph(), new PlaceNode2());
        JFrame frame = new javax.swing.JFrame("Tree Visualizer");
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(tv);
        frame.setVisible(true);
        tv.fitToScreen();
    }

}
