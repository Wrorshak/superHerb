package SuperHerblore;

import SuperHerblore.Tasks.Bank;
import SuperHerblore.Tasks.BuyMoreRanarr;
import SuperHerblore.Tasks.MakinPots;
import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.ClientContext;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Script.Manifest(name="superHerb", description="Makes unf pots, restocks in GE, repeat", properties = "client=4 author=Wrorshak, topic=999")

public class SuperHerb extends PollingScript<ClientContext> implements PaintListener {

    List<Task> taskList = new ArrayList<Task>();
    public static int rannarLeft;
    public static int potsMade = 0;

    @Override
    public void start(){
        taskList.add(new BuyMoreRanarr(ctx));
        taskList.add(new Bank(ctx));
        taskList.add(new MakinPots(ctx));
    }

    @Override
    public void stop() {
        System.out.println("Stopped");
        ctx.controller.stop();
    }

    @Override
    public void poll() {

        for (Task task:taskList){
            if (task.activate()){
                task.execute();
                break;
            }
        }
    }

    @Override
    public void repaint(Graphics graphics) {
        long milliseconds = getTotalRuntime();
        long seconds = (milliseconds / 1000) % 60;
        long minutes = (milliseconds / (1000*60) %60);
        long hours = (milliseconds / (1000*60*60) %24);

        Graphics2D g= (Graphics2D)graphics;
        g.setColor(new Color(0,0,0,180));
        g.fillRect(0,0,150,100);
        g.setColor(new Color(255,255,255));
        g.drawRect(0,0,150,100);

        g.drawString("Wrors SuperHerber", 20,20);
        g.drawString("RunTime: " + String.format("%02d:%02d:%02d", hours, minutes, seconds), 20, 40);
        g.drawString("Ranarr Left " + (rannarLeft), 20,60);
        g.drawString("Pots Made " + (potsMade), 20, 80);
    }
}
