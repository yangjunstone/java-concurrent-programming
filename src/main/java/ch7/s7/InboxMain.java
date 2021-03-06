package ch7.s7;

import akka.actor.*;
import com.typesafe.config.ConfigFactory;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class InboxMain {
    public static void main(String[] args){
        ActorSystem system = ActorSystem.create("inboxdemo", ConfigFactory.load("samplehello.conf"));
        ActorRef worker = system.actorOf(Props.create(MyWorker.class), "worker");

        final Inbox inbox = Inbox.create(system);
        inbox.watch(worker);
        inbox.send(worker, MyWorker.Msg.WORKING);
        inbox.send(worker, MyWorker.Msg.DONE);
        inbox.send(worker, MyWorker.Msg.CLOSE);

        while(true){
            try{
                Object msg = inbox.receive(Duration.create(1, TimeUnit.SECONDS));
                if(msg == MyWorker.Msg.CLOSE){
                    System.out.println("My worker is Closing");
                }else if(msg instanceof Terminated){
                    System.out.print("My worker is dead");
                    system.terminate();
                    break;
                }else{
                    System.out.println(msg);
                }
            }catch (TimeoutException e){
                e.printStackTrace();
            }
        }
    }
}
