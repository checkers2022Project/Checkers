package org.example.builders;

import org.example.Plansza;

public class EnglishBuilder {

    boolean candamabekilled = true;
    boolean manyfieldsdama = false;
    boolean bestkill = false;
    boolean canpawnkillbackwards = false;
    boolean turkishflag=false;

    int size = 8;
    boolean bottomleftcorner = false;//false == czarne chyba
    public void build(){
        Plansza pl =new Plansza(size,bottomleftcorner,turkishflag);
        pl.boardbuilder();
    }
}
