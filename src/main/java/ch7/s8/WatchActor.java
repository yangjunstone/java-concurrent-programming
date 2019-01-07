package ch7.s8;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.*;
import ch7.s7.MyWorker;

import java.util.ArrayList;
import java.util.List;

public class WatchActor extends UntypedActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    public Router router;
    {
        List<Routee> routees = new ArrayList<Routee>();
        for(int i=0; i<5; i++){
            ActorRef worker = getContext().actorOf(Props.create(MyWorker.class), "worker_"+i);
            getContext().watch(worker);
            routees.add(new ActorRefRoutee(worker));
        }

        //router = new Router(new RoundRobinRoutingLogic(), routees);
        //广播策略
        //router = new Router(new BroadcastRoutingLogic(), routees);
        //随机投递策略
        //router = new Router(new RandomRoutingLogic(), routees);
        //空闲Actor优先投递策略
        router = new Router(new SmallestMailboxRoutingLogic(), routees);
    }

    @Override
    public void onReceive(Object msg) throws Exception {
        if(msg instanceof  MyWorker.Msg){
            router.route(msg, getSender());
        }else if(msg instanceof Terminated){
            router = router.removeRoutee(((Terminated)msg).actor());
            log.info(((Terminated)msg).actor().path()+" is closed, routees="+router.routees().size());
            if(router.routees().size() == 0){
                log.info("Close system");
                RouteMain.flag.send(false);
                getContext().system().terminate();
            }
        }else{
            unhandled(msg);
        }
    }
}
