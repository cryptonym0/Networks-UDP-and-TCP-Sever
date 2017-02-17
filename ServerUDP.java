import java.io.*;
import java.net.*;

/******************************************************************************************
* Moon, Silver, S.M. (2012) Socket programming in Java (Version 2.0) [Computer Tutorial].
* Available at http://www.binarytides.com/java-socket-programming-tutorial/
* (Accessed 17 February 2017)
* Used for creating socketClient and Socket Sever
*I used this code to create sockets
*
* Drager, Dave, D.D. (2017) A Simple Java UDP Server and UDP Client [Computer Tutorial].
* Available at https://systembash.com/a-simple-java-udp-server-and-udp-client/
* (Accessed 17 February 2017)
* Used for creating UDPClient and UDPSever
* I used this code to create the backbone of my UDPClient and UDPServer
*
* Tayjay T. (2017) Github [Github User].
* Available at https://github.com/tayjay?tab=repositories
* (Accessed 17 February 2017)
* Used for understanding difference in ports.
*
* ***************************************************************************************/

/*
* The UDP protocol works similarly to TCP, but it throws all the error-checking stuff out.
* All the back-and-forth communication and deliverability guarantees slow things down.
*
* */
public class ServerUDP {

    //Begin main
    public static void main(String args[]) throws Exception
    {
        //Delacre variables
        byte[] receiveData;
        DatagramPacket r;
        String portInput        = args[0];
        int port                = convertToInt(portInput);

		//Begin runing server
        try{
            DatagramSocket serverSocket = new DatagramSocket(port);
            System.out.println("UDP Server Running: " + port);

            //Server must always wait
            while(true){
                receiveData             = new byte[1024];
                serverSocket.receive(r  = new DatagramPacket(receiveData, receiveData.length));
                String s                = new String(r.getData()).trim();
                InetAddress ipAddress   = r.getAddress();
                port                    = r.getPort();
                int t                   = 0;//Total

                //Need to account for if the string != Integer
                try {
                    //Read string
                    for (int i = 0; i < s.length(); ++i) {
                        t += Integer.parseInt(String.valueOf(s.charAt(i)));
                    }
                    if(s.length() <= 1 && Character.isDigit(s.charAt(0))) {
                        System.out.println("Single Didget Entered. Server Shutting Down. ");
                        break;
                    }

                }catch(NumberFormatException e)
                {
                    String er = " Sorry, cannot compute! ";
                    serverSocket.send(new DatagramPacket(er.getBytes(),er.length(),ipAddress,port));
                    continue;//Don't just exit
                }
                //Sends packets back
                String payload = String.valueOf(t);
                serverSocket.send(new DatagramPacket(payload.getBytes(), payload.length(),ipAddress,port));
            }//end while
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }//end main

    //Converty string to int
    public static int convertToInt(String s){
        int output=0;
        try{
            output = Integer.parseInt(String.valueOf(s));
        }catch(NumberFormatException e) {
            output = 32000;
            String er = "Invalid Port Number. Setting Default port to 32000 ";
            System.out.println(er);
        }
        return output;
    }
}//end server
