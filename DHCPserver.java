///// using thread sleep ((not using timer))
import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.*;
class DHCPserver{
	static int i=0;
	DatagramSocket ss;
    DatagramPacket sp1;
	DatagramPacket sp2;
	DatagramSocket rs1;
	DatagramPacket rp1;
	DatagramSocket rs2;
	DatagramPacket rp2;
	DHCPserver()throws Exception{
		Queue<String> queue= new LinkedList<String>();
		queue.add("14.16.1.0");
		queue.add("14.16.1.1");
		queue.add("14.16.1.2");
		System.out.println("DHCPServer");
		ss=new DatagramSocket();
		rs1=new DatagramSocket(1111);
		rs2=new DatagramSocket(1112);
		System.out.println("Server listening...");

	///////client 1///////////
		new Thread(new Runnable(){
			public void run() {
		try {
			String str="Server asking client 1 for renew ? y/n";
			String ip=queue.remove(); 
 
		    byte[] b1=new byte[100];
		    rp1=new DatagramPacket(b1,b1.length);
			rs1.receive(rp1);

			System.out.println("DHCP discovery message is received.."+new String(b1));
			System.out.println("DHCP offer");
			sp1=new DatagramPacket(ip.getBytes(),ip.length(),InetAddress.getByName("localhost"),2222);
			ss.send(sp1);
			System.out.println("DHCP Discovery msg sent..."+new String(ip));

			while(true){ 
				Thread.sleep(10000);
				System.out.println("Client 1 Timer ended");
				sp1=new DatagramPacket(str.getBytes(),str.length(),InetAddress.getByName("localhost"),2222);
			    ss.send(sp1);
				byte[] b2=new byte[1000];
		        rp1=new DatagramPacket(b2,b2.length);
				rs1.receive(rp1);

				byte[] data = new byte[rp1.getLength()];
				System.arraycopy(rp1.getData(), rp1.getOffset(), data, 0, rp1.getLength());
				String msg = new String (data);

				//String input=new String(b2);
				//System.out.println(input);
			    if(msg.equals("y")){
				System.out.println("client 1 agreed to renew ");
				continue;
			    }
			  else{
				System.out.println("client 1 didn't renew");
				queue.add(ip);
				System.out.println("Remaining IP address");
				System.out.print("Available IP addresses: "+queue);
				break;
						}		
					}
			
	}catch (Exception e) {}
     }
      }).start();

    /////////client 2///////////
 	  new Thread(new Runnable(){
		public void run() {
	try {

		String str="Server asking client 2 for renew ? y/n";
		String ip;
		ip=queue.remove();
		byte[] b2=new byte[100];
		rp2=new DatagramPacket(b2,b2.length);
		rs2.receive(rp2);
		System.out.println("DHCP discovery message is received.."+new String(b2));
		System.out.println("DHCP offer");
		sp2=new DatagramPacket(ip.getBytes(),ip.length(),InetAddress.getByName("localhost"),2233);
		ss.send(sp2);
		System.out.println("DHCP Discovery msg sent..."+new String(ip));

		Scanner sc=new Scanner(System.in);
		while(true){ 
			Thread.sleep(20000);
			System.out.println("Client 2 Timer ended ");
			sp2=new DatagramPacket(str.getBytes(),str.length(),InetAddress.getByName("localhost"),2233);
			ss.send(sp2);
			byte[] b3=new byte[100];
			rp2=new DatagramPacket(b3,b3.length);
			rs2.receive(rp2);
          
			byte[] data = new byte[rp2.getLength()];
            System.arraycopy(rp2.getData(), rp2.getOffset(), data, 0, rp2.getLength());
            String msg = new String (data);

		//	String input=new String(b3);
			//System.out.println(input); 
					if(msg.equals("y")){
						System.out.println("client 2 agreed to renew..");
						continue;
					}
					else{
						System.out.println("client 2 didn't renew..");
						queue.add(ip);
						System.out.println("Remaining IP address");
                        System.out.print("Available IP addresses: "+queue);
						break;
					}
				}


}catch (Exception e) {}
 }
  }).start(); 
}
	public static void main(String args[])throws Exception{
		new DHCPserver();
	}
}

