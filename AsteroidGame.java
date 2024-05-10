/***
 * Creates the graphical display and elements like the station, ArrayList of asteroids, ArrayList of rockets, and a listener to detect which keys are pressed.
 * Depending on which key is pressed, the game will quit, alert the station to move cannon, or falert to ire a rocket. It also repaints the window.
 */

import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class AsteroidGame extends Frame {
    private int FrameWidth = 500;
    private int FrameHeight = 400;

    //creates the space world and runs the program
    static public void main(String[] args) {
        AsteroidGame world = new AsteroidGame();
        world.setVisible(true);
        world.run();

    }

    //constructor that sets the frame size, connects to key and window listener to detect certain events, and starts timer
    public AsteroidGame() {
        setTitle("Asteroid Game");
        setSize(FrameWidth, FrameHeight);
        setSize(500, 400);
        addKeyListener(new keyDown());
        addWindowListener(new CloseQuit());
    }

    //moves pieces. Detects whether the rocket and asteroid is within the screen.
    // If they are, they get repainted. Otherwise, they are removed from the array.
    public void run() { // move pieces
        while (true) {
            movePieces();

            for (int i = 0; i < rockets.size(); i++) {
                Rocket r = (Rocket) rockets.get(i);

                if (r.isWithin(getWidth(), getHeight())) {
                    repaint();
                } else {
                    rockets.remove(i);
                }
            }

            for (int i = 0; i < asteroids.size(); i++) {
                Asteroid a = (Asteroid) asteroids.get(i);

                if (a.isWithin(getWidth(), getHeight())) {
                    repaint();
                } else {
                    asteroids.remove(i);
                }
            }

            try { // pause 100 milliseconds in order
                Thread.sleep(100); // to create animation illusion
            } catch (Exception e) {
            } // must be in try-catch
        } // more details later...
    }

    private ArrayList asteroids = new ArrayList<>();
    private ArrayList rockets = new ArrayList<>();
    // Station position middle of baseline
    private Station station = new Station(FrameWidth / 2, FrameHeight - 20);

    //calls paint method for each rocket and asteroid in the ArrayLists
    public void paint(Graphics g) {
        station.paint(g);
        int i = 0;

        while (i < asteroids.size()) {
            Asteroid rock = (Asteroid) asteroids.get(i); // must (cast)
            rock.paint(g);
            i++;
        }

        int j = 0;
        while (j < rockets.size()) {
            Rocket rock = (Rocket) rockets.get(j); // must (cast)
            rock.paint(g);
            j++;
        }
    }

    //creates an asteroid at a random position at a random time. Moves all asteroids and rockets.
    private void movePieces() {
        // create a random new asteroid – 30% of the time
        if (Math.random() < 0.3) {
            Asteroid newRock = new Asteroid(FrameWidth * Math.random(), 20, 10 * Math.random() - 5, 3 + 3 * Math.random());
            asteroids.add(newRock);
        }

        // then move everything
        int i = 0;
        while (i < asteroids.size()) {
            Asteroid rock = (Asteroid) asteroids.get(i);
            rock.move();
            station.checkHit(rock);
            i++;
        }

        int j = 0;
        while (j < rockets.size()) {
            Rocket rock = (Rocket) rockets.get(j);
            rock.move(asteroids);
            j++;
        }
    }

    //detects if certain keys are down to execute their corresponding methods.
    // "j" and "k" move the cannon left and right respectively, " " fires the rocket, and "q" quits the game.
    private class keyDown extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            char key = e.getKeyChar();

            switch (key) {
                case 'j':
                    station.moveLeft();
                    break; // turn left
                case 'k':
                    station.moveRight();
                    break; // turn right
                case ' ':
                    station.fire(rockets);
                    break; // space: fire
                case 'q':
                    System.exit(0); // q: quit
            }
        }
    }

    //allows the system time to catch up with running program. Acts as a buffer
    private class gameMover extends Thread {
        // override the run( ) method of Thread
        public void run() {
            while (true) {
                movePieces();
                repaint();

                try {
                    sleep(100);
                } catch (Exception e) {
                }
            }
        }
    }

    //closes window when CloseQuit() is called, which is when "q" is pressed
    private class CloseQuit extends WindowAdapter {
        public void windowClosing (WindowEvent e) {
            System.exit(0);
        }
    }

}