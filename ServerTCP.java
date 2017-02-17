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

//Server UDP Class
public class ServerTCP {
    //Globals
    static ServerSocket s      = null;
    static Socket con          = null;
    static PrintStream out     = null;
    static BufferedReader in   = null;
    static String msg          = null;

    public static void main(String[] args)
    {
        //Cant be static, Variables for input
        String portInput        = args[0];
        int port                = convertToInt(portInput);

        //Start Server
        try
        {
            s  = new ServerSocket(port, 10);
            System.out.println("TCP Server: "+port);

            //Wait for connection
            while((con = s.accept())!=null)
            {
                //Connection Received
                out = new PrintStream(con.getOutputStream());
                out.flush();
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				//While message exists
                while ((msg = in.readLine()) != null)
                {
                    int t = 0;
                    try
                    {
						//Create packets
                        for (int i = 0; i < msg.length(); i++)
                        {
                            t += Integer.parseInt(msg.charAt(i) + "");
                        }
                        if(msg.length() <= 1 && Character.isDigit(msg.charAt(0))) {
                            System.out.println("Single Didget Entered. Server Shutting Down. ");
                            break;
                        }
                        out.println(String.valueOf(t));
                    } catch (NumberFormatException nfe)
                    {
                        out.println(" Sorry, cannot compute! ");
                    }
                }
            }
            //Close all
            out.close();
            in.close();
            s.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }// end main

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
}