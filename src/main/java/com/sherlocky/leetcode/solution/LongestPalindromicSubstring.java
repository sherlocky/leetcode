package com.sherlocky.leetcode.solution;

import org.apache.commons.lang3.StringUtils;

/**
 * # 5. 最长回文子串
 * <p>
 * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
 * <p>
 * 示例 1：
 * <p>
 * 输入: "babad"
 * 输出: "bab"
 * 注意: "aba" 也是一个有效答案。
 * 示例 2：
 * <p>
 * 输入: "cbbd"
 * 输出: "bb"
 * <p>
 * 链接：https://leetcode-cn.com/problems/longest-palindromic-substring
 * <p>
 * 解题思路（https://leetcode.wang/leetCode-5-Longest-Palindromic-Substring.html）：
 * 1.暴力破解法：{@link #longestPalindrome_1}，大致需要3层for循环，时间复杂度为O(n³)，排除掉。
 * 2.最长公共子串法：{@link #longestPalindrome_2}，时间复杂度：O(n²)
 * 3.暴力破解法使用动态规划优化，{@link #longestPalindrome_3}，时间复杂度：O(n²)
 * 4.扩展中心法，{@link #longestPalindrome_4}，时间复杂度：O(n²)
 * 5.Manacher's Algorithm 马拉车算法，{@link #longestPalindrome_5}，时间复杂度：O(n)
 *
 * @author: zhangcx
 * @date: 2019/11/13 10:29
 * @since:
 */
public class LongestPalindromicSubstring {
    public static void main(String[] args) {
        LongestPalindromicSubstring lps = new LongestPalindromicSubstring();
        // aba, bab
        String s = "babad";
        System.out.println(lps.longestPalindrome(s));

        // bb
        s = "cbbd";
        System.out.println(lps.longestPalindrome(s));

        //TODO 单字符，比如：a 算不算回文字符串？
        // ""
        s = "abc435cba";
        System.out.println(lps.longestPalindrome(s));

        // cabac
        s = "cabac";
        System.out.println(lps.longestPalindrome(s));
    }

    public String longestPalindrome(String s) {
        return longestPalindrome_4(s);
    }

