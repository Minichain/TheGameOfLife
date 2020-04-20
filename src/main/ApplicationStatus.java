package main;

public class ApplicationStatus {
    private static Status status;
    private static long RUNTIME = 0;

    public enum Status {
        RUNNING, PAUSED, STOPPED
    }

    public static Status getStatus() {
        return status;
    }

    public static void setStatus(Status status) {
        ApplicationStatus.status = status;
    }

    public static long getRuntime() {
        return RUNTIME;
    }

    public static void setRuntime(long runtime) {
        ApplicationStatus.RUNTIME = runtime;
    }
}
