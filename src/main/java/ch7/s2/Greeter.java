package ch7.s2;

import akka.actor.UntypedActor;

public class Greeter extends UntypedActor {
    public static enum Msg{
        GREET, DONE;
    }
    @Override
    public void onReceive(Object msg) {
        if(msg == Msg.GREET){
            System.out.println("Hello World!");
            /*
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            */
            getSender().tell(Msg.DONE, getSelf());
        }else{
            unhandled(msg);
        }
    }
}
