package cn.edu.hit.views;

import cn.edu.hit.binarytree.BinaryTree;
import cn.edu.hit.binarytree.TreeNode;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.plaf.FontUIResource;

/**
 * 用于二叉树遍历的可视化
 *
 * @author Edmund_Lai
 * @e-mail 1191000311@stu.hit.edu.cn
 * @create 2021-10-06-10:47
 */
public class BinaryTreeWindow {

    /*
    binaryTree - 二叉树信息
    frame - 可视化窗口对象
    panel - 嵌入可视化窗口中的绘制区域
    textPanel - 用于显式各种按钮、文本的区域
    outputPanel - 打印遍历顺序
    outputArea - outputPanel被嵌入的区域
    BACKGROUND_COLOR - frame的背景颜色
     */
    private final BinaryTree binaryTree;
    private final JFrame frame = new JFrame("Binary Tree Traversal Visualization Program");
    private final DrawAreaPanel drawPanel;
    private final JPanel outputPanel = new JPanel();
    private final JPanel textPanel = new JPanel();
    private final JTextArea outputArea = new JTextArea();
    private final static Color BACKGROUND_COLOR = new Color(247, 246, 233);


    public BinaryTreeWindow(BinaryTree binaryTree) {
        this.binaryTree = binaryTree;
        this.drawPanel = new DrawAreaPanel(binaryTree);
        initializeWindow();
    }

    /**
     * 初始化窗口
     */
    private void initializeWindow() {
        // 可视化窗口的初始化
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 630);
        frame.getContentPane().setLayout(null);

        // 绘制区域的初始化
        drawPanel.setBackground(null);
        drawPanel.setLayout(null);
        drawPanel.setBorder(BorderFactory.createLineBorder(new Color(49, 113, 87), 3));
        drawPanel.setBounds(50, 50, 500, 330);

        //输出序列区域的初始化
        outputPanel.setBackground(null);
        outputPanel.setLayout(null);
        outputPanel.setBorder(BorderFactory.createLineBorder(new Color(49, 113, 87), 3));
        outputPanel.setBounds(50, 430, 500, 100);

        //输出序列的初始化
        outputArea.setBackground(null);
        outputArea.setLayout(null);
        outputArea.setFont(new Font("New Times Roman", Font.PLAIN, 20));
        //outputArea.setEditable(false);
        outputArea.setBounds(80, 480, 400, 40);

        // 功能区域的初始化
        textPanel.setBackground(null);
        textPanel.setLayout(null);
        textPanel.setBorder(BorderFactory.createLineBorder(new Color(49, 113, 87), 3));
        textPanel.setBounds(600, 50, 325, 480);

        // 添加到可视化窗口的容器中
        frame.getContentPane().add(drawPanel);
        frame.getContentPane().add(outputPanel);
        frame.getContentPane().add(textPanel);
        frame.getContentPane().add(outputArea);
        frame.getContentPane().setBackground(BACKGROUND_COLOR);
        frame.setResizable(false);


        //------------------------------------标签声明开始-----------------------------------------
        JLabel addNodeLabel = new JLabel("Node Insertion");
        addNodeLabel.setBounds(25, 25, 400, 50);
        addNodeLabel.setFont(addNodeLabel.getFont().deriveFont(30.0f));
        textPanel.add(addNodeLabel);

        JLabel orderSelectLabel = new JLabel("Traverse");
        orderSelectLabel.setBounds(25, 250, 400, 50);
        orderSelectLabel.setFont(orderSelectLabel.getFont().deriveFont(30.0f));
        textPanel.add(orderSelectLabel);

        JLabel outputSequenceLabel = new JLabel("Output Sequence:");
        outputSequenceLabel.setBounds(15, 10, 400, 40);
        outputSequenceLabel.setFont(outputSequenceLabel.getFont().deriveFont(25.0f));
        outputPanel.add(outputSequenceLabel);

        //------------------------------------按钮声明结束-----------------------------------------


        //------------------------------------按钮声明开始-----------------------------------------
        // 三种遍历的单选按钮初始化
        JRadioButton preOrderButton = new JRadioButton("Pre-Order");
        preOrderButton.setBounds(30, 315, 135, 40);
        preOrderButton.setFont(preOrderButton.getFont().deriveFont(20.0f));
        JRadioButton inOrderButton = new JRadioButton("In-Order");
        inOrderButton.setBounds(30, 365, 135, 40);
        inOrderButton.setFont(inOrderButton.getFont().deriveFont(20.0f));
        JRadioButton postOrderButton = new JRadioButton("Post-Order");
        postOrderButton.setBounds(30, 415, 135, 40);
        postOrderButton.setFont(postOrderButton.getFont().deriveFont(20.0f));
        preOrderButton.setBackground(null);
        inOrderButton.setBackground(null);
        postOrderButton.setBackground(null);

