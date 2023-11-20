package SnakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    private int[] snakeXLength = new int[750];
    private int[] snakeYLength = new int[750];
    private int lengthOfSnake = 3;


   private int[] xPosition = {25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
   private int[] yPosition = {50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550};
   private Random random = new Random();

   int enemyXPosition;
   int enemyYPosition;

    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;

    private int score = 0;
    private int moves = 0;
    private boolean gameOver = false;

    private ImageIcon snaketitle = new ImageIcon("snaketitle.jpg");
    private ImageIcon leftMouth = new ImageIcon("leftmouth.png");
    private ImageIcon RightMouth = new ImageIcon("rightmouth.png");
    private ImageIcon upMouth = new ImageIcon("upmouth.png");
    private ImageIcon downMouth = new ImageIcon("downmouth.png");
    private ImageIcon snakeImage = new ImageIcon("snakeimage.png");
    private ImageIcon enemy = new ImageIcon("enemy.png");

    private Timer timer;
    int delay = 100;


    public GamePanel () {

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);

        timer = new Timer(delay,this);
        timer.start(); // it calls actionListener method after every 100 millisecond.

        newEnemy();

   }


    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.WHITE);
        g.drawRect(24,10,851,55);
        g.drawRect(24,74,851,576);
        snaketitle.paintIcon(this,g,25,11);

        g.setColor(Color.BLACK);
         g.fillRect(25,75,850,575);
//-----------------  Drawing snake Length   --------------------

        if (moves==0){
            snakeXLength[0] = 100;
            snakeXLength[1] = 75;
            snakeXLength[2] = 50;

            snakeYLength[0] = 100;
            snakeYLength[1] = 100;
            snakeYLength[2] = 100;

            moves++;
        }
//-------------------------------------------------------------

        if (left){
           leftMouth.paintIcon(this,g,snakeXLength[0],snakeYLength[0]);
        }
        if (right){
            RightMouth.paintIcon(this,g,snakeXLength[0],snakeYLength[0]);
        }
        if (up){
            upMouth.paintIcon(this,g,snakeXLength[0],snakeYLength[0]);
        }
        if (down){
            downMouth.paintIcon(this,g,snakeXLength[0],snakeYLength[0]);
        }

        for (int i=1;i<lengthOfSnake;i++){
            snakeImage.paintIcon(this,g,snakeXLength[i],snakeYLength[i]);
        }

        enemy.paintIcon(this,g,enemyXPosition,enemyYPosition);

        if (gameOver){

            g.setColor(Color.WHITE);
            g.setFont(new Font("Aerial",Font.BOLD,50));
            g.drawString("Game Over",300,300);

            g.setFont(new Font("Aerial",Font.PLAIN,20));
            g.drawString("Press SPACE to Restart",320,350);

         }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Aerial",Font.PLAIN,14));
        g.drawString("Score : "+score,750,30);
        g.drawString("Length : "+lengthOfSnake,750,50);

        g.dispose();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        for (int i = lengthOfSnake-1;i>0;i--){
            snakeXLength[i]=snakeXLength[i-1];
            snakeYLength[i]=snakeYLength[i-1];
        }

        if (left){
            snakeXLength[0] = snakeXLength[0]-25;
        }
        if (right){
            snakeXLength[0] = snakeXLength[0]+25;
        }
        if (up){
            snakeYLength[0] = snakeYLength[0]-25;
        }
        if (down){
            snakeYLength[0] = snakeYLength[0]+25;
        }
        
        if (snakeXLength[0]>850){
            snakeXLength[0]=25;
        }
        if (snakeXLength[0]<25){
            snakeXLength[0]=850;
        }
        if (snakeYLength[0]>625){
            snakeYLength[0]=75;
        }
        if(snakeYLength[0]<75){
            snakeYLength[0]=625;
        }

        collidesWithEnemy();
        collidesWithBody();

        repaint();

    }
  //-------------------------------------------------------------------------

    @Override
    public void keyPressed(KeyEvent KE) {

        if (KE.getKeyCode()==KeyEvent.VK_SPACE){
            restart();
        }

        if (KE.getKeyCode()== KeyEvent.VK_LEFT && (!right)){
            left = true;
            right = false;
            up = false;
            down = false;
        }
        if (KE.getKeyCode()==KeyEvent.VK_RIGHT && (!left)){
            right = true;
            left = false;
            up = false;
            down = false;
        }
        if (KE.getKeyCode()==KeyEvent.VK_UP && (!down)){
            right = false;
            left = false;
            up = true;
            down = false;
        }
        if (KE.getKeyCode()==KeyEvent.VK_DOWN && (!up)){
            right = false;
            left = false;
            up = false;
            down = true;
        }

    }


    @Override
    public void keyTyped(KeyEvent kE) {
    }

    @Override
    public void keyReleased(KeyEvent KE) {

    }

    public void newEnemy(){
        enemyXPosition = xPosition[random.nextInt(0,31)];
        enemyYPosition = yPosition[random.nextInt(0,20)];

        for (int i=lengthOfSnake-1;i>=0;i--){
            if (snakeXLength[i]==enemyXPosition && snakeYLength[i]==enemyYPosition){
                newEnemy();
            }
        }

    }
    public void collidesWithEnemy(){
        if (snakeXLength[0]==enemyXPosition&&snakeYLength[0]==enemyYPosition){
            newEnemy();
            lengthOfSnake++;
            score++;
        }
    }
   public void collidesWithBody(){
        for (int i = lengthOfSnake-1;i>0;i--){
            if (snakeXLength[i]==snakeXLength[0]&&snakeYLength[i]==snakeYLength[0]){
                timer.stop();
                gameOver = true;
            }
        }
   }
   public void restart(){
        gameOver = false;
        moves = 0;
        score = 0;
        lengthOfSnake = 3;
        left = false;
        right = true;
        up = false;
        down = false;
        timer.start();
        repaint();
        newEnemy();//--------

   }


}
