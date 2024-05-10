import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class gamepanel extends JPanel implements ActionListener {
    static final int SCREEN_WITDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 20;
    static final int GAME_UNITS = (SCREEN_WITDTH * SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 90;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;
    int appleEaten;
    int appleX;
    int appleY;
    int bombX;
    int bombY;
    int HIGHSCORE;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    gamepanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WITDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new Mykeyadapter());
        startgame();
    }
    public void startgame(){
        newapple();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();


    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);

    }
    public void draw(Graphics g){
        if(running) {
            /*
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WITDTH, i * UNIT_SIZE);
            }
            */
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
            g.setColor(Color.blue);
            g.fillOval(bombX,bombY,UNIT_SIZE,UNIT_SIZE);
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));

                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free",Font.BOLD,30));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("SCORE :" + appleEaten,(SCREEN_WITDTH - metrics.stringWidth("SCORE :" + appleEaten))/2,g.getFont().getSize());
        }
        else{
            gameover(g);
        }

    }
    public void checkcollisons(){
        //check for body collisions.
        for(int i = bodyParts;i > 0;i--){
            if ((x[0] == x[i]) && (y[0] == y[i])){
                running = false;
            }
        }
        //check for borders collisions.
        if(x[0] < 0)
        {
            x[0] = SCREEN_WITDTH;
        }
        else if (x[0] > SCREEN_WITDTH) {
            x[0] = 0;
        }
        else if(y[0] < 0){
            y[0] = SCREEN_HEIGHT;
        }
        else if(y[0] > SCREEN_HEIGHT){
            y[0] = 0;
        }

        if (!running){
            timer.stop();
        }
    }
    public void checkforapples(){
        if ((x[0] == appleX) && (y[0] == appleY)){
            bodyParts++;
            appleEaten++;
            newapple();
        }
    }
    public void checkforbombs(){
        if ((x[0] == bombX) && (y[0] == bombY)){
            bodyParts--;
            newbomb();
        }
    }
    public void move(){
        for (int i = bodyParts;i>0;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch (direction){
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }
    public void newapple(){
        appleX = random.nextInt((int)(SCREEN_WITDTH/UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE)) * UNIT_SIZE;

    }
    public void newbomb(){
        bombX = random.nextInt((int)(SCREEN_WITDTH/UNIT_SIZE)) * UNIT_SIZE;
        bombY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE)) * UNIT_SIZE;
    }

    public void gameover(Graphics g){
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,30));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("SCORE :" + appleEaten,(SCREEN_WITDTH - metrics1.stringWidth("SCORE :" + appleEaten))/2,g.getFont().getSize());
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("YOU LOOSE",(SCREEN_WITDTH - metrics.stringWidth("YOU LOOSE"))/2,SCREEN_HEIGHT/2);
    }
    public void actionPerformed(ActionEvent e){
        if(running){
            move();
            checkforapples();
            checkforbombs();
            checkcollisons();
        }
        repaint();
    }
    public class Mykeyadapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent .VK_LEFT :
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent .VK_RIGHT :
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent .VK_UP:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;

                case KeyEvent .VK_DOWN :
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
