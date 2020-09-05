
package brickbreaker;

import javax.swing.JFrame;
/**
 *
 * @author ACHAL KUMAR RAI
 */
public class Main {

    public static void main(String[] args) {
        JFrame obj = new JFrame();
        Game gamePlay = new Game();
        obj.setBounds(50, 10, 700, 600);
        obj.setTitle("Brick Breaker");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gamePlay);
    }
    
}
