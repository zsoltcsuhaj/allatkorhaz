package com.example.allatkorhaz;

public class EvVizsgalat {
    private String nev;
    private String faj;
    private String szuletesiDatum;
    private String gazdiNeve;
    private String gazdiElerhetosege;
    private String legutobbiVizsgalat;

    public EvVizsgalat(String nev, String faj, String szuletesiDatum, String gazdiNeve, String gazdiElerhetosege, String legutobbiVizsgalat) {
        setNev(nev);
        setFaj(faj);
        setSzuletesiDatum(szuletesiDatum);
        setGazdiNeve(gazdiNeve);
        setGazdiElerhetosege(gazdiElerhetosege);
        setLegutobbiVizsgalat(legutobbiVizsgalat);
    }

    public String getNev() { return nev; }
    public String getFaj() { return faj; }
    public String getSzuletesiDatum() { return szuletesiDatum; }
    public String getGazdiNeve() { return gazdiNeve; }
    public String getGazdiElerhetosege() { return gazdiElerhetosege; }
    public String getLegutobbiVizsgalat() { return legutobbiVizsgalat; }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public void setFaj(String faj) {
        this.faj = faj;
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

    public void setLegutobbiVizsgalat(String legutobbiVizsgalat) {
        this.legutobbiVizsgalat = legutobbiVizsgalat;
    }
}
