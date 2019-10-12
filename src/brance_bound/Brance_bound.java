
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package brance_bound;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 *
 * @author soneya
 */
public class Brance_bound {

    int numOfpoints;
    int [][] coordinate;
    double[][] weight;
    double bestSofar;
    int[] solution;
    int [] parent;
    double [] key;
  
   

   public Brance_bound (int N, Scanner input){
        numOfpoints=N;
        weight= new double [N][N];
        coordinate=new int [N][2];
        solution=new int [N+1];
        input(input);
    }
   private void input(Scanner input)
   {
       String in;
       
       for (int i=-1;i<numOfpoints+1;i++)
       {
            in = input.nextLine();
       
            StringTokenizer strToken = new StringTokenizer(in);
            int count = strToken.countTokens();
         
            for(int x = 0;x < count;x++){
                  coordinate[i][x] = Integer.parseInt((String)strToken.nextElement());
                  
            }
         
       }
   }
    void calculate_distance()
    {
        for (int i=0;i<numOfpoints;i++)
        {
            for(int j=0;j<numOfpoints;j++)
            {
                if(i==j) {
                    weight[i][j]=0;
                }
                else 
                {
                    int x=(coordinate[i][0]-coordinate[j][0]);
                    int y=(coordinate[i][1]-coordinate[j][1]);
                    int d= (x*x)+(y*y);
                    double c =Math.sqrt(d);
                    c=Brance_bound .round(c, 2);
                    weight[i][j]=c;
                }
            }
        }
    }
    public static double round(double value, int places) {
    if (places < 0) {
            throw new IllegalArgumentException();
        }

    BigDecimal bd = new BigDecimal(value);
    bd = bd.setScale(places, RoundingMode.HALF_UP);
    return bd.doubleValue();
}
    
    
  public void print_array()
 { 
     
     for (int i = 0; i < numOfpoints; i++) 
     {
            
            for (int j = 0; j < numOfpoints; j++) 
            {
                System.out.print(weight[i][j]+" ");

            }
            System.out.println(" ");
     }
                 
 }
  
  double calculate_cost(Vector<Integer> test)
  {
      double cost=0;
      for(int i=0;i < test.size()-1; i++){
          cost=cost+weight[test.elementAt(i)][test.elementAt(i+1)];
      }
      return cost;
  }
  
  
  
   void updateSofar( Vector<Integer> test)
   {
       double cost=calculate_cost(test);
       System.out.println("actual cost"+cost);
       if(cost<bestSofar)
       {
           bestSofar=cost;
           
           
           for(int p=0;p<test.size();p++)
           {
               solution[p]=test.elementAt(p);
           }
       }
   }
    double calculate_heuristic_cost(Vector<Integer> test,Vector<Integer> test1)
    {     
           double c=0;
           int i=test.size();
           int j= test.elementAt(i-1);
           
           
           double cost1=calculate_cost(test);
           //System.out.println(cost1);
//.........................................................................................................
           double cost2=100000000;
           for(int k=0;k<test1.size();k++)
           {
               if((weight[j][test1.elementAt(k)]<cost2))
               {
                   cost2=weight[j][test1.elementAt(k)];
               }
           }
           //System.out.println(cost2);
//..................................................................................................................................
           double cost3=10000000;
           for(int k=0;k<test1.size();k++)
           {
               if((weight[0][test1.elementAt(k)]<cost3))
               {
                   cost3=weight[0][test1.elementAt(k)];
               }
           }
           //System.out.println(cost3);
//....................................................................................................
           double cost4=MST_prims(test1);
           //System.out.println(cost4);
           c=cost1+cost2+cost3+cost4;
           //System.out.println(test1.elementAt(0));
           //System.out.println(test1.elementAt(1));
           //System.out.println(test1.elementAt(2));
           //System.out.println(test1.elementAt(3));
           return c; 
    }
    
