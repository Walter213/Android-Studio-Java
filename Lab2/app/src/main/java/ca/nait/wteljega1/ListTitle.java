package ca.nait.wteljega1;

public class ListTitle
{
    private int ListTitleID;
    private String ListDescription;

    public ListTitle()
    {

    }

    public ListTitle(int listtitleid, String listdescription)
    {
        this.ListTitleID = listtitleid;
        this.ListDescription = listdescription;
    }

    public void setListTitleID(int id)
    {
        this.ListTitleID = id;
    }
    public int getListTitleID()
    {
        return this.ListTitleID;
    }

    public void setListDescription(String listdescription)
    {
        this.ListDescription = listdescription;
    }
    public String getListDescription()
    {
        return this.ListDescription;
    }
}
