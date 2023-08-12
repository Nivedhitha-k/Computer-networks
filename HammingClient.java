import javax.swing.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
class HammingClient extends JFrame implements ActionListener{
	JButton b;
	JTextArea t1,t2,t3;
	JLabel l1,l2;
	Socket s;
	DataInputStream din;
	DataOutputStream dout;
	HammingClient()throws Exception{
		setTitle("Client");
		setVisible(true);
		setSize(500,500);
		b=new JButton("Press");
		t1=new JTextArea();
		t2=new JTextArea();
		t3=new JTextArea();
		l1=new JLabel("Hamming Code received:");
		l2=new JLabel("orginal data bits: ");
		add(b);add(t1);add(t2);add(l1);add(l2);add(t3);
		
		setLayout(null);
		l1.setBounds(50,50,250,20);
		t1.setBounds(50,80,250,20);
		l2.setBounds(50,110,250,20);
		t2.setBounds(50,140,250,20);
		t3.setBounds(50,170,250,20);
		b.setBounds(350,100,100,50);
		s=new Socket("localhost",2222);
		b.addActionListener(this);
		
		din=new DataInputStream(s.getInputStream());
		dout=new DataOutputStream(s.getOutputStream());
		Thread t=new Thread(new Runnable(){
			public void run(){
				while(true)
				//(res.getBytes(),res.length(),InetAddress.getByName("localhost"),1234);
				try{
					String msg=din.readUTF();
					t1.setText(msg);
					int m=msg.length();
					int r=(int)(Math.log(m)/Math.log(2));
					int[] pr=new int[r]; 
					for(int i=0;i<r;i++)
					{	pr[i]=0;
						for(int j=1;j<m;j++)
						{	if(((j>>i)&1)==1)
							pr[i]=((pr[i]&1)^(int)msg.charAt(j))-48;
							}
					}
					String res="";
					int flag=0;
					for(int i=0;i<r;i++)
					{	if(pr[i]!=0)
						flag=1;
					}
					System.out.println("1");
					if(flag==0)
					{	for(int i=1;i<m;i++)
						{	if((Math.ceil(Math.log(i) / Math.log(2))- Math.floor(Math.log(i) / Math.log(2))) != 0)
								res+=msg.charAt(i);
								}
								}
					else{
					int sum=0;
					for(int i=r-1;i>=0;i--)
					{	if(pr[i]!=0)
						sum+=Math.pow(2,i);
					}
					System.out.println("sum "+sum);
					t3.setText("error found at "+sum+" position");
					StringBuffer str1=new StringBuffer(msg);
					if(str1.charAt(sum)=='0')
					str1.setCharAt(sum,'1');
					else str1.setCharAt(sum,'0');
					for(int i=1;i<m;i++)
						{	if((Math.ceil(Math.log(i) / Math.log(2))- Math.floor(Math.log(i) / Math.log(2)))!= 0)
								{res+=str1.charAt(i);
								System.out.println("sum "+i);}
						}
					}
								t2.setText(res);
				}catch(Exception e){}
				
			}
		});
		t.start();
	}
	public void actionPerformed(ActionEvent ae){
		String str=t3.getText();
		try{
		dout.writeUTF(str);
		}catch(Exception e){}
		
	}
	public static void main(String args[])throws Exception{
		new HammingClient();
	}
}