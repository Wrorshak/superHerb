package SuperHerblore.Tasks;

import SuperHerblore.*;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Component;
import org.powerbot.script.rt4.Item;
import org.powerbot.script.Random;

import java.util.concurrent.Callable;

public class MakinPots extends Task {

    final static int RANARR_ID = 257;
    final static int VIAL_ID = 227;
    final static int WIDGET = 270, COMPONENT = 14;

    public MakinPots(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return  (ctx.inventory.select().id(RANARR_ID).count() > 0 && ctx.inventory.select().id(VIAL_ID).count() > 0 && ctx.players.local().animation() ==-1 && SuperHerb.rannarLeft >= 0);
    }

    @Override
    public void execute() {
        if (ctx.bank.opened()){
            ctx.bank.close();
        }
        Item cleanRanarr = ctx.inventory.select().id(RANARR_ID).poll();
        cleanRanarr.interact("Use");
        Random.getDelay();
        Item vialOfWater = ctx.inventory.select().id(VIAL_ID).poll();
        vialOfWater.interact("Use");
        final Component makePots = ctx.widgets.component(WIDGET, COMPONENT);
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return makePots.visible();
            }
        },200,20);
        makePots.click();
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return ctx.inventory.select().id(RANARR_ID).count() < 1;
            }
        },2000,20);
        SuperHerb.potsMade += ctx.inventory.select().id(99).count();
    }
}
