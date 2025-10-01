package net.forixaim.mana_arts.client;

import net.forixaim.mana_arts.ManaArts;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Comparator;
import java.util.PriorityQueue;

@OnlyIn(Dist.CLIENT)
public class CameraEngine {
    public CameraEngine(){
        instance = this;
    }
    private static CameraEngine instance;
    public static CameraEngine getInstance(){
        return instance;
    }
    private final PriorityQueue<ShakeEntry> queue =
            new PriorityQueue<>(Comparator.comparingDouble(e -> -e.strength));
    public void tick(ViewportEvent.ComputeCameraAngles event, Player player) {
        if (Minecraft.getInstance().isPaused() || queue.isEmpty()) return;

        queue.removeIf(entry -> {
            entry.remainingTicks--;
            entry.strength *= 0.97;
            entry.frequency *= 0.97;
            return entry.remainingTicks <= 0;
        });

        if (!queue.isEmpty()) {
            ShakeEntry top = queue.peek();
            double ticksExistedDelta = player.tickCount + event.getPartialTick();
            double k = top.strength / 4F * 1;
            double f = top.frequency;
            event.setPitch((float) (event.getPitch() + k * Math.cos(ticksExistedDelta * f + 2)));
            event.setYaw((float) (event.getYaw() + k * Math.cos(ticksExistedDelta * f + 1)));
            event.setRoll((float) (event.getRoll() + k * Math.cos(ticksExistedDelta * f)));
            top.strength *= 0.97d;
        }
    }

    public void shakeCamera(ShakeEntry entry){
        ShakeEntry entry1 = entry.copy();
        queue.add(entry1);
    }


    public void shakeCamera(float strength, int time, float frequency){
        this.shakeCamera(new ShakeEntry(strength, time, frequency));
    }
    public void shakeCamera(int time, float strength){
        this.shakeCamera(new ShakeEntry(strength, time, 0.3));
    }

    public void shakeCamera(float strength){
        this.shakeCamera(new ShakeEntry(strength, 1, 0.3));
    }


    @Mod.EventBusSubscriber(modid = ManaArts.MODID, value = Dist.CLIENT)
    public static class Events {
        @SubscribeEvent(priority = EventPriority.LOW)
        public static void cameraSetupEvent(ViewportEvent.ComputeCameraAngles event) {

            Player player = Minecraft.getInstance().player;
            if(player == null) return;

            instance.tick(event, player);
        }
    }

    public static class ShakeEntry {
        double strength;
        int remainingTicks;
        double frequency;
        public ShakeEntry(double strength, int tick, double frequency) {
            this.strength = strength;
            this.remainingTicks = tick;
            this.frequency = frequency;
        }
        public ShakeEntry(double strength, int tick){
            this(strength, tick, 0.3f);
        }

        public ShakeEntry copy(){
            return new ShakeEntry(this.strength, this.remainingTicks, this.frequency);
        }
    }
}