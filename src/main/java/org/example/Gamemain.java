package org.example;

import org.example.builders.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
//SEND I RECEIVE NIE DO KONCA TAK JAKBYM CHCIAL
//ZROB MENU W CONSTRUKTORZE + NAPRAWIC

//ZAMIAST TWORZYC PIONKI W BUILDERZE ROB TO NA SERWERZE, TJ WYSLIJ WIADOMOSC NA SERWER I NIECH ON STWORZY PIONKI


public class Gamemain extends JFrame implements Runnable{

    int size;
    int pawnnumber;
    Boolean werecreated=false;
    public Boolean setWerecreated(){
        this.werecreated= new Gamev2().getWerecreated();
        return this.werecreated;
    }
    static Gamemain gamemain = new Gamemain();
    static JLabel beforethegame;
    static JMenuBar menuBar;

    //public JFrame getFrame() {return this.jFrame;}

    JLabel msg;

    Socket socket;
    PrintWriter out;
    BufferedReader in;

    private int player;

    public final static int PLAYER1 = 1;
    public final static int PLAYER2 = 2;

    public final static int ACTIVE = 0;
    public final static int NONACTIVE = 1;
    private  static int actualPlayer = PLAYER1;

    private static int showing = ACTIVE;
    public int getPlayer(){
        return this.player;
    }
    public Gamemain(){

    }
    public void setsize(int size){
        this.size=size;
        this.pawnnumber=size*(size/2-1);
    }
    public Gamemain getGamemain(){
        return this.gamemain;
    }
    public void removebeforethegame(){
        gamemain.remove(beforethegame);
    }

    public void send(String s){
        // Wysylanie do serwera
        out.println(s);
        showing = ACTIVE;
        actualPlayer = player;
    }
    private void receive(){
        try {
            //MAYBE DEPRECATED
            String str = in.readLine();
            System.out.println(str);
            if(str.contains("warcaby")){
                gamemain.removebeforethegame();
                //IDEA: BUILDERY ZAWIERAJA INFO O GRACZU ZEBY NIE WYSYLAC DO SERWERA INFO O TWORZENIU PIONKOW DLA GRACZA 2
                if (str.contains("warcaby angielskie"))
                    new EnglishBuilder().build(true,str,player);
                else if (str.contains("warcaby polskie"))
                    new PolishBuilder().build(true,str, player);
                else if (str.contains("warcaby brazylijskie"))
                    new BrazilianBuilder().build(true,str,player);
            }
            String[]s= str.split(" ");
            double v=s.length;
            int offset;
            if(player==2){
                offset=8;
                v=(v-8)/3;
            }else{
                offset=2;
                v=(v-2)/3;
            }
            //System.out.println(str);
            //System.out.println(str.contains("[a-zA-Z]+"));
            //if(str.matches(".*\\d.*")){
            //if(str.contains("[a-zA-Z]+")){
            if(str.contains("Leca")){
                //REPAINT JPANEL?
                int[] x = new int[pawnnumber];
                int[] y = new int[pawnnumber];
                boolean[] isWhite = new boolean[pawnnumber];

                for(int i=0; i<v; i++) {
                    System.out.println(s.length);
                    System.out.println(pawnnumber);
                    System.out.println(v);
                    String s1 = s[(i*3)+offset];
                    System.out.println(s1);
                    String s2 = s[(i*3)+offset+1];
                    System.out.println(s2);
                    String s3 = s[(i*3)+offset+2];
                    System.out.println(s3);
                    isWhite[i] = Boolean.parseBoolean(s1);
                    System.out.println("HEJ");
                    x[i] = Integer.parseInt(s2);
                    System.out.println("HEJ");
                    y[i] = Integer.parseInt(s3);
                    System.out.println("HEJ");
                }
                Plansza pl = new Plansza(size, x,y,isWhite, pawnnumber);
                pl.boardbuilder();
            }
        }
        //catch (IOException e) {
        catch (IOException e) {
            System.out.println("Read failed"); System.exit(1);
        }
    }

    /*
    Połaczenie z socketem
     */
    public void listenSocket() {
        try {
            socket = new Socket("localhost", 4444);
            // Inicjalizacja wysylania do serwera
            out = new PrintWriter(socket.getOutputStream(), true);
            // Inicjalizacja odbierania z serwera
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: localhost");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("No I/O");
            System.exit(1);
        }
    }

