package pbl.janai;

import java.util.Random;

public class NormalUser extends Thread{
    private JanAi janai;
    private Random rand;
    public NormalUser (JanAi janai, int i){
        super("User " + i);
        this.janai = janai;
        this.rand = new Random();
    }

    @Override
    public void run(){
        while(!this.isInterrupted()){
            try {
                Thread.sleep(rand.nextInt(2000));//Not using the app
                //Login and start using it, randomly choose what to do
                if(!janai.isMaintenanceOn()){
                    janai.login_user();
                    login();
                    Thread.sleep(rand.nextInt(1000));
                    janai.logoff_user();
                    logoff();
                }
            } catch (InterruptedException e) {
                // TODO: handle exception
                this.interrupt();
            }
        }
    }

    public void login() throws InterruptedException{
        System.out.println(super.getName() + " has succesfully logged in!!!");
    }

    public void logoff() throws InterruptedException{
        System.out.println(super.getName() + " has logged of :(");
    }

    //public void check_progress
    //public void check_status
    //public void calculate_calories
    //public void send_images
    //public void ask_nutrichat
}
