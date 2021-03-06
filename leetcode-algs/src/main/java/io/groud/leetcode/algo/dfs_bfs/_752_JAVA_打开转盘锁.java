package io.groud.leetcode.algo.dfs_bfs;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/open-the-lock/
 * <p>
 * tag：bfs
 *
 * @author Li.Wei by 2020/3/5
 */
public class _752_JAVA_打开转盘锁 {

    // 解题思路：层序遍历 bfs
    static class Solution {
        public int openLock(String[] deadends, String target) {

            Set<String> deals = new HashSet<>(); // 死亡记录
            Collections.addAll(deals, deadends);

            // 如果死亡记录中包含了目标数字对应的 8 个状态，说明永远无法抵达
            if (deals.size() > 7 && deals.containsAll(status(target)))
                return -1;

            String init = "0000"; // 初始状态
            if (deals.contains(init))
                return -1;
            if (init.equals(target))
                return 0;

            Set<String> visited = new HashSet<>(); // 记忆
            Queue<String> queue = new LinkedList<>();
            queue.add(init);

            int step = 0;
            while (!queue.isEmpty()) {
                for (int i = queue.size() - 1; i >= 0; i--) {
                    String curr = queue.poll();
                    if (curr.equals(target))
                        return step;
                    for (String o : status(curr)) {
                        if (!deals.contains(o) && visited.add(o))
                            queue.add(o);
                    }
                }
                step++;
            }
            return -1;
        }

