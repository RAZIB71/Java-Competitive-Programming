import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.*;

/*
 * Author    : joney_000[developer.jaswant@gmail.com]
 * Algorithm : DFS or similar
 * Platform  : Codeforces
 * Ref       : Cycle detection in forest
 */

class A{ 
  
  private InputStream inputStream ;
  private OutputStream outputStream ;
  private FastReader in ;
  private PrintWriter out ;
  
  private final int BUFFER = 100005;
  
  private int    auxInts[] = new int[BUFFER];
  private long   auxLongs[] = new long[1];
  private double auxDoubles[] = new double[1];
  private char   auxChars[] = new char[1];
  private final long mod = 1000000000+7;
  private final int  INF  = Integer.MAX_VALUE;
  private final long INF_L  = Long.MAX_VALUE / 10;

  public A(){}
  public A(boolean stdIO)throws FileNotFoundException{
    // stdIO = false;
    if(stdIO){
      inputStream = System.in;
      outputStream = System.out;
    }else{
      inputStream = new FileInputStream("input.txt");
      outputStream = new FileOutputStream("output.txt");
    }
    in = new FastReader(inputStream);
    out = new PrintWriter(outputStream);
  }

  final int MAXN = (int)1e5;
  // adj representation of graph
  LinkedList <Integer>[] adj = new LinkedList[MAXN + 1];
  boolean vis[] = new boolean[MAXN+1]; // to mark visited
    
  int n, m;

  void run()throws Exception{
    n = i(); m = i();    
    clear(n);

    for(int e = 1; e <= m; e++){
      int u = i(); int v = i();
      adj[u].add(v); // directed graph
    }
    LinkedList<Integer>[] adj0 = getCopy(adj, n);  // maintaining mutability
    boolean isCycle = false;
    for(int i = 1; i <= n; i++){
      if(!vis[i]){
        isCycle = isCycle | isCycle(i, adj0); //PS: Not connected Graph: i.e. forest containing disconnected components 
        if(isCycle){
          break;
        }
      }
    }

    if(isCycle){
      out.write("yes cycle\n");
    }else {
      out.write("no cycle\n");
    }
  }
  
  void clear(int n){
    for(int i = 1; i <= n; i++){
      adj[i] = new LinkedList<Integer>();
    }
  }

  // Maintain immutability
  LinkedList<Integer>[] getCopy(LinkedList<Integer>[] adj, int n)throws Exception{
    LinkedList<Integer> adjCopy[] = new LinkedList[n + 1];
    for(int i = 1; i <= n; i++){
      adjCopy[i] = new LinkedList<Integer>();
      for(int x: adj[i]){
        adjCopy[i].add(x);
      }
    }
    return adjCopy; 
  }

  // int []depth = new int[MAXN + 1];

  boolean isCycle(int root, LinkedList<Integer>[] adj)throws Exception{

    LinkedList <Integer> queue = new LinkedList<Integer>(); //the stack 
    int currentDepth = 0; // level
    queue.add(root);
    vis[root] = true;
    
    while(!queue.isEmpty()){
    
      int u = queue.getLast(); //top
      //depth[u]= currentDepth;
      if(adj[u].size() > 0){
        int v = adj[u].removeFirst();
        if(!vis[v]){
          queue.add(v);
          currentDepth++;
          vis[v] = true; 
        }else {
         return true;
        }
      }else{
        int v = queue.removeLast();
        currentDepth--;
      }
    }
    return false;
  }

  long gcd(long a, long b){
    if(b == 0)return a;
    return gcd(b, a % b);
  }

  long lcm(long a, long b){
    if(a == 0 || b == 0)return 0;
    return (a * b)/gcd(a, b);
  }

  long mulMod(long a, long b, long mod){
    if(a == 0 || b == 0)return 0;
    if(b == 1)return a;
    long ans = mulMod(a, b/2, mod);
    ans = (ans * 2) % mod;
    if(b % 2 == 1)ans = (a + ans)% mod;
    return ans;
  }

  long pow(long a, long b, long mod){
    if(b == 0)return 1;
    if(b == 1)return a;
    long ans = pow(a, b/2, mod);
    ans = (ans * ans);
    if(ans >= mod)ans %= mod;

    if(b % 2 == 1)ans = (a * ans);
    if(ans >= mod)ans %= mod;

    return ans;
  }

  // 20*20   nCr Pascal Table
  long[][] ncrTable(){
    long ncr[][] = new long[21][21];

    for(int i = 0; i <= 20; i++){
      ncr[i][0] = ncr[i][i] = 1L;
    }

    for(int j = 0; j <= 20; j++){
      for(int i = j + 1; i <= 20; i++){
        ncr[i][j] = ncr[i-1][j] + ncr[i-1][j-1];
      }
    }

    return ncr;
  }

  int i()throws Exception{
    return in.nextInt();
  }

  int[] is(int n)throws Exception{
    for(int i=1 ; i <= n ;i++)auxInts[i] = in.nextInt();  
      return auxInts;
  }

  long l()throws Exception{
    return in.nextLong();
  }

  long[] ls(int n)throws Exception{
    for(int i=1 ; i <= n ;i++)auxLongs[i] = in.nextLong();  
      return auxLongs;
  }

  double d()throws Exception{
    return in.nextDouble();
  }

  double[] ds(int n)throws Exception{
    for(int i=1 ; i <= n ;i++)auxDoubles[i] = in.nextDouble();  
      return auxDoubles;
  }

  char c()throws Exception{
    return in.nextCharacter();
  }

  char[] cs(int n)throws Exception{
    for(int i=1 ; i <= n ;i++)auxChars[i] = in.nextCharacter();  
      return auxChars;
  }

