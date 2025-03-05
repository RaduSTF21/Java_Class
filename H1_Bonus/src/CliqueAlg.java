import java.util.ArrayList;
import java.util.Random;

public class CliqueAlg {
    public static void main(String[] args){
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

        int[][] matrix = new int[nodes + 1][nodes + 1];
        Random rand = new Random();

        for(int i = 0; i < nodes; i++){
            for(int j = i+1; j <= nodes; j++){
                int edge = rand.nextBoolean() ? 1 : 0;
                matrix[i][j] = edge;
                matrix[j][i] = edge;
            }
        }

        String matrixString = buildMatrixString(matrix);
        System.out.println(matrixString);

        boolean hasClique = hasClique(matrix, k);
        System.out.println(hasClique);
        boolean hasStableSet = hasStableSet(matrix, k);
        System.out.println(hasStableSet);

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
    public static boolean hasClique(int[][] matrix, int k){
        return findClique(matrix, k, 0 , new ArrayList<>());
    }
    public static boolean findClique(int[][] matrix, int k, int node, ArrayList<Integer> clique){
        if(clique.size() == k){
            return true;
        }
        for(int i = node; i < matrix.length; i++){
            boolean isCon = true;
            for(int vertex : clique){
                if(matrix[vertex][i] == 0)
                {
                    isCon = false;
                    break;
                }
            }
            if(isCon){
                clique.add(i);
                if(findClique(matrix, k, i+1, clique)){
                    return true;
                }
                clique.remove(clique.size()-1);
            }
        }
        return false;
    }
    public static boolean hasStableSet(int[][] matrix, int k){
        return findStableSet(matrix, k, 0, new ArrayList<>());
    }
    public static boolean findStableSet(int[][] matrix, int k, int node, ArrayList<Integer> stableSet){
        if(stableSet.size() == k){
            return true;
        }
        for(int i = node; i < matrix.length; i++){
            boolean isNotCon = true;
            for(int vertex : stableSet){
                if(matrix[vertex][i] == 1)
                {
                    isNotCon = false;
                    break;
                }
            }
            if(isNotCon){
                stableSet.add(i);
                if(findStableSet(matrix, k, i+1, stableSet)){
                    return true;
                }
                stableSet.remove(stableSet.size()-1);
            }
        }
        return false;
    }

}