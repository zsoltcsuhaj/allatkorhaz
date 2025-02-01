package com.example.allatkorhaz;

import java.util.Date;

public class ElmultHet {
    private String kezelesTipusa;
    private String allatNev;
    private String allatFaj;
    private String szuletesiDatum;
    private String gazdiNeve;
    private Date kezelesDatum;

    public ElmultHet(String kezelesTipusa, String allatNev, String allatFaj, String szuletesiDatum, String gazdiNeve, Date kezelesDatum) {
        setKezelesTipusa(kezelesTipusa);
        setAllatNev(allatNev);
        setAllatFaj(allatFaj);
        setSzuletesiDatum(szuletesiDatum);
        setGazdiNeve(gazdiNeve);
        setKezelesDatum(kezelesDatum);
    }

    public String getKezelesTipusa() {
        return kezelesTipusa;
    }

    public void setKezelesTipusa(String kezelesTipusa) {
        this.kezelesTipusa = kezelesTipusa;
    }

    public String getAllatNev() {
        return allatNev;
    }

    public void setAllatNev(String allatNev) {
        this.allatNev = allatNev;
    }

    public String getAllatFaj() {
        return allatFaj;
    }

    public void setAllatFaj(String allatFaj) {
        this.allatFaj = allatFaj;
    }

    public String getSzuletesiDatum() {
        return szuletesiDatum;
    }

    public void setSzuletesiDatum(String szuletesiDatum) {
        this.szuletesiDatum = szuletesiDatum;
    }

    public String getGazdiNeve() {
        return gazdiNeve;
    }

    public void setGazdiNeve(String gazdiNeve) {
        this.gazdiNeve = gazdiNeve;
    }

    public Date getKezelesDatum() {
        return kezelesDatum;
    }

    public void setKezelesDatum(Date kezelesDatum) {
        this.kezelesDatum = kezelesDatum;
    }
}
