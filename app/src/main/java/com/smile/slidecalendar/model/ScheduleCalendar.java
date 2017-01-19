package com.smile.slidecalendar.model;

/**
 * @author Smile Wei
 * @since 2017/1/13.
 */
public class ScheduleCalendar {

    private int d;

    private int load_tasks;

    private int unload_tasks;

    public ScheduleCalendar(int d, int load_tasks, int unload_tasks){
        this.d = d;
        this.load_tasks = load_tasks;
        this.unload_tasks = unload_tasks;
    }


    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

    public int getLoad_tasks() {
        return load_tasks;
    }

    public void setLoad_tasks(int load_tasks) {
        this.load_tasks = load_tasks;
    }

    public int getUnload_tasks() {
        return unload_tasks;
    }

    public void setUnload_tasks(int unload_tasks) {
        this.unload_tasks = unload_tasks;
    }
}
