
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CalculateCost {
    private int cost;                                // ston constractor me vasi tis parametrous upologizetai to kostos diamonis
    CalculateCost(String checkin,String checkout,String roomtype,String breakfast){
        DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");    
        DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate,finishDate;                      // afou ta check-n kai check-out dinontai apo to xristi se morfi string
        try {                                           // to metatrapoume se tupo date gia na einai pio euxrista
            startDate = df1.parse(checkin); 
            finishDate = df2.parse(checkout);
            long diff=finishDate.getTime()-startDate.getTime();
            int days=(int)(diff/(24*60*60*1000));       // ipologizoume tis meres diamonis
           
            int breakfastParametre;                     // ipologizoume to sunoliko kostos
            if(breakfast=="With Breakfast"){breakfastParametre=1;}
            else{breakfastParametre=0;}                 // an uparxei prwino uparxei extra xrewsi
            
            switch (roomtype){
                case "[Single room]": 
                    cost=days*(40+(breakfastParametre*5));
                    break;
                case "[Double room]": 
                    cost=days*(50+(breakfastParametre*2*5));
                    break;
                case "[Triple room]": 
                    cost=days*(65+(breakfastParametre*3*5));
                    break;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }  
        
    }
    public int getCost(){return cost;}                  // epistrofi tou kostous diamonis
}
