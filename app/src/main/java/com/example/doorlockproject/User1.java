package com.example.doorlockproject;

public class User1 {
    private String first_name;
    private String last_name;
    private String pass;
    private int period;
    private String email;
    private String prev_hash;
    private String next_hash;
    private String self_hash;
    private String user_ID;
    private Integer auth;
    private Integer mem_no;

    public User1(){}
    public User1(String user_ID,String email,String first_name, String last_name, String pass, String prev_hash, String next_hash,String self_hash, Integer auth,Integer mem_no,int period) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.pass = pass;
        this.email=email;
        this.period=period;
        this.user_ID = user_ID;
        this.prev_hash = prev_hash;
        this.prev_hash = self_hash;
        this.next_hash = next_hash;
        this.auth=auth;
        this.mem_no=mem_no;
    }
    public String getEmail(){return email;}
    public void setEmail(String email){this.email=email;}
    public int getPeriod(){return period;}
    public void setPeriod(int period){this.period=period;}
    public String getNext_hash() {
        return next_hash;
    }

    public void setNext_hash(String next_hash) {
        this.next_hash = next_hash;
    }
    public String getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(String user_ID) {
        this.user_ID = user_ID;
    }
    public Integer getAuth() {
        return auth;
    }

    public void setAuth(Integer auth) {
        this.auth = auth;
    }
    public Integer getMem_no() {
        return mem_no;
    }

    public void setMem_no(Integer mem_no) {
        this.mem_no = mem_no;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPrev_hash() {
        return prev_hash;
    }

    public void setPrev_hash(String prev_hash) {
        this.prev_hash = prev_hash;
    }
    public String getSelf_hash() {
        return self_hash;
    }

    public void setSelf_hash(String self_hash) {
        this.self_hash = self_hash;
    }
}
