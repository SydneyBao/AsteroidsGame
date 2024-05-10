/***
 * Stores an ArrayList of rockets. Each rocket is given the location of all asteroids, so it can detect if an asteroid has been hit.
 * If so, the rocket notifies each of the asteroids that were hit.
 */

import java.awt.*;
import java.util.ArrayList;


class Rocket {
    public double x, y; // current position coordinates
    private double dx, dy; // idx & idy computed from canon direction


    //constructor sets x and y coordinates and the change in them
    public Rocket (double ix, double iy, double idx, double idy) {
        x = ix;
        y = iy;
        dx = idx;
        dy = idy;
    }

    //changes the x and y coordinates by dx and dy, respectively, causing the rocket to move to the next position
    //Loops through a list of asteroids, and if the rocket is nearby, it calls the hit method. This alerts the asteroid it has been hit.
    public void move (ArrayList asteroids) {
        x += dx;
        y += dy; // move ‘this’ rocket
        int i = 0;

        while(i < asteroids.size()) {
            Asteroid rock = (Asteroid) asteroids.get(i); // must (cast)
            if (rock.nearTo(x, y)) {
                rock.hit();
            }
            i++;
        }
    }

    //paints the rocket red at the x and y coordinates and 5 by 5 dimensions
    public void paint (Graphics g) {
        g.setColor(Color.red); // draw self: red circle inside
        g.fillOval((int) x, (int) y, 5, 5); // a 5 by 5 bounding rectangle
    } // (x, y) is the upper left corner

    //checks if rocket is within the playing window
    public boolean isWithin(int width, int height) {
        return(x >= 0 && x < width && y >= 0 && y < height);
    }
}