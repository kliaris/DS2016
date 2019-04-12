// Giannis Kosmas   icsd11072
// Vagelis Kliaris  icsd11066
// Kon/nos Stafylas icsd12177
import java.io.Serializable;
import java.util.Date;

public class Notice implements Serializable{    // i klasi Notice xaraktirizei mia anakoinwsi
    private int noticeID;
    private final Member author;                // oi idiotites einai ta xaraktiristika mia anakoinwsis
    private Date published,last_edit;
    private String subject,notice;
    private boolean onUse=false;
                                                //constractors
    Notice(Member author,Date published,String subject,String notice){
        this.author=author;
        this.subject=subject;
        this.published=published;
        this.last_edit=published;
        this.notice=notice;
        this.last_edit=null;
        this.noticeID=-1;
    }
    Notice(Member author,Date published,Date last_edit,String subject,String notice){
        this.author=author;
        this.subject=subject;
        this.published=published;
        this.last_edit=published;
        this.notice=notice;
        this.last_edit=last_edit;
        this.noticeID=-1;
    }
    public Member get_author(){return this.author;}
    public String get_subject(){return this.subject;}
    public Date get_published(){return this.published;}
    public Date get_last_edit(){return this.last_edit;}
    public String get_notice(){return this.notice;}
    public int get_ID(){return this.noticeID;}
    public boolean getonUse(){return this.onUse;}
                                                                    // methodoi set kai get
    public void set_ID(int id){this.noticeID=id;}
    public void set_last_edit(Date last_edit){this.last_edit=last_edit;}
    public void set_notice(String notice){this.notice=notice;}
    public void set_onUse(boolean onUse){this.onUse=onUse;}
}
