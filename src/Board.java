import javafx.scene.input.KeyCode;

import java.awt.*;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Board extends JPanel /*implements ActionListener*/ {

    static Image deckPC[] = new Image[52];
    static Image deckPlayer[] = new Image[52];
    Image girl = scaleImage(500, 540, this.getClass().getResource("res/chicks/5_purple.jpg").getPath());;
    JButton drawCard = new JButton("Draw Card");
    JButton endDraw = new JButton("Stop drawing");
    JButton aboutGame = new JButton("Bet 20");
    JPanel pnlButton = new JPanel();
    JLabel points = new JLabel("Points: ");
    Board thisBoard;

    boolean drawNewCard = false;

    static int creditTotal = 200, creditBet = 0, pointsPlayer = 0, pointsPC = 0, randomByte = 51;
    static boolean firstHand = true, allowDrawPC = true, allowDrawPlayer = true, keepPlaying = true;
    static String pcCardsPlayed = "";




    public List<String> getDeck(){
        List<String> deck = new ArrayList<String>();
        for (int i = 0; i < 52; i++){
            deckPlayer[i] = scaleImage(140, 175, this.getClass().getResource("res/" + (i) + ".png").getPath());
        }
        return deck;
    }

    public List<String> getDeckPC(){
        List<String> deck = new ArrayList<String>();
        for (int i = 0; i < 52; i++){
            deckPC[i] = scaleImage(140, 175, this.getClass().getResource("res/" + (i) + ".png").getPath());
        }
        return deck;
    }

    public Board() {
        getDeck();
        this.setBounds(0, 20, 1000, 600);

        aboutGame.setBounds(470, 520, 220, 30);
        drawCard.setLocation(10, 101);
        drawCard.setSize(200, 400);
        endDraw.setBounds(240, 520, 220, 30);

        endDraw.setBackground(new Color(158, 166, 45));
        drawCard.setBackground(new Color(158, 166, 45));
        aboutGame.setBackground(new Color(158, 166, 45));
        aboutGame.setForeground(Color.WHITE);
        endDraw.setForeground(Color.WHITE);
        drawCard.setForeground(Color.WHITE);
        points.setBackground(Color.orange);
        points.setLocation(0, 101);
        points.setSize(200, 400);
        this.add(points);
        this.add(drawCard);
        this.add(endDraw);
        this.add(aboutGame);

        thisBoard = this;


        drawCard.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                drawNewCard = true;
                thisBoard.setBounds(2, 20, 1000, 600);
            }
        });

        aboutGame.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
              //  JOptionPane.showMessageDialog(pnlButton, "The point of the game is to undress the girl. Can you do it?" );
                creditTotal -=20;
                creditBet = 20;
                thisBoard.setBounds(1, 20, 1000, 600);
            }
        });

        endDraw.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {

            }
        });





    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public void paint(Graphics g) {
        Font font = new Font("Verdana", Font.PLAIN, 20);
        Font fontPoints = new Font("Verdana", Font.PLAIN, 17);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setFont(font);
        g2d.setColor(Color.white);

        int x = 10;

        if (firstHand == true){
            for (int i = 0; i < 2; i++){
                Random generator = new Random();
                int rndCardPlayer = generator.nextInt(randomByte);
                g2d.drawImage(deckPlayer[rndCardPlayer], x, 50, null);
                x+=135;
            }
            firstHand = false;
        }



        if (drawNewCard == true){
            Random generator = new Random();
            int rndCardPlayer = generator.nextInt(randomByte);
            g2d.drawImage(deckPlayer[rndCardPlayer], x, 50, null);
            x+=135;
            drawNewCard = false;
        }








        g2d.drawImage(girl, 700, -10, null);
        g2d.drawString("Your cards:", 107, 25);
        g2d.drawRect(170, 250, 400, 200);
        g2d.setFont(fontPoints);
        g2d.drawString("Your bet: " + creditBet, 270, 350);
        g2d.drawString("Your credits: " + creditTotal, 270, 320);
        g2d.drawString("Your points: 17" + pointsPlayer, 270, 380);
    }

    public BufferedImage scaleImage(int WIDTH, int HEIGHT, String filename) {
        BufferedImage bi = null;
        try {
            ImageIcon ii = new ImageIcon(filename);//path to image
            bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TRANSLUCENT);
            Graphics2D g2d = (Graphics2D) bi.createGraphics();
            g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY));
            g2d.drawImage(ii.getImage(), 0, 0, WIDTH, HEIGHT, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bi;
    }


    }

