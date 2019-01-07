package ch7.s5;

import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.UntypedActor;
import akka.japi.Function;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

public class Supervisor extends UntypedActor {
    private static SupervisorStrategy strategy = new OneForOneStrategy(10, Duration.create(1, TimeUnit.MINUTES),
            new Function<Throwable, SupervisorStrategy.Directive>() {
                @Override
                public SupervisorStrategy.Directive apply(Throwable param) throws Exception {
                    if(param instanceof ArithmeticException){
                        System.out.println("meet ArithmeticException, just resume");
                        return SupervisorStrategy.resume();
                    }else if(param instanceof NullPointerException){
                        System.out.println("meet NullPointerException, restart");
                        return SupervisorStrategy.restart();
                    }else if(param instanceof  IllegalArgumentException){
                        return SupervisorStrategy.stop();
                    } else{
                        return SupervisorStrategy.escalate();
                    }
                }
            }
    );

    @Override
    public SupervisorStrategy supervisorStrategy(){
        return strategy;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof Props){
            getContext().actorOf((Props)message, "restartActor");
        }else{
            unhandled(message);
        }
    }
}
