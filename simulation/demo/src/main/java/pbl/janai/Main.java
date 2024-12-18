package pbl.janai;


public class Main {
    private final int NUSERS = 20;
    private final int NPREMIUM = 5;
    private final int NADMINS = 1;

    private JanAi janai;
    private NormalUser[] users;
    private PremiumUser[] premiums;
    private Admin admin;

    public Main(){
        janai = new JanAi();
        users = new NormalUser[NUSERS];
        premiums = new PremiumUser[NPREMIUM];

        
    }

    public void initialize(){
        for(int i = 0; i < NUSERS; i++){
            users[i] = new NormalUser(janai, i);
        }
        for(int i = 0; i < NPREMIUM; i++){
            premiums[i] = new PremiumUser(janai, i);
        }
        admin = new Admin(janai, 1);
    }

    public void startThreads() {
        for (int i = 0; i < NUSERS; i++) {
            users[i].start();
        }
        for (int i = 0; i < NPREMIUM; i++) {
            premiums[i].start();
        }
        admin.start();
    }

    public void interruptThreads() {
        for (int i = 0; i < NUSERS; i++) {
            users[i].interrupt();
        }
        for (int i = 0; i < NPREMIUM; i++) {
            premiums[i].interrupt();
        }
        admin.interrupt();
    }

    public void waitEndOfThreads() {
        try {
            for (int i = 0; i < NUSERS; i++) {
                users[i].join();
            }
            for (int i = 0; i < NPREMIUM; i++) {
                premiums[i].join();
            }
            admin.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.initialize();
        app.startThreads();

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        app.interruptThreads();
        app.waitEndOfThreads();
    }
}