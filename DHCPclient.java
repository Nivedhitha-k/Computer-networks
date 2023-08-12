import java.net.*;
import java.io.*;
import java.util.Scanner;
class DHCPclient{
	DatagramSocket ss;
	DatagramPacket sp;

	DatagramSocket rs;
	DatagramPacket rp;
	DHCPclient(String ip,int rport,int sport,int i)throws Exception{

		new Thread(new Runnable(){
			public void run() {
			try {
				String data="Source:"+ip;
				System.out.println("DHCPClient"+Integer.toString(i));
				ss=new DatagramSocket();
				rs=new DatagramSocket(rport);
				System.out.println("DHCP Discovery...");
				
				sp=new DatagramPacket(data.getBytes(),data.length(),InetAddress.getByName("localhost"),sport);
				ss.send(sp);
				System.out.println("DHCP Discovery msg sent by Client "+Integer.toString(i)+"..."+new String(data));

				System.out.println("Client"+Integer.toString(i)+" listening...");
				byte[] b=new byte[100];
				rp=new DatagramPacket(b,b.length);
				rs.receive(rp);
                System.out.println("DHCP Discovery msg received by Client "+Integer.toString(i)+"..."+new String(b));
				Scanner sc=new Scanner(System.in);

				while(true){
					Thread.sleep(5000);
					rs.receive(rp);
					System.out.println(new String(b));
					String in=sc.next();
					sp=new DatagramPacket(in.getBytes(),in.length(),InetAddress.getByName("localhost"),sport);
					ss.send(sp);
					if(in.equals("n")){
						System.out.println("client "+i+" ended");
						break;
					}  
					else
					System.out.println("client "+i+" renewed");
					continue;
				}
			}catch (Exception e) {}
	
		}
	  }).start();
	}	
	public static void main(String args[])throws Exception{
		new DHCPclient("00:1B:44:11:3A:B7",2222,1111,1);
		new DHCPclient("22:1N:37:87:2K:F1",2233,1112,2);
	}
}


		