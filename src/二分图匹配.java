import java.util.ArrayList;
import java.util.Arrays;

public class 二分图匹配
{
    class max_match
    {
        ArrayList<Integer> Graph[];
        int match_sign[];
        int reach[];
        int dfs(int i)
        {
            for(int s:Graph[i])
                if(reach[s]==0)
                {
                    reach[s]=1;
                    if(match_sign[s]==0||dfs(match_sign[s])==1)
                    {
                        match_sign[s]=i;
                        return 1;
                    }
                }
            return 0;
        }
        max_match(int s)
        {
            match_sign=new int[s+1];
            Graph=new ArrayList[s+1];
            reach=new int[s+1];
            ArrayList<Integer> p=new ArrayList<Integer>();
            for(int i=0;i<s+1;++i)
            {
                Graph[i]=new ArrayList<Integer>();
                reach[i]=0;
                match_sign[i] = 0;
            }
        }
        void insert(int x,int y)
        {
            Graph[x].add(y);
        }
        int Max_match(int x) //以1~x为x方部
        {
            int sum=0;
            for(int i=1;i<=x;++i)
            {
                Arrays.fill(reach,0);
                sum+=dfs(i);
            }
            return sum;
        }
    }
    class HK
    {
        private static final int MAXN = 3000;
        private static final int INF = 0x3f3f3f3f;
        ArrayList<Integer> G[];
        int uN;
        int Mx[],My[];
        int dx[],dy[];
        int dis;
        boolean used[];
        HK(int k)
        {
            Mx=new int[k];
            My=new int[k];
            dx=new int[k];
            dy=new int[k];
        }
        boolean search()
        {

            return dis!=INF;
        }
        boolean DFS(int u)
        {
            return false;
        }
        int Max_match()
        {
            int res=0;
            for(int i=0;i<MAXN;++i)
            {
                Mx[i]=-1;
                My[i]=-1;
            }
            while(search())
            {
                for(int i=0;i<MAXN;++i) used[i]=false;
                for(int i=0;i<uN;++i)
                    if(Mx[i]==-1 && DFS(i))
                        res++;
            }
            return res;
        }
    }

    class KM_max
    {
        private static final int inf = 0x7fffffff;
        private static final int maxn = 310;
        int n, m;
        int lx[], ly[], visx[], visy[];
        int link[], slack[], w[][];
        KM_max()
        {
            lx=new int[maxn];
            ly=new int[maxn];
            slack=new int[maxn];
            visy=new int[maxn];
            visx=new int[maxn];
            w=new int[maxn][maxn];
            link=new int[maxn];
            for(int i=0;i<maxn;++i)
                Arrays.fill(w[i],-1000000);
        }
        int dfs(int x)
        {
            visx[x] = 1;
            for (int y = 1; y <= m; y++) if (w[x][y] != -1000000)
            {
                if (visy[y]>0) continue;
                int t = lx[x] + ly[y] - w[x][y];
                if (t == 0)
                {
                    visy[y] = 1;
                    if (link[y] == -1 || dfs(link[y])==1)
                    {
                        link[y] = x;
                        return 1;
                    }
                }
                else if (slack[y] > t) slack[y] = t;
            }
            return 0;
        }

        int KM()
        {
            Arrays.fill(link,-1);
            Arrays.fill(ly,0);
            for (int x = 1; x <= n; x++)
            {
                lx[x] = -inf;
                for (int y = 1; y <= m; y++)
                    lx[x] = Math.max(lx[x], w[x][y]);
            }
            for (int x = 1; x <= n; x++)
            {
                for (int i = 1; i <= m; i++) slack[i] = inf;
                int flag = 0;
                for (int i = 1; i <= m; i++) if (w[x][i] != -1000000) flag = 1;
                while (flag!=0)
                {
                    Arrays.fill(visx,0);
                    Arrays.fill(visy,0);
                    if (dfs(x)!=0) break;
                    int d = inf;
                    for (int i = 1; i <= m; i++)
                        if (visy[i]==0 && d > slack[i]) d = slack[i];
                    for (int i = 1; i <= n; i++)
                        if (visx[i]!=0) lx[i] -= d;
                    for (int i = 1; i <= m; i++)
                    {
                        if (visy[i]!=0) ly[i] += d;
                        else slack[i] -= d;
                    }
                }
            }
            int ans = 0;
            int vis[]=new int[maxn];
            Arrays.fill(vis,0);
            for (int i = 1; i <= m; i++)
            {
                if (link[i] != -1)
                {
                    ans += w[link[i]][i];
                    vis[link[i]] = 1;
                }
            }
            int i = 1;
            for (i = 1; i <= n; i++) //判断完备匹配
            {
                if (vis[i] == 0) return -1;
            }
            return ans;
        }
    };
}
