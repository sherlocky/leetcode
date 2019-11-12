package com.sherlocky.leetcode.solution;

/**
 * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
 * <p>
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 * <p>
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 * <p>
 * 示例：
 * <p>
 * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出：7 -> 0 -> 8
 * 原因：342 + 465 = 807
 * <p>
 * 链接：https://leetcode-cn.com/problems/add-two-numbers
 *
 * @author: zhangcx
 * @date: 2019/11/12 11:35
 * @since:
 */
public class AddTwoNumbers {
    public static void main(String[] args) {
        // 逆序
        ListNode l1 = new ListNode(2);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(3);

        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(4);

        ListNode sumNode = addTwoNumbers(l1, l2);
        System.out.println(sumNode);
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null || l2 == null) {
            return null;
        }
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        ListNode cursor1 = l1;
        ListNode cursor2 = l2;
        // 和结果的游标虚拟头结点
        ListNode root = new ListNode(-1);
        ListNode sumCursor = root;
        ListNode tempNode = null;
        /**
         * 此处还可以直接将 每次计算的进位数 放入while条件判断，大体类似
         */
        while (cursor1 != null || cursor2 != null) {
            tempNode = addNode(tempNode, cursor1, cursor2);
            sumCursor.next = tempNode;
            // 游标移动
            if (cursor1 != null) {
                cursor1 = cursor1.next;
            }
            if (cursor2 != null) {
                cursor2 = cursor2.next;
            }
            sumCursor = sumCursor.next;
            tempNode = tempNode.next;
        }
        return root.next;
    }

    /**
     * 将超过10的进位数直接缓存入next节点
     * @param node
     * @param node1
     * @param node2
     * @return
     */
    private static ListNode addNode(ListNode node, ListNode node1, ListNode node2) {
        int sumVal = 0;
        if (node != null) {
            sumVal += node.val;
        } else {
            node = new ListNode(0);
        }
        if (node1 != null) {
            sumVal += node1.val;
        }
        if (node2 != null) {
            sumVal += node2.val;
        }
        node.val = sumVal % 10;
        if (sumVal >= 10) {
            node.next = new ListNode(sumVal / 10);
        }
        return node;
    }
}

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }

    @Override
    public String toString() {
        if (next == null) {
            return String.valueOf(val);
        }
        return val + ", " + next.toString();
    }
}