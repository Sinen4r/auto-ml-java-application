package org.users;

public abstract class User {
    private int id ;
    private String name;
    private String email;

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getID(){
        return id;
    }

    public int setID(int id){
        this.id=id;
        return id;
    }

    public String getName(){
        return name;
    }

    public String setName(String name){
        this.name= name;
        return name;
    }

    public String getEmail(){
        return email;
    }

    public String setEmail(String email){
        this.email = email;
        return email;
    }

    public abstract void accessFeatures();
}
