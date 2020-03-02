package ca.nait.wteljega1;

public class ListItems
{
    private int ListItemsID;
    private int ListTitleID;
    private String ListDescription;
    private String Date;
    private int CompletedFlag;

    public ListItems()
    {

    }

    public ListItems(int id, int titleid, String desc, String date, int flag)
    {
        this.ListItemsID = id;
        this.ListTitleID = titleid;
        this.ListDescription = desc;
        this.Date = date;
        this.CompletedFlag = flag;
    }

    public void setListItemsID(int listItemsID)
    {
        ListItemsID = listItemsID;
    }
    public int getListItemsID()
    {
        return ListItemsID;
    }

    public void setListTitleID(int listTitleID)
    {
        ListTitleID = listTitleID;
    }
    public int getListTitleID()
    {
        return ListTitleID;
    }

    public void setListDescription(String listDescription)
    {
        ListDescription = listDescription;
    }
    public String getListDescription()
    {
        return ListDescription;
    }

    public void setDate(String date)
    {
        Date = date;
    }
    public String getDate()
    {
        return Date;
    }

    public void setCompletedFlag(int completedFlag)
    {
        CompletedFlag = completedFlag;
    }
    public int getCompletedFlag()
    {
        return CompletedFlag;
    }
}
