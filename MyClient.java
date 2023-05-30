import java.io.*;
import java.net.*;

public class MyClient {
   public static void main(String args[])throws Exception{
       Socket socket = null;
       socket = new Socket("localhost",50000);

       DataOutputStream dout=new DataOutputStream(socket.getOutputStream()); 
       BufferedReader din = new BufferedReader(new InputStreamReader(socket.getInputStream()));

       int norecs = 0;
       int jobID = 0;
       String count[];

       String reply = "";
       String mess = "";
       String str="",str2="",ok="OK\n",auth="AUTH rogue\n",redy="REDY\n",helo="HELO\n";

       dout.write(helo.getBytes());  
       dout.flush();
       str2=din.readLine();  

       dout.write(auth.getBytes());  
       dout.flush();
       str2=din.readLine();  

       dout.write(redy.getBytes());  
       dout.flush();
       str2=din.readLine();  
        
       //This while loop continues to run until there are no more jobs
        while(!reply.equals("NONE")){
            if(reply.contains("JOBN")) { 
                //If there is a job split the core count, memory and disk space into the array and search for servers avaliable that exactly
                //match the requirements of the job
                String temp = reply;
                String arrOfStr[] = temp.split(" ",-1);
                mess = "GETS Avail "+ arrOfStr[4] + " " + arrOfStr[5] + " " + arrOfStr[6] +"\n";
                dout.write(mess.getBytes());
                dout.flush();
                reply = din.readLine();
                System.out.println(reply);

                temp = reply;
                count = temp.split(" ", -1);
                System.out.println(count[1]);
                
                //if servers are avaliable write data of servers to count
                if(Integer.parseInt(count[1])!=0) { 
                    norecs = Integer.parseInt(count[1]);
                    //System.out.println(norecs);
                    count = new String[norecs];

                    dout.write(ok.getBytes());
                    dout.flush();

                    for(int i = 0; i < norecs; i++){
                        count[i] = din.readLine();
                    }

                    dout.write(ok.getBytes());
                    dout.flush();
                    reply = din.readLine();

                    //take the first server and split the string into substrings
                    String firstServer[] = count[0].split(" ", -1);
                    
                    //schedule the job with the server
                    jobID = Integer.parseInt(arrOfStr[2]);
                    mess = "SCHD" + " " + jobID +" " + firstServer[0] + " " + firstServer[1] + "\n";
                    dout.write(mess.getBytes());
                    dout.flush();
                    reply = din.readLine();
                }    

                //if no servers available get first capable server and schedules it with that server
                else{
                    dout.write(ok.getBytes());
                    dout.flush();
                    reply = din.readLine();

                    mess = "GETS Capable "+ arrOfStr[4] + " " + arrOfStr[5] + " " + arrOfStr[6] +"\n";
                    dout.write(mess.getBytes());
                    dout.flush();
                    reply = din.readLine();

                    temp = reply;
                    count = temp.split(" ", -1);

                    norecs = Integer.parseInt(count[1]);
                    count = new String[norecs];

                    dout.write(ok.getBytes());
                    dout.flush();

                    for(int i = 0; i < norecs; i++){
                        count[i] = din.readLine();
                    }

                    dout.write(ok.getBytes());
                    dout.flush();
                    reply = din.readLine();

                    String firstServer[] = count[0].split(" ", -1);
                    
                    jobID = Integer.parseInt(arrOfStr[2]);
                    mess = "SCHD" + " " + jobID +" " + firstServer[0] + " " + firstServer[1] + "\n";
                    dout.write(mess.getBytes());
                    dout.flush();
                    reply = din.readLine();
                }
            }

            dout.write(redy.getBytes());
            dout.flush();
            reply = din.readLine();
        }
       mess = "QUIT\n";
       dout.write(mess.getBytes());
       dout.flush();
       reply = din.readLine();

       dout.close();
       socket.close();       
    }   
}
        
