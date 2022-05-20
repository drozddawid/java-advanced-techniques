package processing;

public class Listener implements StatusListener{
    @Override
    public void statusChanged(Status s) {
       if(s.getProgress()%10==0) System.out.println(s.getTaskId() +" status changed: " + s.getProgress());
    }
}
