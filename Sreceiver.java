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
class Nck implements Serializable{    
    int nckno;
    Nck(int nckno){
        this.nckno=nckno;
    }
    public String toString(){
        return Integer.toString(nckno);
    }
}

class Sreceiver{
    public static void main(String args[])throws Exception{
        Socket s=new Socket("localhost",1244);
        System.out.println("Connected...");
        int i=0;
        int n_no=0;
        Boolean nckneeded=true;
        String[] storage=new String[7]; 
        ObjectInputStream in=new ObjectInputStream(s.getInputStream());
        ObjectOutputStream out=new ObjectOutputStream(s.getOutputStream());
        while(i<7){
            Frame s1=(Frame)in.readObject();
            System.out.println("Received..."+s1);
            storage[i]=s1.data;
            if(s1.seqno!=i){
              n_no=i;
             // System.out.println("no no"+i);
              nckneeded=true;
              i++;
            }
            i++;
        }  
        if(i==7){
                
        }
        if(nckneeded){
            out.writeObject(new Nck(n_no));
            System.out.println("negative acknowledged frame..."+n_no);
        }
        Frame miss=(Frame)in.readObject();
        System.out.println("re-received..."+miss);
        storage[miss.seqno]=miss.data;
        System.out.println("totally received data.....");
        for(i=0;i<7;i++){
        System.out.println(i+"-->"+storage[i]);
        }
}
}