        // 当前状态转换为 8 个状态，修改当前 char 后创建 Str，然后将 char 数组恢复
        private List<String> status(java.lang.String s) {
            List<java.lang.String> list = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                StringBuilder curBuilder = new StringBuilder(s);
                char modChar = s.charAt(i);
                curBuilder.setCharAt(i, modChar == '0' ? '9' : (char)(modChar - 1));
                list.add(curBuilder.toString());
                curBuilder.setCharAt(i, modChar == '9' ? '0' : (char)(modChar + 1));
                list.add(curBuilder.toString());
            }
            return list;
        }
    }

    // 解题思路：寻找目标数对应的 8 个状态
    static class Solution1 {
        public int openLock(String[] deadends, String target) {
            final List<String> deals = Arrays.asList(deadends);
            if (deals.contains("0000"))
                return -1;

            final List<String> options = new ArrayList<>(); // 可达目标数的 8 个状态
            for (int i = 0; i < 4; i++) {
                char[] cs = target.toCharArray();
                char c = cs[i];
                cs[i] = (char)((c - 48 + 1) % 10 + 48);
                options.add(new String(cs));
                cs[i] = (char)((c - 48 + 9) % 10 + 48);
                options.add(new String(cs));
            }

            options.removeAll(deals); // 可达数字中移除死亡数字
            if (options.isEmpty())
                return -1; // 可达目标数的 8 个状态都在死亡数字中直接返回

            int step = Integer.MAX_VALUE;
            for (String option : options) { // 判断可达状态中最短转动次数
                int curStep = 1;
                char[] cs = option.toCharArray();
                for (int i = 0; i < 4; i++) {
                    int num = cs[i] - 48; // '0' 为 48 减少隐式转换
                    if (num > 5)
                        curStep += 10 - num; // 判断正转倒转
                    else
                        curStep += num;
                }
                step = Math.min(curStep, step); // 记录最小旋转次数
            }
            return step;
        }
    }

    public static void main(String[] args) {

        Solution1 solution = new Solution1();
        System.out.println(((int)'0'));

        // System.out.println(solution.openLock(new String[]{"8888"}, "0009"));

        // System.out.println(solution.openLock(new String[]{"8887", "8889", "8878", "8898", "8788", "8988", "7888",
        // "9888"}, "8888"));

        System.out.println(solution.openLock(new String[] {"4515", "4184", "9093", "6799", "6594", "8484", "8048",
            "2886", "5609", "9801", "7845", "2631", "3962", "5601", "5049", "3916", "7222", "5699", "3980", "0814",
            "2386", "8880", "4524", "5329", "6242", "9184", "5357", "1288", "5446", "9771", "5492", "0361", "8679",
            "2808", "1184", "0228", "6448", "9083", "5730", "3379", "9890", "5713", "2642", "0772", "0141", "8765",
            "4448", "7356", "5382", "8138", "0272", "0802", "7944", "6245", "1345", "6805", "6945", "3377", "6741",
            "0945", "0925", "1471", "1118", "3708", "8332", "6887", "9130", "0851", "5177", "6032", "1906", "0767",
            "5974", "3592", "4967", "2620", "7959", "3805", "4836", "8641", "9805", "6141", "1023", "5291", "6808",
            "8466", "6259", "4084", "8880", "0043", "7394", "6369", "0313", "3293", "5254", "3827", "1728", "5495",
            "5927", "3680", "5454", "1305", "3366", "8174", "2717", "1069", "3785", "9181", "6171", "1462", "8859",
            "4333", "5795", "8883", "9881", "1287", "6416", "5760", "4390", "6260", "9788", "6191", "1510", "2553",
            "0222", "7214", "5214", "2943", "9615", "4492", "5632", "7093", "5869", "4177", "3542", "2433", "3518",
            "0105", "5266", "8033", "3094", "5221", "2240", "5874", "3742", "8687", "5202", "7932", "4512", "4106",
            "0234", "3863", "8154", "3076", "7452", "9081", "1189", "9847", "6463", "5475", "2125", "8509", "8193",
            "7885", "0611", "5479", "4371", "4168", "8870", "1871", "0248", "9145", "7032", "4093", "1429", "5415",
            "5261", "4482", "7241", "7373", "6043", "3156", "1828", "0741", "4792", "7642", "8921", "3979", "8445",
            "2710", "5027", "0658", "6168", "2434", "4568", "6790", "5356", "5643", "8948", "2831", "2411", "0043",
            "4042", "2651", "6041", "8557", "8253", "2634", "0559", "9254", "9501", "3215", "0234", "3108", "3363",
            "8688", "1513", "7747", "3846", "3542", "6671", "9677", "4598", "7304", "8313", "1036", "5811", "3279",
            "7115", "3157", "7761", "3256", "3379", "4807", "2475", "8576", "3612", "6157", "1266", "8635", "9429",
            "9897", "8048", "2654", "3145", "5204", "8731", "9154", "6673", "7213", "0608", "1045", "6692", "0452",
            "3947", "6488", "0525", "5531", "0312", "7363", "5876", "2713", "0484", "2299", "3052", "4392", "0464",
            "2755", "7416", "5527", "1276", "2077", "3723", "0142", "0653", "9606", "0916", "6882", "6575", "2024",
            "6250", "1711", "3381", "7703", "1626", "6859", "1526", "0514", "6271", "3438", "2880", "9874", "5837",
            "6547", "4960", "0712", "9390", "6207", "1437", "1131", "2253", "9308", "0665", "6334", "6648", "4997",
            "1583", "4590", "1032", "4791", "8445", "2328", "8440", "1369", "2595", "8853", "0797", "1989", "3119",
            "5246", "5964", "7501", "2464", "7716", "2772", "8257", "6181", "7195", "5138", "2185", "8121", "1753",
            "5144", "1776", "3221", "3883", "5573", "7268", "7162", "5602", "3035", "5843", "1417", "1823", "9366",
            "6477", "0108", "5719", "8666", "8901", "7289", "2498", "2219", "4520", "2951", "7929", "5504", "0797",
            "7586", "5306", "2656", "7479", "6606", "4227", "7727", "4449", "2299", "0142", "5099", "3898", "7005",
            "4275", "3692", "1905", "5540", "8365", "8971", "9541", "7449", "6146", "2844", "1026", "7639", "2614",
            "0796", "5920", "4633", "9839", "9761", "5748", "5524", "1332", "5586", "3026", "9057", "1498", "8197",
            "0692", "7714", "6334", "7656", "1649", "2989", "4393", "6227", "5183", "6328", "9864", "5972", "2203",
            "7032", "3643", "2429", "0981", "4729", "0501", "9624", "1464", "2619", "7712", "6739", "9171", "0899",
            "9731", "1058", "7006", "1859", "4002", "5325", "1039", "0466", "2060", "4203", "8816", "8867", "1797",
            "9832", "6489", "4771", "9789", "0271", "7684", "6345", "0825", "9022", "4285", "8081", "9435", "0946",
            "4466", "6551", "4722", "3580", "5484", "5191", "4582", "0220", "1580", "0045", "8701", "3895", "1795",
            "0614", "3118", "4836", "2101", "2072", "7090", "9275", "8715", "1303", "4864", "1116", "6102", "2818",
            "9196", "1222", "3481", "1709", "6145", "2349", "3395", "5314", "3404", "4626", "3770", "7762", "8413",
            "7310", "9659", "0892", "9920", "7195", "7049", "7443", "5505", "3400", "2275", "0669", "6024"}, "4894"));
    }

    private static List<String> status(String s) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            StringBuilder curBuilder = new StringBuilder(s);
            char modChar = s.charAt(i);
            curBuilder.setCharAt(i, modChar == '0' ? '9' : (char)(modChar - 1));
            list.add(curBuilder.toString());
            curBuilder.setCharAt(i, modChar == '9' ? '0' : (char)(modChar + 1));
            list.add(curBuilder.toString());
        }
        return list;
    }
}
