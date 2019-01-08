package ch7.s12;

import akka.actor.UntypedActor;
import akka.transactor.Coordinated;
import scala.concurrent.stm.Ref;
import scala.concurrent.stm.japi.STM;

public class EmployeeActor extends UntypedActor {
    private Ref.View<Integer> count = (Ref.View<Integer>) STM.newRef(50);

    @Override
    public void onReceive(Object msg) throws Throwable {
        if(msg instanceof Coordinated){
            final Coordinated c = (Coordinated)msg;
            final int downCount = (Integer)c.getMessage();
            try{
                c.atomic(new Runnable(){
                    @Override
                    public void run(){
                        STM.increment(count, downCount);
                    }
                });
            }catch (Exception e){

            }
        }else if("GetCount".equals(msg)) {
            getSender().tell(count.get(), getSelf());
        }else{
            unhandled(msg);
        }
    }
}
