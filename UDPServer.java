import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
class UDPServer extends JFrame {
    JButton b;
    JTextArea t1;
    JTextArea t2;
    JTextArea t3;
    DatagramSocket ss;
    DatagramPacket sp1;
            DatagramPacket sp2;
    DatagramSocket rs1;
    DatagramPacket rp1;
    DatagramSocket rs2;
    DatagramPacket rp2;


    UDPServer()throws Exception{
        setTitle("Server");
        setVisible(true);
        setSize(600,600);
        b=new JButton("Press");
        add(b);
        t1=new JTextArea();
        add(t1);
        t2=new JTextArea();
        add(t2);
        t3=new JTextArea();
        add(t3);
        setLayout(null);
        b.setBounds(500,50,100,50);
        t1.setBounds(50,50,100,100);
        t2.setBounds(200,50,100,100);
        t3.setBounds(350,50,100,100);

        ss=new DatagramSocket();
        rs1=new DatagramSocket(1221);
        rs2=new DatagramSocket(1222);
        b.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent ae){
        try{                    
            sp1=new DatagramPacket(t1.getText().getBytes(),t1.getText().length(),InetAddress.getByName("localhost"),1211);
            sp2=new DatagramPacket(t1.getText().getBytes(),t1.getText().length(),InetAddress.getByName("localhost"),1223);
            ss.send(sp1);
            ss.send(sp2);
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
                        rp1=new DatagramPacket(b,b.length);
                        rs1.receive(rp1);
                        t2.setText(new String(b));
                }catch (Exception e) {}
                }  
                    }
                }).start();

        new Thread(new Runnable(){
                        public void run() {
                        while (true) {
                        try {
                                byte[] b=new byte[100];
                                rp2=new DatagramPacket(b,b.length);
                                rs2.receive(rp2);
                                t3.setText(new String(b));
                            }catch (Exception e) {}
                            }  
                    }
                }).start();

        }

    public static void main(String args[])throws Exception{
        new UDPServer();
    }
}
