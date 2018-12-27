

import com.sun.org.apache.bcel.internal.generic.SWAP;

import java.io.*;
import java.util.*;
import java.math.*;

public class Main
{
    public static InputReader in = new InputReader(System.in);
    public static PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args)
    {

        out.flush();
        out.close();
    }
}

class union_find1
{
    int fa[];
    union_find1(int n)
    {
        fa=new int[n];
        Arrays.fill(fa,-1);
    }
    int find(int u)
    {
        if(fa[u]<0)
            return u;
        int x=find(fa[u]);
        fa[u]=x;
        return x;
    }
    void union(int u,int v)
    {
        int x=find(u);
        int y=find(v);
        fa[x]=y;
    }
}

class zhu_liu
{
    private static final int inf = 0x3f3f3f3f;
    class edge { int u,v,w;}
    edge edge1[];
    int pre[],id[],visit[],in[];
    zhu_liu()
    {

    }
    int zhuliu(int root,int  n,int m)
    {
        int res=0,u,v;
        while (true)
        {
            for (int i=0;i<n;++i)
                in[i]=inf;
            for(int i=0;i<m;i++)
                if(edge1[i].u!=edge1[i].v && edge1[i].w<in[edge1[i].v])
                {
                    pre[edge1[i].v]=edge1[i].u;
                    in[edge1[i].v]=edge1[i].w;
                }
                for(int i=0;i<n;++i)
                    if(i!=root && in[i]==inf)
                        return -1;
            int tn=0;
            Arrays.fill(id,-1);
            Arrays.fill(visit,-1);
            in[root]=0;
            for(int i=0;i<n;i++)
            {
                res+=in[i];
                v=i;
                while (visit[v]!=i && id[v]==-1 && v!=root)
                {
                    visit[v]=i;
                    v=pre[v];
                }
                if(v!=root && id[v]==-1) {
                    for (u = pre[v]; u != v; u = pre[u])
                        id[u] = tn;
                    id[v] = tn++;
                }
            }
            if(tn==0) break;
            for(int i=0;i<n;++i)
                if(id[i]==-1)
                    id[i]=tn++;
            for(int i=0;i<m;)
            {
                v=edge1[i].v;
                edge1[i].u=id[edge1[i].u];
                edge1[i].v=id[edge1[i].v];
                if(edge1[i].u !=edge1[i].v)
                    edge1[i++].w-=in[v];
                else
                    { edge p=edge1[--m];edge1[--m]=edge1[i];edge1[i]=p;}
            }
            n=tn;
            root=id[root];
        }
        return res;
    }

}
class match_normal {
    int N;
    boolean graph[][], inqueue[], inpath[], inblossom[];
    int head, tail;
    int queue[], father[], base[], match[];
    int start, finish, newbase;

    match_normal(int MAXN,int n)
    {
        N=n;
        graph=new boolean[MAXN][MAXN];
        inqueue=new boolean[MAXN];
        inpath=new boolean[MAXN];
        inblossom=new boolean[MAXN];
        queue= new int[MAXN];
        father=new int[MAXN];
        base=new int[MAXN];
        match=new int[MAXN];
        for(int i=0;i<MAXN;++i)
            Arrays.fill(graph[i],false);
    }
    void push(int u) {
        queue[tail] = u;
        tail++;
        inqueue[u] = true;
    }

    int pop() {
        int res = queue[head];
        head++;
        return res;
    }

    int find_common_ancestor(int u, int v) {
        Arrays.fill(inpath, false);
        while (true) {
            u = base[u];
            inpath[u] = true;
            if (u == start)
                break;
            u = father[match[u]];
        }
        while (true) {
            v = base[v];
            if (inpath[v]) break;
            v = father[match[v]];
        }
        return v;
    }

    void resert_trace(int u) {
        int v;
        while (base[u] != newbase) {
            v = match[u];
            inblossom[base[u]] = inblossom[base[v]] = true;
            u = father[v];
            if (base[u] != newbase) father[u] = v;
        }
    }

    void blossomconstract(int u, int v) {
        newbase = find_common_ancestor(u, v);
        Arrays.fill(inblossom, false);
        resert_trace(u);
        resert_trace(v);
        if (base[u] != newbase) father[u] = v;
        if (base[v] != newbase) father[v] = u;
        for (int tu = 1; tu <= N; tu++)
            if (inblossom[base[tu]]) {
                base[tu] = newbase;
                if (!inqueue[tu]) push(tu);
            }
    }

    void find_aug() {
        Arrays.fill(inqueue, false);
        Arrays.fill(father, 0);
        for (int i = 1; i <= N; ++i)
            base[i] = i;
        head = tail = 1;
        push(start);
        finish = 0;
        while (head < tail) {
            int u = pop();
            for (int v = 1; v <= N; v++) {
                if (graph[u][v] && (base[u] != base[v]) && (match[u] != v)) {
                    if (v == start || (match[v] > 0) && father[match[v]] > 0)
                        blossomconstract(u, v);
                    else if (father[v] == 0) {
                        father[v] = u;
                        if (match[v] > 0)
                            push(match[v]);
                        else {
                            finish = v;
                            return;
                        }
                    }
                }
            }
        }
    }
    void aug_path()
    {
        int u,v,w;
        u=finish;
        while (u>0)
        {
            v=father[u];
            w=match[v];
            match[v]=u;
            match[u]=v;
            u=w;
        }
    }
    void edmond()
    {
        Arrays.fill(match,0);
        for(int u=0;u<=N;++u)
            if(match[u]==0)
            {
                start=u;
                find_aug();
                if(finish>0) aug_path();
            }
    }
}
class InputReader{
    private final static int BUF_SZ = 65536;
    BufferedReader in;
    StringTokenizer tokenizer;
    public InputReader(InputStream in) {
        super();
        this.in = new BufferedReader(new InputStreamReader(in),BUF_SZ);
        tokenizer = new StringTokenizer("");
    }
    public String next() {
        while (!tokenizer.hasMoreTokens()) {
            try {
                tokenizer = new StringTokenizer(in.readLine());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return tokenizer.nextToken();
    }
    public boolean hasNext() {  //处理EOF
        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
            try {
                String line = in.readLine();
                if(line == null) return false;
                tokenizer = new StringTokenizer(line);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }
    public int nextInt() {
        return Integer.parseInt(next());
    }
    public long nextLong()
    {
        return Long.parseLong(next());
    }
}
