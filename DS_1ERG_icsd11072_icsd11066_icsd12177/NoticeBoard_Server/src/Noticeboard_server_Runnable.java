
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Noticeboard_server_Runnable extends Noticeboard_Server implements Runnable{

    static ArrayList<Notice> searched_notices=new ArrayList();
    
    static ObjectOutputStream out;  
    static ObjectInputStream input;
    static ObjectOutputStream output;
    @Override
    public void run(){
        
        try {
           
            input= new ObjectInputStream(sock.getInputStream());
            output = new ObjectOutputStream(sock.getOutputStream());// o dialogos tha ginei mesw natikeimenwn
        } catch (IOException ex) {
            Logger.getLogger(Noticeboard_server_Runnable.class.getName()).log(Level.SEVERE, null, ex);
        }
                    
        try {
            
            try {
                Request req=(Request) input.readObject();       // diavazoume to minima tou xristi
                
                switch(req.getMessage()){
                    case ("LOGIN"):                             // an mas steilei login seimainei pws mas esteile kai ena username
                        System.out.println(req.getMessage());   // to opoio tha prepei na ton thesoume ws sindemeno an vrethei 
                        boolean founded=false;                  // sti lista me ta meli
                        Member memb=req.getMember();
                        for(int i=0;i<members.size();i++){      // trexoume tin lista
                            if(members.get(i).getUsername().equals(memb.getUsername()) &&
                                    members.get(i).getPassword().equals(memb.getPassword())){
                                memb.setName(members.get(i).getName());         // an vrethei to username simplirwnoume sto antikeimeno kai ta  
                                memb.setSurname(members.get(i).getSurname());   // upoloipa stoixeia tou  
                                if(members.get(i).isAdmin()==true){
                                    memb.setisAdmin();                          // kathws episis kai to an einai admin
                                }
                                output.writeObject(new Request("CONNECTED",memb));
                                output.flush();                                 // stelnoume sto xristi connected kai ta pliri stoixeia tou
                                founded=true;
                                break;
                            }
                            
                        }
                        if (founded==false){                                    // an den vrethei tote tou stelnoume minima lathous
                            output.writeObject(new Request("Invalid Username or Password"));
                            output.flush();
                        }
                        break;
                    case("SIGNIN"):                             // an o client steilei signin auto seimanei oti prokeitai gia neo xristi
                        System.out.println(req.getMessage());
                        Member newmember=req.getMember();       // pernoume ta stoixeia pou mas esteile
                        members.add(newmember);                 // kai afou dimiourgisoume antikeimeno tuppou member ton vazoume sti lista
                        out=new ObjectOutputStream(new FileOutputStream("members.txt"));
                        out.writeObject(members);               // sti sinexeia ananewnoume to aarxeio me tou xristes
                        
                        output.writeObject(new Request("CONNECTED",newmember));
                        output.flush();                         // stelnoume connected 
                        break;
                    case("SAVE"):                               // an mas steilei save tote auto seimainei pws prokeitai
                        System.out.println(req.getMessage());   // gia apothikeusi neas anakoinwsis
                        Notice theNotice=req.getNotice();
                        notices.add(theNotice);                 // apothikeuoume tin anakoinwsi pou mas esteile sti lista
                        try {                                   
                            out=new ObjectOutputStream(new FileOutputStream("notices.txt"));
                            for(int i=0;i<notices.size();i++){  
                                notices.get(i).set_ID(i);       // thetoume id stin anakoinwsi me vasi tin thesi pou exei sti lista
                                
                            }
                            out.writeObject(notices);           // ananewnoume to arxei me tis anakoinwseis
                            out.flush();
                            out.close();
                                                                // kai tou apantame pws apothikeutike
                            output.writeObject(new Request("SAVED"));
                            output.flush();
                            break;
                        } catch (IOException ex) {              // pianoume ola ta exceptions pou mporei na prokupsoun
                            System.out.println("Provlima Eggrafis Sto arxeio.");
                        }
                        break;
                    case("FIND"):                               // an mas steilei find twte mas zita na tou vroume  anakoinwseis 
                        System.out.println(req.getMessage());
                        memb=req.getMember();
                        searched_notices=new ArrayList();
                        if(memb.isAdmin()==false){              // an o xristis den einai admin
                            if(req.getFromDate()==null && req.getToDate()==null){   // an den mas dwsei eurws imerominiwn
                                for(int i=0;i<notices.size();i++){  // psaxnoume tin anakoinwseis sti lista tis opoies exei dimosieusei o idios                            
                                    if(notices.get(i).get_author().getUsername().equals(memb.getUsername())){
                                        searched_notices.add(notices.get(i));   // kai tis vazoume se mia proswrini lista
                                    }
                                }                                               // tou apantame founded mazi me tin lista auti
                                output.writeObject(new Request("FOUNDED",searched_notices));
                                output.flush();
                                
                            }else{                              // an mas steilei imerominies
                                for(int i=0;i<notices.size();i++){  // tote psaxnoume oles tis anakoinwseis pou vriskontai mesa sto eurws pou mas
                                    if((notices.get(i).get_published().compareTo(req.getFromDate())>=0)&&       // edwse
                                            (notices.get(i).get_published().compareTo(req.getToDate())<=0)){
                                        searched_notices.add(notices.get(i));
                                    }            
                                }                                   // tou apantame founded me tin lista auti
                                output.writeObject(new Request("FOUNDED",searched_notices));
                                output.flush();
                            }
                        }else{          // an o xristis einai admin kai den mas edwse imerominies
                            if(req.getFromDate()==null && req.getToDate()==null){   
                                for(int i=0;i<notices.size();i++){      // tote tou vriskoume oles tis anakoinwseis ,akoma kai ekeines pou den                 
                                    searched_notices.add(notices.get(i));   // dimosieuse o idios
                                    
                                }
                                output.writeObject(new Request("FOUNDED",searched_notices));
                                output.flush();
                                System.out.println("edw");
                            }else{
                                for(int i=0;i<notices.size();i++){
                                    if((notices.get(i).get_published().compareTo(req.getFromDate())>0)&&
                                            (notices.get(i).get_published().compareTo(req.getToDate())<0)){
                                        searched_notices.add(notices.get(i));
                                        System.out.println(notices.get(i).get_published()+"  "+req.getFromDate()+"  "+req.getToDate() +" "+searched_notices.size());
                                    }          
                                }
                                output.writeObject(new Request("FOUNDED",searched_notices));
                                output.flush();
                            }
                        }
                        
                        break;
                    case("EDIT"):                   // an mas steilei edit tote prokeitai na epeksergastei tin anakoinwsi pou esteile 
                        System.out.println(req.getMessage());   // me to minima auto
                        Notice editingThis =req.getNotice();
                        
                        for(int i=0;i<notices.size();i++){
                            if(editingThis.get_ID()==notices.get(i).get_ID()){
                                if(notices.get(i).getonUse()==true){            // psaxnoume na vroume tin anakoinwsi sti lista
                                    output.writeObject(new Request("LOCKED"));  // an auti xreisimopoieitai apo kapoion allon
                                    output.flush();                             // tote tou apantame locked kai den ton afinoume na 
                                }else{                                          // kanei kapoia parapanw energeia
                                    output.writeObject(new Request("WAITING")); // alliws tou stelnoume wait
                                    output.flush();
                                    notices.get(i).set_onUse(true);             // kai thetoume tis anakoinwsi oti xrisimopoieitai
                                    synchronized(notices.get(i)){               // kleidwnoume me synchronize to antikeimeno
                                        editNotice(notices.get(i));             // kai kaloume tin methodo editNotice gia tin epeksergasia
                                        notices.get(i).set_onUse(false);        // afou teleiwsei i methods apeleutherwnoume to antikeimeno                              
                                    }
                                }                               
                                break;                               
                            }                       
                        } 
                        break; 
                        case("READ"):                               // an o xristis steilei read tote auto seimainei pws esteile 
                            System.out.println(req.getMessage());   // kai mia anakoinwsi pou thelei na diavasei
                            for(int i=0;i<notices.size();i++){      // psaxnoume tin anakoinwsi sti lista                          
                                if(req.getNotice().get_ID()==notices.get(i).get_ID()){
                                    if(notices.get(i).getonUse()==true){    // an xreisimopoieitai apo kapoion gia epeksergasia
                                        output.writeObject(new Request("LOCKED"));  //den ton afinoume na tin diavasei stelnwntas tou lock
                                        output.flush();
                                    }else{                          // an einai eleutheri tote mesw tou synchronize kleidwnoume to antikeimeno
                                        synchronized(notices.get(i)){   // wste na min to zitisei kapoios 
                                            notices.get(i).set_onUse(true);     // kai tou stelnoume read this                                   
                                            output.writeObject(new Request("READ THIS",notices.get(i)));
                                            output.flush();
                                        }
                                    }
                                    
                                    
                                }         
                            }
                            
                            output.flush();
                            break;
                        case("READED"):                             // an o xristis mas steilei readed auto seimainei pws exei teliwsei
                            System.out.println(req.getMessage());   // me tin anagnwsi tin anakoinwsis
                            for(int i=0;i<notices.size();i++){                                   
                                if(req.getNotice().get_ID()==notices.get(i).get_ID()){
                                    synchronized(notices.get(i)){
                                        notices.get(i).set_onUse(false);    // thetoume oti i anakoinwsi den xreisimopoiitai pleon
                                        notices.get(i).notifyAll();
                                    }
                                }      
                            }                                               // kai stelnoume readed this
                            output.writeObject(new Request("READED THIS"));
                            output.flush();
                            break;
                }
                
                req=(Request) input.readObject();       // se kathe apo tis parapanw periptwseis sti sunexeia diavazoume to minima tou
                
                                                        // an einai ok perimenoume na termatisei tin sindesi
                if(req.getMessage().equals("OK")){System.out.println(req.getMessage());}
            } catch (ClassNotFoundException ex) {       
                System.out.println("I sindesi termatistike");
                sock.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Noticeboard_server_Runnable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public  void editNotice(Notice editThis){       // otan klithei i editNotice prokeitai gia epeksergasia anakoinwsis
        try {
            
            Request req=(Request) input.readObject();   // perimenw tin energeia pou thelei na kanei o xristis sti dimosieusi pou esteile
            System.out.println(req.getMessage());
            for(int i=0;i<notices.size();i++){
                
                if(editThis.get_ID()==req.getNotice().get_ID()){
//                    while( notices.get(i).getonUse()==true){
//                        wait();
//                        System.out.println("Perimenw");
//                    }
                    notices.get(i).set_onUse(true);                 // thetw tin dimosieusi se xrisi
                    if(req.getMessage().equals("CHANGE")){          // an steilei change tote o xristis thelei na tin epeksergastei
                            System.out.println(req.getMessage());
                            notices.set(i,req.getNotice());         // antikathistw tin palia dimosieusi me autin pou mou esteile
                                                                    // kai tou apantw pws oi allages eginan
                            output.writeObject(new Request("CHANGES SAVED"));
                            output.flush();
                            notices.get(i).set_onUse(false);        // apeleutherwnw tin anakoinwsi
                            break;
                    }
                    else if(req.getMessage().equals("DELETE")){     // an mou steilei delete tote o xristis thelei na tin diagrapsei                   
                        notices.remove(i);                          // diagrafw tin anakoinwsi
                                                                    
                       output.writeObject(new Request("REMOVED"));  // kai apantw pws diagraftike
                       output.flush();
                       break;
                    }else{
                        sock.close();
                        break;
                    }                  
              
                }

            }

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Noticeboard_server_Runnable.class.getName()).log(Level.SEVERE, null, ex);
        } //catch (InterruptedException ex) {
//            Logger.getLogger(Noticeboard_server_Runnable.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
    }
}
