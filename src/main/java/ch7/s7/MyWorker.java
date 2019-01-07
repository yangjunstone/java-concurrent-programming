package ch7.s7;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class MyWorker extends UntypedActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    public static enum Msg{
        WORKING, DONE, CLOSE;
    }

    @Override
    public void onReceive(Object msg) throws Exception {
        switch ((Msg)msg){
            case WORKING:
                log.info("I am working");
                break;
            case DONE:
                log.info("Stop working");
                break;
            case CLOSE:
                log.info("I will shutdown");
                getSender().tell(Msg.CLOSE, getSelf());
                getContext().stop(getSelf());
                break;
                default:
                    unhandled(msg);
                    break;
        }
    }
}
