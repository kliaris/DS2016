//Vaggelis Kliaris icsd11066
import java.io.Serializable;
import java.util.Date;


public class Request implements Serializable{       //klasi request
    private static final long serialVersionUID = 2L;
    final private String message;                   // idiotites tis klasis request
    private String name,surname;
    private int id;
    private Date checkin;
    private Reservation reserv;
                                                    // constructors pou dinoun times stis idiotites analogws tis parametrous
    Request(){
        this.message=null;                         
        this.reserv=null;
        this.name=null;
        this.surname=null;
        this.checkin=null;
        this.id=-1;
    }
    
    Request(String mess){
        this.message=mess;                          // edw tha steiloume ena request me ena aplo minima
        this.reserv=null;
        this.name=null;
        this.surname=null;
        this.checkin=null;
    }
    Request(String mess,int id){                    // edw tha steiloume ena minima kai ena id 
        this.message=mess;                          //opote tha anaferthoume sto delete
        this.id=id;                                     
        this.reserv=null;
        this.name=null;
        this.surname=null;
        this.checkin=null;
    }
    Request(String mess,Reservation res){               // edw tha stiloume ena minima kai ena antikeimeno request
        this.message=mess;                              // ara anaferomaste sto insert
        this.reserv=res;
        this.name=null;
        this.surname=null;
        this.checkin=null;
    }
    Request(String mess,Date checkin){                  // edw tha steiloume ena minima kai ena checkin 
        this.message=mess;                              // opote tha anaferthoume stin anazitisi me checkin
        this.checkin=checkin;
        this.name=null;
        this.surname=null;
        this.reserv=null;
    }
    Request(String mess,String name,String surname){
        this.message=mess;                              // edw tha steiloume ena minima ena onoma kai ena epitheto
        this.name=name;                                 // ara tha anaferthoume stin anazitisi me onomateponimo
        this.surname=surname;
        this.checkin=null;
        this.reserv=null;
    }                                                    // methodoi getters gia na silleksoume tis plirofories pou tha mas stalthoun
     public Reservation get_Reserv(){
        return this.reserv; 
    }
    public String get_Mess(){
        return this.message;
    }
    
    public Date get_Checkin(){
        return this.checkin;
    }
    public String get_Name(){
        return this.name;
    }
    public String get_Surname(){
        return this.surname;
    }
    public int get_Id(){
        return this.id;
    }
}
