package pbl.janai;

import java.util.concurrent.Semaphore;


public class JanAi {
    private int nUsers;
    private final int MAX_USERS = 10;
    private boolean maintenanceOn;

    private Semaphore mutex;
    private Semaphore serverAccess;
    public JanAi() {
        this.nUsers = 0;
        this.maintenanceOn = false;
        this.mutex = new Semaphore(1);
        this.serverAccess = new Semaphore(MAX_USERS);
    }

    public void login_user() throws InterruptedException {
        serverAccess.acquire();
        mutex.acquire();
        try {
            if (maintenanceOn) {
                serverAccess.release();
                System.out.print("Ongoing maintenance, can't login");
            }
            nUsers++;
        } finally {
            mutex.release();
        }
    }

    public void logoff_user() {
        try {
            mutex.acquire();
            nUsers--;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            mutex.release();
            serverAccess.release();
        }
    }

    public void undergo_maintenance() throws InterruptedException {
        mutex.acquire();
        try {
            maintenanceOn = true;
            System.out.println("Starting maintenance, kicking users out.");
            nUsers = 0;
        } finally {
            mutex.release();
            serverAccess.drainPermits();
        }
    }

    public void finishMaintenance() {
        try {
            mutex.acquire();
            maintenanceOn = false;
            System.out.println("Maintenance finished.");
            serverAccess.release(MAX_USERS); // Restaurar capacidad máxima
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            mutex.release();
        }
    }

    public boolean isMaintenanceOn() {
        return maintenanceOn;
    }
}