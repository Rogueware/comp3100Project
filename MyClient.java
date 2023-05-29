import java.net.*;  
import java.io.*;  


class MyClient {
//Function for finding index of whitespace
    public static int ordinalIndexOf(String str, String substr, int n) {
        int pos = -1;
        do {
            pos = str.indexOf(substr, pos + 1);
        } while (n-- > 0 && pos != -1);
        return pos;
    }
    public static void main(String args[])throws Exception{  
        Socket s=new Socket("localhost",50000);  
        BufferedReader din=new BufferedReader(new InputStreamReader(s.getInputStream()));  
        DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));  
        
        String str="",str2="",ok="OK\n",auth="AUTH rogue\n",redy="REDY\n",helo="HELO\n";
        String largestname="";
        int largestcore=0;
        int numberoflargestserver=0;

        int jobID=0;
        String hold;

        //Completing initial connection steps like HELO and AUTH
        dout.write((helo).getBytes());  
        dout.flush();
        str2=din.readLine();  
        System.out.println("Server says: "+str2);  

        dout.write((auth).getBytes());  
        dout.flush();
        str2=din.readLine();  
        System.out.println("Server says: "+str2);  

        dout.write((redy).getBytes());  
        dout.flush();
        str2=din.readLine();  
        System.out.println("Server says: "+str2);

        //Saving JobID
        jobID = Integer.valueOf(str2.substring(ordinalIndexOf(str2," ",1)+1,ordinalIndexOf(str2," ",2)));
        //hold = (str2.substring(ordinalIndexOf(str2," ",1)+1,ordinalIndexOf(str2," ",2)));

        //Retrive server records
        dout.write(("GETS All\n").getBytes());  
        dout.flush();
        str2=din.readLine();  
        System.out.println("Server says: "+str2);  

        //Compare and save largest
        while(!str2.equals(".")){
            dout.write((ok).getBytes());  
            dout.flush();
            str2=din.readLine();  
            System.out.println("Server says: "+str2);  

            dout.write((auth).getBytes());  
            dout.flush();
            str2=din.readLine();  
            System.out.println("Server says: "+str2);  

            dout.write((redy).getBytes());  
            dout.flush();
            str2=din.readLine();  
            System.out.println("Server says: "+str2);

            jobID = Integer.valueOf(str2.substring(ordinalIndexOf(str2," ",1)+1,ordinalIndexOf(str2," ",2)));
            //hold = (str2.substring(ordinalIndexOf(str2," ",1)+1,ordinalIndexOf(str2," ",2)));

            dout.write(("GETS All\n").getBytes());  
            dout.flush();
            str2=din.readLine();  
            System.out.println("Server says: "+str2);  

            while(!str2.equals(".")){
                dout.write((ok).getBytes());  
                dout.flush();
                str2=din.readLine();  
                //System.out.println("Server says: "+str2);
                if(str2.equals(".")) break;
                //if(){
                    largestname = str2.substring(0, str2.indexOf(' '));
                    numberoflargestserver = Integer.valueOf((str2.substring(ordinalIndexOf(str2," ",0)+1,ordinalIndexOf(str2," ",1))));
                    largestcore = Integer.valueOf(str2.substring(ordinalIndexOf(str2," ",3)+1,ordinalIndexOf(str2," ",4)));
            }

            //System.out.println(jobID+","+largestname+","+numberoflargestserver+","+largestcore);

            while(!str.equals("stop")){  
                str=br.readLine();
                dout.write((str+"\n").getBytes());  
                dout.flush();
                str2=din.readLine();  
                System.out.println("Server says: "+str2);  
            }  
        
            dout.close();  
            s.close();  
        }
    }
}
