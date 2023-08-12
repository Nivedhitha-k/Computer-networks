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
class Nck implements Serializable{    
    int nckno;
    Nck(int nckno){
        this.nckno=nckno;
    }
    public String toString(){
        return Integer.toString(nckno);
    }
}
class Ssender{
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
       // if(type(in.readObject())==Nck)
        Nck n1=(Nck)in.readObject();
        System.out.println("resending frame at..."+n1.nckno);
            System.out.println("resent.."+n1.nckno+"->"+storage[n1.nckno]);
            out.writeObject(new Frame(n1.nckno,storage[n1.nckno])); 
            
        
            }
    }

