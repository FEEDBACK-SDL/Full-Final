package com.example.fedsev.feedback;


public class Contact
{
    private String Name;
    private String Phone;
    private int photo;

    public Contact()
    {
    }

    public Contact(String name, String phone, int photo)
    {
        Name = name;
        Phone = phone;
        this.photo = photo;
    }
    //getter
    public String getName()
    {
        return Name;
    }

    public String getPhone()
    {
        return Phone;
    }

    public int getPhoto()
    {
        return photo;
    }
    //setter
    public void setName(String name)
    {
        Name = name;
    }

    public void setPhone(String phone)
    {
        Phone = phone;
    }

    public void setPhoto(int photo)
    {
        this.photo = photo;
    }
}
