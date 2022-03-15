package winx.bitirme.chat.client.controller;

import java.util.Date;
import java.util.concurrent.Callable;

public class ChatRunnable implements Callable {

    @Override
    public Object call() throws Exception {
        int i=0;
        while(i>=0){
            i++;
            System.out.println("Task performed on " + new Date());
        }
        return null;
    }
}
