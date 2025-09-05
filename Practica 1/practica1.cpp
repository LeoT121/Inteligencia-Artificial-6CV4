#include <bits/stdc++.h>

using namespace std;

int dp[(1 << 4)][8];
int timep[4] = {1, 2, 5, 10};
int inf = 100000000;


int dpf(int num, int t) {
    if(num == 15) return dp[num][t] = 0;
    if(t == 7)
        return dp[num][t] = (num == 15) ? 0 : inf;
    int & ans = dp[num][t];
    if(ans != -1) return ans;
    ans = inf;
    if(t & 1) {
        //estan del otro lado
        for(int i = 0; i < 4; ++ i)
            if(num & (1 << i))
                ans = min(ans, timep[i] + dpf(num ^ (1 << i), t + 1));
        return ans;
    }
    for(int i = 0; i < 4; ++ i) {
        for(int j = 0; j < 4; ++ j) {
            if(((1 << i) & num) || ((1 << j) & num)) continue;
            if(i == j)
                ans = min(ans, timep[i] + dpf(num | (1 << i), t + 1));
            else 
                ans = min(ans, max(timep[i], timep[j]) + dpf(num | (1 << i) | (1 << j), t + 1));
        }
    }
    return ans;
}

void build(int num, int t) {
    if(num == 15) return;
    if(t & 1) {
        for(int i = 0; i < 4; ++ i) {
            if(num & (1 << i) && dp[num][t] - timep[i] == dp[num ^ (1 << i)][t + 1]) {
                cout << timep[i] << " <- " << timep[i] << '\n';
                build(num ^ (1 << i), t + 1);
                return;
            }
        }
    }
    for(int i = 0; i < 4; ++ i) {
        for(int j = 0; j < 4; ++ j) {
            if(((1 << i) & num) || ((1 << j) & num)) continue;
            if(i == j && dp[num][t] - timep[i] == dp[num | (1 << i)][t + 1]) {
                cout << timep[i] << " -> " << timep[i] << '\n';
                build(num | (1 << i), t + 1);
                return;
            }
            if(dp[num][t] - max(timep[i], timep[j]) == dp[num | (1 << i) | (1 << j)][t + 1]) {
                cout << '(' << timep[i] << ',' << timep[j] << ") -> " << max(timep[i], timep[j]) << '\n';
                build(num | (1 << i) | (1 << j), t + 1);
                return;
            }
        }
    }
} 

int main() {
    for(int i = 0; i < 4; ++ i) cin >> timep[i];
    for(int i = 0; i < (1 << 4); ++ i)
        for(int j = 0; j < 8; ++ j) 
            dp[i][j] = -1;
    cout << dpf(0, 0) << '\n';
    build(0, 0);
    return 0;
}