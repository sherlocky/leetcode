package com.sherlocky.leetcode.solution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * # 3. 无重复字符的最长子串
 * <p>
 * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 * <p>
 * 示例 1:
 * <p>
 * 输入: "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 * 示例 2:
 * <p>
 * 输入: "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 * 示例 3:
 * <p>
 * 输入: "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 * <p>
 * 链接：https://leetcode-cn.com/problems/longest-substring-without-repeating-characters
 *
 * @author: zhangcx
 * @date: 2019/11/12 14:52
 * @since:
 */
public class LongestSubstringWithoutRepeatingCharacters {
    public static void main(String[] args) {
        String s1 = "abcabcbb";
        // 3
        System.out.println(lengthOfLongestSubstring(s1));
        String s2 = "bbbbb";
        // 1
        System.out.println(lengthOfLongestSubstring(s2));
        String s3 = "pwwkew";
        // 3
        System.out.println(lengthOfLongestSubstring(s3));
        // 1
        System.out.println(lengthOfLongestSubstring(" "));
        // 3
        System.out.println(lengthOfLongestSubstring("dvdf"));
        // 4
        System.out.println(lengthOfLongestSubstring("asdfsdfsdfsdfasdfdjdjjdjjdjjjjjajsdjjdjdjjd"));
    }

    /**
     * 效率不如下面的 map 方法
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring_(String s) {
        int nonoRepeatLen = 0;
        List tempChars = new ArrayList();
        for (int i = 0; i < s.length(); i++) {
            char temp = s.charAt(i);
            // 如果已存在重复的
            int idx = tempChars.indexOf(temp);
            if (idx > -1) {
                // 重复的是最后一个元素，则重新 new 一个 temp 字符 list
                if (idx == tempChars.size() - 1) {
                    tempChars = new ArrayList();
                } else {
                    // 重复的不是最后一个元素，则截取list
                    tempChars = new ArrayList(tempChars.subList(idx + 1, tempChars.size()));
                }
            }
            tempChars.add(temp);
            nonoRepeatLen = Math.max(nonoRepeatLen, tempChars.size());
        }
        return nonoRepeatLen;
    }

    public static int lengthOfLongestSubstring(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        Map<Character, Integer> map = new HashMap<>();
        int newStart = 0;
        int max = Integer.MIN_VALUE;
        for (int i = 0, len = s.length(); i < len; i++) {
            char temp = s.charAt(i);
            if (map.keySet().contains(temp)) {
                // 每次有重复的字符时，取重复值后一个元素的下标 -> 新的开始
                newStart = Math.max(map.get(temp) + 1, newStart);
            }
            // 填入各个字符对应的下标，如果重复则覆盖为最新出现的下标位置
            map.put(temp, i);
            //
            max = Math.max(max, (i - newStart) + 1);
        }
        return max;
    }
}
