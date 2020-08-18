package snakepackage;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import enums.GridSize;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author jd-
 */
public class SnakeApp {

    private static SnakeApp app;
    public static final int MAX_THREADS =16;
    Snake[] snakes = new Snake[MAX_THREADS];
    private Snake firstBlood = null;
    private int idLongestSnake=0;
    private static AtomicInteger firstBloodId=new AtomicInteger(-1);
    private static final Cell[] spawn = {
            new Cell(1, (GridSize.GRID_HEIGHT / 2) / 2),
            new Cell(GridSize.GRID_WIDTH - 2,
                    3 * (GridSize.GRID_HEIGHT / 2) / 2),
            new Cell(3 * (GridSize.GRID_WIDTH / 2) / 2, 1),
            new Cell((GridSize.GRID_WIDTH / 2) / 2, GridSize.GRID_HEIGHT - 2),
            new Cell(1, 3 * (GridSize.GRID_HEIGHT / 2) / 2),
            new Cell(GridSize.GRID_WIDTH - 2, (GridSize.GRID_HEIGHT / 2) / 2),
            new Cell((GridSize.GRID_WIDTH / 2) / 2, 1),
            new Cell(3 * (GridSize.GRID_WIDTH / 2) / 2,
                    GridSize.GRID_HEIGHT - 2)};
    private JFrame frame;
    private static Board board;
    int nr_selected = 0;
    Thread[] thread = new Thread[MAX_THREADS];
    private boolean areRunning=false;

    public SnakeApp() {

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame = new JFrame("The Snake Race");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setSize(618, 640);
        frame.setSize(GridSize.GRID_WIDTH * GridSize.WIDTH_BOX + 300,
                GridSize.GRID_HEIGHT * GridSize.HEIGH_BOX + 40);
        frame.setLocation(dimension.width / 2 - frame.getWidth() / 2,
                dimension.height / 2 - frame.getHeight() / 2);
        board = new Board();


        frame.add(board, BorderLayout.CENTER);

        JPanel actionsBPabel = new JPanel();

        actionsBPabel.setLayout(new FlowLayout());
        JButton start = new JButton("Start ");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.startGame();
            }
        });
        actionsBPabel.add(start);
        JButton pause = new JButton("Pause ");
        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mensaje=(firstBloodId.get()==-1)?"No snake is dead so far.":"The worst snake:(the first snake  dead) Snake #"+Integer.toString(firstBloodId.get());
                System.out.println(firstBloodId.get());
                board.setMessages("The Longest Snake: "+Integer.toString(idLongestSnake),mensaje);

                app.pauseGame();



            }
        });
        actionsBPabel.add(pause);
        JButton resume = new JButton("Resume ");
        resume.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.setMessages("","");
                app.resumeGame();
            }
        });
        actionsBPabel.add(resume);

        frame.add(actionsBPabel, BorderLayout.SOUTH);


    }

    public static void main(String[] args) {
        app = new SnakeApp();
        app.prepararElementos();
        app.init();
    }

    private void prepararElementos(){
        for (int i = 0; i != MAX_THREADS; i++) {
            snakes[i] = new Snake(i + 1, spawn[i%8], i + 1,firstBloodId);
            snakes[i].addObserver(board);
            thread[i] = new Thread(snakes[i]);
        }
        frame.setVisible(true);


    }
    private void startGame(){
        if (!areRunning){
            areRunning=true;
            for (int i = 0; i != MAX_THREADS; i++) {
                System.out.println(thread[i].isAlive());

                thread[i].start();

            }

        }

    }
    private void pauseGame(){
        int a = 0;
        for (int i = 0; i < 100; i++) {
            a+=i;

        }
        for (int i = 0; i != MAX_THREADS; i++) {
            snakes[i].dormir();
        }

    }
    private void resumeGame(){
        for (int i = 0; i != MAX_THREADS; i++) {
            snakes[i].despertar();
        }
    }
    private int largestSnake(){
        int max = snakes[0].getGrowing();
        int idLongestSnake = snakes[0].getIdt();
        for (int i = 1; i != MAX_THREADS; i++) {
            if (snakes[i].getGrowing() > max){
                max = snakes[i].getGrowing();
                idLongestSnake = snakes[i].getIdt();

            }

        }
        return idLongestSnake;
    }
    private void init() {

        while (true) {
            int x = 0;
            for (int i = 0; i != MAX_THREADS; i++) {
                //if (snakes[i].isSnakeEnd() == true && x==0 ) {
                  //  firstBlood = snakes[i];
                //}
                if (snakes[i].isSnakeEnd() == true) {
                    x++;
                }
                idLongestSnake = largestSnake();

            }
            if (x == MAX_THREADS) {
                break;
            }
        }

        String mensaje=(firstBloodId.get()==-1)?"No snake is dead so far.":"The worst snake:(the first snake  dead) Snake #"+Integer.toString(firstBloodId.get());
        board.setMessages("The Longest Snake: "+Integer.toString(idLongestSnake),mensaje);
        System.out.println("Thread (snake) status:");

        for (int i = 0; i != MAX_THREADS; i++) {
            System.out.println("[" + i + "] :" + thread[i].getState());
            //System.out.println(thread[i].isAlive());
        }


    }

    public static SnakeApp getApp() {
        return app;
    }

}
