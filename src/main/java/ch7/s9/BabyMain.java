package ch7.s9;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.actor.Props;
import ch7.s8.WatchActor;
import com.typesafe.config.ConfigFactory;

public class BabyMain {
    public static void main(String[] args){
        ActorSystem system = ActorSystem.create("become", ConfigFactory.load("samplehello.conf"));
        ActorRef child = system.actorOf(Props.create(BabyActor.class), "baby");
        //system.actorOf(Props.create(WatchActor.class, child), "watcher");
        child.tell(BabyActor.Msg.PLAY, ActorRef.noSender());
        child.tell(BabyActor.Msg.SLEEP, ActorRef.noSender());
        child.tell(BabyActor.Msg.PLAY, ActorRef.noSender());
        child.tell(BabyActor.Msg.PLAY, ActorRef.noSender());

        child.tell(PoisonPill.getInstance(), ActorRef.noSender());
    }
}
