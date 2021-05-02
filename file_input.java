
import java.util.*;
import java.io.File;
public class file_input{
    public static void main(String args[])        
   {     try{
                Scanner scanner = new Scanner(new File("input.txt"));
                int [] tall = new int [100];
                int i = 0;
                while(scanner.hasNextInt())
                {
                    tall[i++] = scanner.nextInt();
                    System.out.println(tall[i-1]);
                }

            }

         catch(Exception ex)
         {
            ex.printStackTrace();   
         }   



    }    

}