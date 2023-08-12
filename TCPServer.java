import javax.swing.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
class TCPServer extends JFrame implements ActionListener{
	JButton b;
	JTextArea t1;
	JTextArea t2;
	ServerSocket ss;
	Socket s;
	DataInputStream din;
	DataOutputStream dout;
	TCPServer()throws Exception{
		setTitle("Server");
		setVisible(true);
		setSize(500,500);
		
		b=new JButton("Press");
		t1=new JTextArea();
		t2=new JTextArea();

		add(b);add(t1);add(t2);
		setLayout(null);
		t1.setBounds(50,100,100,50);
		t2.setBounds(200,100,100,50);
		b.setBounds(350,100,100,50);
		ss=new ServerSocket(1234);
		s=ss.accept();
		b.addActionListener(this);

		din=new DataInputStream(s.getInputStream());
		dout=new DataOutputStream(s.getOutputStream());
		Thread t=new Thread(new Runnable(){
			public void run(){
				while(true)
				try{
					String msg=din.readUTF();
					t2.setText(msg);
				}catch(Exception e){}
				
			}
		});
		t.start();


	}
	public void actionPerformed(ActionEvent ae){
		String s=t1.getText();
		t1.setText("");
		try{
		dout.writeUTF(s);
		}catch(Exception e){}
		
	}
	public static void main(String args[])throws Exception{
		new TCPServer();
	}
}
