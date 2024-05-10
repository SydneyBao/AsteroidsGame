/***
 * The station is controlled by the j and k keys and is responsible for aiming the cannon. It also detects if an asteroid hits the station.
 */

import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;

class Station {

    //constructor sets x and y coordinates
    public Station (double ix, double iy) {
        x = ix;
        y = iy;
    }

    private double angle = Math.PI / 2.0; // public static final double PI 3.141592653589793d
    private int hits = 0; // So, angle is initialized to 90 degrees
    private final double x;
    private final double y;
    Timer timer = new Timer();
    TimerTask exit = new TimerTask() {
        public void run() {
            System.exit(0);
        }
    };

    //angles the cannon to the left
    public void moveLeft() {
        angle = angle + 0.1;
    }

    //angles the cannon to the right
    public void moveRight() {
        angle = angle - 0.1;
    }

    //Fires the rocket at a calculated angle, based on the cannon's angle
    public void fire (ArrayList rockets) {
        double cosAngle = Math.cos(angle);
        double sinAngle = Math.sin(angle);
        // rocket goes same direction as gun is pointing
        // length of Rocket Launcher is 20; size of rocket is 5; (20 – 5 = 15)
        Rocket r = new Rocket(x + 15 * cosAngle, y - 15 * sinAngle, 5 * cosAngle, - 5 * sinAngle);
        rockets.add(r);
    }

    // check if asteroid (rock) hit me (station) then hits is incremented by asteroid size. If the hit count exceeds 1000, the game terminates
    public void checkHit (Asteroid rock) {
        if (rock.nearTo((double) x, (double) y)) {
            hits += rock.size;

            if (hits >= 1000) {
                timer.schedule(exit, new Date(System.currentTimeMillis() + 0 * 1000));
            }
        }
    }

    //paints the cannon red at an angle, depending on the user's input with the j and k keys. Also, it displays an updated score.
    public void paint (Graphics g) {
        // paint rocket launcher (length 20 pixels)
        g.setColor (Color.red);
        double lv = 20 * Math.sin(angle); // launcher tip vertical coordinate
        double lh = 20 * Math.cos(angle); // launcher tip horizontal coordinate

        // (x, y) is launcher base, (x+lh, y-lv) is tip of launcher
        g.drawLine((int) x, (int) y, (int) (x + lh), (int) (y - lv));

        // display updated score
        g.drawString("hits: " + hits, (int) (x + 10), (int) (y - 5));
    }
}
