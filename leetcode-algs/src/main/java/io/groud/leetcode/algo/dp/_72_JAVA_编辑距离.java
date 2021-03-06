package io.groud.leetcode.algo.dp;

/**
 * https://leetcode-cn.com/problems/edit-distance/
 * <p>
 *
 * @author Li.Wei by 2020/3/18
 */
public class _72_JAVA_编辑距离 {

    /**
     * dp 思路
     * ==========================================
     * 1. 确定状态
     * 最优策略的最后一步：dp[i][j] = 操作次数最少
     * 子问题：dp[i][j] 中的状态分别表示 S1.i 和 S2.j 之间的最小编辑距离
     * 对于两个字符 S1 = "rad" S2 = "apple"
     * 我们从前往后比较，对于最初的比较位置 S1.i = r , S2.j = a
     *
     * 如果相等，我们无需任何操作。
     * 如果不相等，我们可以有三种选择：
     * 一、删除：删除 r 这个字符，当前的编辑距离 = 取 i-1 与 j 之间的最小编辑距离后加一。即 dp[i][j] = dp[i-1][j] + 1
     * 二、插入：在 i 位置后面插入一个 a 字符，当前的编辑距离 = 取 i 与 j-1 之间的最小编辑距离后加一。即 dp[i][j] = dp[i][j-1] + 1
     * 三、替换：把 i 位置的字符替换为 a 字符，当前的编辑距离 = 取 i-1 与 j-1 之间的最小编辑距离后加一。即 dp[i][j] = dp[i-1][j-1] + 1
     *
     * 此处扩展思维：为什么这三种选择会影响最小编辑距离呢？
     * 如果你全部选择删除后，删除完 S1 全部字符后，最终只能用空字符串构造一个 S2
     * 如果你全部选择插入后，相当于用空字符串构造一个 S2，然后把多余的 S1 字符串全部删除
     * 如果你全部选择替换后，这时可能会出现相同的字符，多余的字符或者缺失的字符使用删除、插入完成
     *
     * 其实在这三种选择下字符会发生不同的位移，删除时后面的左移，插入后后面的右移，替换时不变。通过位移能让一部分字符串对齐，这样编辑距离就会减少
     *
     * 2. 转移方程
     * 如果不相等时 dp[i][j] = min(dp[i-1][j] , dp[i][j-1] , dp[i-1][j-1]) + 1
     * 如果不相等时 dp[i][j] = dp[i-1][j-1]
     *
     * 3. 初始条件与边界情况
     * dp[0][0] = grid[0][0]
     * 初始化第一行、第一列
     *
     * 4. 计算顺序
     * 左上角到右下角
     *
     * 5. 优化时间空间复杂度
     */
    /*
     * aaaaa -> bbbbbaaaaa
     */
    static class Solution {
        public int minDistance(String word1, String word2) {
            int l1 = word1.length();
            int l2 = word2.length();
            int[][] dp = new int[l1 + 1][l2 + 1];
            for (int i = 0; i <= l1; i++) {
                for (int y = 0; y <= l2; y++) {
                    if (i == 0) { // 边界初始化
                        dp[0][y] = y;
                    } else if (y == 0) { // 边界初始化
                        dp[i][0] = i;
                    } else if (word1.charAt(i - 1) == word2.charAt(y - 1)) { // 0 被空字符串代替，因此下标 - 1
                        dp[i][y] = dp[i - 1][y - 1];
                    } else {
                        dp[i][y] = Math.min(dp[i - 1][y], Math.min(dp[i][y - 1], dp[i - 1][y - 1])) + 1;
                    }
                    System.out.println("dp[" + i + "][" + y + "]=" + dp[i][y]);
                }
            }
            return dp[l1][l2];
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.minDistance("aaa", "bbbaaa"));
    }
}
