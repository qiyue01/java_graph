import java.util.Arrays;

public class 生成树系列
{
    class second_minimum_tree
    {
        private static final int INF = 0x3f3f3f3f;
        boolean vis[],used[][];
        int lowc[],pre[],Max[][],cost[][];
        second_minimum_tree(int MAXN)
        {
            vis=new boolean[MAXN];
            used=new boolean[MAXN][MAXN];
            cost=new int[MAXN][MAXN];
            Max=new int[MAXN][MAXN];
            lowc=new int[MAXN];
            pre=new int[MAXN];
        }
        int prim(int n)
        {
            int ans=0;
            Arrays.fill(vis,false);
            Arrays.fill(Max,0);
            Arrays.fill(used,false);
            vis[0]=true;
            pre[0]=-1;
            for(int i=1;i<n;++i) {
                lowc[i] = cost[0][i];
                pre[i]=0;
            }
            lowc[0]=0;
            for(int i=1;i<n;++i)
            {
                int minc=INF;
                int p=-1;
                for(int j=0;j<n;++j)
                    if(!vis[j]&&minc>lowc[j])
                    {
                        minc=lowc[j];
                        p=j;
                    }
                if(minc==INF)
                    return -1;
                ans+=minc;
                vis[p]=true;
                used[p][pre[p]]=used[pre[p]][p]=true;
                for(int j=0;j<n;j++)
                {
                    if(vis[j] && j!=p) Max[j][p]=Max[p][j]=Math.max(Max[j][pre[p]],lowc[p]);
                    if(!vis[j]&& lowc[j]>cost[p][j])
                    {
                        lowc[j] = cost[p][j];
                        pre[j]=p;
                    }
                }
            }
            return ans;
        }


    }
}
