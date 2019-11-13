package com.sherlocky.leetcode.solution;

/**
 * # 4. 寻找两个有序数组的中位数
 * <p>
 * 给定两个大小为 m 和 n 的有序数组 nums1 和 nums2。
 * <p>
 * 请你找出这两个有序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。
 * <p>
 * 你可以假设 nums1 和 nums2 不会同时为空。
 * <p>
 * 示例 1:
 * <p>
 * nums1 = [1, 3]
 * nums2 = [2]
 * <p>
 * 则中位数是 2.0
 * 示例 2:
 * <p>
 * nums1 = [1, 2]
 * nums2 = [3, 4]
 * <p>
 * 则中位数是 (2 + 3)/2 = 2.5
 * <p>
 * <p>
 * 这里提到了时间复杂度为 O(log(m+n))，很容易想到的就是【二分查找】，所以现在要做的就是在两个排序数组中进行二分查找。<p>
 * 具体思路如下，将问题【转化为在两个数组中找第 K 个小的数】，
 * 参考：https://mp.weixin.qq.com/s/FBlH7o-ssj_iMEPLcvsY2w，https://leetcode.wang/leetCode-4-Median-of-Two-Sorted-Arrays.html。<p>
 * 求中位数，其实就是求第 k 小数的一种特殊情况。<p>
 * <p>
 * 首先在两个数组中分别找出第 k/2 小的数，再比较这两个第 k/2 小的数，这里假设两个数组为 A ，B。<p>
 * <p>
 * 那么比较结果会有下面几种情况：
 * <p>
 * <ul>
 * <li>A[k/2] = B[k/2]，那么第 k 小的数就是 A[k/2]，实际代码中要考虑边界问题</li>
 * <li>A[k/2] > B[k/2]，那么第 k 小的数肯定在 A[0:k/2+1] 和 B[k/2:] 中，
 *  这样就将原来的所有数的总和减少到一半了，再在这个范围里面找第 k/2 小的数即可，这样也达到了二分查找的区别了。</li>
 * <li>A[k/2] < B[k/2]，那么第 k 小的数肯定在 B[0:k/2+1]和 A[k/2:] 中，
 *  同理在这个范围找第 k/2 小的数就可以了。</li>
 * </ul>
 * <p>
 * 链接：https://leetcode-cn.com/problems/median-of-two-sorted-arrays
 *
 * @author: zhangcx
 * @date: 2019/11/12 16:07
 * @since:
 */
public class MedianOfTwoSortedArrays {
    public static void main2(String[] args) {
        int[] aa = {1, 2};
        int[] bb = {-1, 3};
        // 第2小的数 -- 1
        System.out.println(median(aa, 0, aa.length -1, bb, 0, bb.length - 1, 2));
        // 第3小的数 -- 2
        System.out.println(median(aa, 0, aa.length -1, bb, 0, bb.length - 1, 3));
    }
    public static void main(String[] args) {
        int[] nums1 = new int[]{1, 3};
        int[] nums2 = new int[]{2};
        // 2.0
        System.out.println(findMedianSortedArrays(nums1, nums2));

        nums1 = new int[]{1, 2};
        nums2 = new int[]{3, 4};
        // 2.5
        System.out.println(findMedianSortedArrays(nums1, nums2));

        // 4.5
        nums1 = new int[]{1, 3, 4, 7};
        nums2 = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.println(findMedianSortedArrays(nums1, nums2));

        // 23.0
        int[] a = {1, 2, 5, 8, 44, 45, 45};
        int[] b = {1, 2, 3, 4, 5, 6, 7, 23, 23, 23, 33, 44, 45, 45, 56, 77, 5555};
        System.out.println(findMedianSortedArrays(a, b));

        // 1.5
        int[] aa = {1, 2};
        int[] bb = {-1, 3};
        System.out.println(findMedianSortedArrays(aa, bb));
    }

