import java.util.Arrays;

public class SAT
{
    class SAT_2  //染色法
    {
        int head[],tot,to[],next[];
        void init()
        {
            tot=0;
            Arrays.fill(head,-1);
        }
        void add_edge(int u,int v)
        {
            to[tot]=v;
            next[tot]=head[u];
            head[u]=tot++;
        }
        boolean vis[];
        int s[],top;
        SAT_2(int MAXN,int MAXM)
        {
            vis=new boolean[MAXN];
            s=new int[MAXN];
            head=new int[MAXM];
            to=new int[MAXM];
            next=new int[MAXM];
        }
        boolean dfs(int u)
        {
            if(vis[u^1]==true) return false;
            if(vis[u]==true) return true;
            vis[u]=true;
            s[top++]=u;
            for(int i=head[u];i!=-1;i=next[i])
                if(dfs(to[i])==false)
                    return false;
            return true;
        }
        boolean two_sat(int n)
        {
            Arrays.fill(vis,false);
            for(int i=0;i<n;i+=2)
            {
                if(vis[i] || vis[i^1]) continue;
                top=0;
                if(!dfs(i))
                {
                    while (top!=0) vis[s[--top]]=false;
                    if(!dfs(i^1)) return  false;
                }
            }
            return  true;
        }
    }
}
