import java.io.*;
import java.util.*;
import java.util.concurrent.*;


class shared
{
  static Stack<Integer> s1 = new Stack<>();
  static Stack<Integer> s2 = new Stack<>();
    
  static float avg;
  static int median;  
   
   
}


class MyThread extends Thread
{
   String threadName;

   public MyThread( String threadName)
   {
      this.threadName = threadName;
   }

   public void run()
   {
     if(threadName.equals("A"))
     {
        System.out.println("Starting Thread 1");
        try
        {
           int sum = 0;
           while(shared.s1.size()>0)
           {
              sum += shared.s1.pop();
           }
           shared.avg = (sum)/5;
               
          System.out.println("Execution of Thread 1 completed successfully");
          Thread.sleep(1000);
        }

        catch(InterruptedException exc)
        {
          System.out.println(exc); 
        }
        

     }

      if(threadName.equals("B"))
     {
        System.out.println("Starting Thread 2");
        try
        {
            int[] arr = new int[5];
            int i=0;
            while(shared.s2.size() > 0)
            {
                  arr[i] = shared.s2.pop();
                  i++;
            }
            
            Arrays.sort(arr);
           
            shared.median = arr[2];
             Thread.sleep(1000);
          System.out.println("Execution of Thread 2 completed successfully");
        }

        catch(InterruptedException exc)
        {
          System.out.println(exc); 
        }
        

     }
     

   }
}


class program
{
  public static void main(String[] args) throws Exception 
  {
     Scanner scn = new Scanner(System.in);

     System.out.println("Enter the Elements in Stack 1");
      for(int i=0 ; i<5 ; i++)
      {
        int val = scn.nextInt(); 
        shared.s1.push(val);
      }

     System.out.println("Enter the Elements in Stack 2");
      for(int i=0 ; i<5 ; i++)
      {
        int val = scn.nextInt(); 
        shared.s2.push(val);
      }


     
       MyThread thread1 = new MyThread("A");
       MyThread thread2 = new MyThread("B");

       thread1.start();
       thread2.start();

       thread1.join();
       thread2.join();


       System.out.println();
       System.out.println();
       System.out.println("Now Execution comes to the main Thread");

                         
       System.out.println();
       System.out.println();
       float diff = Math.abs(shared.avg - shared.median);
       System.out.println("Average value is " + shared.avg);
       System.out.println("Median value is " + shared.median);
       System.out.println("Difference of two number is " + diff); 

     

  }
}