    /**
     * 找出这两个有序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))
     * <p>
     * 【时间复杂度】：每进行一次循环，减少 k/2 个元素，所以时间复杂度是 O(log(k)，而 k = (m+n) / 2，所以最终的复杂也就是 O(log(m+n）。
     * 【空间复杂度】：虽然用到了递归，但是可以看到这个递归属于尾递归，所以编译器不需要不停地堆栈，所以空间复杂度为 O(1)。
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1 != null ? nums1.length : 0;
        int n = nums2 != null ? nums2.length : 0;
        // 两个数列都是正序的，求整体的中位数，其实就是求数列中第 k 小的数
        int count = m + n;
        // 如果总和是偶数，则需要找第 count/2 和 (count+2)/2 小的数，并取二者的平均数
        if (count % 2 == 0) {
            // 需要找俩数，然后取平均值
            int leftK = count / 2;
            int rightK = (count + 2) / 2;
            return (median(nums1, 0, m - 1, nums2, 0, n - 1, leftK)
                    + median(nums1, 0, m - 1, nums2, 0, n - 1, rightK))
                    * 0.5;
        }
        // 如果总和是奇数，则需要找第 (count+1)/2 小的数
        int k = (count + 1) / 2;
        return median(nums1, 0, m - 1, nums2, 0, n - 1, k) * 1.0;
    }

    /**
     * @param nums1  数列1
     * @param start1 数列1查找起始位置下标
     * @param end1   数列1查找结束位置下标
     * @param nums2  数列2
     * @param start2 数列2查找起始位置下标
     * @param end2   数列2查找结束位置下标
     * @param k      查找第k小的数
     * @return
     */
    private static int median(int[] nums1, int start1, int end1, int[] nums2, int start2, int end2, int k) {
        // 不合法的情况，直接返回-1
        if (k <= 0) {
            return -1;
        }
        // 数列1，2 长度
        int len1 = end1 - start1 + 1;
        int len2 = end2 - start2 + 1;
        if (len1 <= 0 && len2 <= 0) {
            return -1;
        }
        // 数列有一个为空的情况
        if (len1 <= 0) {
            // k 为第几个，下标需减去1
            return nums2[start2 + k - 1];
        }
        if (len2 <= 0) {
            return nums1[start1 + k - 1];
        }
        if (k == 1) {
            // 如果是取第一个元素，直接比较两数列首元素即可
            return Math.min(nums1[start1], nums2[start2]);
        }
        // 防止k值二分后 超过数列长度，之前未考虑周全导致数组下标越界
        int leftIndex = start1 + Math.min((k / 2), len1) - 1;
        int rightIndex = start2 + Math.min((k / 2), len2) - 1;

        // 如果 nums1[k/2] > nums1[k/2]，那么第 k 小的数肯定在 nums1[0:k/2+1] 和 nums2[k/2:] 中
        if (nums1[leftIndex] > nums2[rightIndex]) {
            /**
             * nums2 的 start2 ~ rightIndex 下标的元素被排除
             * 原来的求第 K 小数的问题，就转化为求新数列第 K - (rightIndex - start2 + 1) 小数的问题
             */
            int newK = k - (rightIndex - start2 + 1);
            return median(nums1, start1, end1, nums2, rightIndex + 1, end2, newK);
        }
        /**
         * 如果 nums1[k/2] < nums1[k/2]，那么第 k 小的数肯定在 nums2[0:k/2+1]和 nums1[k/2:] 中
         * nums1 的 start1 ~ leftIndex 下标的元素被排除
         * 新的K转化为 k - (leftIndex - start2 + 1)
         */
        int newK = k - (leftIndex - start1 + 1);
        return median(nums1, leftIndex + 1, end1, nums2, start2, end2, newK);
    }

    /**
     *
     */
    private static double findMedianSortedArrays_(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int m = nums2.length;
        /**
         * 假如一共 14 个数字，是偶数，要找出它们的第 15 / 2 = 7 小的数字与第 16 / 2 = 8 小的数字 。
         * 假如一共 15 个数字，是奇数，要找出它们的第 16 / 2 = 8 小的数字
         */
        int left = (n + m + 1) / 2;
        int right = (n + m + 2) / 2;
        //一个小技巧：将偶数和奇数的情况合并，如果是奇数，会求两次同样的 k 。
        return (getKth(nums1, 0, n - 1, nums2, 0, m - 1, left) + getKth(nums1, 0, n - 1, nums2, 0, m - 1, right)) * 0.5;
    }

    private static int getKth(int[] nums1, int start1, int end1, int[] nums2, int start2, int end2, int k) {
        int len1 = end1 - start1 + 1;
        int len2 = end2 - start2 + 1;
        //让 len1 的长度小于 len2，这样就能保证如果有数组空了，一定是 len1
        if (len1 > len2) {
            return getKth(nums2, start2, end2, nums1, start1, end1, k);
        }
        // 如果 nums1 为空，则直接从 nums2 中取第 k 小的元素就行
        if (len1 == 0) {
            return nums2[start2 + k - 1];
        }

        // 如果是查第 1 小的元素，直接取起始位置的就可以了
        if (k == 1) {
            return Math.min(nums1[start1], nums2[start2]);
        }

        int i = start1 + Math.min(len1, k / 2) - 1;
        int j = start2 + Math.min(len2, k / 2) - 1;

        if (nums1[i] > nums2[j]) {
            return getKth(nums1, start1, end1, nums2, j + 1, end2, k - (j - start2 + 1));
        } else {
            return getKth(nums1, i + 1, end1, nums2, start2, end2, k - (i - start1 + 1));
        }
    }
}
