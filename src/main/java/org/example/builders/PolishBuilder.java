package org.example.builders;

import org.example.Gamemain;
import org.example.Gamev2;
import org.example.Plansza;
/**
 * This class is responsible for building Polish checkers
 */
public class PolishBuilder{
    boolean isPolish=true;

    int size = 10;
    /**
     * Build Polish checkers
     * @param werecreated if pawns werecreated
     * @param s message to server
     * @param player which player is it
     */
    public void build(Boolean werecreated, String s, int player){
        Gamemain gamemain = new Gamemain().getGamemain();
        if(!werecreated) {
            System.out.println("Ile razy tu weszlismy");
            new Gamev2().assignvalues(size);
            ///PLAN, PRZESLIJ BOOTMLEFT ITD TUTAJ W IFIE, I WTEDY ZWYKLY SEN JEST OK


            s+=" Stworz Pionki ";
            s+=Integer.toString(size);
            s+=" ";
            s+=Boolean.toString(isPolish);
            s+=" ";
            if(player==1) {
                gamemain.send(s);
            }
        }
        gamemain.setsize(size);

        //Plansza pl =new Plansza(size);
        //pl.boardbuilder();
    }
}
