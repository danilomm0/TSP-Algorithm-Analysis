package algorithms;

import other.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// MST stands for Minimum Span Tree
public class MST {
    private Map mst = new Map();

    ArrayList<City> cities;
    private int[][] adjMatrix;

    public MST(Map map) {
        adjMatrix = new int[map.size()][map.size()];
        cities = new ArrayList<>(map.getCities());
        for (int i = 0; i < map.size(); i++) {
            for (Road road : cities.get(i).getNear()) {
                adjMatrix[i][cities.indexOf(road.getCity())] = road.getLength();
                adjMatrix[cities.indexOf(road.getCity())][i] = road.getLength();
            }
        }
    }

    int minKey(int key[], Boolean mstSet[])
    {
        // Initialize min value
        int min = Integer.MAX_VALUE, min_index = -1;
 
        for (int v = 0; v < cities.size(); v++)
            if (mstSet[v] == false && key[v] < min) {
                min = key[v];
                min_index = v;
            }
 
        return min_index;
    }

    void printMST(int parent[], int graph[][])
    {
        System.out.println("Edge \tWeight");
        for (int i = 1; i < cities.size(); i++)
            System.out.println(parent[i] + " - " + i + "\t"
                               + graph[i][parent[i]]);
    }

    public int[] primMST(int graph[][])
    {
        // Array to store constructed MST
        int parent[] = new int[cities.size()];
 
        // Key values used to pick minimum weight edge in
        // cut
        int key[] = new int[cities.size()];
 
        Boolean mstSet[] = new Boolean[cities.size()];
 
        // Initialize all keys as INFINITE
        for (int i = 0; i < cities.size(); i++) {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }
 
        key[0] = 0;
       
        parent[0] = -1;
 
        for (int count = 0; count < cities.size() - 1; count++) {
             
            // Pick the minimum key vertex from the set of
            // vertices not yet included in MST
            int u = minKey(key, mstSet);
 
            // Add the picked vertex to the MST Set
            mstSet[u] = true;
 
            // Update key value and parent index of the
            // adjacent vertices of the picked vertex.
            for (int v = 0; v < cities.size(); v++)
                if (graph[u][v] != 0 && mstSet[v] == false
                    && graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
        }

        return parent;
    }

    public void buildTree(Map map) {
        int[] mapping = primMST(adjMatrix);

        for (int i = 1; i < mapping.length; i++) {
            int j = mapping[i];
            mst.addRoad(cities.get(i).getName(), cities.get(j).getName(), adjMatrix[i][mapping[i]]);
            System.out.printf("%s %s %d\n", cities.get(i).getName(), cities.get(j).getName(), adjMatrix[i][mapping[i]]);
        }
    }

    public Map getMST() { return mst; }

}