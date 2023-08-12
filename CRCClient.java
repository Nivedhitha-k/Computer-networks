//import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
class CRCClient extends JFrame {
	JButton b;
	JTextArea t1;
	JTextArea t2,t3;
	DatagramSocket rs_divisor=new DatagramSocket(1211);
	DatagramPacket rp_divisor;
	JLabel l1, l2, l3;


	static int[] dividewithDivisor(int[] oldData,int[] divisor){

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

		static int exOR(int x, int y){
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

	static boolean recievedData(int[] data, int[] divisor){
		int rem[] = dividewithDivisor(data, divisor);

		for(int i = 0; i < rem.length; i++) { 
		if(rem[i] != 0) {   
                return false;
            }
        }
        return true;
	}



	CRCClient()throws Exception{
		setTitle("CRC_Client");
		setVisible(true);
		setSize(500,500);
		b=new JButton("Verify");
		add(b);
		t1=new JTextArea();
		add(t1);
		t2=new JTextArea();
		add(t2);
		t3=new JTextArea();
		add(t3);
		l1 = new JLabel("Enter recieved data:");
		add(l1);
		l2 = new JLabel("Output:");
		add(l2);
		l3 = new JLabel("Polynomial coeff:");
		add(l3);
		setLayout(null);
		b.setBounds(350,50,120,50);
		l1.setBounds(50, 20, 125, 25);
		t1.setBounds(50,50,125,100);
		l2.setBounds(215, 20, 100, 25);
		t2.setBounds(215,50,100,100);
		l3.setBounds(50, 170, 150, 25);
		t3.setBounds(50,200,125,100);

		 // readMessage thread
		new Thread(new Runnable(){
      	            public void run() {
  	                while (true) {
    	                try {
			 
    	    byte[] div = new byte[100];
			rp_divisor = new DatagramPacket(div,div.length);
			
			rs_divisor.receive(rp_divisor);
                        
                        t2.setText("Divisor: " + new String(div));
                        


                   	}catch (Exception e) {}
                       }  
           	   }
       		 }).start();

	
		b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				try{    	

				String t1_data = t1.getText();
				String t3_data = t3.getText();	                

				int[] data = conversion(t1_data);
				int[] divisor = conversion(t3_data);
					
				



				if(recievedData(data, divisor)){
					t2.setText("No error");
					l2.setText("Output Verified");
				}
				else{
					t2.setText("Data is corrupted");
					l2.setText("Error recieved!!");

				}
				t1.setText("");
				t3.setText("");

				}catch(Exception e){}
			}
                });
	}
	public static void main(String args[])throws Exception{
		new CRCClient();
			}
}