import javax.swing.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
class TCPClient extends JFrame implements ActionListener{
	JButton b;
	JTextArea t1;
	JTextArea t2;
	Socket s;
	DataInputStream din;
	DataOutputStream dout;
	TCPClient()throws Exception{
		setTitle("Client");

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
		s=new Socket("127.0.0.1",1234);
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
		new TCPClient();
	}
}