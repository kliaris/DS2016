//icsd11066 Vaggelis Kliaris
import java.io.Serializable;

public class JReservation implements Serializable{
final private String name,surname,tel,checkin,checkout,roomtype,breakfast;
final private int cost;
                            
    JReservation(String name,String surname,String tel,String checkin,String checkout,String roomtype,String breakfast,int cost){
        this.name=name;             // idiotites tou antikeimenou kratisis
        this.surname=surname;
        this.tel=tel;
        this.checkin=checkin;
        this.checkout=checkout;
        this.roomtype=roomtype;
        this.breakfast=breakfast;
        this.cost=cost;
    }                               // methodoi get gia tin epistrofi twn idiotitwn tis kratisis
    public String getName(){    
        return name;
    }
    public String getSurname(){
        return surname;
    }
    public String getTel(){
        return tel;
    }
    public String getCheckin(){
        return checkin;
    }
    public String getCheckout(){
        return checkout;
    }
    public String getRoomtype(){
        return roomtype;
    }
    public String getBreakfast(){
        return breakfast;
    }
    public int getCost(){
        return cost;
    }
    public String display(){                    // methodos gia tin emfanisi twn idiotitwn mias kratisis
       String dis=(name+" "+surname+" "+tel+checkin+" "+checkout+" "+roomtype+" "+breakfast+" "+cost);
       return dis;
    }
}
