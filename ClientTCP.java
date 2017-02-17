import java.io.*;
import java.net.*;
import java.util.Scanner;

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

public class ClientTCP {
    public static void main(String[] args)
    {
        //Variables
        Socket s                = new Socket();
//        String host             = "127.0.0.1";
        String host             = args[0];
        String portInput        = args[1];
        PrintWriter s_out       = null;
        BufferedReader s_in     = null;
        Scanner scan            = new Scanner(System.in);

        int port = convertToInt(portInput);

        try
        {
            //Attempt to connect to host
            s.connect(new InetSocketAddress(host,port));
            //Connection Passed
            System.out.println("ClientTCP "+ host +" "+port);
            //Create Writer from Socket
            s_out = new PrintWriter(s.getOutputStream(), true);
            //Create Reader from Socket
            s_in = new BufferedReader(new InputStreamReader(s.getInputStream()));

        } catch (IOException e)
        {
            //Connection Failed
            System.out.println("Error connecting to " + host + " on port "+port);
            System.exit(1);
        }
        //Take input off numbers, validation will occur on Server Side
        System.out.print("Enter String of numbers: ");
        s_out.println(scan.nextLine());
        //Wait for response from server
        String r;
        try
        {
            while ((r = s_in.readLine()) != null && !r.contains("Sorry"))
            {
                System.out.println("FROM SERVER: " + r);
                if (r.length() <= 1) {
                    break;
                }else {
                    s_out.println(r);
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        //Close All inputs
        try
        {
            s_out.close();
            s_in.close();
            s.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //Convert string to int
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
