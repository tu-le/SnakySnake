/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package snakysnake;

import audio.AudioPlayer;
import environment.Environment;
import environment.GraphicsPalette;
import environment.Grid;
import image.ResourceTools;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *
 * @author Tom Le
 */
class SnakeEnvironment extends Environment {

    private GameState gameState = GameState.PAUSED;
    private Grid grid;
    private int score = 0;
    private Snake snake;
    private ArrayList<Point> apples;
    private ArrayList<Point> poisonBottles;
    private ArrayList<Point> bombs;
    private int speed = 2;
    private int moveCounter = speed;

    public SnakeEnvironment() {
    }

    @Override
    public void initializeEnvironment() {
        this.setBackground(ResourceTools.loadImageFromResource("resources/Snake Picture for CS.jpg"));
        this.grid = new Grid();
        this.grid.setColor(Color.BLUE);
        this.grid.setColumns(43);
        this.grid.setRows(27);
        this.grid.setCellHeight(20);
        this.grid.setCellWidth(20);
        this.grid.setPosition(new Point(50, 100));

        this.apples = new ArrayList<Point>();
        this.apples.add(new Point(getRandomGridLocation()));
        this.apples.add(new Point(getRandomGridLocation()));
        this.apples.add(new Point(getRandomGridLocation()));
        this.apples.add(new Point(getRandomGridLocation()));
        this.apples.add(new Point(getRandomGridLocation()));
        this.apples.add(new Point(getRandomGridLocation()));
        this.apples.add(new Point(getRandomGridLocation()));
        this.apples.add(new Point(getRandomGridLocation()));
        this.apples.add(new Point(getRandomGridLocation()));
        this.apples.add(new Point(getRandomGridLocation()));
        this.apples.add(new Point(getRandomGridLocation()));
        this.apples.add(new Point(getRandomGridLocation()));

        this.poisonBottles = new ArrayList<Point>();
        this.poisonBottles.add(new Point(getRandomGridLocation()));
        this.poisonBottles.add(new Point(getRandomGridLocation()));
        this.poisonBottles.add(new Point(getRandomGridLocation()));
        this.poisonBottles.add(new Point(getRandomGridLocation()));        
        
        
        this.bombs = new ArrayList<Point>();
        this.bombs.add(new Point(getRandomGridLocation()));
        this.bombs.add(new Point(getRandomGridLocation()));
        this.bombs.add(new Point(getRandomGridLocation()));
        this.bombs.add(new Point(getRandomGridLocation()));


        this.snake = new Snake();
        this.snake.getBody().add(new Point(5, 5));
        this.snake.getBody().add(new Point(5, 4));
        this.snake.getBody().add(new Point(5, 3));
        this.snake.getBody().add(new Point(4, 3));

        this.snake.getHead();
        
        AudioPlayer.play("/resources/I won't let you go.wav"); 
    }

    @Override
    public void timerTaskHandler() {
        if (this.gameState == GameState.RUNNING) {
            if (snake != null) {
                if (moveCounter <= 0) {
                    snake.move();
                    moveCounter = speed;
                    checkSnakeIntersection();
                    if (snake.selfHitTest()){
                        gameState = GameState.ENDED;
                    }
                } else {
                    moveCounter--;
                }
            } 
            if (snake.getHead().x < 0) {
                snake.getHead().x = grid.getColumns();
            } else if (snake.getHead().x > grid.getColumns()) {
                snake.getHead().x = 0;
            } else if (snake.getHead().y < 0) {
                snake.getHead().y = grid.getRows();
            } else if (snake.getHead().y > grid.getRows()) {
                snake.getHead().y = 0;
            }
        }




    }

