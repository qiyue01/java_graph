import java.util.ArrayList;
import java.util.Arrays;

public class 图的联通性
{
    class Tarjan //求图的强连通分量，常用于压点
    {
        private static final int MAXN = 20010;
        private static final int MAXM = 50010;
        class Edge
        {
            int to,next;
        }
        Edge egde[];
        int head[],tot;
        int low[],DFN[],Stack[],Belong[];
        int Index,top;
        int scc;
        boolean Instack[];
        int num[];
        Tarjan(int maxn,int maxm)//maxn点数,maxm边数 Belong边数组的值为1~scc
        {
            egde=new Edge[maxm];
            low=new int[maxn];
            DFN=new int[maxn];
            Stack=new int[maxn];
            Belong=new int[maxn];
            num=new int[maxn];
            Instack=new boolean[maxn];
            head=new int[maxn];
        }
        void addedge(int u,int v)
        {
            egde[tot]=new Edge();
            egde[tot].to=v;
            egde[tot].next=head[u];
            head[u]=tot++;
        }
        void Tarjan(int u)
        {
            int v;
            low[u]=DFN[u]=++Index;
            Stack[top++]=u;
            Instack[u]=true;
            for (int i=head[u];i!=-1;i=egde[i].next)
            {
                v=egde[i].to;
                if(DFN[v]==0)
                {
                    Tarjan(v);
                    if(low[u]>low[v])
                        low[u]=low[v];
                }
                else if(Instack[v] && low[u]>DFN[v])
                    low[u]=DFN[v];
            }
            if(low[u]==DFN[u])
            {
                scc++;
                do {
                    v=Stack[--top];
                    Instack[v]=false;
                    Belong[v]=scc;
                    num[scc]++;
                }while (v!=u);
            }
        }
        void solve(int N)
        {
            Arrays.fill(DFN,0);
            Arrays.fill(Instack,false);
            Arrays.fill(num,0);
            Index=scc=top=0;
            for(int i=1;i<=N;++i)
                if(DFN[i]==0)
                    Tarjan(i);
        }
        void init()
        {
            tot=0;
            Arrays.fill(head,-1);
        }
    }

    class mininum_cut { //起点只允许1-N，不好改
        int n, m, v[], mat[][], dis[];
        boolean vis[];
        mininum_cut() {
            v = new int[510];
            dis = new int[510];
            mat = new int[510][510];
            vis = new boolean[510];
        }

        void init(int a, int b) {
            n = a;
            m = b;
            for (int i = 0; i < 510; ++i)
                Arrays.fill(mat[i], 0);
        }

        void add_edge(int i, int j, int k) {
            mat[i][j] += k;
            mat[j][i] += k;
        }

        int Stoer_Wagner(int n) {
            int i, j;
            int res = 100000000;
            for (i = 0; i < n; i++)
                v[i] = i + 1;//初始化第i个结点就是i
            while (n > 1) {
                int maxp = 1, prev = 0;
                for (i = 1; i < n; i++) //初始化到已圈集合的割大小,并找出最大距离的顶点
                {
                    dis[v[i]] = mat[v[0]][v[i]];
                    if (dis[v[i]] > dis[v[maxp]])
                        maxp = i;
                }
                Arrays.fill(vis, false);
                vis[v[0]] = true;
                for (i = 1; i < n; i++) {
                    if (i == n - 1)  //只剩最后一个没加入集合的点，更新最小割
                    {
                        res = Math.min(res, dis[v[maxp]]);
                        for (j = 0; j < n; j++)  //合并最后一个点以及推出它的集合中的点
                        {
                            mat[v[prev]][v[j]] += mat[v[j]][v[maxp]];
                            mat[v[j]][v[prev]] = mat[v[prev]][v[j]];
                        }
                        v[maxp] = v[--n];//第maxp个节点去掉，第n个节点变成第maxp个
                    }
                    vis[v[maxp]] = true;
                    prev = maxp;
                    maxp = -1;
                    for (j = 1; j < n; j++)
                        if (!vis[v[j]])  //将上次求的maxp加入集合，合并与它相邻的边到割集
                        {
                            dis[v[j]] += mat[v[prev]][v[j]];
                            if (maxp == -1 || dis[v[maxp]] < dis[v[j]])
                                maxp = j;
                        }
                }
            }
            return res;
        }
    }
    class euler_road
    {
        boolean vis[];
        int n;
        int degree[];
        ArrayList<Integer> edge[];
        euler_road(int i)
        {
            vis=new boolean[i];
            edge=new ArrayList[i];
            for(int a=0;a<i;++a)
                edge[a]=new ArrayList<Integer>();
            n=i;
            degree=new int[i];
            Arrays.fill(vis,false);
            Arrays.fill(degree,0);
        }
        void dfs(int a)
        {
            for(int i=0;i<edge[a].size();++i)
            {
                if(!vis[edge[a].get(i)])
                {
                    vis[edge[a].get(i)]=true;
                    dfs(edge[a].get(i));
                }
            }
        }
        boolean union()
        {
            vis[1]=true;
            dfs(1);
            for (int i=1;i<n;++i)
                if(vis[i]==false)
                    return false;
            return true;
        }
        void add(int a,int b)
        {
            degree[a]++;
            degree[b]++;
            edge[a].add(b);
            edge[b].add(a);
        }
        boolean euler()
        {
            for (int i=0;i<degree.length;++i)
                if(degree[i]%2==1)
                    return false;
            return true;
        }
    }
}

