package com.example.doorlockproject;

public class AcceptRequest {
    private String name;
    private String circle_id;

    public AcceptRequest(){}
    public AcceptRequest(String name,String circle_id){
        this.circle_id=circle_id;
        this.name=name;
    }
    public String getName(){return name;}
    public void setName(String name){this.name=name;}
    public String getCircle_id(){return circle_id;}
    public void setCircle_id(String circle_id){this.circle_id=circle_id;}
}
