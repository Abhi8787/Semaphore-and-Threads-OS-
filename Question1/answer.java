import java.io.*;
import java.util.*;
import java.util.concurrent.*;



class shared 
{
 static int x = 0; 
 static int b0 = 0;
 static int b1 = 0;
 static int[] fib = new int[18]; 
 static HashMap<Integer,Integer> hm = new HashMap<>(); 

 static Semaphore lock = new Semaphore(1);

 static Semaphore s0 = new Semaphore(1);
 static Semaphore s1 = new Semaphore(0);
 static Semaphore s2 = new Semaphore(0);

}


class MyThread extends Thread
{
  String threadName;

  public MyThread(String threadName)
 {
    this.threadName = threadName;
 }   

 public void run()
 {
    if(threadName.equals("A"))
    {
      while(true) {
      System.out.println("Starting Thread 1");
      try 
      {
        shared.x = shared.x+1;        
        shared.s0.acquire();
        shared.lock.acquire();
        shared.b0 = shared.x;
        shared.lock.release();
        shared.s1.release();  
        Thread.sleep(1000);
        

      }
  
      catch(InterruptedException exc)
      {
         System.out.println(exc);
      }
      
  }
    }




    if(threadName.equals("B"))
    {
      while(true) {
      System.out.println("Starting Thread 2");
      try 
      {

        
        shared.s1.acquire();
        shared.lock.acquire();

        if(shared.b0%2 !=0 && shared.b0 %3 !=0  && shared.b0 %5 != 0 && shared.b0%7 != 0)
        shared.b1 = shared.b0;
        
        shared.lock.release();
        shared.s2.release();  
        Thread.sleep(1000);

      }
  
      catch(InterruptedException exc)
      {
         System.out.println(exc);
      }
}

    }


    if(threadName.equals("C"))
    {
      while(true) {
      System.out.println("Starting Thread 3");
      try 
      {

        
        shared.s2.acquire();
        shared.lock.acquire();
        
         boolean isFib = true;

         for(int val : shared.fib)
         {
            if(shared.b1 == val)
              isFib = false;
         }
        
         if(isFib == true && shared.hm.containsKey(shared.b1) == false)
         {
             System.out.println(shared.b1);
             shared.hm.put(shared.b1 , 1); 
         }        
         
        shared.lock.release();
        shared.s0.release();  
        Thread.sleep(1000);

      }
  
      catch(InterruptedException exc)
      {
         System.out.println(exc);
      }
}
    }


 }

}


class program
{
 
  public static void main(String[] args) throws Exception 
  {
      
     shared.fib[0] = 0;
     shared.fib[1] = 1;
     
     for(int i=2 ; i<18 ; i++)
      shared.fib[i] = shared.fib[i-1] + shared.fib[i-2]; 
          

   
     MyThread thread1 = new MyThread("A");
     MyThread thread2 = new MyThread("B");
     MyThread thread3 = new MyThread("C");
     
 
      thread1.start();
      thread2.start();
      thread3.start();

      thread1.join(); 
      thread2.join();
      thread3.join();
      

     
   
 
   System.out.println("The End");
     
  }
}
