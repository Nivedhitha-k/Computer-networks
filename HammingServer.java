import javax.swing.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
class HammingServer extends JFrame implements ActionListener{
	JButton b;
	JTextArea t1;
	JTextArea t2;
	JTextArea t3;
	JLabel l1,l2;
	ServerSocket ss;
	Socket s;
	DataInputStream din;
	DataOutputStream dout;
	HammingServer()throws Exception{
		setTitle("Server");
		setVisible(true);
		setSize(500,500);
		
		b=new JButton("Press");
		t1=new JTextArea();
		t2=new JTextArea();
		t3=new JTextArea();
		l1=new JLabel("Enter data to be sent:");
		l2=new JLabel("Hamming code: ");
		add(b);add(t1);add(t2);add(l1);add(l2);add(t3);
		setLayout(null);
		l1.setBounds(50,50,250,20);
		t1.setBounds(50,80,250,20);
		l2.setBounds(50,110,250,20);
		t2.setBounds(50,140,250,20);
		t3.setBounds(50,170,250,20);
		b.setBounds(350,100,100,50);
		ss=new ServerSocket(2222);
		s=ss.accept();
		b.addActionListener(this);
		
		din=new DataInputStream(s.getInputStream());
		dout=new DataOutputStream(s.getOutputStream());
		
		Thread t=new Thread(new Runnable(){
			public void run(){
				while(true)
				try{
					String msg=din.readUTF();
					t3.setText(msg);
				}catch(Exception e){}
				
			}
		});
		t.start();
	}
	static int[] generateCode(String str, int M, int r)
    {
        int[] ar = new int[r + M + 1];
        int j = 0;
        for (int i = 1; i < ar.length; i++) {
            if ((Math.ceil(Math.log(i) / Math.log(2))- Math.floor(Math.log(i) / Math.log(2))) == 0) {
                ar[i] = 0;
            }
            else {
                ar[i] = (int)(str.charAt(j) - '0');
                j++;
            }
        }
        return ar;
    }
	public void actionPerformed(ActionEvent ae){
		String str=t1.getText();
		int M = str.length();
        int r = 1;
        while (Math.pow(2, r) < (M + r + 1)) r++;
		int[] ar = generateCode(str, M, r);
        System.out.println("Generated hamming code ");
        ar = calculation(ar, r);
	String st=new String();
	for(int i=0;i<ar.length;i++)
		st+=ar[i];
	System.out.println(st);
	t2.setText(st);
	/*(This commented is to send code manually)
	StringBuffer err_msg= new StringBuffer(st);
	err_msg.setCharAt(3,'1');
	t3.setText("Sending error manually");*/
	//To send error msg we need to use 'dout.writeUTF(err_msg.toString());'
		try{
		dout.writeUTF(st);
		}catch(Exception e){}
		
	}
	static int[] calculation(int[] ar,int r)
	{
        for (int i = 0; i < r; i++) {
            int x = (int)Math.pow(2, i);
            for (int j = 1; j < ar.length; j++) {
                if (((j >> i) & 1) == 1) {
                    if (x != j) ar[x] = ar[x] ^ ar[j];
                }
            }
            System.out.println("r" + x + " = " + ar[x]);
        }
        return ar;
    }
	public static void main(String args[])throws Exception{
		new HammingServer();
	}
}