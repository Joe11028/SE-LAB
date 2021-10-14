package cn.edu.hit.views;

import cn.edu.hit.binarytree.BinaryTree;
import cn.edu.hit.binarytree.TreeNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
     */
    private final BinaryTree binaryTree;
    private final JFrame frame = new JFrame("二叉树遍历可视化小程序");
    private final DrawAreaPanel drawPanel;
    private final JPanel textPanel = new JPanel();

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
        frame.setSize(1200, 650);
        frame.getContentPane().setLayout(null);

        // 绘制区域的初始化
        drawPanel.setBackground(Color.WHITE);
        drawPanel.setLayout(null);
        drawPanel.setBounds(50, 50, 500, 500);

        // 文本区域的初始化
        textPanel.setBackground(Color.WHITE);
        textPanel.setLayout(null);
        textPanel.setBounds(600, 50, 500, 500);

        // 添加到可视化窗口的容器中
        frame.getContentPane().add(drawPanel);
        frame.getContentPane().add(textPanel);


        //------------------------------------标签声明开始-----------------------------------------
        JLabel addNodeLabel = new JLabel("请选择结点编号与插入结点的方向:");
        addNodeLabel.setBounds(50, 50, 400, 50);
        addNodeLabel.setFont(addNodeLabel.getFont().deriveFont(20.0f));
        textPanel.add(addNodeLabel);

        JLabel orderSelectLabel = new JLabel("请选择遍历方式：");
        orderSelectLabel.setBounds(50, 260, 400, 50);
        orderSelectLabel.setFont(orderSelectLabel.getFont().deriveFont(20.0f));
        textPanel.add(orderSelectLabel);
        //------------------------------------按钮声明结束-----------------------------------------


        //------------------------------------按钮声明开始-----------------------------------------
        // 三种遍历的单选按钮初始化
        JRadioButton preOrderButton = new JRadioButton("前序遍历");
        preOrderButton.setBounds(25, 310, 125, 50);
        preOrderButton.setFont(preOrderButton.getFont().deriveFont(20.0f));
        JRadioButton inOrderButton = new JRadioButton("中序遍历");
        inOrderButton.setBounds(175, 310, 125, 50);
        inOrderButton.setFont(inOrderButton.getFont().deriveFont(20.0f));
        JRadioButton postOrderButton = new JRadioButton("后序遍历");
        postOrderButton.setBounds(325, 310, 125, 50);
        postOrderButton.setFont(postOrderButton.getFont().deriveFont(20.0f));
        textPanel.add(preOrderButton);
        textPanel.add(inOrderButton);
        textPanel.add(postOrderButton);

        //声明一个ButtonGroup来保证同一时间按钮只能选择一个遍历方式
        ButtonGroup orderGroup = new ButtonGroup();
        orderGroup.add(preOrderButton);
        orderGroup.add(inOrderButton);
        orderGroup.add(postOrderButton);

        // 开始遍历、重置遍历结果的按钮初始化
        JButton traversalButton = new JButton("开始遍历");
        traversalButton.setBounds(50, 380, 150, 40);
        traversalButton.setFont(traversalButton.getFont().deriveFont(20.0f));
        textPanel.add(traversalButton);

        // 重置按钮初始化
        JButton resetButton = new JButton("重置");
        resetButton.setBounds(270, 380, 150, 40);
        resetButton.setFont(resetButton.getFont().deriveFont(20.0f));
        textPanel.add(resetButton);

        // 确认插入结点
        JButton confirmButton = new JButton("确认插入");
        confirmButton.setBounds(70, 200, 130, 40);
        confirmButton.setFont(confirmButton.getFont().deriveFont(20.0f));
        textPanel.add(confirmButton);

        // 确认删除结点
        JButton deleteButton = new JButton("删除");
        deleteButton.setBounds(270, 200, 130, 40);
        deleteButton.setFont(deleteButton.getFont().deriveFont(20.0f));
        textPanel.add(deleteButton);
        //------------------------------------按钮声明结束-----------------------------------------


        //------------------------------------下拉框选择声明开始-----------------------------------------
        // 方向选择
        JComboBox<String> directionBox = new JComboBox();
        directionBox.setBounds(270, 120, 150, 40);
        directionBox.setFont(directionBox.getFont().deriveFont(20.0f));
        textPanel.add(directionBox);

        // 结点选择
        JComboBox<Integer> indexBox = new JComboBox();
        indexBox.setBounds(70, 120, 150, 40);
        indexBox.setFont(indexBox.getFont().deriveFont(20.0f));
        textPanel.add(indexBox);
        //------------------------------------下拉框选择声明结束-----------------------------------------


        //------------------------------------按钮绑定事件开始-----------------------------------------
        //确认插入事件绑定
        confirmButton.addActionListener(e -> {
            Integer selectedNodeIndex = (Integer) indexBox.getSelectedItem();
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
            binaryTree.deleteTreeNodeByIndex(selectedNodeIndex);

            // 更新下拉框
            refreshIndexBox(indexBox);

            // 绘图
            drawPanel.repaint();
        });

        //开始遍历事件绑定
        traversalButton.addActionListener(e -> {
            // 无效化插入、遍历、重置的按钮，防止误触导致程序崩溃
            traversalButton.setEnabled(false);
            resetButton.setEnabled(false);
            confirmButton.setEnabled(false);

            // repaint方法不总是立刻生效，Swing框架默认的行为是会合并多个repaint的操作的
            // 正确的操作是使用多线程的方式处理
            new Thread(()->{
                Enumeration<AbstractButton> orderButtons = orderGroup.getElements();
                while (orderButtons.hasMoreElements()){
                    AbstractButton selectedOrderButton = orderButtons.nextElement();
                    if (selectedOrderButton.isSelected()){
                        String selection = selectedOrderButton.getText();
                        binaryTree.getTraversalOrder().clear(); //每次遍历都要情况前一次遍历的结果
                        switch (selection){
                            case "前序遍历":
                                binaryTree.preOrderTraversal(binaryTree.getHead());
                                break;
                            case "中序遍历":
                                binaryTree.inOrderTraversal(binaryTree.getHead());
                                break;
                            case "后序遍历":
                                binaryTree.postOrderTraversal(binaryTree.getHead());
                                break;
                            default:break;
                        }
                        List<Integer> traversalOrder = binaryTree.getTraversalOrder();
                        for (Integer treeNodeIndex : traversalOrder) {
                            TreeNode treeNode = binaryTree.getTreeNodeByIndex(treeNodeIndex);
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
                    }
                }
            }).start();

        });

        //重置事件绑定
        resetButton.addActionListener(e->{
            List<TreeNode> treeNodes = binaryTree.getNodes();
            for (TreeNode treeNode : treeNodes) {
                treeNode.setColor(BinaryTree.NODE_COLOR);
            }
            drawPanel.repaint();
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
