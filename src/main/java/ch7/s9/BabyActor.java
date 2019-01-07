package ch7.s9;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Procedure;

public class BabyActor extends UntypedActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().system(),this);
    public static enum Msg{
        SLEEP, PLAY, CLOSE;
    }

    Procedure<Object> angry = new Procedure<Object>() {
        @Override
        public void apply(Object message) throws Exception {
            log.info("angryApply:"+message);
            if(message == Msg.SLEEP){
                getSender().tell("I am already angry", getSelf());
                log.info("I am already angry");
            }else if(message == Msg.PLAY){
                log.info("I like palying");
                getContext().become(happy);
            }
        }
    };

    Procedure<Object> happy = new Procedure<Object>() {
        @Override
        public void apply(Object message) throws Exception {
            log.info("happyApply:"+message);
            if(message == Msg.PLAY){
                getSender().tell("I am already happy :-", getSelf());
                log.info("I am already happy :-");
            }else if(message == Msg.SLEEP){
                log.info("I don't want to sleep");
                getContext().become(angry);
            }
        }
    };

    @Override
    public void onReceive(Object msg) throws Exception {
        log.info("onReceive:"+msg);
        if(msg == Msg.SLEEP){
            getContext().become(angry);
        }else if(msg == Msg.PLAY){
            getContext().become(happy);
        }else{
            unhandled(msg);
        }
    }
}
