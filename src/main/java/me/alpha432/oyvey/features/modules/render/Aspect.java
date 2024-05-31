package me.alpha432.oyvey.features.modules.render;

import me.alpha432.oyvey.event.events.PerspectiveEvent;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.setting.Setting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Aspect
        extends Module {
    public Setting<Float> aspect = this.register(new Setting<Float>("Alpha", Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(5.0f)));

    public Aspect() {
        super("Aspect", "Change ur screen aspect like fortnite.", Category.CLIENT, true, false, false);
    }

    @SubscribeEvent
    public void onPerspectiveEvent(PerspectiveEvent perspectiveEvent) {
        perspectiveEvent.setAspect(this.aspect.getValue().floatValue());
    }
}
