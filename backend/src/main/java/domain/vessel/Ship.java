package domain.vessel;

import domain.Agent;
import domain.util.AgentType;
import domain.util.Carrier;
import domain.util.Coordinates;
import domain.world.Node;

import java.util.Collections;
import java.util.List;

/**
 * @author Oliver Lea
 */
public abstract class Ship extends Agent implements Carrier {

    private int capacity;
    private int load;

    private static final double DISTANCE_PER_TICK_MULTIPLIER = 0.1;
    private static final double MAXIMUM_ON_POINT_DISTANCE = 0.1;

    private List<Node> route;
    private Node next;
    private Coordinates positionUpdateVector;

    public Ship(AgentType agentType, Coordinates initialLoc, int capacity, int load) {
        super(agentType, initialLoc);
        this.capacity = capacity;
        this.load = load;
    }

    public void setRoute(List<Node> route) {
        this.route = route;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public int getLoad() {
        return load;
    }

    public void followRoute() {
        // check if we have reached the next waypoint
        // TODO figure out what a sensible distance is to be considered "on" the next waypoint
        if (this.next.getCoordinates().distance(this.getCoordinates()) < MAXIMUM_ON_POINT_DISTANCE) {

            // if the end of the route has been reached start a return trip
            // else set the next route point
            // TODO always a return trip?
            if (routeEndReached()) {
                startReturnTrip();
            } else {
                nextRouteStop();
            }

            // calculate new vector toward next waypoint
            calculatePositionUpdateVector();
        }

        // move toward next
        this.getCoordinates().add(this.positionUpdateVector);
    }

    private boolean routeEndReached() {
        return this.route.indexOf(this.next) == this.route.size()-1;
    }

    private void calculatePositionUpdateVector() {

        // calculate distance include direction in vector
        double xdiff = this.next.getCoordinates().getLatitude() - this.getCoordinates().getLatitude();
        double ydiff = this.next.getCoordinates().getLongitude() - this.getCoordinates().getLongitude();

        // normalise vector
        double length = this.next.getCoordinates().distance(this.getCoordinates());
        this.positionUpdateVector = new Coordinates(xdiff/length, ydiff/length);
        this.positionUpdateVector.mul(DISTANCE_PER_TICK_MULTIPLIER); // TODO figure out a good amount of travel per tick
    }

    public void nextRouteStop() {
        int current = this.route.indexOf(this.next);
        if (current != this.route.size()-1) {
            this.next = this.route.get(current+1);
        }
    }

    public void startReturnTrip() {
        Collections.reverse(route);
        this.next = route.get(0);
    }
}
