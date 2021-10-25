package cn.edu.hit;

import cn.edu.hit.binarytree.BinaryTree;
import cn.edu.hit.views.BinaryTreeWindow;

/**
 * App启动类
 *
 * @author Edmund_Lai
 * @e-mail 1191000311@stu.hit.edu.cn
 * @create 2021-10-06-9:15
 */
public class Main {
    public static void main(String[] args) {
        BinaryTreeWindow binaryTreeWindow = new BinaryTreeWindow(new BinaryTree());
        binaryTreeWindow.setVisible(true);
    }
}
