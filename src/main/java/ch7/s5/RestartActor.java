package ch7.s5;

import akka.actor.UntypedActor;
import scala.Option;

public class RestartActor  extends UntypedActor {
    public enum Msg {
        DONE, RESUME, RESTART
    }

    @Override
    public void preStart() {
        System.out.println("preStart hashcode:" + this.hashCode());
    }

    @Override
    public void postStop() {
        System.out.println("postStop hashcode:" + this.hashCode());
    }

    @Override
    public void postRestart(Throwable reason) throws Exception {
        super.postRestart(reason);
        System.out.println("postRestart hashcode:" + this.hashCode());
    }

    @Override
    public void preRestart(Throwable reason, Option<Object> message) throws Exception {
        System.out.println("preRestart hashcode:" + this.hashCode());
    }

    @Override
    public void onReceive(Object message) throws Exception {
        System.out.println("RestartActor::onReceive, message="+(Msg)message);
        if(message == Msg.DONE){
            getContext().stop(getSelf());
        }else if(message == Msg.RESTART){
            //抛出空指针异常 默认会被restart
            System.out.println(((Object)null).toString());
        }else if(message == Msg.RESUME){
            // 抛出算术除0 异常，resume
            double a = 0/0;
        }
        unhandled(message);
    }
}
