/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package snakysnake;

import environment.ApplicationStarter;

/**
 *
 * @author Tom Le
 */
public class SnakySnake {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        start();
    }

    private static void start() {
        ApplicationStarter.run("SnakySnake", new SnakeEnvironment());
    }
}
