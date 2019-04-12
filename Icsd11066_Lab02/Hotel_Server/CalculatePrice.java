//  Vaggelis Kliaris icsd11066
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CalculatePrice {
    private double cost;                                // ston constractor me vasi tis parametrous upologizetai to kostos diamonis
    CalculatePrice(Reservation res){

            long diff=res.getCheckout().getTime() -res.getCheckin().getTime();
            int days=(int)(diff/(24*60*60*1000));       // ipologizoume tis meres diamonis
           
            int breakfastParametre;                     // ipologizoume to sunoliko kostos
            if("With Breakfast".equals(res.getBreakfast())){breakfastParametre=1;}
            else{breakfastParametre=0;}                 // an uparxei prwino uparxei extra xrewsi
            
            switch (res.getRoomtype()){
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
    }

   
    public double getCost(){return cost;}                  // epistrofi tou kostous diamonis
}
