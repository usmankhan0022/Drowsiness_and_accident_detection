package usman.com.myapplication;

public class driver {
    private int ID ;
    private String fname ;
    private String lname ;
    private String blood ;
    private String no1 ;
    private String no2;
    private String no3;
    /*
    *this const use for insert data
    * @param name
    * @param email
    * @param Password
     * */

    public driver(int ID, String fname, String lname, String blood,String no1,String no2,String no3)
    {
        this.ID =ID;
        this.fname=fname;
        this.lname=lname;
        this.blood=blood;
        this.no1=no1;
        this.no2=no2;
        this.no3=no3;

    }
    public driver( String fname, String lname, String blood,String no1,String no2,String no3)
    {

        this.fname=fname;
        this.lname=lname;
        this.blood=blood;
        this.no1=no1;
        this.no2=no2;
        this.no3=no3;
    }

    public int getID()
    {
        return ID;
    }
    public String getFname()
    {
        return fname;
    }

    public String getLname()
    {
        return lname;
    }

    public String getBlood()
    {
        return blood;
    }
    public String getNo1()
    {
        return no1;
    }
    public String getNo2()
    {
        return no2;
    }
    public String getNo3()
    {
        return no3;
    }




}
