/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package brance_bound;

import java.util.Vector;

/**
 *
 * @author soneya
 */
class tour_subproblem {
    int start_node;
    int last_node;
    Vector<Integer> S = new Vector<>(); 
    Vector<Integer> V_S = new Vector<>(); 

    public tour_subproblem( Vector<Integer> SS,int N) {
     
       S=SS;
       create_v_s(N);
    }
   private void create_v_s(int N)
   {
    for(int i=0;i<N;i++)
    {
        int flag=0;
        for(int j=0;j<S.size();j++)
        {
            if(i==S.elementAt(j)) {
                flag=1;
                break;
            }
        }
        
           if(flag==0) {
            V_S.add(i);
        }
        
        
    }
   }
    
   
}
