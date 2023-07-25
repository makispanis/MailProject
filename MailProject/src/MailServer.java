import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MailServer {

    public static void main(String[] args) throws IOException {

        ArrayList accounts=new ArrayList<>();
        Email email1 = new Email("user2@email.gr","user1@email.gr","test 1","test email 1",true);
        Email email2 =new Email("user2@email.gr","user1@email.gr","test 2","test email 2",false);
        Email email3= new Email("user2@email.gr","user1@email.gr","test 3","test email 3",true);
        Account account1= new Account();
        account1.username="user1@email.gr";
        account1.password="password1";
        account1.mailbox.add(email1);
        account1.mailbox.add(email2);
        account1.mailbox.add(email3);
        accounts.add(account1);
        Email email11 = new Email("user1@email.gr","user2@email.gr","test 1","test email 1",false);
        Email email22 =new Email("user1@email.gr","user2@email.gr","test 2","test email 2",true);
        Email email33= new Email("user1@email.gr","user2@email.gr","test 3","test email 3",false);
        Account account2= new Account();
        account2.username="user2@email.gr";
        account2.password="password2";
        account2.mailbox.add(email11);
        account2.mailbox.add(email22);
        account2.mailbox.add(email33);
        accounts.add(account2);

        ServerSocket serverSocket = null;
        boolean listening = true;
        int port=Integer.parseInt(args[0]);

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Could not listen on port: "+port+".");
            System.exit(-1);
        }

        while (listening)
            new MailServerThread(serverSocket.accept(),accounts).start();

        serverSocket.close();
    }
}
