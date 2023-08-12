import java.net.*;
import java.io.*;
import java.util.Random; 

class Frame implements Serializable{    
    int seqno;
    String data;
    Frame(int seqno, String data){
        this.seqno=seqno;
        this.data=data;
    }
    public String toString(){
        return seqno+"->"+data;
    }
}
class Ack implements Serializable{    
    int ackno;
    Ack(int ackno){
        this.ackno=ackno;
    }
    public String toString(){
        return Integer.toString(ackno);
    }
}
class Gsender{
    public static void main(String args[])throws Exception{
        int ws=7;
        String[] storage=new String[ws];
        Random rand=new Random();
        int r_no=rand.nextInt(8);
        System.out.println(r_no);
        ServerSocket ss=new ServerSocket(1244);
        Socket s=ss.accept();
        System.out.println("Connected...");
        DataInputStream dis=new DataInputStream(System.in);
        ObjectOutputStream out=new ObjectOutputStream(s.getOutputStream());
        ObjectInputStream in=new ObjectInputStream(s.getInputStream());
        for (int i=0;i<ws;i++){
            String d=dis.readLine();
            storage[i]=d;}
        for (int i=0;i<ws;i++){
            if(i==r_no){
                continue;
            }
            Frame f=new Frame(i,storage[i]);
            out.writeObject(f);
            System.out.println("sent ..."+f);
        }
                    Ack a1=(Ack)in.readObject();
                    System.out.println("resending frame from..."+a1.ackno);
                    for (int j=a1.ackno;j<ws;j++){
                        System.out.println("resent.."+j+"->"+storage[j]);
                        out.writeObject(new Frame(j,storage[j]));
                    }
          
    }
}
