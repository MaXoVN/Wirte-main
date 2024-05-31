package me.alpha432.oyvey.features.modules.render;

import net.minecraft.init.MobEffects;
import net.minecraft.network.play.server.SPacketEntityEffect;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.event.events.PacketEvent;
import me.alpha432.oyvey.features.setting.Setting;
public class FullBright
        extends Module {
    public  Setting<Mode> mode;
    public  Setting<Boolean> effects;
    private  float previousSetting;

    @Override
    public void onUpdate() {
        if (this.mode.getValue() == Mode.GAMMA) {
            FullBright.mc.gameSettings.gammaSetting = 1000.0f;
        }
        if (this.mode.getValue() == Mode.POTION) {
            FullBright.mc.player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 5210));
        }
    }

    @SubscribeEvent
    public void onPacketReceive(PacketEvent.Receive receive) {
        if (receive.getStage() == 0 && receive.getPacket() instanceof SPacketEntityEffect && this.effects.getValue().booleanValue()) {
            SPacketEntityEffect sPacketEntityEffect = (SPacketEntityEffect)receive.getPacket();
            if (FullBright.mc.player != null && sPacketEntityEffect.getEntityId() == FullBright.mc.player.getEntityId() && (sPacketEntityEffect.getEffectId() == 9 || sPacketEntityEffect.getEffectId() == 15)) {
                receive.setCanceled(true);
            }
        }
    }

    @Override
    public void onEnable() {
        this.previousSetting = FullBright.mc.gameSettings.gammaSetting;
    }

    public FullBright() {
        super("FullBright", "Makes your game brighter.", Module.Category.RENDER, true, false, false);
        this.mode = this.register(new Setting<Mode>("Mode", Mode.GAMMA));
        this.effects = this.register(new Setting<Boolean>("Effects", false));
        this.previousSetting = 1.0f;
    }

    @Override
    public String getDisplayInfo() {
        switch (this.mode.getValue()) {
            case GAMMA: {
                return "Gamma";
            }
            case POTION: {
                return "Potion";
            }
        }
        return null;
    }

    @Override
    public void onDisable() {
        if (this.mode.getValue() == Mode.POTION) {
            FullBright.mc.player.removePotionEffect(MobEffects.NIGHT_VISION);
        }
        FullBright.mc.gameSettings.gammaSetting = this.previousSetting;
    }

    public static enum Mode {
        GAMMA,
        POTION;

    }
}