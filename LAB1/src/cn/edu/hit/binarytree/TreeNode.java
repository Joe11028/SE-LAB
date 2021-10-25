package cn.edu.hit.binarytree;

import java.awt.*;
import java.util.Objects;

/**
 * 表示二叉树结点，用于封装包括结点索引、结点在图中的位置等信息
 *
 * @author Edmund_Lai
 * @e-mail 1191000311@stu.hit.edu.cn
 * @create 2021-10-06-9:17
 */
public class TreeNode {

    /*
    index - 结点索引
    leftChild, rightChild - 左、右孩子结点
    depth - 结点所在的层数，根节点记作0，子结点的层数等于父节点的层数加1
    color - 可视化时结点的颜色，遍历后更换
    x, y - 结点中心在图中的位置
     */
    private Integer index;
    private TreeNode leftChild, rightChild;
    private Integer depth;
    private Color color;
    private int x, y;

    public TreeNode(Integer index, Integer depth, Color color, int x, int y) {
        this.index = index;
        this.depth = depth;
        this.color = color;
        this.x = x;
        this.y = y;
    }

    /*
        所有的get、set方法统一放到这下面
         */
    public Integer getIndex() {
        return index;
    }

    public TreeNode getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(TreeNode leftChild) {
        this.leftChild = leftChild;
    }

    public TreeNode getRightChild() {
        return rightChild;
    }

    public void setRightChild(TreeNode rightChild) {
        this.rightChild = rightChild;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Integer getDepth() {
        return depth;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreeNode treeNode = (TreeNode) o;
        return this.index.equals(treeNode.getIndex());
    }

}
