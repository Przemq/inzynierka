package algorithm;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Przemek on 23.05.2016.
 */
public class DijkstraAlgorithm {
    public static final int NUMBER_OF_VERTEX = 17;
    private int sourceVertex;
    private int destinationVertex;
    private int[] solutionPath;

    public int[] getSolutionPath() {
        return solutionPath;
    }

    public DijkstraAlgorithm(int source, int destination) {
        this.sourceVertex = source;
        this.destinationVertex = destination;
    }

    public int findMinimalDistenceIndex(int distanceArray[], Boolean processedVertex[]) {
        int minDistance = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int i = 0; i < NUMBER_OF_VERTEX; i++) {
            if (!processedVertex[i] && distanceArray[i] <= minDistance) {
                minDistance = distanceArray[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    public void printSolution(int distance[], int n) {
        System.out.println("Vertex   Distance from Source");
        for (int i = 0; i < NUMBER_OF_VERTEX; i++)
            System.out.println(i + " \t\t " + distance[i]);  // do poprawy po napisaniu rekonstrukcji ścieżki
    }

    public int[] pathReconstruction(int parent[], int source, int destination){
        ArrayList<Integer> path = new ArrayList<Integer>();
        int index = destination;
        if(source == destination)
            path.add(destination);
        else
            while (index != -1){
                path.add(index);
                index = parent[index];
            }
        Collections.reverse(path);
        System.out.println("Shortest path: " + path);
        int[] result = new int[path.size()];
        for (int i = 0; i < path.size(); i++) {
                            result[i] = path.get(i);
        }
        solutionPath = result;
        return result;
    }


    public int[] dijkstra(int matrix[][]) {
        int distance[] = new int[NUMBER_OF_VERTEX];
        Boolean processedVertex[] = new Boolean[NUMBER_OF_VERTEX];
        int previous[] = new int[NUMBER_OF_VERTEX];

        for (int i = 0; i < NUMBER_OF_VERTEX; i++) {
            distance[i] = Integer.MAX_VALUE;
            processedVertex[i] = false;
            previous[i] = -1;

        }
        distance[sourceVertex] = 0;
        for (int vertex = 0; vertex < NUMBER_OF_VERTEX - 1; vertex++) {  // dla ostatniego nie sprawdzam
            //if(vertex == destinationVertex) break;
            int u = findMinimalDistenceIndex(distance, processedVertex);
            processedVertex[u] = true;
            for (int v = 0; v < NUMBER_OF_VERTEX; v++) {  // relaksacja
                if (!processedVertex[v] && matrix[u][v] != 0 && distance[u] != Integer.MAX_VALUE &&
                        distance[u] + matrix[u][v] < distance[v]) {
                    distance[v] = distance[u] + matrix[u][v];
                    previous[v] = u;
                }
            }


        }
        printSolution(distance, sourceVertex);
        pathReconstruction(previous,sourceVertex,destinationVertex);
        return previous;
    }

    public void setSourceVertex(int sourceVertex) {
        this.sourceVertex = sourceVertex;
    }

    public void setDestinationVertex(int destinationVertex) {
        this.destinationVertex = destinationVertex;
    }
}
