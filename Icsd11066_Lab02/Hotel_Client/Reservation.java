//icsd11066 Vaggelis Kliaris
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservation implements Serializable{           // klasi Reservation
    private static final long serialVersionUID = 1L;
    
    final private String name,surname,tel,roomtype,breakfast;
    Date checkin,checkout;                                  // idiotites tou antikeimenou kratisis
    private int id;
    private double price;
    boolean error_flag=false;

                                            // constructor 
    Reservation(String name,String surname,String tel,String checkin,String checkout,String roomtype,String breakfast){
        this.name=name;                                 
        this.surname=surname;
        this.tel=tel;
        this.roomtype=roomtype;
        this.breakfast=breakfast;
        this.id=-1;
        this.price=-1;
                                            //metatrepoume ta pedia twn imerominiwn apo string se date
        DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");    
        DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
        
        try {                                           
            this.checkin = df1.parse(checkin); 
            this.checkout = df2.parse(checkout);
            
            long diff=this.checkout.getTime() -this.checkin.getTime();
            if(diff<1){error_flag=true;}                // elegxoume an to checkin einai pio prin apo to check out
        } catch (ParseException e) { 
            error_flag=true;
        }  
        
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
    public Date getCheckin(){
        return checkin;
    }
    public Date getCheckout(){
        return checkout;
    }
    public String getRoomtype(){
        return roomtype;
    }
    public String getBreakfast(){
        return breakfast;
    }
    public int getId(){
        return id;
    }
    public double getPrice(){
        return price;
    }
     public boolean getError_flag(){
        return error_flag;
    }                                               // methodoi setters
     public void setId(int id){
        this.id=id;
    }
    public void setPrice(double price){
        this.price =price;
    }
    public String display(){                    // methodos gia tin emfanisi twn idiotitwn mias kratisis
       String dis=(name+" "+surname+" "+tel+checkin+" "+checkout+" "+roomtype+" "+breakfast);
       return dis;
    }
}
