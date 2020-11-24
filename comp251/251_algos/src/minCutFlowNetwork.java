//The max value of is the min cut, because the maxiumum value of flow is the minimum capacity of a cut. 

//flow = f-out(e) - fin(e) 

import java.util.LinkedList; 
import java.util.Queue;




/**
 * @author DavidKronish
 * NOTE: This algorithm's implementation uses the standard bfs algorithm to define is there 
 * exists a path from s->t in the residual graph
 * The DFS algorithm is used to compute the reachable vertices to define the set A. 
 *  
 */

public class minCutFlowNetwork {
	
	
	private static void minCut(int[][] graph, int s, int t) {
		
		int u,v;
		//define and populate rGraph
		int[][] rGraph = new int[graph.length][graph.length];  
        for (int i = 0; i < graph.length; i++) { 
            for (int j = 0; j < graph.length; j++) { 
                rGraph[i][j] = graph[i][j]; 
            } 
        }
        
        //This array is filled by BFS and is used to store the path
        int[] parent = new int[graph.length];
        
        
        //Augment the path by FF
        while (bfs(rGraph, s, t, parent)) { 
            // Find minimum residual capacity of the edhes  
            // along the path filled by BFS. Or we can say  
            // find the maximum flow through the path found. 
            int pathFlow = Integer.MAX_VALUE;          
            for (v = t; v != s; v = parent[v]) { 
                u = parent[v]; 
                pathFlow = Math.min(pathFlow, rGraph[u][v]); 
            } 
              
            // update residual capacities of the edges and  
            // reverse edges along the path 
            for (v = t; v != s; v = parent[v]) { 
                u = parent[v]; 
                rGraph[u][v] = rGraph[u][v] - pathFlow; 
                rGraph[v][u] = rGraph[v][u] + pathFlow; 
            } 
        }
        
        //At this point the value of pathFlow is maximized
        //find reachable vertices from s
        boolean[] isVisited = new boolean[graph.length];
        dfs(rGraph,s,isVisited); //Vertex visit status is now store into the isVisited array.
        
        //Print all edges that are from reachable vertex
        for(int i = 0; i < graph.length; i++) {
        	for(int j = 0; j < graph.length;j++) {
        		if(graph[i][j] > 0 && isVisited[i] && !isVisited[j]) {
        			System.out.println(i + " - " + j);
        		}
        	}
        }
		
	}
	
	
	
	
	
	// Returns true if there is a path 
    // from source 's' to sink 't' in residual  
    // graph. Also fills parent[] to store the path  
    private static boolean bfs(int[][] rGraph, int s, int t, int[] parent) { 
        // Create a visited array and mark  
        // all vertices as not visited      
        boolean[] visited = new boolean[rGraph.length]; 
          
        // Create a queue, enqueue source vertex 
        // and mark source vertex as visited      
        Queue<Integer> q = new LinkedList<Integer>(); 
        q.add(s); 
        visited[s] = true; 
        parent[s] = -1; 
          
        // Standard BFS Loop      
        while (!q.isEmpty()) { 
            int v = q.poll(); 
            for (int i = 0; i < rGraph.length; i++) { 
                if (rGraph[v][i] > 0 && !visited[i]) { 
                    q.offer(i); 
                    visited[i] = true; 
                    parent[i] = v; 
                } 
            } 
        } 
          
        // If we reached sink in BFS starting  
        // from source, then return true, else false      
        return (visited[t] == true); 
    } 
      
    // A DFS based function to find all reachable  
    // vertices from s. The function marks visited[i]  
    // as true if i is reachable from s. The initial  
    // values in visited[] must be false. We can also  
    // use BFS to find reachable vertices 
    private static void dfs(int[][] rGraph, int s, boolean[] visited) { 
        visited[s] = true; 
        for (int i = 0; i < rGraph.length; i++) { 
                if (rGraph[s][i] > 0 && !visited[i]) { 
                    dfs(rGraph, i, visited); 
                } 
        } 
    } 

    
    public static void main(String[] args) {
    	 int graph[][] = { {0, 16, 13, 0, 0, 0}, 
                 {0, 0, 10, 12, 0, 0}, 
                 {0, 4, 0, 0, 14, 0}, 
                 {0, 0, 9, 0, 0, 20}, 
                 {0, 0, 0, 7, 0, 4}, 
                 {0, 0, 0, 0, 0, 0} 
             }; 
         minCut(graph, 0, 5); 
	}
}



/**
 * To compute the minimal cut through ford-fulkerson
 *  Run FF to compute the max flow.
 *  The FF will show us us the residual graph. 
 *  We extract all vertices reachable from s by running DFS/BFS.
 * 	The reachable vertices define the set A for the the minimal cut.
 * All edges which are from the reachable vertexs to the non-reachable vertexes are the min cut edges.  
 **/

/**
 * The max flow min cut theorum states in a flow network, the amount of max
 * flow is equal to the capacity of the minimal cut. 
 */

