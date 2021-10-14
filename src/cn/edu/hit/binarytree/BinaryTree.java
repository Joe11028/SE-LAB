package cn.edu.hit.binarytree;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于维护二叉树的结构，将结点类对象组织起来的类
 *
 * @author Edmund_Lai
 * @e-mail 1191000311@stu.hit.edu.cn
 * @create 2021-10-06-9:19
 * @version1.0
 */
public class BinaryTree {
    /*
    head - 树根结点
    nodes - 用于维护所有的结点的列表
    scalableNodes - 用于维护所有仍然可以生长的结点
    ADD_LEFT, ADD_RIGHT - 表示插入结点的方向
    NODE_COLOR - 未遍历结点的颜色
    AFTER_TRAVERSAL_COLOR - 遍历后的结点颜色
    TraversalOrder - 记录遍历的顺序，以索引标识
    nodesToBeDeleted - 需要删除的所有结点
     */
    public final static Color NODE_COLOR = new Color(183, 231, 253);
    public final static Color AFTER_TRAVERSAL_COLOR = new Color(255, 233, 87);
    private final TreeNode head = new TreeNode(1, 0, NODE_COLOR, 220, 10);
    private final List<TreeNode> nodes = new ArrayList<>();
    private final List<Integer> scalableNodes = new ArrayList<>();
    public static final Integer ADD_LEFT = 0;
    public static final Integer ADD_RIGHT = 1;
    private final List<Integer> traversalOrder = new ArrayList<>();
    private final List<TreeNode> nodesToBeDeleted = new ArrayList<>();


    public BinaryTree() {
        nodes.add(head);
        scalableNodes.add(head.getIndex());
    }

    /**
     * 指定父结点和插入方向，插入一个结点
     *
     * @param parent    插入结点的父结点
     * @param direction 插入方向，只能是左或者右
     */
    public void addTreeNode(TreeNode parent, Integer direction) {
        TreeNode node;
        if (!direction.equals(ADD_LEFT) && !direction.equals(ADD_RIGHT)) {
            throw new RuntimeException("Unidentified insert direction !");
        }
        if (direction.equals(ADD_LEFT)) {
            if (parent.getLeftChild() != null) {
                throw new RuntimeException("This node already has a left child node !");
            }
            node = new TreeNode(parent.getIndex() * 2, parent.getDepth() + 1, NODE_COLOR, parent.getX() - 100 / (1 << parent.getDepth()), parent.getY() + 80);
            parent.setLeftChild(node);
            if (parent.getRightChild() != null) {
                scalableNodes.remove((Integer) parent.getIndex());
            }
        } else {
            if (parent.getRightChild() != null) {
                throw new RuntimeException("This node already has a right child node !");
            }
            node = new TreeNode(parent.getIndex() * 2 + 1, parent.getDepth() + 1, NODE_COLOR, parent.getX() + 100 / (1 << parent.getDepth()), parent.getY() + 80);
            parent.setRightChild(node);
            if (parent.getLeftChild() != null) {
                scalableNodes.remove((Integer) parent.getIndex());
            }
        }
        this.nodes.add(node);
        this.scalableNodes.add(node.getIndex());
    }

    /**
     * 根据结点索引获取对应结点，若不存在返回null
     *
     * @param index 结点索引
     * @return TreeNode 节点索引对应的结点
     * @author Edmund_Lai
     */
    public TreeNode getTreeNodeByIndex(Integer index) {
        for (TreeNode node : nodes) {
            if (node.getIndex().equals(index)) {
                return node;
            }
        }
        return null;
    }

    /**
     * 以中序的方式遍历整棵树
     *
     * @param root 当前结点
     */
    public void inOrderTraversal(TreeNode root) {
        if (root != null) {
            inOrderTraversal(root.getLeftChild());
            traversalOrder.add(root.getIndex());
            inOrderTraversal(root.getRightChild());
        }
    }

    /**
     * 以后序的方式遍历整棵树
     *
     * @param root 当前结点
     */
    public void postOrderTraversal(TreeNode root) {
        if (root != null) {
            postOrderTraversal(root.getLeftChild());
            postOrderTraversal(root.getRightChild());
            traversalOrder.add(root.getIndex());
        }
    }

    /**
     * 以前序的方式遍历整棵树
     *
     * @param root 当前结点
     */
    public void preOrderTraversal(TreeNode root) {
        if (root != null) {
            traversalOrder.add(root.getIndex());
            preOrderTraversal(root.getLeftChild());
            preOrderTraversal(root.getRightChild());
        }
    }

    /**
     * 根据索引删除结点及该结点的所有子结点，不允许删除根节点
     *
     * @param index 需要删除结点的索引
     */
    public void deleteTreeNodeByIndex(Integer index) {
        Integer parentIndex = index / 2;
        TreeNode parentTreeNode = getTreeNodeByIndex(parentIndex);
        if (index % 2 == 0) { //结点索引为偶数的清空为父结点的左子树
            parentTreeNode.setLeftChild(null);
        } else {
            parentTreeNode.setRightChild(null);
        }
        nodesToBeDeleted.clear();
        findNodesToBeDeleted(getTreeNodeByIndex(index));
        this.nodes.removeAll(nodesToBeDeleted);
        scalableNodes.clear();
        freshScalableNodes(head);
    }

    /**
     * 找到需要删除的所有结点
     * @param head 被删除结点
     */
    private void findNodesToBeDeleted(TreeNode head){
        nodesToBeDeleted.add(head);
        if (head.getLeftChild()!=null){
            findNodesToBeDeleted(head.getLeftChild());
        }
        if (head.getRightChild()!=null){
            findNodesToBeDeleted(head.getRightChild());
        }
    }

    private void freshScalableNodes(TreeNode head){
        if (head.getLeftChild()==null || head.getRightChild()==null){
            scalableNodes.add(head.getIndex());
        }
        if (head.getLeftChild()!=null){
            freshScalableNodes(head.getLeftChild());
        }
        if (head.getRightChild()!=null){
            freshScalableNodes(head.getRightChild());
        }
    }

    /**
     * 获取当前二叉树的根结点
     *
     * @return TreeNode 树根结点
     */
    public TreeNode getHead() {
        return head;
    }

    public List<TreeNode> getNodes() {
        return nodes;
    }

    public List<Integer> getScalableNodes() {
        return scalableNodes;
    }

    public List<Integer> getTraversalOrder() {
        return traversalOrder;
    }

    public List<TreeNode> getNodesToBeDeleted() {
        return nodesToBeDeleted;
    }
}
