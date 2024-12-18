package pbl.janai;

import java.util.Random;

public class PremiumUser extends Thread{
    private JanAi janai;
    private Random rand;
    private int action;
    public PremiumUser(JanAi janai, int i){
        super("Premium " + i);
        this.janai = janai;
        this.rand = new Random();
        this.action = 0;
    }

    @Override
    public void run(){
        while(!this.isInterrupted()){
            try {
                Thread.sleep(rand.nextInt(2000));//Not using the app
                //Login and start using it, randomly choose what to do
                janai.login_user();
                login();
                Thread.sleep(rand.nextInt(1000));
                janai.logoff_user();
                logoff();
            }catch (IllegalStateException ie){
                System.out.println(super.getName() + ": Can't login " + ie.getMessage());
            } catch (InterruptedException e) {
                // TODO: handle exception
                this.interrupt();
            }
        }
    }

    public void login() throws InterruptedException{
        System.out.println(super.getName() + " has succesfully logged in!!!");;
    }

    public void logoff() throws InterruptedException{
        System.out.println(super.getName() + " has logged of :(");
    }
}
