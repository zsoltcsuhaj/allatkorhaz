package com.example.allatkorhaz;

import java.util.Date;

public class Kezeles {
    private String kezelesTipusa;
    private String allatorvosNeve;
    private Date datum;
    private String megjegyzes;
    private String allatNev;

    public Kezeles(String kezelesTipusa, String allatNev, String allatorvosNeve, Date datum, String megjegyzes) {
        setKezelesTipusa(kezelesTipusa);
        setAllatNev(allatNev);
        setAllatorvosNeve(allatorvosNeve);
        setDatum(datum);
        setMegjegyzes(megjegyzes);
    }

    public String getAllatNev() {
        return allatNev;
    }

    public String getKezelesTipusa() {
        return kezelesTipusa;
    }

    public String getKezelesekOsszesitve() {
        return allatNev + " - " + kezelesTipusa;
    }

    public String getAllatorvosNeve() {
        return allatorvosNeve;
    }

    public Date getDatum() {
        return datum;
    }

    public String getMegjegyzes() {
        return megjegyzes;
    }

    public void setKezelesTipusa(String kezelesTipusa) {
        this.kezelesTipusa = kezelesTipusa;
    }

    public void setAllatorvosNeve(String allatorvosNeve) {
        this.allatorvosNeve = allatorvosNeve;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public void setMegjegyzes(String megjegyzes) {
        this.megjegyzes = megjegyzes;
    }

    public void setAllatNev(String allatNev) {
        this.allatNev = allatNev;
    }
}
