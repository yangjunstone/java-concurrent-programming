package ch7.s10;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import ch7.s7.MyWorker;

public class Printer extends UntypedActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object msg) throws Exception {
        if(msg instanceof Integer){
            log.info("Printer:"+msg);
        }
        if(msg == MyWorker.Msg.DONE){
            log.info("Stop working");
        }if(msg == MyWorker.Msg.CLOSE){
            log.info("I will shutdown");
            getSender().tell(MyWorker.Msg.CLOSE, getSelf());
            getContext().stop(getSelf());
        }else{
            unhandled(msg);
        }
    }
}
