package pillapp.Model;

/**
 * Created by Vyom on 01-08-2016.
 */
public class Panic {
    private String id;
    private String mobile1;
    private String mobile2;
    private String mobile3;
    private String mobile4;
    private String mobile5;
    public Panic()
    {
         }

public Panic(String mobile1,String mobile2,String mobile3,String mobile4,String mobile5)
{
    this.mobile1=mobile1;
    this.mobile2=mobile2;
    this.mobile3=mobile3;
    this.mobile4=mobile4;
    this.mobile5=mobile5;

}
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile1() {
        return mobile1;
    }

    public void setMobile1(String mobile1) {
        this.mobile1 = mobile1;
    }

    public String getMobile2() {
        return mobile2;
    }

    public void setMobile2(String mobile2) {
        this.mobile2 = mobile2;
    }

    public String getMobile3() {
        return mobile3;
    }

    public void setMobile3(String mobile3) {
        this.mobile3 = mobile3;
    }

    public String getMobile4() {
        return mobile4;
    }

    public void setMobile4(String mobile4) {
        this.mobile4 = mobile4;
    }

    public String getMobile5() {
        return mobile5;
    }

    public void setMobile5(String mobile5) {
        this.mobile5 = mobile5;
    }
}
