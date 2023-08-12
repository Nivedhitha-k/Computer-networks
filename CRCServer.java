import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
class CRCServer extends JFrame {
	JButton b;
	JTextArea t1;
	JTextArea t2;
	JTextArea t3;
	JTextArea t4;
	JLabel l1, l2, l3;
	DatagramSocket ss;
	DatagramPacket sp;

		int[] dividewithDivisor(int[] oldData,int[] divisor){

			int rem[] = new int[divisor.length];
			int data[] = new int[divisor.length+oldData.length];

			System.arraycopy(oldData,0,data,0,oldData.length);
			System.arraycopy(data,0,rem,0,divisor.length);


			for(int i=0;i<oldData.length;i++){
				if(rem[0]==1){
					for(int j =1; j< divisor.length;j++){
						rem[j-1] = exOR(rem[j], divisor[j]);
					}
				}
				else if(rem[0]==0){
					for(int j =1;j<divisor.length;j++){
						rem[j-1] = exOR(rem[j], 0);
					}
				}
				rem[divisor.length-1] = data[divisor.length+i]; 
			}	

			return rem;
		}


		int exOR(int x, int y){
			if(x==y){
				return 0;
			}

			return 1;
		}


		static int[] conversion(String a){

				String[] splitArray = a.split(" ");
				int[] arr = new int[splitArray.length];
				for(int i =0; i<splitArray.length;i++){
					arr[i] = Integer.parseInt(splitArray[i]);
				}
				
				return arr;

			}



	CRCServer()throws Exception{
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
		l1 = new JLabel("Enter the data: ");
		add(l1);
		l2 = new JLabel("Generated CRC code ");
		add(l2);
		l3 = new JLabel("Enter the divisor: ");
		add(l3);
		setLayout(null);
		b.setBounds(400,50,100,50);
		l1.setBounds(50, 20, 150, 25);
		t1.setBounds(50,50,100,100);
		l2.setBounds(200, 20, 150, 25);
		t2.setBounds(200,50,175,100);
		l3.setBounds(50, 170, 150, 25);
		t3.setBounds(50,200,100,100);

		ss=new DatagramSocket();

		b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				try{  
				String t1_data = t1.getText();
				String t3_data = t3.getText();  		                
				
                int[] data = conversion(t1_data);
				int[] divisor = conversion(t3_data);

				int rem[] = dividewithDivisor(data, divisor);
				int crc[] = new int[data.length+divisor.length-1];


			System.arraycopy(data,0,crc,0,data.length);
			for(int j =0; j<rem.length-1;j++){
			 crc[data.length+j] = rem[j];
				}

			String res = "";	
			for(int i =0; i<crc.length;i++){
				res += "" + crc[i];
				}
    			t2.setText("CRC code "+res);
    			sp=new DatagramPacket(t3.getText().getBytes(),t3.getText().length(),InetAddress.getByName("localhost"),1211);
  				ss.send(sp);

    			// sp=new DatagramPacket(res.getBytes(),res.length(),InetAddress.getByName("localhost"),1231);
  				// ss.send(sp);
  				
    			t1.setText("");
    			t3.setText("");
				}catch(Exception e){}
			}
                });

	}

   
	
	
	public static void main(String args[])throws Exception{
		new CRCServer();

	}
}

