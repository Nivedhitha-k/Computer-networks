import javax.swing.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
class Receiver extends JFrame{
    JTextArea t1;
    Socket s;
    Receiver()throws Exception{
        setTitle("Client");
        setVisible(true);
        setSize(500,500);
        t1=new JTextArea();

        add(t1);
        setLayout(null);
        t1.setBounds(50,100,100,50);
       
        s=new Socket("localhost",1234);

        Thread t=new Thread(new Runnable(){
            public void run(){
                while(true)
                try{
                    InputStream in =s.getInputStream();
                    byte data[]=new byte[1000];
                    in.read(data);
                    t1.setText(new String(data));
                    File f=new File("E:\\Temp\\124018040\\filetransfer\\receivedfile.txt");
                    FileOutputStream fout=new FileOutputStream(f);
                    fout.write(data);
                    fout.close();
                }catch(Exception e){}
               
            }
        });
        t.start();
    }
    public static void main(String args[])throws Exception{
        new Receiver();
    }
}


