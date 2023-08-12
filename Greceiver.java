import java.net.*;
import java.io.*;
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
class Greceiver{
    public static void main(String args[])throws Exception{
        Socket s=new Socket("localhost",1244);
        System.out.println("Connected...");
        int i=0;
        String[] storage=new String[7]; 
        ObjectInputStream in=new ObjectInputStream(s.getInputStream());
        ObjectOutputStream out=new ObjectOutputStream(s.getOutputStream());
        for (i=0;i<7;i++){
            Frame s1=(Frame)in.readObject();
            storage[i]=s1.data;
            if(s1.seqno!=i)
            break;
            System.out.println("Received..."+s1);
        }
        out.writeObject(new Ack(i));
        System.out.println("acknowledged frame..."+i);
       final int missed=i;
       for (int j=missed;j<7;j++){
        Frame miss=(Frame)in.readObject();
        System.out.println("rereceived..."+miss);
        storage[j]=miss.data;
    }
    System.out.println("totally received data.....");
    for(i=0;i<7;i++){
        System.out.println(i+"-->"+storage[i]);
    }
}
}