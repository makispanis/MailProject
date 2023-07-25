import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MailProtocol {
    public List<Account> accounts;
    public Account currentUser;


    private static final int FirstMenu = 0;
    private static final int SecondMenu = 1;
    private static final int ThirdMenu = 2;

    private int state = FirstMenu;




    public MailProtocol(ArrayList<Account> accounts){

        this.accounts=accounts;
    }

    public boolean searchName(String name) {
        for (Account i : accounts) {
            if (i.username.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean searchPass(String pass,String name) {
        for (Account i : accounts) {
            if (i.password.equals(pass)&&i.username.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public Account searchUser(String name) {
        for (Account i : accounts) {
            if (i.username.equals(name)) {
                return i;
            }
        }
        return null;
    }

    public String login(BufferedReader in, PrintWriter out) throws IOException {

        String outputLine;
        String inputLine;
        String username = null;
        String password;
        boolean userB=false;
        boolean passB=false;
        int tries=0;
        //Account newUser = new Account();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Type your username: ");
        stringBuilder.append("\n");
        stringBuilder.append("END");
        outputLine = stringBuilder.toString();
        out.println(outputLine);


        while ((inputLine = in.readLine()) != null) {
            //System.out.println("Login: " + inputLine);
            if (searchName(inputLine)&&!userB) {
                username = inputLine;
                userB = true;
            }
            if (userB){
                if (searchPass(inputLine,username)){
                    passB=true;
                    password=inputLine;
                }
            }
            if (passB && userB) {
                password = inputLine;
                outputLine="Welcome back " + username;
                currentUser=searchUser(username);
                break;
            } else if (!passB && userB) {
                if (tries == 0) {
                    stringBuilder = new StringBuilder();
                    tries++;
                    stringBuilder.append("Type your password:  ");
                    stringBuilder.append("\n");
                    stringBuilder.append("END");
                    outputLine = stringBuilder.toString();
                    out.println(outputLine);
                } else {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Wrong password! Try again:  ");
                    stringBuilder.append("\n");
                    stringBuilder.append("END");
                    outputLine = stringBuilder.toString();
                    out.println(outputLine);
                }

            } else if (!passB && !userB) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Wrong username! Try again:  ");
                stringBuilder.append("\n");
                stringBuilder.append("END");
                outputLine = stringBuilder.toString();
                out.println(outputLine);
            }


        }
        return outputLine;
    }

    public String register(BufferedReader in, PrintWriter out) throws IOException {

        String outputLine;
        String inputLine;
        String username = null;
        String password;
        boolean registered=false;
        int tries=0;
        Account newUser = new Account();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Type your username: ");
        stringBuilder.append("\n");
        stringBuilder.append("END");
        out.println(stringBuilder.toString());


        while ((inputLine = in.readLine()) != null) {
            //System.out.println("signin   "+inputLine);
            if(!searchName(inputLine)&& !registered){
                newUser.username=inputLine;
                registered=true;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Type your password: ");
                stringBuilder.append("\n");
                stringBuilder.append("END");
                out.println(stringBuilder.toString());
            }else if (registered){
                newUser.password=inputLine;
                accounts.add(newUser);
                currentUser=newUser;
                break;
            }else {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Username already exists! Give another : ");
                stringBuilder.append("\n");
                stringBuilder.append("END");
                out.println(stringBuilder.toString());
            }
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("Welcome ");
        stringBuilder.append(currentUser.username);
        return stringBuilder.toString();

    }

    public String newEmail(BufferedReader in, PrintWriter out) throws IOException {
        Email newEmail = new Email();
        newEmail.sender = currentUser.username;
        boolean receiver=false;
        String receiver1 = null;
        boolean subject=false;
        boolean mainbody=false;
        String outputLine;
        String inputLine;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Receiver: ");
        stringBuilder.append("\n");
        stringBuilder.append("END");
        outputLine = stringBuilder.toString();
        out.println(outputLine);
        while ((inputLine = in.readLine()) != null) {
            if (!searchName(inputLine)&& !receiver) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Receiver doesn't exist! Try again:  ");
                stringBuilder.append("\n");
                stringBuilder.append("END");
                outputLine = stringBuilder.toString();
                out.println(outputLine);
            }else if(searchName(inputLine) && !receiver){
                newEmail.receiver = inputLine;
                receiver1=inputLine;
                receiver=true;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Subject: ");
                stringBuilder.append("\n");
                stringBuilder.append("END");
                outputLine = stringBuilder.toString();
                out.println(outputLine);
            }else if(receiver && !subject && ! mainbody){
                newEmail.subject=inputLine;
                subject=true;
                stringBuilder = new StringBuilder();
                stringBuilder.append("MainBody: ");
                stringBuilder.append("\n");
                stringBuilder.append("END");
                outputLine = stringBuilder.toString();
                out.println(outputLine);
            }else if(receiver && subject && !mainbody){
                newEmail.mainbody=inputLine;
                mainbody=true;
                newEmail.isNew=true;
                searchUser(receiver1).mailbox.add(newEmail);
                break;
            }
        }

        return "Mail sent successfully!";


    }

    public String showEmails() {

        String outputLine;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" Id           From                        Subject");
        stringBuilder.append("\n");
        if(!currentUser.mailbox.isEmpty() && currentUser.mailbox!=null){
            for (Email i : currentUser.mailbox) {
                if (i.isNew) {
                    stringBuilder.append(currentUser.mailbox.indexOf(i));
                    stringBuilder.append(".   [New]   ");
                    stringBuilder.append( i.sender );
                    stringBuilder.append("              ");
                    stringBuilder.append(i.subject);
                    stringBuilder.append("\n");
                } else {
                    stringBuilder.append(currentUser.mailbox.indexOf(i));
                    stringBuilder.append(".           ");
                    stringBuilder.append( i.sender );
                    stringBuilder.append("              ");
                    stringBuilder.append(i.subject);
                    stringBuilder.append("\n");
                }
            }
        }

        outputLine = stringBuilder.toString();
        return outputLine;
        //out.println(outputLine);
    }

    public Integer readEmail(BufferedReader in, PrintWriter out) throws IOException {
        String outputLine;
        String inputLine;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Type the email's id: ");
        stringBuilder.append("\n");
        stringBuilder.append("END");
        outputLine = stringBuilder.toString();
        out.println(outputLine);
        while ((inputLine = in.readLine()) != null) {
            break;
        }
        return Integer.parseInt(inputLine);
    }

    public String readEmail(int id) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Receiver: "+currentUser.mailbox.get(id).receiver);
        stringBuilder.append("\n");
        stringBuilder.append("Sender: "+currentUser.mailbox.get(id).sender);
        stringBuilder.append("\n");
        stringBuilder.append("Subject: "+currentUser.mailbox.get(id).subject);
        stringBuilder.append("\n");
        stringBuilder.append("Main body: " + currentUser.mailbox.get(id).mainbody);
        stringBuilder.append("\n");
        if(currentUser.mailbox.get(id).isNew==true)
            currentUser.mailbox.get(id).isNew=false;
        return stringBuilder.toString();
    }

    public Integer deleteEmail(BufferedReader in, PrintWriter out) throws IOException {
        String outputLine;
        String inputLine;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Type the email's id: ");
        stringBuilder.append("\n");
        stringBuilder.append("END");
        outputLine = stringBuilder.toString();
        out.println(outputLine);
        while ((inputLine = in.readLine()) != null) {
            break;
        }
        return Integer.parseInt(inputLine);
    }

    public String deleteEmail(int id) {
        currentUser.mailbox.remove(id);
        return "Mail successfully deleted!";
    }

    public String processInput(String theInput,BufferedReader in, PrintWriter out) throws IOException {

        if(state== FirstMenu)
        {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("-------------");
            stringBuilder.append("\n");
            stringBuilder.append("MailServer : ");
            stringBuilder.append("\n");
            stringBuilder.append("-------------");
            stringBuilder.append("\n");
            stringBuilder.append("Hello, you connected as a guest.");
            stringBuilder.append("\n");
            stringBuilder.append("-------------");
            stringBuilder.append("\n");
            stringBuilder.append("-------------");
            stringBuilder.append("\n");
            stringBuilder.append("> LogIn");
            stringBuilder.append("\n");
            stringBuilder.append("> SignIn");
            stringBuilder.append("\n");
            stringBuilder.append("> Exit");
            stringBuilder.append("\n");
            stringBuilder.append("-------------");
            stringBuilder.append("\n");
            stringBuilder.append("END");
            stringBuilder.append("\n");
            state = SecondMenu;
            return stringBuilder.toString();

        }else if(state== SecondMenu && theInput.equals("LogIn")) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(login(in, out));
            stringBuilder.append("\n");
            stringBuilder.append("-------------");
            stringBuilder.append("\n");
            stringBuilder.append("> NewEmail");
            stringBuilder.append("\n");
            stringBuilder.append("> ShowEmails");
            stringBuilder.append("\n");
            stringBuilder.append("> ReadEmail");
            stringBuilder.append("\n");
            stringBuilder.append("> DeleteEmail");
            stringBuilder.append("\n");
            stringBuilder.append("> LogOut");
            stringBuilder.append("\n");
            stringBuilder.append("> Exit");
            stringBuilder.append("\n");
            stringBuilder.append("END");
            stringBuilder.append("\n");
            state = ThirdMenu;
            return stringBuilder.toString();
        }else if(state== SecondMenu && theInput.equals("SignIn")){
            StringBuilder stringBuilder = new StringBuilder();
            String op=register(in,out);
            stringBuilder.append(op);
            stringBuilder.append("\n");
            stringBuilder.append("-------------");
            stringBuilder.append("\n");
            stringBuilder.append("> NewEmail");
            stringBuilder.append("\n");
            stringBuilder.append("> ShowEmails");
            stringBuilder.append("\n");
            stringBuilder.append("> ReadEmail");
            stringBuilder.append("\n");
            stringBuilder.append("> DeleteEmail");
            stringBuilder.append("\n");
            stringBuilder.append("> LogOut");
            stringBuilder.append("\n");
            stringBuilder.append("> Exit");
            stringBuilder.append("\n");
            stringBuilder.append("END");
            stringBuilder.append("\n");
            state = ThirdMenu;
            return stringBuilder.toString();
        }else if (state== SecondMenu && theInput.equals("Exit")){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("user exits");
            stringBuilder.append("\n");
            stringBuilder.append("END");
            stringBuilder.append("\n");
            return stringBuilder.toString();
        }else if(state==ThirdMenu && theInput.equals("NewEmail")){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(newEmail(in, out));
            stringBuilder.append("\n");
            stringBuilder.append("-------------");
            stringBuilder.append("\n");
            stringBuilder.append("-------------");
            stringBuilder.append("\n");
            stringBuilder.append("> NewEmail");
            stringBuilder.append("\n");
            stringBuilder.append("> ShowEmails");
            stringBuilder.append("\n");
            stringBuilder.append("> ReadEmail");
            stringBuilder.append("\n");
            stringBuilder.append("> DeleteEmail");
            stringBuilder.append("\n");
            stringBuilder.append("> LogOut");
            stringBuilder.append("\n");
            stringBuilder.append("> Exit");
            stringBuilder.append("\n");
            stringBuilder.append("END");
            return stringBuilder.toString();
        }else if(state==ThirdMenu && theInput.equals("ShowEmails")){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(showEmails());
            stringBuilder.append("-------------");
            stringBuilder.append("\n");
            stringBuilder.append("> NewEmail");
            stringBuilder.append("\n");
            stringBuilder.append("> ShowEmails");
            stringBuilder.append("\n");
            stringBuilder.append("> ReadEmail");
            stringBuilder.append("\n");
            stringBuilder.append("> DeleteEmail");
            stringBuilder.append("\n");
            stringBuilder.append("> LogOut");
            stringBuilder.append("\n");
            stringBuilder.append("> Exit");
            stringBuilder.append("\n");
            stringBuilder.append("END");
            return stringBuilder.toString();
        }else if(state==ThirdMenu && theInput.equals("ReadEmail")){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(readEmail(readEmail(in,out)));
            stringBuilder.append("-------------");
            stringBuilder.append("\n");
            stringBuilder.append("> NewEmail");
            stringBuilder.append("\n");
            stringBuilder.append("> ShowEmails");
            stringBuilder.append("\n");
            stringBuilder.append("> ReadEmail");
            stringBuilder.append("\n");
            stringBuilder.append("> DeleteEmail");
            stringBuilder.append("\n");
            stringBuilder.append("> LogOut");
            stringBuilder.append("\n");
            stringBuilder.append("> Exit");
            stringBuilder.append("\n");
            stringBuilder.append("END");
            return stringBuilder.toString();
        }else if(state==ThirdMenu && theInput.equals("DeleteEmail")){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(deleteEmail(deleteEmail(in,out)));
            stringBuilder.append("\n");
            stringBuilder.append("-------------");
            stringBuilder.append("\n");
            stringBuilder.append("> NewEmail");
            stringBuilder.append("\n");
            stringBuilder.append("> ShowEmails");
            stringBuilder.append("\n");
            stringBuilder.append("> ReadEmail");
            stringBuilder.append("\n");
            stringBuilder.append("> DeleteEmail");
            stringBuilder.append("\n");
            stringBuilder.append("> LogOut");
            stringBuilder.append("\n");
            stringBuilder.append("> Exit");
            stringBuilder.append("\n");
            stringBuilder.append("END");
            return stringBuilder.toString();
        }else if(state==ThirdMenu && theInput.equals("LogOut")){

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("-------------");
            stringBuilder.append("\n");
            stringBuilder.append("Bye "+currentUser.username +"!");
            stringBuilder.append("\n");
            stringBuilder.append("-------------");
            stringBuilder.append("\n");
            stringBuilder.append("MailServer : ");
            stringBuilder.append("\n");
            stringBuilder.append("-------------");
            stringBuilder.append("\n");
            stringBuilder.append("Hello, you connected as a guest.");
            stringBuilder.append("\n");
            stringBuilder.append("-------------");
            stringBuilder.append("\n");
            stringBuilder.append("-------------");
            stringBuilder.append("\n");
            stringBuilder.append("> LogIn");
            stringBuilder.append("\n");
            stringBuilder.append("> SignIn");
            stringBuilder.append("\n");
            stringBuilder.append("> Exit");
            stringBuilder.append("\n");
            stringBuilder.append("-------------");
            stringBuilder.append("\n");
            stringBuilder.append("END");
            currentUser=null;
            state = SecondMenu;
            return stringBuilder.toString();

        }else if(state==ThirdMenu && theInput.equals("Exit")){

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("user exits");
            stringBuilder.append("\n");
            stringBuilder.append("END");
            return stringBuilder.toString();
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            System.out.println("wrong option");
            stringBuilder.append("That is not a menu option! Try again: ");
            stringBuilder.append("\n");
            stringBuilder.append("END");
            return stringBuilder.toString();
        }


    }
}
