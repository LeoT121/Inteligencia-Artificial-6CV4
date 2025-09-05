import java.util.Scanner;

public class practica1 {

    public static int dp[][] = new int[(1 << 4)][8];
    public static int timep[] = new int[4];
    public static int inf = 100000000;

    public static int dpf(int num, int t) {
        if(num == 15) 
            return dp[num][t] = 0;
        if(t == 7) 
            return dp[num][t] = (num == 15) ? 0 : inf;
        if(dp[num][t] != -1) 
            return dp[num][t];
        dp[num][t] = inf;
        if((t & 1) != 0) {
            for(int i = 0; i < 4; ++ i)
                if((num & (1 << i)) != 0)
                    dp[num][t] = Math.min(dp[num][t], timep[i] + dpf(num ^ (1 << i), t + 1));
            return dp[num][t];
        }
        for(int i = 0; i < 4; ++ i) {
            for(int j = 0; j < 4; ++ j) {
                if(((1 << i) & num) != 0 || ((1 << j) & num) != 0) continue;
                if(i == j)
                    dp[num][t] = Math.min(dp[num][t], timep[i] + dpf(num | (1 << i), t + 1));
                else 
                    dp[num][t] = Math.min(dp[num][t], Math.max(timep[i], timep[j]) + dpf(num | (1 << i) | (1 << j), t + 1));
            }
        }
        return dp[num][t];
    }

    public static void build(int num, int t) {
        if(num == 15) return;
        if((t & 1) != 0) {
            for(int i = 0; i < 4; ++ i) {
                if((num & (1 << i)) != 0 && dp[num][t] - timep[i] == dp[num ^ (1 << i)][t + 1]) {
                    System.out.println(timep[i] + " -> " + timep[i]);
                    build(num ^ (1 << i), t + 1);
                    return;
                }
            }
        }
        for(int i = 0; i < 4; ++ i) {
            for(int j = 0; j < 4; ++ j) {
                if(((1 << i) & num) != 0 || ((1 << j) & num) != 0) continue;
                if(i == j && dp[num][t] - timep[i] == dp[num | (1 << i)][t + 1]) {
                    System.out.println(timep[i] + " -> " + timep[i]);
                    build(num | (1 << i), t + 1);
                    return;
                }
                if(dp[num][t] - Math.max(timep[i], timep[j]) == dp[num | (1 << i) | (1 << j)][t + 1]) {
                    System.out.println("(" + timep[i] + "," + timep[j] + ") -> " + Math.max(timep[i], timep[j]));
                    build(num | (1 << i) | (1 << j), t + 1);
                    return;
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        for(int i = 0; i < 4; ++ i) {
            timep[i] = scanner.nextInt();
        }
        scanner.close();
        for(int i = 0; i < (1 << 4); ++ i) 
            for(int j = 0; j < 8; ++ j) 
                dp[i][j] = -1;
        int totaltime = dpf(0, 0);
        System.out.println(totaltime);
        build(0, 0);
    }
}
