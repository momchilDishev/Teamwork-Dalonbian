import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MainForm extends JFrame implements ActionListener{

    static int creditTotal = 200, creditBet = 0, pointsPlayer = 0, pointsPC = 0, randomByte = 51, girlNow = 1;
    static java.util.List<String> deckPlayer;
    static java.util.List<String> deckPC;
    JPanel mainItemHolder, chickHolder, cardsHolder; //a panel that uses CardLayout
    JLabel pointsPlayerVar, pointsPCVar, creditsTotal, creditsBet, lastDrawnCards;
    JButton drawCard, pass, bet, buyGirl, girlImage, card1, card2;
    ImageIcon girl = scaleImage(610, 650, this.getClass().getResource("res/chicks/4_purple.jpg").getPath());;
    ImageIcon cardToAdd = scaleImage(90, 120, this.getClass().getResource("res/17.png").getPath());;


    static boolean firstHand = true, allowDrawPC = true, allowDrawPlayer = true, keepPlaying = true, betAvaliable = false;
    static String pcCardsPlayed = "", lastDrawnCardsStr = "";



    public void beginGame(){
        newSet();
        Random generator = new Random();

        if (firstHand == true){
            int rndCardPlayer = generator.nextInt(randomByte);
            //System.out.println("Your Card:  Total Points: " + String.valueOf(getPoints(deckPlayer.get(rndCardPlayer), pointsPlayer, deckPlayer, "Player")));

            lastDrawnCardsStr+=deckPlayer.get(rndCardPlayer);
            lastDrawnCards.setText("Your last cards: " + lastDrawnCardsStr);
            pointsPlayerVar.setText("Points: " + String.valueOf(getPoints(deckPlayer.get(rndCardPlayer), pointsPlayer, deckPlayer, "Player")));


            rndCardPlayer = generator.nextInt(randomByte);
            System.out.println("Your Card: " + deckPlayer.get(rndCardPlayer) + " Total Points: " + String.valueOf(getPoints(deckPlayer.get(rndCardPlayer), pointsPlayer, deckPlayer, "Player")));

            lastDrawnCardsStr+=deckPlayer.get(rndCardPlayer);
            lastDrawnCards.setText("Your last cards: " + lastDrawnCardsStr);
            pointsPlayerVar.setText("Points: " + String.valueOf(getPoints(deckPlayer.get(rndCardPlayer), pointsPlayer, deckPlayer, "Player")));

            int rndCardPC = generator.nextInt(randomByte);
            System.out.println("PC Card: " + deckPC.get(rndCardPC));
            pcCardsPlayed+=("\nPC drawed cards:\nPC Card: " + deckPC.get(rndCardPC) + " Total Points: " + String.valueOf(getPoints(deckPC.get(rndCardPC), pointsPC, deckPC, "PC")));
            rndCardPC = generator.nextInt(randomByte);

            pcCardsPlayed+=("\nPC Card: " + deckPC.get(rndCardPC) + " Total Points: " + String.valueOf(getPoints(deckPC.get(rndCardPC), pointsPC, deckPC, "PC")));

            firstHand = false;
        }
        else{
            if (allowDrawPlayer == true){
                int rndCardPlayer = generator.nextInt(randomByte);
               // System.out.println("Your Card: " + deckPlayer.get(rndCardPlayer) + " Total Points: " + String.valueOf(getPoints(deckPlayer.get(rndCardPlayer), pointsPlayer, deckPlayer, "Player")));

                lastDrawnCardsStr+=deckPlayer.get(rndCardPlayer);
                lastDrawnCards.setText("Your last cards: " + lastDrawnCardsStr);
                pointsPlayerVar.setText("Points: " + String.valueOf(getPoints(deckPlayer.get(rndCardPlayer), pointsPlayer, deckPlayer, "Player")));
            }

            if (allowDrawPC == true){
                int rndCardPC = generator.nextInt(randomByte);
                pcCardsPlayed+=("\nPC Card: " + deckPC.get(rndCardPC) + " Total Points: " + String.valueOf(getPoints(deckPC.get(rndCardPC), pointsPC, deckPC, "PC")));
            }
        }


        if (pointsPC > 21){
            System.out.println("You win! Player points: " + pointsPlayer);
            System.out.println("PC points: " + pointsPC + "\nPlayer points: " + pointsPlayer);
            creditTotal+=creditBet*2;
            allowDrawPC = false;
            newSet();
        }

        if (pointsPlayer == 21 ){
            System.out.println("Congratulations!!! You win!"); creditTotal+=creditBet*2;
            System.out.println("PC points: " + pointsPC + "\nPlayer points: " + pointsPlayer);
            newSet();
        }
        else if (pointsPC == 21 || pointsPlayer > 21) {
            System.out.println("PC wins. You lose.");
            System.out.println("PC points: " + pointsPC + "\nPlayer points: " + pointsPlayer);
            newSet();
        }
        else if (pointsPC >=17 && pointsPC < 21){
            allowDrawPC = false;
        }

        if (allowDrawPlayer == false && allowDrawPC == false){
            System.out.println("PC points: " + pointsPC + "\nPlayer points: " + pointsPlayer);

            if (pointsPC > pointsPlayer){System.out.println("You lose!");}
            else if (pointsPC < pointsPlayer){System.out.println("You Win!"); creditTotal+=creditBet*2; }
            else if (pointsPC == pointsPlayer){System.out.println("Draw!");}

            keepPlaying = false;
            newSet();
        }

    }


    public void newSet(){
        deckPlayer = getDeck();
        deckPC = getDeck();
        creditBet = 0;
        pointsPlayer = 0;
        pointsPC = 0;
        randomByte = 51;
        firstHand = true;
        allowDrawPlayer = true;
        allowDrawPC = true;
        keepPlaying = true;
        lastDrawnCardsStr = "";
        lastDrawnCards.setText("Last cards: "  + lastDrawnCardsStr);
        creditsTotal.setText("Total credits: " + String.valueOf(creditTotal));
    }


    public ImageIcon scaleImage(int WIDTH, int HEIGHT, String filename) {
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
        return new ImageIcon(bi);
    }

    public static void getBet(){
        Scanner scan = new Scanner(System.in);
        System.out.println("How much do you want to bet? You have " + creditTotal + ". If you want to stop the game, type 'NO'");

        String bet = scan.next();

        if ("NO".equals(bet.toUpperCase())){
            System.out.println("Have a nice day!");
            keepPlaying = false;
            System.exit(0);
        }

        while ((Integer.parseInt(bet) < 0) || (Integer.parseInt(bet) > creditTotal)) {
            System.out.println("Not enough credits!");
            bet=String.valueOf(scan.nextInt());
        }

        creditBet = Integer.parseInt(bet);
        creditTotal -= creditBet;
    }

    public static int getPoints(String card, int points, java.util.List<String> deck, String typePlayer){
        if (card.charAt(0) == '2')      {points+=2; deck.remove(card);}
        else if (card.charAt(0) == '1') {points+=10; deck.remove(card);}
        else if (card.charAt(0) == '3') {points+=3; deck.remove(card);}
        else if (card.charAt(0) == '4') {points+=4; deck.remove(card);}
        else if (card.charAt(0) == '5') {points+=5; deck.remove(card);}
        else if (card.charAt(0) == '6') {points+=6; deck.remove(card);}
        else if (card.charAt(0) == '7') {points+=7; deck.remove(card);}
        else if (card.charAt(0) == '8') {points+=8; deck.remove(card);}
        else if (card.charAt(0) == '9') {points+=9; deck.remove(card);}
        else if (card.charAt(0) == 'J' || card.charAt(0) == 'Q' || card.charAt(0) == 'K') {points+=10; deck.remove(card);}
        else if (card.charAt(0) == 'A') {

            if (typePlayer.equals("PC")){
                Scanner scan = new Scanner(System.in);
                System.out.println("Your card is 'Ace'. Do you want it to count as a '11' or as '1'? Type 11 or 1:");
                String choice = scan.nextLine();
                if ("11".equals(choice))  points+=11;
                else points+=1;
                deck.remove(card);
            }
            else {
                if ((11 + pointsPC) >= 22){
                    pointsPC+=1;
                }
                else pointsPC +=11;
            }
        }
        randomByte--;


        if (typePlayer.equals("PC")){
            pointsPC = points;
            return pointsPC;
        }
        else if (typePlayer.equals("Player")){
            pointsPlayer = points;
            return pointsPlayer;
        }

        return points;
    }

    private static void creditsLimit(Scanner input) {
        int Credits = input.nextInt();
        int creditsNow=200;
        while ((Credits<0) || (Credits>creditsNow)) {
            System.out.println("Nqmash tolkova crediti");
            Credits=input.nextInt();
        }
    }


    public void addComponentToPane(Container pane) {
        //Put the JComboBox in a JPanel to get a nicer look.
        JPanel level1Items = new JPanel(); //use FlowLayout
        pointsPlayerVar = new JLabel("Your Points: " + pointsPlayer);
        pointsPCVar= new JLabel("   PC Points: " + pointsPC);
        creditsTotal = new JLabel("   Total credits: " + creditTotal);
        creditsBet = new JLabel("   Bet: " + creditBet);
        lastDrawnCards = new JLabel("   Last drawn cards: " + lastDrawnCardsStr);

        level1Items.add(pointsPlayerVar);
        level1Items.add(pointsPCVar);
        level1Items.add(creditsTotal);
        level1Items.add(creditsBet);


        //Create the "cards".
        JPanel level2Items = new JPanel();

        drawCard = new JButton("Draw Card");
        drawCard.addActionListener(this);
        pass = new JButton("Pass");
        bet = new JButton("Bet");
        buyGirl = new JButton("Buy girl");
        buyGirl.addActionListener(this);
        bet.addActionListener(this);


        level2Items.add(drawCard);
        level2Items.add(pass);
        level2Items.add(bet);
        level2Items.add(buyGirl);
        level2Items.add(lastDrawnCards);

        cardsHolder = new JPanel(new CardLayout());
        card1 = new JButton("", cardToAdd);
        cardsHolder.add(card1);
        //Create the panel that contains the "cards".
        mainItemHolder = new JPanel(new CardLayout());
        mainItemHolder.add(level2Items);

        chickHolder = new JPanel(new CardLayout());
        girlImage = new JButton("", girl);
        chickHolder.add(girlImage);

       //cards.add(card2);

        pane.add(level1Items, BorderLayout.PAGE_START);
        pane.add(mainItemHolder, BorderLayout.CENTER);
       // pane.add(cardsHolder, BorderLayout.LINE_START);
        pane.add(chickHolder, BorderLayout.PAGE_END);

    }

    @Override
    public  void actionPerformed(java.awt.event.ActionEvent event){
        if (event.getSource() == drawCard){
            if (betAvaliable == true)
                beginGame();
            else
                JOptionPane.showMessageDialog(mainItemHolder, "You have to place a bet first!" );
        }
        else if (event.getSource() == buyGirl){

            if (girlNow >= 6){
                girlNow = 1;
            }
            girl = scaleImage(610, 650, this.getClass().getResource("res/chicks/" + girlNow + "_purple.jpg").getPath());
            girlImage.setIcon(girl);
            girlNow++;
        }
        else  if (event.getSource() == bet){
            if (betAvaliable == false){
                creditTotal-=50;
                creditsTotal.setText("Total credits: " + String.valueOf(creditTotal));
                betAvaliable = true;
            }
            else{
                JOptionPane.showMessageDialog(mainItemHolder, "There is already a bet!" );
            }
        }
    }

    public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout)(mainItemHolder.getLayout());
        cl.show(mainItemHolder, (String)evt.getItem());
    }


    private static java.util.List<String> getDeck() {
        String[] numbers = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        String[] color = {"\u2663", "\u2666", "\u2665", "\u2660"};
        java.util.List<String> thisDeck = new ArrayList<String>();

        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < color.length; j++) {
                thisDeck.add(numbers[i] + "" + color[j]);
            }
        }
        return thisDeck;
    }


    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    public static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Nai-experimentalnata versiq EVER");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        MainForm demo = new MainForm();
        demo.addComponentToPane(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
