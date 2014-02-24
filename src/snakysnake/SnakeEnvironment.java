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

        this.poisonBottles = new ArrayList<Point>();
        this.poisonBottles.add(new Point(20, 20));
        this.poisonBottles.add(new Point(1, 4));


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

                if (this.apples != null) {
                    for (int i = 0; i < this.poisonBottles.size(); i++) {
//                        this.apples.get(i);
                        GraphicsPalette.drawPoisonBottle(graphics, this.grid.getCellPosition(this.poisonBottles.get(i)), this.grid.getCellSize(), Color.yellow);
                    }
                }

            }
            graphics.setFont(new Font("Calibri", Font.BOLD, 60));
            graphics.drawString("Score: " + this.score, 50, 50);

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
            graphics.drawString("Game Over!", 500, 500);
        }
    }

    private void checkSnakeIntersection() {
        //If the snake location is the same as any apple location.
        //Then grow the snake and remove the apple.
        //Later, move apple and make a sound and increase the score

        for (int i = 0; i < this.apples.size(); i++) {
            if (snake.getHead().equals(this.apples.get(i))) {
                this.snake.addGrowthcounter(moveCounter);
                System.out.println("Apple Chomp!!!!");
                this.apples.get(i).setLocation(getRandomGridLocation());
//                this.snake.get(i).setLocation(getRandomGridLocation());
            }
        }
    }

    private Point getRandomGridLocation() {
        return new Point((int) (Math.random() * this.grid.getColumns()), (int) (Math.random() * this.grid.getRows()));
    }
}
