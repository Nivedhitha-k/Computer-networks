import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
class UDPClient extends JFrame {
    JButton b;
    JTextArea t1;
    JTextArea t2;
    DatagramSocket ss;
    DatagramPacket sp;
    DatagramPacket rp;

    UDPClient(int pn1,int pn)throws Exception{
      
    DatagramSocket rs=new DatagramSocket(pn1);
    setTitle("Client");
    setVisible(true);
    setSize(500,500);
    b=new JButton("Press");
    add(b);
    t1=new JTextArea();
    add(t1);
    t2=new JTextArea();
    add(t2);
    setLayout(null);
    b.setBounds(350,50,100,50);
    t1.setBounds(50,50,100,100);
    t2.setBounds(200,50,100,100);
    ss=new DatagramSocket();
    b.addActionListener(new ActionListener(){
    public void actionPerformed(ActionEvent ae){
    try{                    
          sp=new DatagramPacket(t1.getText().getBytes(),t1.getText().length(),InetAddress.getByName("localhost"),pn);
          ss.send(sp);
          t1.setText("");
    }catch(Exception e){}
    }
    });
            // readMessage thread
    new Thread(new Runnable(){
          public void run() {
             while (true) {
               try {
                    byte[] b=new byte[100];
                    rp=new DatagramPacket(b,b.length);
                    rs.receive(rp);
                    t2.setText(new String(b));
                  }catch (Exception e) {}
                }  
                  }
            }).start();

}
public static void main(String args[])throws Exception{
new UDPClient(1211,1221);
new UDPClient(1223,1222);
}
}