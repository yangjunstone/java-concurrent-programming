package ch7.s5;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;

public class SupervisorMain {

    private static void customStrategy(ActorSystem system) {
        ActorRef a = system.actorOf(Props.create(Supervisor.class), "Supervisor");
        a.tell(Props.create(RestartActor.class), ActorRef.noSender());

        ActorSelection sel = system.actorSelection("akka://lifecycle/user/Supervisor/restartActor");

        //发送50次RESTART消息
        for(int i=0; i<7; i++){
            sel.tell(RestartActor.Msg.RESTART, ActorRef.noSender());
        }

        //发送50次RESUME消息
        for(int i=0; i<7; i++){
            sel.tell(RestartActor.Msg.RESUME, ActorRef.noSender());
        }
    }

    public static void main(String[] args){
        ActorSystem system = ActorSystem.create("lifecycle", ConfigFactory.load("lifecycle.conf"));
        customStrategy(system);
    }
}
