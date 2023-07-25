import java.io.*;
import java.net.*;

public class MailClient {
    public static void main(String[] args) throws IOException {

        Socket msSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        int port=Integer.parseInt(args[1]);
        String ip=args[0];

        //"127.0.0.1"
        try {
            msSocket = new Socket(ip, port);
            out = new PrintWriter(msSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(msSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: taranis.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: taranis.");
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;

        while ((fromServer = in.readLine()) != null) {
            System.out.println("Server: " + fromServer);

            while((fromServer = in.readLine()) != null) {
                if(fromServer.equals("END")) {
                    //in.readLine();

                    break;
                }
                System.out.println("Server:  " + fromServer);
            }


            fromUser = stdIn.readLine();
            if (fromUser.equals("Exit"))
            {
                break;
            }

            if (fromUser != null) {
                System.out.println("Client: " + fromUser);
                out.println(fromUser);
            }
        }

        out.close();
        in.close();
        stdIn.close();
        msSocket.close();
    }
}

