//naive algortithm 
//Initialize f=0 //f stores the flow  
//while true
//if there exists a path P from s to to such that all edges have flow < = capacity
//then {
//   B = min{ c(e) - f(e) | e is an element of P}
//   for all e is an element of P {
//       f(e) += B;
//   }
//}
//else {
//   break;
//}


//next version with residual graphs 

//for each edge e = (u,v) is an eleement of E {
//   if (f(e) < c(e)} then {
//       put a forward edge (u,v) in Ef //the residual graph. 
//       the edge has the residual capacity cf(e) = c(e) - f(e)
//   }
//   if f(e) > 0 then {
//       put a backwards edge (v,u) in Ef with residual capacity f(e)
//   }
//}


//The residual graph shows how much can be added to the forward/backwards edges to find the augmented path. 


//The aug path 

//pseudo for augmenting a path

//f.augment(P) {
//  B = min{c(e) - f(e)| e is an eleement of P} //the bottleneck
//  for each edge e = (u,v) is an eleement of P {
//      if(e is a forward edge) {
//              f(e) += B;
//      } else { //backwards edge 
//          f(e) -=B;
//      } 
//}
//}


//The ford fulkerson algorithm 

//1) flow on an edge doesn't exceed the given capacity of an edge. 
//2) Incoming flow is equal to outgoing flow for every vertex except s and t. 


/**
* pseudo: 
* 
* f <- 0 
* gf <- 0 
* while( there is an s -t path in Gf) {
*  f.augment(P)
*  update Gf based on new f
* }
* 
* 
*/


//Ford Fulkerson Algorithm

//1- Start with flow  = 0 
//2- While there is an augmenting path from s to t 
//  add this path to the flow 
//return flow


import java.util.*;
import java.lang.*;
import java.io.*;
import java.util.LinkedList;

import javax.swing.plaf.PanelUI;

class MaxFlowProblem{
 //define number of vertices in the graph 
 static final int V = 6;

 //Use a 2D array to store the vertices and their flows & capcities? 

 //two fucntions are needed:
 
 /**
  * Returns true if there is a path from source to sink in the residual graph. 
  * It will also fill the parent array[] p[]
  * 
  * @return
  */
 boolean bfs(int rGraph[][],int s, int t, int parent[]){
     //Initialize a visited arrray and mark all vertices as not visited at first. 
     boolean visited[] = new boolean[V];
     for(int i= 0; i<V;i++){
         visited[i] = false;
     }
     
     //create a queue, enqueue the source vertex and mark the source vertex as visited
     LinkedList<Integer> queue = new LinkedList<Integer>();
     queue.add(s);
     visited[s] = true;
     parent[s] = -1; //marks 

     //standard bfs loop
     while(queue.size() != 0){
         int u = queue.poll();
         for(int v = 0; v < V; v++){
             if(visited[v] == false && rGraph[u][v] > 0){
                 queue.add(v);
                 parent[v] = u;
                 visited[u] = true;
             }
         }
     }
     //if we reach the sink in the BFS starting from sourec then return true, else return false
     return (visited[t] == true);
 }

 /**
  * Recall the process is : graph -> rGraph -> maxFlow Graph 
  * @return the max flow from s to t in the original graph
  */
 int fordFulkerson(int graph [][], int s, int t){
     int u, v; //represents the edges

     //first intialize the residual graph
     int rGraph[][] = new int [V][V];//rGraph[i][j] = residual value of edge between vertices i & j

     // populate with residual capacities
     for(u = 0;u < V; u++){
            for(v = 0; v <V;v++){
                rGraph[u][v] = graph[u][v];//This will copy the contents of the regular graph into the residual     
             }
     }

     //Initialize the parent array to store the path (aug)
     int parent[] = new int[V];
     //Initialize flow
     int max_flow = 0;

     //Augment the flow while there is a path from s - t in the residual graph
     //standard bfs checks for a path in the residual graph
     while (bfs(rGraph,s,t,parent)){
         //augment
         //find the min bottleneck ~ min residual capacity of all edges in the rGraph
         int path_flow = Integer.MAX_VALUE;
         for(v=t; v != s; v = parent[v]){
             u = parent[v];
             path_flow = Math.min(path_flow, rGraph[u][v]); //DONT UNDERSTAND THIS LINE 
         }

         //update the residual capacities of the edges and reverse the edges along the path. 
         for (v=t; v != s; v=parent[v]) 
         { 
             u = parent[v]; 
             rGraph[u][v] -= path_flow; 
             rGraph[v][u] += path_flow; 
         } 
         //add the path flow to the overall flow...the path flow is the bottle neck. 
         max_flow += path_flow;
     }
     return max_flow;
 }

//Driver program to test above functions 
public static void main (String[] args) throws java.lang.Exception 
{ 
 // Let us create a graph shown in the above example 
 int graph[][] =new int[][] { {0, 16, 13, 0, 0, 0}, 
                              {0, 0, 10, 12, 0, 0}, 
                              {0, 4, 0, 0, 14, 0}, 
                              {0, 0, 9, 0, 0, 20}, 
                              {0, 0, 0, 7, 0, 4}, 
                              {0, 0, 0, 0, 0, 0} 
                            }; 
 MaxFlowProblem m = new MaxFlowProblem(); 

 System.out.println("The maximum possible flow is " + 
                    m.fordFulkerson(graph, 0, 5)); 

} 
}
