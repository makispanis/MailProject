import java.util.ArrayList;
import java.util.List;

public class Account {
    public String username;
    public String password;
    public List<Email> mailbox=new ArrayList<>();
    public Account(){

    }
    public Account(String username,String password,List<Email> mailbox){

        this.username=username;
        this.password=password;
        this.mailbox=mailbox;
    }

}
