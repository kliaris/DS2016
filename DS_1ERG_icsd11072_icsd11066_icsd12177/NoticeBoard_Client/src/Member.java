// Giannis Kosmas   icsd11072
// Vagelis Kliaris  icsd11066
// Kon/nos Stafylas icsd12177
import java.io.Serializable;


public class Member implements Serializable{        // i klasi member xaraktirizei ena melos kai einai serializable wste na mporoume na
    private String username,password,name,surname;  // antallasete sta minimata me ton server
    private boolean isAdmin=false;
    private boolean isConnected=false;              // oi idiotites enos melous
    
     Member(){                                      // constractors me diaforetika orismata
        this.username="V";
        this.password="V";
        this.name="V";
        this.surname="V";
    }
    Member(String username,String password){
        this.username=username;
        this.password=password;
        this.name=null;
        this.surname=null;
    }
    Member(String username,String password,String name,String surname){
        this.username=username;
        this.password=password;
        this.name=name;
        this.surname=surname;
    }
    
    
    public String getUsername(){return username;}                   // methodoi set kai get
    public String getPassword(){return password;}
    public String getName(){return name;}
    public String getSurname(){return surname;}
    public boolean isAdmin(){return isAdmin;}
    public boolean isConnected(){return isConnected;}
    public void setUsername(String username){this.username=username;}
    public void setPassword(String password){this.password=password;}
    public void setName(String name){this.name=name;}
    public void setSurname(String surname){this.surname=surname;}
    public void setisAdmin(){this.isAdmin=true;}
    public void setisConnected(){this.isConnected=true;}
   
}
