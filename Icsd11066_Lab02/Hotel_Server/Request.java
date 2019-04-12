// Vaggelis Kliaris icsd11066
import java.io.Serializable;
import java.util.Date;


public class Request implements Serializable{            //klasi request
    private static final long serialVersionUID = 2L;
    private String message,name,surname;                // idiotites tis klasis request
    private int id;
    private Date checkin;
    private Reservation reserv;
                                                        // constructors pou dinoun times stis idiotites analogws tis parametrous
    Request(String mess){
        this.message=mess;                               
        this.reserv=null;                               // edw tha steiloume ena request me ena aplo minima
        this.name=null;
        this.surname=null;
        this.checkin=null;
    }
    Request(String mess,int id){
        this.message=mess;                              // edw tha steiloume ena minima kai ena id 
        this.id=id;                                     //opote tha anaferthoume sto delete
        this.reserv=null;
        this.name=null;
        this.surname=null;
        this.checkin=null;
    }
    Request(String mess,Reservation res){
        this.message=mess;                              // edw tha stiloume ena minima kai ena antikeimeno request
        this.reserv=res;                                // ara anaferomaste sto insert
        this.name=null;
        this.surname=null;
        this.checkin=null;
    }
    Request(String mess,Date checkin){
        this.message=mess;                              // edw tha steiloume ena minima kai ena checkin
        this.checkin=checkin;                           // opote tha anaferthoume stin anazitisi me checkin
        this.name=null;
        this.surname=null;
        this.reserv=null;
    }
    Request(String mess,String name,String surname){
        this.message=mess;                              // edw tha steiloume ena minima ena onoma kai ena epitheto
        this.name=name;                                 // opote tha anaferthoume stin anazitisi me onomateponimo
        this.surname=surname;
        this.checkin=null;
        this.reserv=null;
    }                                                   // methodoi getters gia na silleksoume tis plirofories pou tha mas stalthoun
    public String get_Mess(){
        return this.message;
    }
    public Reservation get_Reserv(){
        return this.reserv; 
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

