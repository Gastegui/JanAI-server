package pbl.janai;

import java.util.Random;

public class PremiumUser extends Thread{
    private JanAi janai;
    private Random rand;
    private int action;
    private UserMacros macros;
    private int mealsRegistered;

    public PremiumUser(JanAi janai, int i){
        super("Premium " + i);
        this.janai = janai;
        this.rand = new Random();
        this.action = 0;
        this.mealsRegistered = 0;
        this.macros = new UserMacros(rand.nextInt(1800, 2600),
                                    rand.nextInt(235, 335),
                                    rand.nextInt(50,83),
                                    rand.nextInt(28, 42),
                                    rand.nextInt(60, 180));
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
                if(mealsRegistered < 3){
                    updateProgress();
                    checkProfile();
                    mealsRegistered++;
                Thread.sleep(500);
                }
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
    
    public void checkProfile(){
        System.out.println(super.getName() + " Is checking it's daily progress:");
        System.out.println("-------------------" + super.getName() + "----------------------");
        System.out.println(macros);
        System.out.println("--------------------------------------------------");
        
    }
    public void updateProgress(){
        System.out.println(super.getName() + " Is updating it's progress.");
        macros.setConsumedCalories(macros.getConsumedCalories() + rand.nextInt(600, 900));
        macros.setConsumedCarbs(macros.getConsumedCarbs() + rand.nextInt(52, 68));
        macros.setConsumedFats(macros.getConsumedFats() + rand.nextInt(17, 28));
        macros.setConsumedFiber(macros.getConsumedFiber() + rand.nextInt(8, 16));
        macros.setConsumedProteins(macros.getConsumedProteins() + rand.nextInt(15, 70));
    }

}