    @Override
    public void keyPressedHandler(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                if (gameState == GameState.RUNNING) {
                    gameState = GameState.PAUSED;
                } else if (gameState == GameState.PAUSED) {
                    gameState = GameState.RUNNING;
                } 
            } else if (e.getKeyCode() == KeyEvent.VK_M) {
                    snake.move();
                } else if (e.getKeyCode() == KeyEvent.VK_S) {
                    snake.setDirection(Direction.DOWN);
                    snake.move();
                } else if (e.getKeyCode() == KeyEvent.VK_A) {
                    snake.setDirection(Direction.LEFT);
                    snake.move();
                } else if (e.getKeyCode() == KeyEvent.VK_W) {
                    snake.setDirection(Direction.UP);
                    snake.move();
                } else if (e.getKeyCode() == KeyEvent.VK_D) {
                    snake.setDirection(Direction.RIGHT);
                    snake.move();
                } else if (e.getKeyCode() == KeyEvent.VK_G) {
                    snake.setGrowthcounter(2);
                    snake.move(); 
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    gameState = GameState.ENDED;
                }
    }

    @Override
    public void keyReleasedHandler(KeyEvent e) {
    }

    @Override
    public void environmentMouseClicked(MouseEvent e) {
    }

    @Override
    public void paintEnvironment(Graphics graphics) {
        if (this.grid != null) {
            grid.paintComponent(graphics);

            if (this.apples != null) {
                for (int i = 0; i < this.apples.size(); i++) {
                    Point cellPosition = this.grid.getCellPosition(this.apples.get(i));
//                    this.apples.get(i);
                    GraphicsPalette.drawApple(graphics, this.grid.getCellPosition(this.apples.get(i)), this.grid.getCellSize());
                }
            }
            if (this.grid != null) {
                grid.paintComponent(graphics);

            if (this.poisonBottles != null) {
                for (int i = 0; i < this.poisonBottles.size(); i++) {
                    Point cellPosition = this.grid.getCellPosition(this.poisonBottles.get(i));
//                        this.apples.get(i);
                    GraphicsPalette.drawPoisonBottle(graphics, this.grid.getCellPosition(this.poisonBottles.get(i)), this.grid.getCellSize(), Color.yellow);
                 }
            }

            }
            graphics.setFont(new Font("Calibri", Font.BOLD, 60));
            graphics.drawString("Score: " + this.score, 50, 50);
            graphics.setFont(new Font("Calibri", Font.ITALIC, 30));
            graphics.drawString("Press Space to PLAY/PAUSE", 50, 90);

//        GraphicsPalette.drawApple(graphics, new Point(100,50), new Point(100,100));


            Point cellLocation;
            graphics.setColor(Color.blue);
            if (snake != null) {
                for (int i = 0; i < snake.getBody().size(); i++) {

                    if (i == 0) {
                        graphics.setColor(Color.yellow);
                    } else {
                        graphics.setColor(Color.red);
                    }

                    cellLocation = grid.getCellPosition(snake.getBody().get(i));
                    graphics.fillRect(cellLocation.x, cellLocation.y, grid.getCellWidth(), grid.getCellHeight());
                }
            }
        }
        if (gameState == GameState.ENDED) {
            graphics.setColor(Color.red);
            graphics.setFont(new Font("Calibri", Font.ITALIC, 100));
            graphics.drawString("Game Over!", 300, 300);
        }
    }
    
 
    
    private void checkSnakeIntersection() {
        //If the snake location is the same as any apple location.
        //Then grow the snake and remove the apple.
        //Later, move apple and make a sound and increase the score

        for (int i = 0; i < this.apples.size(); i++) {
            if (snake.getHead().equals(this.apples.get(i))) {
                this.gameState = GameState.RUNNING;
                this.snake.addGrowthcounter(moveCounter);
                System.out.println("Apple Chomp!!!!");
                this.apples.get(i).setLocation(getRandomGridLocation());
                this.setScore(this.getScore() + 50);
////                this.snake.get(i).setLocation(getRandomGridLocation());
//            } else if (snake.getHead() == (this.poisonBottles.get(i))){
//                this.gameState = GameState.ENDED;
            }
        }
    
        
//        for (int p = 0; p < this.poisonBottles.size(); p++){
//            if (snake.getHead().equals(this.poisonBottles.get(p)));
//                this.gameState = GameState.ENDED;
//            }
    }
    
    public int getScore() {
        return score;
    }
   
    public void setScore(int newScore) {
        this.score = newScore;
    }

//    private Point getRandomGridLocation() {
//        return new Point((int) (Math.random() * this.grid.getColumns()), (int) (Math.random() * this.grid.getRows()));
//    }
    
    public Point getRandomGridLocation() {
        //generate a new random point in the grid
        int x = (int) (Math.random() * grid.getColumns());
        int y = (int) (Math.random() * grid.getRows());

        Point randomPoint = new Point(x, y);
        
        
        //check the point, if the position is occupied, move it 
        //across the grid until you find an open point.
        for (int row = 0; row < grid.getRows(); row++) {
            for (int column = 0; column < grid.getColumns(); column++) {
                randomPoint.setLocation((x + row) % grid.getColumns(), (y + column) % grid.getRows());
                
                if (!locationOccupied(randomPoint)) {
                    return randomPoint;
                }
            }
        }
        return randomPoint;
    }
    
    private boolean locationOccupied(Point location){
        return apples.contains(location);
//        return poisonBottles.contains(location);

//        if you have many ArrayLists with different objects, you will need to 
//        check all of them with a statement as follows (assume we have "apples"
//        and "lollipops" and "bombs":
        
//        return (apples.contains(location) || poisonBottles.contains(location));
    }

}
