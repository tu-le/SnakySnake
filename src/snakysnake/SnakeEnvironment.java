/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package snakysnake;

import environment.Environment;
import environment.Grid;
import image.ResourceTools;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author Tom Le
 */
class SnakeEnvironment extends Environment {

    private Grid grid;
    private int score = 0;
    private Snake snake;
    
    private int delay = 5;
    private int moveCounter = delay;
    
    public SnakeEnvironment() {
    }

    @Override
    public void initializeEnvironment() {
        this.setBackground(ResourceTools.loadImageFromResource("resources/Snake Picture for CS.jpg"));
        this.grid = new Grid();
        this.grid.setColor(Color.BLUE);
        this.grid.setColumns(43);
        this.grid.setRows(27);
        this.grid.setCellHeight(10);
        this.grid.setCellWidth(10);
        this.grid.setPosition(new Point(50, 100));

        this.snake = new Snake();
        this.snake.getBody().add(new Point(5, 5));
        this.snake.getBody().add(new Point(5, 4));
        this.snake.getBody().add(new Point(5, 3));
        this.snake.getBody().add(new Point(4, 3));
        
        this.snake.getHead();
    }

    @Override
    public void timerTaskHandler() {
    }

    @Override
    public void keyPressedHandler(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            this.score += 557;
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
        }
        graphics.setFont(new Font("Calibri", Font.BOLD, 60));
        graphics.drawString("Score: " + this.score, 50, 50);

        Point cellLocation;
        graphics.setColor(Color.blue);
        if (snake != null) {
            for (int i = 0; i < snake.getBody().size(); i++) {
                
        if (i == 0){
            graphics.setColor(Color.yellow);
        } else {
            graphics.setColor(Color.red);
        }

                cellLocation = grid.getCellPosition(snake.getBody().get(i));
                graphics.fillRect(cellLocation.x, cellLocation.y, grid.getCellWidth(), grid.getCellHeight());
            }
        }
    }
}
