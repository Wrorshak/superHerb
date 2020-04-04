package SuperHerblore.Tasks;

import SuperHerblore.*;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.*;
import org.powerbot.script.rt4.Bank;

import java.util.concurrent.Callable;

public class BuyMoreRanarr extends Task {
    public BuyMoreRanarr(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return SuperHerb.rannarLeft < 0 && ctx.inventory.select().id(258).count() < 1;
    }

    public static final int[] geClerk = {2148, 2149, 2150, 2151};


    final static int RANARR_ID = 257;
    final static int RANNARUNF_ID = 100;
    final static int VIAL_ID = 227;
    final static int WIDGET = 12, COMPONENT = 22;
    final static int[] GECLERK = {2148, 2149, 2150, 2151};


    @Override
    public void execute() {
        if (!ctx.bank.opened()){
            ctx.bank.open();
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.bank.opened();
                }
            },200,200);
        }
        ctx.bank.depositInventory();
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return ctx.inventory.select().count() > 1;
            }
        },200,200);
        final Component noteRanarr = ctx.widgets.component(WIDGET, COMPONENT);
        ctx.bank.withdraw(995, Bank.Amount.ALL);
        noteRanarr.click();
        Random.getDelay();
        ctx.bank.withdraw(99, Bank.Amount.ALL);
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return ctx.bank.select().id(99).poll().stackSize() > 0;
            }
        }, 200, 20);
        if(ctx.bank.opened()){
            ctx.bank.close();
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return !ctx.bank.opened();
                }
            },200,20);
       }
        Npc myClerk = ctx.npcs.select().nearest().id(GECLERK).poll();
        myClerk.interact("Exchange");
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return ctx.widgets.component(465,7).visible();
            }
        },2000,20);
        if (ctx.inventory.select().id(RANNARUNF_ID).count()>0){
            ctx.widgets.component(465,7,4).click();
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.widgets.component(465,24,6).visible();
                }
            },2000,20);
            Item unfPots = ctx.inventory.select().id(RANNARUNF_ID).poll();
            unfPots.click();
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.widgets.component(465,24,54).visible();
                }
            },2000,20);
            ctx.widgets.component(465,24,10).click();
            Random.getDelay();
            ctx.widgets.component(465,24,54).click();
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.inventory.select().id(RANNARUNF_ID).count() < 1;
                }
            },2000,20);
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.widgets.component(465,7,22).visible();
                }
            },2000,20);
            ctx.widgets.component(465,6,0).click();
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return !ctx.widgets.component(465,7,22).visible();
                }
            },2000,20);
        }
        ctx.widgets.component(465,7,26).click();
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return ctx.widgets.component(162,45).visible();
            }
        },2000,20);
        ctx.input.sendln("Ranarr");
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return ctx.widgets.component(162,53,9).visible();
            }
        },200,200);
        ctx.widgets.component(162,53,9).click();
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return ctx.widgets.component(465,24,53).visible();
            }
        },200,200);
        ctx.widgets.component(465,24,47).click();
        Random.getDelay();
        ctx.widgets.component(465,24,53).click();
        Random.getDelay();
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return ctx.widgets.component(465,24,54).visible();
            }
        },200,200);
        ctx.widgets.component(465,24,54).click();
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return ctx.widgets.component(465,7,22).visible();
            }
        },2000,200);
        ctx.widgets.component(465,6,0).click();
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return !ctx.widgets.component(465,7,22).visible();
            }
        },2000,20);
        ctx.widgets.component(465,2,11).click();
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return !ctx.widgets.component(465,2,11).visible();
            }
        },200,20);
    }
}
