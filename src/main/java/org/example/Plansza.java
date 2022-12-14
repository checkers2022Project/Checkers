package org.example;

import org.w3c.dom.css.RGBColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Plansza {

    private int size;
    private boolean bottomleftcorner;
    //turkish checkers require special positioning of pawns
    private boolean turkishflag;

    JFrame jFrame;
    Gamemain gamemain;
    final Dimension[] size_of_window = new Dimension[1];
    final static int[] windowH = new int[1];
    final static int[] windowW = new int[1];
    public Plansza(int size, boolean bottomleftcorner,boolean turkishflag) {
        this.size=size;
        this.bottomleftcorner=bottomleftcorner;
        this.turkishflag=turkishflag;
    }
    public Plansza(){}
    //public JFrame getFrame() {return this.jFrame;}
    //public Dimension rozmiar() {return this.jFrame.getContentPane().getSize();}
    public int getwindowW(){return this.windowW[0];}
    public int getwindowH(){return this.windowH[0];}
    //Fils rows with pawns
    //TO BE MOVED TO SERVER

    public void boardbuilder() {
        //ew swithc to main
        Gamemain main =  new Gamemain();
        //jFrame = main.getFrame();
        gamemain=main.getGamemain();


        gamemain.setBounds(10, 10, 1024, 720);
        //size_of_window = jFrame.getContentPane().getSize();
        //windowH = (int) (size_of_window.getHeight() / 8);
        //windowW = (int) (size_of_window.getWidth() / 8);
        //number of rows = siza/2 -1
//        //HALO HALO PIONKI USTAWIAMY NA SERWIE NIE NA KLIENCIE?
//        Gamev2 gamev2 = new Gamev2();
//        //Ustawiam pionki
//        for(int i =0; i<size/2-1; ++i)
//            gamev2.fillrow(i,turkishflag,bottomleftcorner,size);

        JPanel jPanel = new JPanel() {
            public void paint(Graphics g) {
                size_of_window[0] = gamemain.getContentPane().getSize();
                windowH[0] = (int) (size_of_window[0].getHeight() / size);
                windowW[0] = (int) (size_of_window[0].getWidth() / size);
                for(int y=0; y<size;y++){
                    for(int x=0; x<size; x++){
                        if((x%2==0&&y%2==1)||(y%2==0&&x%2==1)){
                            if(bottomleftcorner)
                                g.setColor(Color.getHSBColor(360.F,0.2F,0.7F));
                            else
                                g.setColor(Color.getHSBColor(25.F,1.F,0.5F));

                        }
                        else{
                            if(bottomleftcorner)
                                g.setColor(Color.getHSBColor(25.F,1.F,0.5F));
                            else
                                g.setColor(Color.getHSBColor(360.F,0.2F,0.7F));
                        }
                        g.fillRect(
                                x* windowW[0],
                                y* windowH[0],
                                windowW[0],
                                windowH[0]
                        );
                    }
                }
                //TO BE MOVED TO SERVER or not?
                for(Pionek pion : Pionek.getPionki() ){
                    //System.out.println(pion);
                    if(pion.isWhite()){
                        g.setColor(Color.WHITE);
                    }else{
                        g.setColor(Color.BLACK);
                    }
                    g.fillOval(
                            pion.getX()* windowW[0],
                            pion.getY()* windowH[0],
                            windowW[0], windowH[0]
                    );
                }
                //TO BE MOVED TO SERVER or not
                if(Pionek.getActivePionek()!=null){
                    g.setColor(Color.YELLOW);
                    g.fillRect(
                            Pionek.getActivePionek().getX()* windowW[0],
                            Pionek.getActivePionek().getY()* windowH[0],
                            windowW[0],
                            windowH[0]
                    );
                    if(Pionek.getActivePionek().isWhite()){
                        g.setColor(Color.WHITE);
                    }else{
                        g.setColor(Color.BLACK);
                    }
                    g.fillOval(
                            Pionek.getActivePionek().getX()* windowW[0],
                            Pionek.getActivePionek().getY()* windowH[0],
                            windowW[0],
                            windowH[0]
                    );
                    for(int i=0; i<Pionek.getActivePionek().legalneKafelki().size(); i+=2){
                        g.setColor(Color.green);
                        g.fillRect(
                                Pionek.getActivePionek().legalneKafelki().get(i)*windowW[0],
                                Pionek.getActivePionek().legalneKafelki().get(i+1)* windowH[0],
                                windowW[0],
                                windowH[0]
                        );
                    }
                }
            }
        };
        //ew swithc to main
        //Gamemain gamemain = new Gamemain.CheckerBoardHandler();
        Gamemain.CheckerBoardHandler CBH= gamemain.new CheckerBoardHandler();
        jPanel.addMouseListener(CBH);
        //jFrame.setPreferredSize(new Dimension(1024, 720));
        ///jFrame.pack();
        //jFrame.setLayout(gridLayout);
        //jFrame.setDefaultCloseOperation(3);
        //jFrame.setVisible(true);
        gamemain.add(jPanel);
        gamemain.repaint();
        gamemain.setVisible(true);
    }
}