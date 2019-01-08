package ch7.s11;

import akka.actor.UntypedAbstractActor;
import akka.dispatch.Mapper;
import scala.concurrent.Future;

public class CounterActor  extends UntypedAbstractActor {
    Mapper addMapper = new Mapper<Integer, Integer>() {
        @Override
        public Integer apply(Integer i) {
            return i+1;
        }
    };

    @Override
    public void onReceive(Object msg) throws Throwable {
        if(msg instanceof Integer){
            for(int i=0; i<10000; i++){
                //我希望能够知道future何时结束
                Future<Integer> f = AgentDemo.counterAgent.alter(addMapper);
                AgentDemo.futures.add(f);
            }
            getContext().stop(getSelf());
        }else{
            unhandled(msg);
        }
    }
}
