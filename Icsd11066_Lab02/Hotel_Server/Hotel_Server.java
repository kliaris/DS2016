// Vaggelis Kliaris     icsd11066
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Hotel_Server {
    static private int id_counter;                  // counter gia na xeirizomaste to monadiko id gia tis kratiseis
    static ArrayList<Reservation> list=new ArrayList<Reservation>(); // dimiourgia listas gia tin apothikeusi twn kratisewn 
    static ArrayList<Reservation> reserv;                             // dimiourgia listas gia tin proswrini apothikeusi twn kratisewn pou anazitei o xristis
    public static void main(String[] args) {
        id_counter=0;                               // vazoume ton counter 0
        ServerSocket server ;                        //dilwnoume server
        
        try {   
            server = new ServerSocket(8080);        // dilwnoume ton dserver se poia porta tha perimenei sindesi
            while(true){                            // kanoume atermwn loop gia na trexei sinexeia
                boolean error_founded=false;        // an vrethei lathos i metavliti auti tha ginei true kai tha termatistei i sindesi
                Socket sock=server.accept();        // dexomaste tin sindesi me ton client
                ObjectInputStream input= new ObjectInputStream(sock.getInputStream());
                ObjectOutputStream output = new ObjectOutputStream(sock.getOutputStream());
                                                    // o dialogos tha ginei mesw natikeimenwn
                try {                           
                    Request req=(Request) input.readObject();       // diavazoume to minima tou

                    if(req.get_Mess().equals("START")){             // an einai start
                        System.out.println(req.get_Mess());         // to emfanizoume 
                        output.writeObject("WAITING");              // kai tou apantame me waiting
                        output.flush();
                     }else{             
                        sock.close();                               // an den einai start kleinoume tin sindesi
                    }
                    req=(Request) input.readObject();               // an einai start diavazoume to minima pou tha mas steilei meta
                    if(req.get_Mess().equals("INSERT") || req.get_Mess().equals("SEARCH") || req.get_Mess().equals("DELETE") ){
                        switch(req.get_Mess()){

                            case "INSERT":                          // an steilei insert tha steilei kai mia kratis
                                Reservation res=(Reservation) req.get_Reserv();  
                                res.setPrice(new CalculatePrice(res).getCost());    // tha ipologisoume to kostos kai tha allaksoume tin 
                                if(res.getPrice()<0){                               // katallili idiotita tis kratisis
                                    sock.close();
                                    error_founded=true;                             // an gia kapoio logo mas vgei arnitiko to kostos kleinoume tin sindesi
                                    break;
                                }
                                res.setId(++id_counter);                            // thetoume ena monadiko id afou o counter auksanetai sinexws
                                list.add(res);                                      // vazoume tin kratisi sti lista
                                output.writeObject(req);                            // kai tin stelnoume ston client
                                break;
                            case "SEARCH":                                          // an einai search to minima
                                Searching search=new Searching(req.get_Checkin(),req.get_Name(),req.get_Surname());
                                reserv=search.get_list(list);                       // tha sinodeuetai apo ena checkin ena onoma kai ena epitheto
                                output.writeObject(reserv);                         // stelnoume tis parametrous gia anazitis stin search
                                reserv.clear();                                     // vazoume ta apotelesmata se mia lista kai ta stelnoume
                                break;                                              // meta adiazoume tin lista auti
                            case "DELETE":
                                boolean remove_done=false;                          // se periptwsi pou steilei delete to minima tha sinodeuete apo ena id
                                                                                    // kratame se ena boolean to an egine telika i diagrafi apo ti lista
                                for(int i=0;i<list.size();i++){                     // trexoume ti lista
                                    if(req.get_Id()== list.get(i).getId()){         // an to id pou esteile o client vrethei sti lista
                                        list.remove(i);                             // tote diagrafoume to antikeimeno auto
                                        remove_done=true;                           // enimerwnoume to boolean
                                        break;
                                    }
                                }
                                
                                if(remove_done==true){                              // analogws an egine i diagrafi stelnoume pisw to katallilo minima
                                    output.writeObject("DELETE OK");
                                }else{
                                    output.writeObject("DELETE FAIL");
                                }
                                break;
                        }
                    }
                    else{                                       // an to minima den einai ena ek twn Insert,delete,search
                        sock.close();                           // kleinoume tin sindesi
                    }
                    if(error_founded==false){                   // an den vrethei lathos stis leitourgies
                        output.writeObject("OK");               //stelnoume ok
                        String res=(String) input.readObject(); //perimenoume apantisi
                        if(res.equals("END")){                  // kai an  i apantisi einai end
                            System.out.println(res);            // to ektupwnoume
                        }
                    }
                    
                }catch (EOFException ex){                       // an egerthoun autes oi eksereseis tha simainei pws i sindesi exei kleisei apo emas 
                    System.out.println("I sindesi termatistike apo ton xristi");    // i apo ton client
                }catch (IOException ex) {
                    sock.close();
                }catch (ClassNotFoundException ex) {
                    Logger.getLogger(Hotel_Server.class.getName()).log(Level.SEVERE, null, ex);         
                }

            } 
        } catch (IOException ex) {
            Logger.getLogger(Hotel_Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
