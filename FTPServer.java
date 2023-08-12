
import javax.swing.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.io.FileReader;
class Sender extends JFrame implements ActionListener{
    JButton b;
    JTextArea t1;
    ServerSocket ss;
    Socket s;
    Sender()throws Exception{
        setTitle("Server");
        setVisible(true);
        setSize(500,500);
       
        b=new JButton("Send");
        t1=new JTextArea();

        add(b);add(t1);
        setLayout(null);
        t1.setBounds(50,100,100,50);
        b.setBounds(200,100,100,50);
        ss=new ServerSocket(1234);
        s=ss.accept();
        b.addActionListener(this);
    }
    public void actionPerformed(ActionEvent ae){
        try{
            JFileChooser fc=new JFileChooser();    
            fc.showOpenDialog(this);    
            File f=fc.getSelectedFile();      
        //File f=new File("E:/Temp/124018040/filetransfer/file1.txt");
        FileInputStream fin=new FileInputStream(f);
        byte data[]=new byte[(int)f.length()];
        fin.read(data);
        t1.setText(new String(data));
        OutputStream out=s.getOutputStream();
        out.write(data);
        System.out.println("file sent ");
        fin.close();
        }catch(Exception e){}
    }
    public static void main(String args[])throws Exception{
        new Sender();
    }
}
