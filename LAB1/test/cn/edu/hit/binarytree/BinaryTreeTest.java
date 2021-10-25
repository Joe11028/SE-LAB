package cn.edu.hit.binarytree;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * BinaryTree 的测试类
 *
 * @author Edmund_Lai
 * @e-mail 1191000311@stu.hit.edu.cn
 * @create 2021-10-06-10:12
 */
public class BinaryTreeTest {

    /**
     * 返回一棵根节点加根节点有两个子节点的二叉树，便于测试
     * @return BinaryTree
     */
    private BinaryTree initializeBinaryTree(){
        BinaryTree binaryTree = new BinaryTree();
        TreeNode head = binaryTree.getHead();
        binaryTree.addTreeNode(head,BinaryTree.ADD_LEFT);
        binaryTree.addTreeNode(head,BinaryTree.ADD_RIGHT);
        return binaryTree;
    }

    @Test
    public void testGetTreeNodeByIndex(){
        BinaryTree binaryTree = initializeBinaryTree();
        Assert.assertEquals(new Integer(3),binaryTree.getTreeNodeByIndex(3).getIndex());
    }

    @Test
    public void testAddTreeNode(){
        BinaryTree binaryTree = initializeBinaryTree();
        Assert.assertEquals(3,binaryTree.getNodes().size());
        Assert.assertEquals(2,binaryTree.getScalableNodes().size());

        binaryTree.addTreeNode(binaryTree.getTreeNodeByIndex(2),BinaryTree.ADD_LEFT);
        binaryTree.addTreeNode(binaryTree.getTreeNodeByIndex(2),BinaryTree.ADD_RIGHT);
        binaryTree.addTreeNode(binaryTree.getTreeNodeByIndex(3),BinaryTree.ADD_RIGHT);
        Assert.assertEquals(6,binaryTree.getNodes().size());
        Assert.assertEquals(4,binaryTree.getScalableNodes().size());
    }

    @Test
    public void testDeleteNode(){
        BinaryTree binaryTree = initializeBinaryTree();
        binaryTree.addTreeNode(binaryTree.getTreeNodeByIndex(2),BinaryTree.ADD_LEFT);
        binaryTree.addTreeNode(binaryTree.getTreeNodeByIndex(2),BinaryTree.ADD_RIGHT);
        binaryTree.addTreeNode(binaryTree.getTreeNodeByIndex(3),BinaryTree.ADD_RIGHT);
        binaryTree.deleteTreeNodeByIndex(2);
        List<Integer> indexes = binaryTree.getScalableNodes();
        for (Integer node : indexes) {
            System.out.println(node);
        }
        System.out.println("---------------------");

        List<TreeNode> nodes = binaryTree.getNodesToBeDeleted();
        for (TreeNode node : nodes) {
            System.out.println(node.getIndex());
        }
    }
}
