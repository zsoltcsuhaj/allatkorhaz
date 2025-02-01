package com.example.allatkorhaz;

import java.util.Date;

public class Kezeles {
    private String kezelesTipusa;
    private String allatorvosNeve;
    private Date datum;
    private String megjegyzes;
    private String allatNev; // Az állat neve

    // A konstruktor módosítása
    public Kezeles(String kezelesTipusa, String allatNev, String allatorvosNeve, Date datum, String megjegyzes) {
        this.kezelesTipusa = kezelesTipusa;
        this.allatNev = allatNev;  // Beállítjuk az állat nevét
        this.allatorvosNeve = allatorvosNeve;
        this.datum = datum;
        this.megjegyzes = megjegyzes;
    }

    // Getter és setter metódusok
    public String getAllatNev() {
        return allatNev;
    }

    public String getKezelesTipusa() {
        return kezelesTipusa;
    }

    public String getKezelesekOsszesitve() {
        return allatNev + " - " + kezelesTipusa; // Összefűzve az állat neve és a kezelés típusa
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
}
