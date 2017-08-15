package serverapp;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.*;
public class ServerThread extends Thread{
    //Create Variable of server form
    ServerForm sf;
    //Variables to read and write message
    ObjectInputStream oin;
    ObjectOutputStream out;
    //Server socket object to create server
    ServerSocket serverSocket;
    //Socket object to create socket connection
    Socket socket;
    
    //Will pass parameter ServerForm Object
    public ServerThread(ServerForm sf){
        this.sf=sf;   
        try
       {
           //Create server object with specific port number
       serverSocket=new ServerSocket(Settings.port);
        JOptionPane.showMessageDialog(sf,"Server Started");
        start();
       }catch(Exception e)
       {
           
       }
    }
    //Create a method to send message.
    public void sendMessage(String msg)
    {
     try
     {
         out.writeObject(msg.toString());
     }catch(Exception e)
     {
         e.printStackTrace();
     }
     
    }
        @Override
       public void run()
       {
        while(true)
        {
            try
            {
                //Accepting incoming connections to the server using thread
                socket=serverSocket.accept();
                //Calling method to create input output objects
                openReader();
            }catch(Exception e)
            {
                e.printStackTrace();
        }
       }
    }
       private void openReader()
       {
          try
          {
              oin=new ObjectInputStream(socket.getInputStream());
              out=new ObjectOutputStream(socket.getOutputStream());
              MsgRecThread mrt=new MsgRecThread(sf,oin,out);
          }
          catch(Exception e)
          {
             e.printStackTrace();
          }              
}
       public class MsgRecThread extends Thread
       {
           ServerForm sf;
           ObjectInputStream oin;
           ObjectOutputStream out;
           public MsgRecThread(ServerForm sf,ObjectInputStream oin, ObjectOutputStream out)
           {
               this.sf=sf;
               this.oin=oin;
               this.out=out;
               start();
           }
           public void run()
           {//To receive message continously
               while(true)
               {
               
               try
               {
                   //here in serverThread also it should be append
                   sf.jtaRec.append(oin.readObject().toString()+"\n");
               }catch(Exception e)
               {
                   e.printStackTrace();
               }
           }
       }
}
    

}