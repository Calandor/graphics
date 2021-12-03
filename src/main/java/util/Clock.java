package util;

import static org.lwjgl.glfw.GLFW.*;

public class Clock {
    private double time;
    private double dt;

    private double elapsed;
    private double lastTickTimeUpdate;
    private double tickTime;
    private double tickTimeUpdateInterval;
    private int updatesSinceTickTimeUpdate;

    public Clock(double tickTimeUpdateInterval){
        this.tickTimeUpdateInterval = tickTimeUpdateInterval;
        time = -1;
        elapsed = 0;
        lastTickTimeUpdate = -tickTimeUpdateInterval;
        tickTime = 0;
        dt = 0;
    }

    public Clock(){
        new Clock(0.2);
    }

    public void update(){
        double t = glfwGetTime();
        if(time == -1) {
            time = t;
            return;
        }
        dt = t - time;
        elapsed += dt;
        time = t;
        updatesSinceTickTimeUpdate++;

        if (time - lastTickTimeUpdate >= tickTimeUpdateInterval) {
            tickTime = elapsed / updatesSinceTickTimeUpdate;
            lastTickTimeUpdate = time;
            updatesSinceTickTimeUpdate = 0;
            elapsed = 0;
        }
    }

    public double time(){ return time;}

    public double dt(){ return dt;}

    public double tickTime(){ return tickTime;}

}
