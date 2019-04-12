// Vaggelis Kliaris icsd 11066
import java.util.ArrayList;
import java.util.Date;


public class Searching {
    private Date checkin;                           // idiotites tou searching
    private String surname,name;
    static ArrayList<Reservation> reserv=new ArrayList<Reservation>();

     Searching(Date checkin,String name,String surname){
        this.checkin=checkin;                       // ston constructor dinoume tis times stis idiotites
        this.name=name;
        this.surname=surname;
          
    }
    public ArrayList<Reservation> get_list(ArrayList<Reservation> list){
        if(checkin!=null){                          // an to check in den einai null tote katalavainoume pws prokeitai gia anazitis
            for(int i=0;i<list.size();i++){         // mesw check in
                if(list.get(i).getCheckin().equals(checkin)){   // trexoume ti lista me tis kratiseis 
                    reserv.add(list.get(i));                    // kai an vrethei to sigkekrimeno checkin tote to prosthetoume se mia proswrini lista
                }
            } 
        }else{
            for(int i=0;i<list.size();i++){         // an to checkin einai null tote to onoma kai to epitheto tha exoun times opote katalavainoume 
                if(list.get(i).getName().equals(this.name) && list.get(i).getSurname().equals(this.surname)){   //pws prokeitai gia anazitisi mesw onom/mou
                    reserv.add(list.get(i));        //trexoume ti lista kai an vrethei to onom/no prwsthetoume to antikeimeno se proswrini lista
                }
            } 
        }
        
        return reserv;                              // epistrefoume ti lista gia na tin steiloume ston client
    }
}
