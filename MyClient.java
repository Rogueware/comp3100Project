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
                
               String temp = reply;
               String arrOfStr[] = temp.split(" ",-1);
               mess = "GETS Avail "+ arrOfStr[4] + " " + arrOfStr[5] + " " + arrOfStr[6] +"\n";
               System.out.println(mess);
               dout.write(mess.getBytes());
               dout.flush();
               reply = din.readLine();

               temp = reply;
               count = temp.split(" ", -1);

                if(Integer.parseInt(count[1])!=0) { // no server readily available
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

// public String WriteFlushRead(String mess, doutputStream out, BufferedReader in) {
//     out.write(mess.getBytes());
//     out.flush();
//     return in.readLine();
// }

}
        