    double MST_prims(Vector<Integer> test1)
    {
        
        
        
            //System.out.println(test1.size());
            key=new double [test1.size()];
            parent=new int [test1.size()];
           
            double cost=0;
            for (int j = 0; j < test1.size(); j++) 
            {
                if(j==0) {
                    key[j]=0;
                    parent[j]=-1;
                }
                else
                {
                    key[j]=1000000000;
                    parent[j]=-1;
                }
            }
            
            /*for (int j = 0; j <test1.size() ; j++) 
            { 
                  System.out.println(parent[j]);
            } */
//........................................................................................
            
            for (int j = 0; j < test1.size(); j++) // 2 3 4 
            {
               int node=min_priority_queue(test1.size()); 
               
               /*System.out.println("......."+node+"......."+test1.elementAt(node));
                for (int l = 0; l <test1.size() ; l++) 
                { 
                      System.out.println(key[l]);
                }*/
               for (int k = 0; k < test1.size(); k++) 
               {
                  if((key[k]!=-10) && (weight[test1.elementAt(node)][test1.elementAt(k)]<key[k]))
                  {             
                      parent[k]=test1.elementAt(node);
                      key[k]=weight[test1.elementAt(node)][test1.elementAt(k)];
                  }
               }
               
               
            }
            
            
            /*for (int l = 0; l <test1.size() ; l++) 
            { 
                      System.out.println(parent[l]);
            }*/
            
            for (int j = 1; j < test1.size(); j++) // 1 2 3 4 
            {
               cost=cost+weight[test1.elementAt(j)][parent[j]];
            }
            return cost;
    }
    int min_priority_queue(int size)
    {
            int min_index=-1;
            double min_cost=1000000;
            for (int j = 0; j < size; j++) 
            {
                
                if((key[j]<min_cost) && (key[j]>=0)) {
                  
                    min_index=j;
                    min_cost=key[j];
                }
            }
            
            key[min_index]=-10;
            return min_index;
    }
    
    
    
    
    void branch_bound_tour()
    {
      
       
       Vector<tour_subproblem> subproblem_set1 = new Vector();
       bestSofar=1000000000;
       Vector<Integer> SS = new Vector<>(); 
       SS.add(0);
       tour_subproblem v=new tour_subproblem(SS,numOfpoints);
       subproblem_set1.addElement(v);
       Vector<Integer> test;
       Vector<Integer> test1;
       
       //System.out.println(SS.elementAt(0));
       //System.out.println(subproblem_set1.elementAt(0).V_S.elementAt(5));
       
       //!subproblem_set1.isEmpty()
       int m=0;
       while (!subproblem_set1.isEmpty())
       {
           tour_subproblem current = subproblem_set1.elementAt(subproblem_set1.size()-1);
           subproblem_set1.remove(current);
           
           
           
           
           
           test=current.S;
           test1=current.V_S;
           
           //System.out.println(test.elementAt(0));
           //System.out.println(test1.elementAt(3));Vector v2 = (Vector)v1.clone(); 
           System.out.println(test1.size());

           //test1.size()
           for(int i=0;i<test1.size();i++)
           { 
               Vector<Integer> test2=(Vector)test.clone();
               Vector<Integer> test3=(Vector)test1.clone(); 
               System.out.println(test2.size());
               System.out.println(test3.size());
               test2.add(test3.elementAt(i));
               test3.remove(i);
               
               
               double c=calculate_heuristic_cost(test2,test3);
               System.out.println(c);
               
               if(test2.size()==numOfpoints)
               {
                    test2.add(0);
                    System.out.println("yes");
                    
                    //call_subproblem(subproblem_set1);
                    updateSofar(test2);
                    System.out.println("best so far"+bestSofar);
                    
               }
               else{
                    if(c<bestSofar) 
                    {
                       subproblem_set1.addElement(new tour_subproblem(test2,numOfpoints));
                    }
               }
               
               
           }
        
           
           
           call_subproblem(subproblem_set1);
           
           
          m++; 
       }
       
    }
    
     void call_subproblem(Vector<tour_subproblem> subproblem_set1)
     {
         for(int i=0;i<subproblem_set1.size();i++)
         {
             for(int k=0;k<subproblem_set1.elementAt(i).S.size();k++)
            {
               System.out.print(subproblem_set1.elementAt(i).S.elementAt(k)+" ");
            }
             System.out.println(); 
         }
        
         
     }
    
    
    void print_solution()
    {
        for(int j=0;j<solution.length;j++)
        {
           System.out.print(solution[j]+" ");
        }
    }
    public static void main(String[] args) throws FileNotFoundException, IOException {
        final int sta_Time =(int) System.currentTimeMillis();

        Scanner input = new Scanner(new File("input_17.txt"));
        int data_size = input.nextInt();
     
        
        Brance_bound  instance= new Brance_bound (data_size,input);
        instance.calculate_distance();
        instance.print_array();
        instance.branch_bound_tour();
        System.out.println("best_final: "+ instance.bestSofar);
        instance.print_solution();
        final int end_Time =(int) System.currentTimeMillis();
        int duration=end_Time-sta_Time;
        
        System.out.println("exectution time : "+duration+" millisecond");  
        File dir=new File(".");
        File dir1=new File(".");
        String location= dir.getCanonicalPath()+File.separator+ "BnB_1005105.txt";
        String location1= dir1.getCanonicalPath()+File.separator+ "BnB_cost_1005105.txt";
        FileWriter f=new FileWriter(location,true);
        FileWriter f1=new FileWriter(location1,true);
        BufferedWriter b= new BufferedWriter(f);
        BufferedWriter b1= new BufferedWriter(f1);
        b.write(Integer.toString(data_size));
        b.write("  ");
        b1.write(Integer.toString(data_size));
        b1.write("  ");
        b.write(Integer.toString(duration));
        b1.write(Double.toString(instance.bestSofar));
        b.newLine();
        b1.newLine();
        b.close();
        b1.close();
       
       
    }
}


/*
 * Start with some problem P0
Let S = {P0}, the set of active subproblems
bestsofar = ∞
Repeat while S is nonempty:
choose a subproblem (partial solution) P ∈ S and remove it from S
expand it into smaller subproblems P1, P2, . . . , Pk
For each Pi:
If Pi is a complete solution: update bestsofar
else if lowerbound(Pi) < bestsofar: add Pi to S
return bestsofar

 */