  String s()throws Exception{
    return in.nextLine();
  }

  BigInteger bi()throws Exception{
    return in.nextBigInteger();
  }

  private void closeResources(){
    out.flush();
    out.close();
    return;
  }

//  IMP: roundoff upto 2 digits 
//  double roundOff = Math.round(a * 100.0) / 100.0;
//                    or
//  System.out.printf("%.2f", val);

//  print upto 2 digits after decimal
//  val = ((long)(val * 100.0))/100.0;    

  public static void main(String[] args) throws java.lang.Exception{
  
    A driver = new A(true);
    driver.run();
    driver.closeResources();
  }
}

class FastReader{

  private boolean finished = false;

  private InputStream stream;
  private byte[] buf = new byte[4 * 1024];
  private int curChar;
  private int numChars;
  private SpaceCharFilter filter;

  public FastReader(InputStream stream){
    this.stream = stream;
  }

  public int read(){
    if (numChars == -1){
      throw new InputMismatchException ();
    }
    if (curChar >= numChars){
      curChar = 0;
      try{
        numChars = stream.read (buf);
      } catch (IOException e){
        throw new InputMismatchException ();
      }
      if (numChars <= 0){
        return -1;
      }
    }
    return buf[curChar++];
  }

  public int peek(){
    if (numChars == -1){
      return -1;
    }
    if (curChar >= numChars){
      curChar = 0;
      try{
        numChars = stream.read (buf);
      } catch (IOException e){
        return -1;
      }
      if (numChars <= 0){
        return -1;
      }
    }
    return buf[curChar];
  }

  public int nextInt(){
    int c = read ();
    while (isSpaceChar (c))
      c = read ();
    int sgn = 1;
    if (c == '-'){
      sgn = -1;
      c = read ();
    }
    int res = 0;
    do{
      if(c==','){
        c = read();
      }
      if (c < '0' || c > '9'){
        throw new InputMismatchException ();
      }
      res *= 10;
      res += c - '0';
      c = read ();
    } while (!isSpaceChar (c));
    return res * sgn;
  }

  public long nextLong(){
    int c = read ();
    while (isSpaceChar (c))
      c = read ();
    int sgn = 1;
    if (c == '-'){
      sgn = -1;
      c = read ();
    }
    long res = 0;
    do{
      if (c < '0' || c > '9'){
        throw new InputMismatchException ();
      }
      res *= 10;
      res += c - '0';
      c = read ();
    } while (!isSpaceChar (c));
    return res * sgn;
  }

  public String nextString(){
    int c = read ();
    while (isSpaceChar (c))
      c = read ();
    StringBuilder res = new StringBuilder ();
    do{
      res.appendCodePoint (c);
      c = read ();
    } while (!isSpaceChar (c));
    return res.toString ();
  }

  public boolean isSpaceChar(int c){
    if (filter != null){
      return filter.isSpaceChar (c);
    }
    return isWhitespace (c);
  }

  public static boolean isWhitespace(int c){
    return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
  }

  private String readLine0(){
    StringBuilder buf = new StringBuilder ();
    int c = read ();
    while (c != '\n' && c != -1){
      if (c != '\r'){
        buf.appendCodePoint (c);
      }
      c = read ();
    }
    return buf.toString ();
  }

  public String nextLine(){
    String s = readLine0 ();
    while (s.trim ().length () == 0)
      s = readLine0 ();
    return s;
  }

  public String nextLine(boolean ignoreEmptyLines){
    if (ignoreEmptyLines){
      return nextLine ();
    }else{
      return readLine0 ();
    }
  }

  public BigInteger nextBigInteger(){
    try{
      return new BigInteger (nextString ());
    } catch (NumberFormatException e){
      throw new InputMismatchException ();
    }
  }

  public char nextCharacter(){
    int c = read ();
    while (isSpaceChar (c))
      c = read ();
    return (char) c;
  }

  public double nextDouble(){
    int c = read ();
    while (isSpaceChar (c))
      c = read ();
    int sgn = 1;
    if (c == '-'){
      sgn = -1;
      c = read ();
    }
    double res = 0;
    while (!isSpaceChar (c) && c != '.'){
      if (c == 'e' || c == 'E'){
        return res * Math.pow (10, nextInt ());
      }
      if (c < '0' || c > '9'){
        throw new InputMismatchException ();
      }
      res *= 10;
      res += c - '0';
      c = read ();
    }
    if (c == '.'){
      c = read ();
      double m = 1;
      while (!isSpaceChar (c)){
        if (c == 'e' || c == 'E'){
          return res * Math.pow (10, nextInt ());
        }
        if (c < '0' || c > '9'){
          throw new InputMismatchException ();
        }
        m /= 10;
        res += (c - '0') * m;
        c = read ();
      }
    }
    return res * sgn;
  }

  public boolean isExhausted(){
    int value;
    while (isSpaceChar (value = peek ()) && value != -1)
      read ();
    return value == -1;
  }

  public String next(){
    return nextString ();
  }

  public SpaceCharFilter getFilter(){
    return filter;
  }

  public void setFilter(SpaceCharFilter filter){
    this.filter = filter;
  }

  public interface SpaceCharFilter{
    public boolean isSpaceChar(int ch);
  }
}

class Pair implements Comparable<Pair>{
  public int a;
  public int b;
   
  public Pair(){
    this.a = 0;
    this.b = 0;
  }

  public Pair(int a,int b){
    this.a = a;
    this.b = b;
  }

  public int compareTo(Pair p){
    if(this.a == p.a){
     return this.b - p.b;  
   }
   return this.a - p.a; 
  }

  @Override
  public String toString(){
    return "a = " + this.a + " b = " + this.b;
  }
} 
