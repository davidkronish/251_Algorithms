import java.util.*;


/**
 * TME COMPLEXITY O(n^2) 
 * 
 * @author DavidKronish
 * Pseudocode: 
 * 
 * matching <- Empty
 * while there exists a free element from A not matched to element from B 
 * 	b <- preference[a].removefirst()
 * if b not yet matched then
 * 			matching -=<- matching U {(a,b)} 
 * else 
 *   x <- b's current match
 *   if b prefers a more than x then
 *   matching <- matching - {(a,b)}
 * 
 * 
 */

/*
 * Pseudocode from geeks
  Initialize all men and women to free
  while there exist a free man m who still has a woman w to propose to 
{
    w = m's highest ranked such woman to whom he has not yet proposed
    if w is free
       (m, w) become engaged
    else some pair (m', w) already exists
       if w prefers m to m'
          (m, w) become engaged
           m' becomes free
       else
          (m', w) remain engaged    
}
  
 */


/*
 * Input: a 2d matric of size (2*N)*N where N is the number of women or men.
 *  Rows 0 -> n-1 represent preference list of men
 *  Rows n -> 2n-1 represent pref of women. 
 */
public class GaleShapleyAlgorithm {
	//intilize nm of M & W 
	static int n = 4;
	
	public static void main(String[] args) {
		int prefer[][] = new int[][]{{7, 5, 6, 4},{5, 4, 6, 7},  
                {4, 5, 6, 7},  
                {4, 5, 6, 7},  
                {0, 1, 2, 3},  
                {0, 1, 2, 3},  
                {0, 1, 2, 3},  
                {0, 1, 2, 3}};
            stableMarriage(prefer);
		
	}
	
	//A function that returns true if the woman prefers m1 or m 
	static boolean womanPrefersM1overM2(int man1, int man2, int woman,int prefer[][]) {
		//check if m1 is better than m2 on her preference sheet
		for(int i =0;i < n;i++) {
			//preference is based on if the value comes up first, if m1 comes up first,
			//return true if not than return false. 
			if(prefer[woman][i]==man1) {
				return true;
			} 
			if(prefer[woman][i]==man2) {
				return false;
			}
		}
		return false;
	}
		
	
	
	
	//Takes in the 2D input array
	static void stableMarriage(int prefer[][]) {
		//The value of womanPartner[i] is the partner assigned to woman n+i.
		int womanPartner[] = new int[n];
		//If mFree[i] is false, man 'i' is free. 
		boolean manFree[] = new boolean[n];
		int nm_of_free_men = n;
		
		for(int i=0; i<n; i++) 
		{
			womanPartner[i] = -1;
		}
//		for( int e: womanPartner) {
//			System.out.println(e);
//		}
		
		while(nm_of_free_men > 0) 
		{
			//choose a man and offer him to a B
			int m;
			for (m=0; m < n;m++) if(manFree[m]==false) break;
			//now m holds the value of the first free man 
			//m's preferences will be stored in the array prefer[m][]
			for(int i = 0; i<n && manFree[m]==false;i++) 
			{
				int w = prefer[m][i];
				if (womanPartner[w-n]==-1) {
					womanPartner[w-n] = m;
					manFree[m] = true; //therefore he is engaged 
					nm_of_free_men--;
				}
				else //woman is not free			
				{	
				//will have to see if the man m is a better option than her current one
				//if yes then change the partner array, change man status, don't change counter
				//if no keep match as is
				int currentMan = womanPartner[w-n];
				int proposedMan = m;
					if (womanPrefersM1overM2(m, currentMan, w, prefer)) {
						womanPartner[w-n] = m;
						manFree[currentMan] = false;
						manFree[m] = true;
					}
				}
			}
		}
						
		System.out.println("Woman Man");  
		for (int i = 0; i < n; i++)  
		{ 
		    System.out.print(" ");  
		    System.out.println(i + n + "     "+womanPartner[i]); 
		} 		
	}
	}
	
	
	
	













