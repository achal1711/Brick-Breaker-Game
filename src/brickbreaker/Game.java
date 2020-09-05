
package brickbreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import javax.swing.JPanel;
import java.util.Random;

public class Game extends JPanel implements KeyListener, ActionListener{
    Random rand = new Random();
    private boolean play = false;
    private int score = 0;
    private int level = 1;
    
    private int totalBricks = 21;
    
    private Timer timer;
    private int delay = 8;
    
    private int playerX = 310;
    private int ballposX = rand.nextInt(350);
    private int ballposY = rand.nextInt(600);
    private int ballXdir = -1;
    private int ballYdir = -2;
    
    private MapGenerator map;
    
    public Game()
    {
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }
    
    public void paint(Graphics g)
    {
        g.setColor(Color.black);
        g.fillRect(1,1, 692, 592);
        
        map.draw((Graphics2D)g);
        
        g.setColor(Color.blue);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);
        
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + score, 590, 30);
        
        g.setColor(Color.yellow);
        g.fillRect(playerX, 550, 100, 8);
        
        g.setColor(Color.yellow);
        g.fillOval(ballposX, ballposY, 20, 20);
        
        if(totalBricks <= 0)
        {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 25));
            g.drawString("Level " + (level - 1) + " Cleared " +  "Your Score is: " + score, 190, 300);
            
            g.setFont(new Font("serif", Font.BOLD, 25));
            g.drawString("Press Enter to for next level" , 230, 350);
        }
        
        if(ballposY > 570){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 25));
            g.drawString("Game Over, Your Score is: " + score, 190, 300);
            
            g.setFont(new Font("serif", Font.BOLD, 25));
            g.drawString("Press Enter to restart" , 230, 350);
        }
        
        g.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            if(playerX >= 597){
                playerX = 597;
            }
            else{
                moveRight();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            if(playerX <= 3){
                playerX = 3;
            }
            else{
                moveLeft();
            }
        }
        
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            //Game Over
            if(!play && level == 1){
                play = true;
                ballposX = rand.nextInt(350);
                ballposY = rand.nextInt(600);
                ballXdir = -1;
                ballYdir = -2;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                map = new MapGenerator(3, 7);
                repaint();
            }
            //Level 2
            else if(!play && level == 2){
                play = true;
                ballposX = rand.nextInt(350);
                ballposY = rand.nextInt(600);
                ballXdir = -1;
                ballYdir = -2;
                playerX = 310;
                totalBricks = 28;
                map = new MapGenerator(4, 7);
                repaint();
            }
            else if(!play && level == 3){
                play = true;
                ballposX = rand.nextInt(350);
                ballposY = rand.nextInt(600);
                ballXdir = -1;
                ballYdir = -2;
                playerX = 310;
                totalBricks = 35;
                map = new MapGenerator(5, 7);
                repaint();
            }
        }
    }
    
    public void moveRight()
    {
        play = true;
        playerX += 20;
    }
    
    public void moveLeft()
    {
        play = true;
        playerX -= 20;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       timer.start();
       if(play){
           if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8)))
           {
               ballYdir = -ballYdir;
           }
               for(int i = 0; i < map.map.length; i++)
               {
               A : for(int j = 0; j < map.map[0].length; j++){
                   if(map.map[i][j]> 0){
                       int brickX = j * map.brickWidth + 80;
                       int brickY = i * map.brickHeight + 50;
                       int brickWidth = map.brickWidth;
                       int brickHeight = map.brickHeight;
                       
                       Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                       Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
                       Rectangle brickRect = rect;
                       
                       if(ballRect.intersects(brickRect)){
                           map.setBrickValue(0, i, j);
                           totalBricks--;
                           score += 5;
                           if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width){
                               ballXdir = -ballXdir;
                           }
                           else{
                               ballYdir = -ballYdir;
                           }
                           if(totalBricks == 0){
                               level++;
                           }
                           break A;
                       }
                   }
               }
           }
           ballposX += ballXdir;
           ballposY += ballYdir;
           if(ballposX < 0){
               ballXdir = -ballXdir;
           }
           if(ballposY < 0){
               ballYdir = -ballYdir;
           }
           if(ballposX > 670){
               ballXdir = -ballXdir;
           }
       }
       repaint();
    }
    
}