        textPanel.add(preOrderButton);
        textPanel.add(inOrderButton);
        textPanel.add(postOrderButton);

        //声明一个ButtonGroup来保证同一时间按钮只能选择一个遍历方式
        ButtonGroup orderGroup = new ButtonGroup();
        orderGroup.add(preOrderButton);
        orderGroup.add(inOrderButton);
        orderGroup.add(postOrderButton);

        // 开始遍历、重置遍历结果的按钮初始化
        JButton traversalButton = new JButton("Start");
        traversalButton.setBounds(175, 336, 100, 40);
        traversalButton.setFont(traversalButton.getFont().deriveFont(20.0f));
        textPanel.add(traversalButton);

        // 重置按钮初始化
        JButton resetButton = new JButton("Reset");
        resetButton.setBounds(175, 394, 100, 40);
        resetButton.setFont(resetButton.getFont().deriveFont(20.0f));
        textPanel.add(resetButton);

        // 确认插入结点
        JButton confirmButton = new JButton("Insert");
        confirmButton.setBounds(50, 165, 100, 40);
        confirmButton.setFont(confirmButton.getFont().deriveFont(20.0f));
        textPanel.add(confirmButton);

        //删除结点
        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(175, 165, 100, 40);
        deleteButton.setFont(deleteButton.getFont().deriveFont(20.0f));
        deleteButton.setBackground(new Color(147, 209, 255));
        textPanel.add(deleteButton);
        //------------------------------------按钮声明结束-----------------------------------------


        //------------------------------------下拉框选择声明开始-----------------------------------------
        // 方向选择
        JComboBox<String> directionBox = new JComboBox();
        directionBox.setBounds(175, 100, 100, 40);
        directionBox.setFont(directionBox.getFont().deriveFont(20.0f));
        textPanel.add(directionBox);

        // 结点选择
        JComboBox<Integer> indexBox = new JComboBox();
        indexBox.setBounds(50, 100, 100, 40);
        indexBox.setFont(indexBox.getFont().deriveFont(20.0f));
        textPanel.add(indexBox);

        //------------------------------------下拉框选择声明结束-----------------------------------------


        //------------------------------------按钮绑定事件开始-----------------------------------------
        //确认插入事件绑定
        confirmButton.addActionListener(e -> {
            Integer selectedNodeIndex = (Integer) indexBox.getSelectedItem();
            if(selectedNodeIndex>= (1<<BinaryTree.MAX_DEPTH)){ //防止插入过多的点显示失败
                UIManager.put("OptionPane.minimumSize", new Dimension(400,100));
                UIManager.put("OptionPane.messageFont", new Font(null, Font.PLAIN, 18));
                UIManager.put("OptionPane.buttonFont", new Font(null, Font.BOLD, 14));
                UIManager.put("OptionPane.okButtonText", "OK");
                JOptionPane.showMessageDialog(frame,"Can't insert more node at this node!","Warning!",JOptionPane.WARNING_MESSAGE);
                return;
            }
            String selectedDirectionString = (String) directionBox.getSelectedItem();
            Integer selectedDirection = (selectedDirectionString.equals("Left")) ? BinaryTree.ADD_LEFT : BinaryTree.ADD_RIGHT;
            binaryTree.addTreeNode(binaryTree.getTreeNodeByIndex(selectedNodeIndex),selectedDirection);

            // 更新下拉框
            refreshIndexBox(indexBox);

            // 绘图
            drawPanel.repaint();
        });

        //删除事件绑定
        deleteButton.addActionListener(e -> {
            Integer selectedNodeIndex = (Integer) indexBox.getSelectedItem();
            if (selectedNodeIndex.equals(new Integer(1))){
                UIManager.put("OptionPane.minimumSize", new Dimension(400,100));
                UIManager.put("OptionPane.messageFont", new Font(null, Font.PLAIN, 18));
                UIManager.put("OptionPane.buttonFont", new Font(null, Font.BOLD, 14));
                UIManager.put("OptionPane.okButtonText", "OK");
                JOptionPane.showMessageDialog(frame,"Can't delete root node!","Warning!",JOptionPane.WARNING_MESSAGE);
                return;
            }
            binaryTree.deleteTreeNodeByIndex(selectedNodeIndex);

            // 更新下拉框
            refreshIndexBox(indexBox);

            // 绘图
            drawPanel.repaint();
        });

