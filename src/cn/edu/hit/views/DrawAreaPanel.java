package cn.edu.hit.views;

import cn.edu.hit.binarytree.BinaryTree;
import cn.edu.hit.binarytree.TreeNode;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * 可视化窗口中绘制二叉树的组件
 *
 * @author Edmund_Lai
 * @e-mail 1191000311@stu.hit.edu.cn
 * @create 2021-10-06-10:59
 */
public class DrawAreaPanel extends JPanel {
    /*
    binaryTree - 二叉树
    DIAMETER - 结点半径
    TEXT_COLOR - 结点索引的字体颜色
     */
    private final BinaryTree binaryTree;
    private final static Integer DIAMETER = 30;
    private final static Color TEXT_COLOR = Color.BLACK;

    public DrawAreaPanel(BinaryTree binaryTree) {
        this.binaryTree = binaryTree;
    }

    /**
     * 更新二叉树的展示，该方法为回调方法，调用repaint方法后Graphics类对象会调用这个方法
     */
    @Override
    public void paint(Graphics graphics){
        super.paint(graphics);
        List<TreeNode> treeNodes = binaryTree.getNodes();
        for (TreeNode treeNode : treeNodes) {
            graphics.setColor(treeNode.getColor());
            // 画圆
            graphics.fillOval(treeNode.getX(),treeNode.getY(),DIAMETER,DIAMETER);
            TreeNode leftChild = treeNode.getLeftChild();
            TreeNode rightChild = treeNode.getRightChild();
            // 画线
            if (leftChild!=null){
                graphics.drawLine(treeNode.getX()+15, treeNode.getY()+15, leftChild.getX()+15, leftChild.getY()+15);
            }
            if (rightChild!=null){
                graphics.drawLine(treeNode.getX()+15, treeNode.getY()+15, rightChild.getX()+15, rightChild.getY()+15);

            }
            // 标号
            graphics.setColor(TEXT_COLOR);
            graphics.setFont(new JLabel().getFont().deriveFont(16.0f));
            String text = treeNode.getIndex().toString();
            if (text.length()==1){
                graphics.drawString(treeNode.getIndex().toString(),treeNode.getX()+12,treeNode.getY()+21);
            }else {
                graphics.drawString(treeNode.getIndex().toString(),treeNode.getX()+7,treeNode.getY()+21);
            }
        }
    }
}
