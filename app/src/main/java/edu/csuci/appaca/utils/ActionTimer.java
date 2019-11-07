package edu.csuci.appaca.utils;

public class ActionTimer {


    private float time;

    private float timer;

    private ActionTimerEvent actionTimerEvent;

    private ActionTimerMode timerMode;

    private boolean hasRun;


    private static final ActionTimerMode DEFAULT_MODE = ActionTimerMode.RUN_ONCE;

    public ActionTimer(float timer) {
        this(timer, DEFAULT_MODE, null);
    }


    public ActionTimer(float timer, ActionTimerEvent actionTimerEvent) {
        this(timer, DEFAULT_MODE, actionTimerEvent);
    }


    public ActionTimer(float timer, ActionTimerMode timerMode) {
        this(timer, timerMode, null);
    }

    public ActionTimer(float timer, ActionTimerMode timerMode, ActionTimerEvent actionTimerEvent) {
        this.timer = timer;
        this.reset();
        this.timerMode = timerMode;
        setActionTimerEvent(actionTimerEvent);
        this.hasRun = false;
    }


    public void reset() {
        this.time = 0;
    }


    public void setActionTimerEvent(ActionTimerEvent actionTimerEvent) {
        this.actionTimerEvent = actionTimerEvent;
    }


    public float timeLeft() {
        float timeLeft;
        if (this.timerMode.equals(ActionTimerMode.RUN_ONCE) && hasRun) {
            timeLeft = 0;
        } else {
            timeLeft = timer - time;
        }
        return timeLeft;
    }


    public void update(float dt) {
        time += dt;
        if (time >= timer) {
            if (!(this.timerMode.equals(ActionTimerMode.RUN_ONCE) && hasRun)) {
                if (actionTimerEvent != null) {
                    this.actionTimerEvent.action();
                    this.hasRun = true;
                } else {
                    throw new NullActionTimerEventException();
                }
            }
            this.reset();
        }
    }

    public void setTimer(float timer) {
        this.timer = timer;
    }

    public enum ActionTimerMode {
        RUN_ONCE, RUN_CONTINUOUSLY
    }


    public interface ActionTimerEvent {

        void action();
    }

    public class NullActionTimerEventException extends RuntimeException {
        public NullActionTimerEventException() {
            super("The Action Timer Event for this Action Timer has not been set.");
        }
    }

}
