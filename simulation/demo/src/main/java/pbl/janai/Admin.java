package pbl.janai;

import java.util.Random;

public class Admin extends Thread{
    private JanAi janai;
    private Random rand;
    public Admin(JanAi janai, int i){
        super("Admin " + i);
        this.janai = janai;
        rand = new Random();
    }

    @Override
    public void run(){
        while(!this.isInterrupted()){
            try {
                Thread.sleep(rand.nextInt(10000, 15000));//Not using the app
                //Login and start using it, start maintenance in the server
                janai.login_user();
                login();
                Thread.sleep(5000);
                janai.undergo_maintenance();
                start_maintenance();
                Thread.sleep(1000);
                janai.finishMaintenance();
                end_maintenance();
                janai.logoff_user();
                logoff();
                

            } catch (InterruptedException e) {
                // TODO: handle exception
                this.interrupt();
            }
        }
    }

    public void login() throws InterruptedException{
        System.out.println(super.getName() + " has succesfully logged in!!!");;
    }

    public void start_maintenance() throws InterruptedException{
        System.out.println(super.getName() + " has started the servers maintenance.");
    }

    public void end_maintenance() throws InterruptedException{
        System.out.println(super.getName() + " finished with the servers maintenance.");
    }

    public void logoff() throws InterruptedException{
        System.out.println(super.getName() + " has logged of :(");
    }    

}
