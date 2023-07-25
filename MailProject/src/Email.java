public class Email {
    public String sender;
    public String receiver;
    public String subject;
    public String mainbody;
    public boolean isNew;
    public Email(){

    }
    public Email(String sender,String receiver,String subject,String mainbody,boolean isNew){
        this.sender=sender;
        this.receiver=receiver;
        this.subject=subject;
        this.mainbody=mainbody;
        this.isNew=isNew;
    }
    public boolean isNew(){
        return isNew;
    }
}
