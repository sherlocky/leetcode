package com.sherlocky.leetcode.solution;

import java.util.HashMap;
import java.util.Map;

/**
 * # 1. 两数之和
 * <p>
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 * <p>
 * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
 * <p>
 * 示例:
 * <p>
 * 给定 nums = [2, 7, 11, 15], target = 9
 * <p>
 * 因为 nums[0] + nums[1] = 2 + 7 = 9
 * 所以返回 [0, 1]
 * <p>
 * 链接：https://leetcode-cn.com/problems/two-sum
 */
public class TwoSum {

    public static void main(String[] args) {
        int[] nums = new int[]{2, 5, 5, 11};
        int target = 10;

        int[] result = twoSum(nums, target);
        System.out.println(result[0] + "_" + result[1]);
    }

    /**
     * 暴力法：
     * 时间复杂度：O(n^2)， 对于每个元素，我们试图
     * 通过遍历数组的其余部分来寻找它所对应的目标元素，这将耗费 O(n) 的时间。因此时间复杂度为 O(n^2)
     * 空间复杂度：O(1)
     *
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum_(int[] nums, int target) {
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }

        return null;
    }

    /**
     * 一遍Hash表法：
     * 时间复杂度：O(n)，我们只遍历了包含有 n 个元素的列表一次。在表中进行的每次查找只花费 O(1) 的时间。
     * 空间复杂度：O(n)O(n)， 所需的额外空间取决于哈希表中存储的元素数量，该表最多需要存储 n 个元素。
     *
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> numM = new HashMap<>();
        int[] result = new int[2];
        for (int i = 0; i < nums.length; i++) {
            if (numM.containsKey(target - nums[i])) {
                result[0] = i;
                result[1] = numM.get(target - nums[i]);
            }
            numM.put(nums[i], i);
        }

        return result;
    }
}