        //开始遍历事件绑定
        traversalButton.addActionListener(e -> {

            // repaint方法不总是立刻生效，Swing框架默认的行为是会合并多个repaint的操作的
            // 正确的操作是使用多线程的方式处理
            new Thread(()->{
                Enumeration<AbstractButton> orderButtons = orderGroup.getElements();
                while (orderButtons.hasMoreElements()){
                    AbstractButton selectedOrderButton = orderButtons.nextElement();
                    if (selectedOrderButton.isSelected()){

                        // 无效化插入、遍历、重置的按钮，防止误触导致程序崩溃
                        traversalButton.setEnabled(false);
                        resetButton.setEnabled(false);
                        confirmButton.setEnabled(false);
                        deleteButton.setEnabled(false);

                        String selection = selectedOrderButton.getText();
                        if (binaryTree.getTraversalOrder().size()!=0){
                            UIManager.put("OptionPane.minimumSize", new Dimension(400,100));
                            UIManager.put("OptionPane.messageFont", new Font(null, Font.PLAIN, 18));
                            UIManager.put("OptionPane.buttonFont", new Font(null, Font.BOLD, 14));
                            UIManager.put("OptionPane.okButtonText", "OK");
                            JOptionPane.showMessageDialog(frame,"Please push the reset button before next traversal !","Warning!",JOptionPane.WARNING_MESSAGE);
                            traversalButton.setEnabled(true);
                            resetButton.setEnabled(true);
                            confirmButton.setEnabled(true);
                            deleteButton.setEnabled(true);
                            return;
                        }
                        switch (selection){
                            case "Pre-Order":
                                binaryTree.preOrderTraversal(binaryTree.getHead());
                                break;
                            case "In-Order":
                                binaryTree.inOrderTraversal(binaryTree.getHead());
                                break;
                            case "Post-Order":
                                binaryTree.postOrderTraversal(binaryTree.getHead());
                                break;
                            default:break;
                        }
                        List<Integer> traversalOrder = binaryTree.getTraversalOrder();
                        int count = 0;
                        for (Integer treeNodeIndex : traversalOrder) {
                            TreeNode treeNode = binaryTree.getTreeNodeByIndex(treeNodeIndex);
                            if (count==0){
                                outputArea.append(String.valueOf(treeNodeIndex));
                                count++;
                            }else {
                                outputArea.append("\u2192"+treeNodeIndex);
                            }
                            treeNode.setColor(BinaryTree.AFTER_TRAVERSAL_COLOR);
                            drawPanel.repaint();

                            try {
                                TimeUnit.MILLISECONDS.sleep(500);
                            } catch (InterruptedException interruptedException) {
                                interruptedException.printStackTrace();
                            }
                        }

                        traversalButton.setEnabled(true);
                        resetButton.setEnabled(true);
                        confirmButton.setEnabled(true);
                        deleteButton.setEnabled(true);
                        return;
                    }
                }

                UIManager.put("OptionPane.minimumSize", new Dimension(400,100));
                UIManager.put("OptionPane.messageFont", new Font(null, Font.PLAIN, 18));
                UIManager.put("OptionPane.buttonFont", new Font(null, Font.BOLD, 14));
                UIManager.put("OptionPane.okButtonText", "OK");
                JOptionPane.showMessageDialog(frame,"Please choose an order for traversal !","Warning!",JOptionPane.WARNING_MESSAGE);
            }).start();

        });

        //重置事件绑定
        resetButton.addActionListener(e->{
            List<TreeNode> treeNodes = binaryTree.getNodes();
            for (TreeNode treeNode : treeNodes) {
                treeNode.setColor(BinaryTree.NODE_COLOR);
            }
            drawPanel.repaint();
            outputArea.setText("");
            binaryTree.getTraversalOrder().clear();
        });

        //------------------------------------按钮绑定事件结束-----------------------------------------


        //------------------------------------下拉框内容更新开始-----------------------------------------
        //可选结点初始化
        refreshIndexBox(indexBox);

        //左右两个选项
        directionBox.addItem("Left");
        directionBox.addItem("Right");

        //监听当前选中结点的左右结点位置是否能够插入
        indexBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) { //选择的结点变化的情况重新确认哪些方向可以插入
                Integer selectedNodeIndex = (Integer) indexBox.getSelectedItem(); //得到选中结点的索引
                directionBox.removeAllItems();// 首先清除所有选项，然后根据选择的结点提供左、右插入的选项
                if (this.binaryTree.getTreeNodeByIndex(selectedNodeIndex).getLeftChild() == null) {
                    directionBox.addItem("Left");
                }
                if (this.binaryTree.getTreeNodeByIndex(selectedNodeIndex).getRightChild() == null) {
                    directionBox.addItem("Right");
                }
            }
        });
        //------------------------------------下拉框内容更新结束-----------------------------------------

    }

    /**
     * 为 indexBox 刷新可扩展结点的列表
     * @param indexBox 保存可以扩展结点的列表
     */
    private void refreshIndexBox(JComboBox<Integer> indexBox){
        indexBox.removeAllItems();
        for (Integer scalableNodeIndex : this.binaryTree.getScalableNodes()) {
            indexBox.addItem(scalableNodeIndex);
        }
    }

    /**
     * 设置可视化窗口是否可见
     *
     * @param visible 是否可见
     */
    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}
