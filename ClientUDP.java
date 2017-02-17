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

//Udp Client Class
public class ClientUDP {
    //Global variables
    static BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
    static String payload;

    //Begin Main
    public static void main(String args[]) throws Exception
    {
        //Delare variables
        byte[] sendData;
        byte[] receiveData;
        String host             = args[0];
        String portInput        = args[1];
        int port                = convertToInt(portInput);

        //Begin try
        try{
            DatagramSocket clientSocket     = new DatagramSocket();
            InetAddress IPAddress           = InetAddress.getByName(host);
            //InetAddress IPAddress           = InetAddress.getByName("localhost");
            //Send the packet
            getPayload(IPAddress, port);
            sendData                        = payload.getBytes();
            DatagramPacket sendPacket       = new DatagramPacket(sendData, sendData.length,IPAddress,port);
            clientSocket.send(sendPacket);
            DatagramPacket receivePacket;

            //While Loop
            while(true){
                receiveData                         = new byte[1024];
                clientSocket.receive(receivePacket  = new DatagramPacket(receiveData, receiveData.length));
                String modifiedSentence             = new String(receivePacket.getData()).trim();

                //Recieve From Server
                System.out.println("FROM SERVER:" + modifiedSentence);
                if (modifiedSentence.length()<=1 || modifiedSentence.contains("Sorry"))
                {
                    break;
                } else
                {
					//Send the data
                    sendData = modifiedSentence.getBytes();
                    sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                    clientSocket.send(sendPacket);
                }
            }
            //close the socket
            clientSocket.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
			//Close program
            System.out.println("Response Received. Program Closing.");
        }
    }//end main

    //Get Payload Method
    public static String getPayload(InetAddress i, int p) throws Exception
    {
        System.out.println("ClientUDP " + i.getHostAddress() + " " + p);
        System.out.print("Enter String of numbers: ");
        payload = inFromUser.readLine();
        return payload;
    }//End Get payload Method

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
}//End Program