    /*
        Poczatkowe ustawienia klienta. Ustalenie ktory socket jest ktorym kliente
    */
    private void startThread() {
        Thread gTh = new Thread(this);
        gTh.start();
    }
    private void receiveInitFromServer() {
        //jFrame.a
        msg=new JLabel("Status");
        menuBar.add(msg);
        try {
            player = Integer.parseInt(in.readLine());
            if (player == PLAYER1) {
                System.out.println("player");
                System.out.println(player);
                msg.setText("My Turn");
            } else {
                System.out.println("player");
                System.out.println(player);
                msg.setText("Opposite turn");
                //send.setEnabled(false);
            }
        } catch (IOException e) {
            System.out.println("Read failed");
            System.exit(1);
        }
    }

    @Override
    public void run() {
        if (player==PLAYER1) {
            f1();
        }
        else{
            f2();
        }
    }
    void f1(){
        while(true) {
            synchronized (this) {
                if (actualPlayer== PLAYER1) {
                    try {
                        wait(10);
                    } catch (InterruptedException e) {
                    }
                }
                if (showing ==ACTIVE){
                    //actualPlayer = player;
                    System.out.println("actualPlayer");
                    System.out.println(actualPlayer);
                    receive();
                    showing =NONACTIVE;
                }
                notifyAll();
            }
        }
    }

    /// Metoda uruchamiana w run dla PLAYER2
    void f2(){
        while(true) {
            synchronized (this) {
                if (actualPlayer== PLAYER2) {
                    try {
                        wait(10);
                    } catch (InterruptedException e) {
                    }
                }
                if (showing ==ACTIVE){
                    //actualPlayer = player;
                    System.out.println("actualPlayer");
                    System.out.println(actualPlayer);
                    receive();
                    showing =NONACTIVE;
                }
                notifyAll();
            }
        }
    }
    ///CHWILA CHWILA A TO NIE WYSTARCZY PRZED RUCHEM REPAINT ZROBIC?
    class CheckerBoardHandler implements MouseListener {
        Plansza pl= new Plansza();
        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("Klik");
            //wysylamy zapytanie czy pionek
            for (Pionek pion : Pionek.getPionki()) {
                if (pion.isActive()) {
                    pion.move(e.getX() / pl.getwindowW(), e.getY() / pl.getwindowH());
                    pion.setActive(false);
                    gamemain.repaint();
                    return;
                }
            }
            if (Pionek.getPionekByCords(e.getX() / pl.getwindowW(), e.getY() / pl.getwindowH()) != null) {

                for (Pionek pion : Pionek.getPionki()) {
                    pion.setActive(false);
                }

                Pionek.getPionekByCords(e.getX() / pl.getwindowW(), e.getY() / pl.getwindowH()).setActive(true);
                gamemain.repaint();
            }
        }
        @Override
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
    }


    public static void main(String[] args) {
        //Gamemain gamemain= new Gamemain();
        //jFrame = new JFrame();
        gamemain.setBounds(10, 10, 800, 800);


        beforethegame = new JLabel(
                "WYBIERZ TRYB GRY",
                SwingConstants.CENTER
        );
        beforethegame.setFont(beforethegame.getFont().deriveFont(50.0F));
        MenuCreator menuCreator = new MenuCreator();
        //idk czy ok
        //ZAMIAST TEGO SPROBUJMY MOZE KOMUNIKACJI Z SERWEREM
        //THATSS NOT THE POINT, MENU ROIBIMY ZAWSZE, ALE NIE PIONKI, ALE Z KOLEJ KLIENT POTRZEBUJE MIEC STWORZONE KONKRETNE MENU

        menuCreator.createmenu();

        menuBar=menuCreator.getMenuBar();



        gamemain.setJMenuBar(menuBar);
        gamemain.setLayout(new BorderLayout());
        gamemain.add(beforethegame, BorderLayout.CENTER);
        //jFrame.add(jPanel);

        gamemain.setDefaultCloseOperation(3);
        gamemain.setVisible(true);
        gamemain.listenSocket();
        gamemain.receiveInitFromServer();
        gamemain.startThread();


    }

}