    /**
     * 【解法一 暴力破解】
     * 暴力求解，列举所有的子串，判断是否为回文串，保存最长的回文串
     * 【时间复杂度】：O（n³）。两层 for 循环 O（n²），for 循环里边判断是否为回文，O（n），所以时间复杂度为 O（n³）。
     * 【空间复杂度】：O（1），常数个变量。
     *
     * @return
     */
    public String longestPalindrome_1(String s) {
        if (StringUtils.isBlank(s)) {
            return null;
        }
        String ps = "";
        int max = 0;
        int len = s.length();
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j <= len; j++) {
                String temp = s.substring(i, j);
                if (isPalindromic(temp) && temp.length() > max) {
                    ps = temp;
                    max = temp.length();
                }
            }
        }
        return ps;
    }

    // 判断一个字符串是否是回文字符串
    private boolean isPalindromic(String s) {
        int len = s.length();
        // 中间数，考虑了总数为偶数和奇数两种情况
        int median = len / 2;
        for (int i = 0; i < median; i++) {
            if (s.charAt(i) != s.charAt(len - i - 1)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 【解法二 最长公共子串】
     * 根据回文串的定义，正着和反着读一样，我们可以把原来的字符串倒置了，然后找最长的公共子串就可以了。
     * 【时间复杂度】：两层循环，O（n²）
     * 【空间复杂度】：一个二维数组，O（n²）,可优化为一维数组： O（n） {@link #longestPalindrome_2_}
     * <p>
     * 最长公共子串算法详情可参见：[sherlocky/interview] 下 LongestCommonSubstring.java
     * <p>
     * 存在特殊情况，如 S = "abc435cba"，S’ = "abc534cba" ，最长公共子串是 "abc" 和 "cba" ，但很明显这两个字符串都不是回文串。
     * 所以求出最长公共子串后，并不一定是回文串，还需要判断该字符串倒置前的下标和当前的字符串下标是不是匹配（只需要判断末尾字符就可以）。
     * @return
     */
    public String longestPalindrome_2(String s) {
        if (StringUtils.isBlank(s)) {
            return null;
        }
        // 倒置原字符串
        String reverse = StringUtils.reverse(s);
        int len = s.length();
        // table[i][j] 保存的是公共子串的长度
        int[][] table = new int[len][len];
        int maxLen = 0, maxEnd = 0;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (s.charAt(i) == reverse.charAt(j)) {
                    if (i == 0 || j == 0) {
                        table[i][j] = 1;
                    } else {
                        table[i][j] = table[i - 1][j - 1] + 1;
                    }
                }
                if (table[i][j] > maxLen) {
                    /**
                     * 首先 i ，j 始终指向子串的末尾字符
                     */
                    // j 所指向的字符，倒置前的下标（即子串首位下标）是：
                    int beforeRev = len - j - 1;
                    // 判断下标是否对应（倒置前的下标+子串长度-1 应该等于 末尾字符的下标i）
                    if (beforeRev + table[i][j] - 1 == i) {
                        maxLen = table[i][j];
                        // 以 i 位置结尾的字符
                        maxEnd = i;
                    }
                }
            }
        }
        return s.substring(maxEnd - maxLen + 1, maxEnd + 1);
    }

    public String longestPalindrome_2_(String s) {
        if (StringUtils.isBlank(s)) {
            return null;
        }
        String reverse = StringUtils.reverse(s);
        int len = s.length();
        // arr[j] 保存的是公共子串的长度
        int[] arr = new int[len];
        int maxLen = 0, maxEnd = 0;
        for (int i = 0; i < len; i++) {
            for (int j = len - 1; j >= 0; j--) {
                if (s.charAt(i) == reverse.charAt(j)) {
                    if (i == 0 || j == 0) {
                        arr[j] = 1;
                    } else {
                        arr[j] = arr[j - 1] + 1;
                    }
                } else {
                    // 之前二维数组，每次用的是不同的列，所以不用置 0 。
                    arr[j] = 0;
                }
                if (arr[j] > maxLen) {
                    int beforeRev = len - j - 1;
                    if (beforeRev + arr[j] - 1 == i) {
                        maxLen = arr[j];
                        maxEnd = i;
                    }
                }
            }
        }
        return s.substring(maxEnd - maxLen + 1, maxEnd + 1);
    }

    /**
     *【解法三 暴力破解优化】
     * <p>
     *【时间复杂度】：两层循环，O（n²）
     *【空间复杂度】：用二维数组 P 保存每个子串的情况，O（n²）,可优化为一维数组： O（n） {@link #longestPalindrome_3_}
     * <p>
     * 如果 S[i+1,j−1] 是回文串，那么只要 S[i] == S[j] ，就可以确定 S[i,j] 也是回文串了。
     * <p>
     * 使用动态规划方法： P(i,j) = ( P(i+1,j−1) && S[i]==S[j] )
     * 可以空间换时间，把已经求出的 P（i，j）存储起来。
     * <p>
     * @return
     */
    public String longestPalindrome_3(String s) {
        if (StringUtils.isBlank(s)) {
            return null;
        }
        String ps = "";
        int maxLen = 0;
        int len = s.length();
        boolean[][] P = new boolean[len][len];
        /**
         * 子串长度下标要在外层循环
         * 要先初始化短位的子串的P值（如果开始下标在内层循环，会存在P(i+1,j−1)还未计算出的情况，导致错误）
         */
        // 遍历子串的长度
        for (int subLen = 1; subLen <= len; subLen++) {
            // 开始下标
            for (int start = 0; start < len; start++) {
                int end = start + subLen - 1;
                // 下标越界，退出循环
                if (end >= len) {
                    break;
                }
                // 但是求长度为 1 和长度为 2 的 P ( i , j ) 时不能用上边的公式
                // 长度为1和2的单独判断
                P[start][end] = subLen == 1 || (subLen == 2 && s.charAt(start) == s.charAt(end)) || (P[start + 1][end - 1] && s.charAt(start) == s.charAt(end));
                if (P[start][end] && subLen > maxLen) {
                    ps = s.substring(start, end + 1);
                    maxLen = ps.length();
                }
            }
        }
        return ps;
    }

    public String longestPalindrome_3_(String s) {
        if (StringUtils.isBlank(s)) {
            return null;
        }
        String ps = "";
        int len = s.length();
        boolean[] P = new boolean[len];
        // 当求第 i 行的时候我们只需要第 i + 1 行的信息，并且 j 的话需要 j - 1 的信息，所以和之前一样 j 也需要倒序
        for (int i = len - 1; i >= 0; i--) {
            for (int j = len - 1; j >= i; j--) {
                int subLen = j - i + 1;
                P[j] = subLen == 1 || (subLen == 2 && s.charAt(i) == s.charAt(j)) || (P[j - 1] && s.charAt(j) == s.charAt(i));
                if (P[j] && subLen > ps.length()) {
                    ps = s.substring(i, j + 1);
                }
            }
        }
        return ps;
    }

    /**
     *【解法四 扩展中心】
     * <p>
     *【时间复杂度】：两层循环，O（n²）
     *【空间复杂度】：O（1）
     * <p>
     * 回文串一定是对称的，所以我们可以每次循环选择一个中心，进行左右扩展，判断左右字符是否相等即可。
     * <p>
     * 由于存在奇数的字符串和偶数的字符串，所以我们需要从一个字符开始扩展，或者从两个字符之间开始扩展，所以总共有 n + n - 1 个中心。
     * @return
     */
    public String longestPalindrome_4(String s) {
        if (StringUtils.isBlank(s)) {
            return null;
        }
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            // 奇数
            int oddLen = expandAroundCenter(s, i, i);
            // 偶数
            int evenLen = expandAroundCenter(s, i, i + 1);
            int subLen = Math.max(oddLen, evenLen);
            if (subLen > (end - start)) {
                // 同时考虑奇数、偶数的情况
                start = i - (subLen - 1) / 2;
                end = i + subLen / 2;
            }
        }
        return s.substring(start, end + 1);
    }
    // 向左向右扩展，left、right 代表每次选取的中心点字符位置（偶数时，中心点在left和right中间；奇数时，中心点就是left，且left==right）
    private int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        // 因为跳出循环前，left减了1，right加了1，应该返回：(right - 1) - (left + 1) + 1 ==> right - left -1
        return right - left - 1;
    }

    public String longestPalindrome_5(String s) {
        if (StringUtils.isBlank(s)) {
            return null;
        }

        return null;
    }
}
