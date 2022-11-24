package com.example.udoncar;

public class MainData {
    private String maintitle;
    private String mainpath;
    private String maindate;

    public MainData(String maintitle, String mainpath, String maindate) {
        this.maintitle = maintitle;
        this.mainpath = mainpath;
        this.maindate = maindate;
    }

    public String getMaintitle() {
        return maintitle;
    }

    public void setMaintitle(String maintitle) {
        this.maintitle = maintitle;
    }

    public String getMainpath() {
        return mainpath;
    }

    public void setMainpath(String mainpath) {
        this.mainpath = mainpath;
    }

    public String getMaindate() {
        return maindate;
    }

    public void setMaindate(String maindate) {
        this.maindate = maindate;
    }
}
