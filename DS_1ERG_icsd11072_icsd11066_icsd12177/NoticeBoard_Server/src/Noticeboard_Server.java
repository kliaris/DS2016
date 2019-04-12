// Giannis Kosmas   icsd11072
// Vagelis Kliaris  icsd11066
// Kon/nos Stafylas icsd12177
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Noticeboard_Server {
    static ServerSocket server ;            // dilwnoume ton server kai dio listes gia ta meli kai tis anakoinwseis
    static ArrayList<Member> members=new ArrayList();
    static ArrayList<Notice> notices=new ArrayList();
    static Socket sock;
    
    static ObjectInputStream in;
    
    public static void main(String[] args)  {   
            Member admin1=new Member("admin1","123","Vagelis","Kliaris");
            Member admin2=new Member("admin2","123","Giannis","Kosmas");
            admin1.setisAdmin();            // molis o server ksekina dimiourgei dio xristes
            admin2.setisAdmin();            // kai tous thetei ws admin
          
            members.add(admin1);            // kai tous prosthetei sti lista me ta meli
            members.add(admin2); 
            
        try {                                                               // sti sinexei diavazei apo katallila arxei gia idi uparxwn
            in = new ObjectInputStream(new FileInputStream("notices.txt")); //  anakoinwseis kai idi gramena meli
            notices=(ArrayList <Notice>) in.readObject();
            in = new ObjectInputStream(new FileInputStream("members.txt"));
            members=(ArrayList <Member>) in.readObject();
        }catch(InvalidClassException ex) {
            System.out.println("Provlima me arxeio");
        } catch (IOException | ClassNotFoundException ex){
            Logger.getLogger(Noticeboard_Server.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            
                   
                                    
            try {            
                server = new ServerSocket(8080);        // dilwnoume ton server se poia porta tha perimenei sindesi

                while(true){
                    sock=server.accept();               // dexomaste tin sindesi me ton client
                    
                                                        // kai dimiourgoume ena nima gia ton xristi pou molis sindethike
                    Thread t=new Thread(new Noticeboard_server_Runnable());
                    t.start();                  
                                                        
                }
            }catch (IOException ex) {
                System.out.println("Server Error");
            }

        }
       
}
