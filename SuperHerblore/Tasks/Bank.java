package SuperHerblore.Tasks;

import SuperHerblore.*;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;

import java.util.concurrent.Callable;

public class Bank extends Task {

    final static int RANARR_ID = 257;
    final static int VIAL_ID = 227;


    public Bank(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return ctx.inventory.select().id(RANARR_ID).count()<1 || ctx.inventory.select().id(VIAL_ID).count()<1 && SuperHerb.rannarLeft > 0 || ctx.inventory.select().id(258).count() > 0 ;
    }

    @Override
    public void execute() {
        if (!ctx.bank.opened()){
            ctx.bank.open();
        }
        if (ctx.bank.opened()){
            ctx.bank.depositInventory();
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.inventory.select().count() < 1;
                }
            }, 200, 20);
            SuperHerb.rannarLeft = ctx.bank.select().id(RANARR_ID).poll().stackSize();
            if(SuperHerb.rannarLeft < 0){
                return;
            }else if (SuperHerb.rannarLeft <=0){
                ctx.bank.withdraw(RANARR_ID, 14);
                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return ctx.inventory.select().id(RANARR_ID).count() > 0;
                    }
                },200,20);
                ctx.bank.withdraw(VIAL_ID,ctx.inventory.select().id(RANARR_ID).count());
                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return ctx.inventory.select().count() > 0;
                    }
                },200,20);
                ctx.bank.close();
                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return !ctx.bank.opened();
                    }
                },200,20);
            }
        }
    }
}
