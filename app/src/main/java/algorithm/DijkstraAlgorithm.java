package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Przemek on 23.05.2016.
 */
public class DijkstraAlgorithm {
    private int sourceVertex;
    private int destinationVertex;
    int numberOfVertex;
    private List<Integer> solutionPath;

    public DijkstraAlgorithm(int source, int destination, int numberOfVertex) {
        this.sourceVertex = source;
        this.destinationVertex = destination;
        this.numberOfVertex = numberOfVertex;
    }

    public int findMinimalDistanceIndex(int distanceArray[], Boolean processedVertex[]) {
        int minDistance = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int i = 0; i < numberOfVertex; i++) {
            if (!processedVertex[i] && distanceArray[i] <= minDistance) {
                minDistance = distanceArray[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    public void printSolution(int distance[]) {
        System.out.println("Vertex   Distance from Source");
        for (int i = 0; i < numberOfVertex; i++)
            System.out.println(i + " \t\t " + distance[i]);
    }

    public List pathReconstruction(int parent[], int source, int destination){
        solutionPath = new ArrayList<Integer>();
        int index = destination;
        if(source == destination)
            solutionPath.add(destination);
        else
            while (index != -1){
                solutionPath.add(index);
                index = parent[index];
            }
        Collections.reverse(solutionPath);
        System.out.println("Shortest path: " + solutionPath);
        return solutionPath;
    }


    public int[] dijkstra(int matrix[][]) {
        int distance[] = new int[numberOfVertex];
        Boolean processedVertex[] = new Boolean[numberOfVertex];
        int previous[] = new int[numberOfVertex];
        for (int i = 0; i < numberOfVertex; i++) {
            distance[i] = Integer.MAX_VALUE;
            processedVertex[i] = false;
            previous[i] = -1;
        }
        distance[sourceVertex] = 0;
        for (int vertex = 0; vertex < numberOfVertex - 1; vertex++) {  // dla ostatniego nie sprawdzam
            //if(vertex == destinationVertex) break;
            int u = findMinimalDistanceIndex(distance, processedVertex);
            processedVertex[u] = true;
            for (int v = 0; v < numberOfVertex; v++) {  // relaksacja
                if (!processedVertex[v] && matrix[u][v] != 0 && distance[u] != Integer.MAX_VALUE &&
                        distance[u] + matrix[u][v] < distance[v]) {
                    distance[v] = distance[u] + matrix[u][v];
                    previous[v] = u;
                }
            }
        }
        pathReconstruction(previous,sourceVertex,destinationVertex);
        return previous;
    }


    public List<Integer> getSolutionPath() {
        return solutionPath;
    }
}
