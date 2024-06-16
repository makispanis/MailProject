# MailProject

This is an implementation of a Mail Server and Client written in Java using IntelliJ.   
JAR files included.  
In order to run the full application, first, you need to run the MailServer.jar that takes one argument, the port number.
You can run it using the following command:
```
java -jar MailServer.jar 1
```
Well done you have the server up and running.  
Now you can have as many clients as you want running the MailClient.jar which takes two arguments, the IP of the client and the port number (same as before).
```
java -jar MailClient.jar "127.0.0.1" 1
```
For multiple clients keep the port number the same and just change the IP each time you run the MailClient.jar.

This project does all that it says.   
A server is created to serve the clients.   
Every client can create a mail account or log-in to an existing one.  
The clients can read, send and delete emails.  
A complete preview of the project can be seen in the following video, in which the server is created and then two clients do stuff at the same time.

  

https://github.com/makispanis/MailProject/assets/56236325/1e29308f-b9e6-4e82-8eec-099b63929c79

