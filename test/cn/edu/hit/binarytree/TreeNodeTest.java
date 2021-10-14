package cn.edu.hit.binarytree;

import org.junit.Assert;
import org.junit.Test;

import java.awt.*;

/**
 * TreeNode 的测试类
 *
 * @author Edmund_Lai
 * @e-mail 1191000311@stu.hit.edu.cn
 * @create 2021-10-06-10:12
 */
public class TreeNodeTest {

    @Test
    public void testInitializeTreeNode(){
        TreeNode treeNode = new TreeNode(1, 1, Color.RED, 50, 50);
        Assert.assertEquals(new Integer(1),treeNode.getIndex());
        Assert.assertEquals(new Integer(1), treeNode.getDepth());
        Assert.assertEquals(Color.RED,treeNode.getColor());
        Assert.assertEquals(50,treeNode.getX());
        Assert.assertEquals(50,treeNode.getY());
    }
}
