// Giannis Kosmas   icsd11072
// Vagelis Kliaris  icsd11066
// Kon/nos Stafylas icsd12177
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


public class Request implements Serializable{       // i klasi request xaraktirizei to minima pou tha antalasei o xristis me ton server
                                                        
    private String message;                         // oi idiotites xaraktirizounn ta periexomena enos minimatos
    private Member member;
    private Notice theNotice;
    private Date from,to;
    
    private ArrayList<Notice> noticeList;
    Request(String message){
        this.message=message;
        this.member=null;
        this.theNotice=null;
        this.noticeList=null;
    }
    Request(String message,Member member){
        this.message=message;
        this.member=member;
        this.theNotice=null;
        this.noticeList=null;
        this.from=null;
        this.to=null;
    }
    Request(String message,Member member,Date from,Date to){
        this.message=message;
        this.member=member;
        this.theNotice=null;
        this.noticeList=null;
        this.from=from;
        this.to=to;
    }
    Request(String message,Notice theNotice){
        this.message=message;
        this.theNotice=theNotice;
        this.member=null;
        this.noticeList=null;
    }
    Request(String message,ArrayList noticeList){
        this.message=message;
        this.noticeList=noticeList;
        this.member=null;
        this.theNotice=null;
    }
    public String getMessage(){return this.message;}                            // methodoi set kai get
    public Member getMember(){return this.member;}
    public Notice getNotice(){return this.theNotice;}
    public ArrayList getNoticeList(){return this.noticeList;}
    public Date getFromDate(){return this.from;}
    public Date getToDate(){return this.to;}
    public void setMessage(String mess){this.message=mess;}
    public void setMember(Member memb){this.member=memb;}
}
