import java.util.Random;
import java.util.Arrays;
public class GraphGen {
    public static void main(String[] args) {

        if(args.length < 2){
            System.out.println("Not enough arguments");
            System.exit(1);
        }
        long startTime = System.currentTimeMillis();
        int nodes = Integer.parseInt(args[0]);
        int k = Integer.parseInt(args[1]);
        if(nodes < k)
        {
            System.out.println("nodes must be >= k");
            System.exit(1);
        }
        if (k > 1 && nodes < 2*k)
        {
            System.out.println("nodes must be >= 2*k to allow disjoint clique and independent set");
            System.exit(1);
        }

        int[][] matrix = new int[nodes + 1 ][nodes +1];
        Random rand = new Random();

        for(int i = 0; i < nodes; i++){
            for(int j = i+1; j <= nodes; j++){
                int edge = rand.nextBoolean() ? 1 : 0;
                matrix[i][j] = edge;
                matrix[j][i] = edge;
            }
        }


        int[] clique = getRandomSubset(nodes,k, rand);
        for (int i = 0; i< k;i++)
        {
            for(int j = i+1; j < k ; j++)
            {
                int u = clique[i];
                int v = clique[j];
                matrix[u][v] = 1;
                matrix[v][u] = 1;
            }
        }

        int[] indSet = getRandomSubsetExcluding(nodes,k,clique, rand);
        for  (int i = 0; i< k;i++)
        {
            for(int j = i+1; j < k; j++)
            {
                int u = indSet[i];
                int v = indSet[j];
                matrix[u][v] = 0;
                matrix[v][u] = 0;
            }
        }

        int m = 0;
        int[] deg =  new int[nodes];
        for(int i = 0; i < nodes; i++){
            int deg1 = 0;
            for(int j = 0; j < nodes; j++){
                deg1 += matrix[i][j];
                if(j>i)
                {
                    m += matrix[i][j];
                }
            }
            deg[i]=deg1;
        }
        int maxDeg = Arrays.stream(deg).max().getAsInt();
        int minDeg = Arrays.stream(deg).min().getAsInt();
        int SumDeg = Arrays.stream(deg).sum();
        boolean vDeg = (SumDeg == 2*m);
        long endTime = System.currentTimeMillis();
        long runtime = endTime - startTime;
        if(nodes <= 30000)
        {
            String matrixString = buildMatrixString(matrix);
            System.out.println(matrixString);
        }
        else {
            System.out.println("Matrix not displayed due to size");
        }
        System.out.println("Number of edges: " + m);
        System.out.println("Maximum degree Δ(G): " + maxDeg);
        System.out.println("Minimum degree δ(G): " + minDeg);
        System.out.println("Sum of degrees: " + SumDeg + "(should equal 2*m = " +(2*m) + ") -- >" + (vDeg ? "Valid" : "Invalid"));

        if(nodes > 30000)
        {
            System.out.println("Running time: " + runtime + "ms");
        }
    }
    private static int[] getRandomSubset(int n, int k1, Random rand1) {
        int[] subset = new int[n];
        for(int i = 0; i < n; i++){
            subset[i] = i;
        }
        for(int i = n-1 ; i>0;i--)
        {
            int j = rand1.nextInt(i+1);
            int temp = subset[i];
            subset[i] = subset[j];
            subset[j] = temp;
        }
        int[] result = new int[k1];
        System.arraycopy(subset, 0, result, 0, k1);
        return result;

    }
    private static String buildMatrixString(int[][] matrix) {
        StringBuilder sb = new StringBuilder();
        for(int[] row : matrix){
            for(int cell : row){
                sb.append(cell == 1 ? "■ " : "□ ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    private static int[] getRandomSubsetExcluding(int nodes, int k,int[] exclude, Random rand) {
        boolean[] visited = new boolean[nodes];
        for(int index : exclude){
            visited[index] = true;
        }
        int aCount = 0;
        for (int i = 0; i < nodes; i++) {
            if(!visited[i]){ aCount++; }
        }
        if(aCount < k){
            throw new IllegalArgumentException("Not enough vertices to make an independent set disjoint from the clique");
        }
        int [] result = new int[aCount];
        int idx = 0;
        for (int i = 0; i < nodes; i++) {
            if(!visited[i]){result[idx++] = i;}
        }
        for (int i = aCount-1; i > 0; i--) {
            int j = rand.nextInt(i+1);
            int temp = result[i];
            result[i] = result[j];
            result[j] = temp;
        }
        int[] subset = new int[k];
        System.arraycopy(result, 0, subset, 0, k);
        return subset;
    }
}