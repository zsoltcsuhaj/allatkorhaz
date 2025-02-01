package com.example.allatkorhaz;

public class Allat {
    private String nev;
    private String faj;
    private String fajtajelleg;
    private String szuletesiDatum;
    private String gazdiNeve;
    private String gazdiElerhetosege;
    private String orvosiElozmenyek;

    public Allat(String nev, String faj, String fajtajelleg, String szuletesiDatum, String gazdiNeve, String gazdiElerhetosege, String orvosiElozmenyek) {
        setNev(nev);
        setFaj(faj);
        setFajtajelleg(fajtajelleg);
        setSzuletesiDatum(szuletesiDatum);
        setGazdiNeve(gazdiNeve);
        setGazdiElerhetosege(gazdiElerhetosege);
        setOrvosiElozmenyek(orvosiElozmenyek);
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public void setFaj(String faj) {
        this.faj = faj;
    }

    public void setFajtajelleg(String fajtajelleg) {
        this.fajtajelleg = fajtajelleg;
    }

    public void setSzuletesiDatum(String szuletesiDatum) {
        this.szuletesiDatum = szuletesiDatum;
    }

    public void setGazdiNeve(String gazdiNeve) {
        this.gazdiNeve = gazdiNeve;
    }

    public void setGazdiElerhetosege(String gazdiElerhetosege) {
        this.gazdiElerhetosege = gazdiElerhetosege;
    }

    public String getNev() {
        return nev;
    }

    public String getFaj() {
        return faj;
    }

    public String getFajtajelleg() {
        return fajtajelleg;
    }

    public String getSzuletesiDatum() {
        return szuletesiDatum;
    }

    public String getGazdiNeve() {
        return gazdiNeve;
    }

    public String getGazdiElerhetosege() {
        return gazdiElerhetosege;
    }

    public String getOrvosiElozmenyek() {
        return orvosiElozmenyek;
    }

    public void setOrvosiElozmenyek(String orvosiElozmenyek) {
        this.orvosiElozmenyek = orvosiElozmenyek;
    }
}