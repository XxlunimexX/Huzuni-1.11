package net.halalaboos.huzuni.mod.misc;

import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.node.Mode;
import net.halalaboos.huzuni.api.task.LookTask;
import net.halalaboos.mcwrapper.api.event.player.PreMotionUpdateEvent;

import java.util.Random;

import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;

public class Retard extends BasicMod {

	private static final int HEADBANG_RATE = 45, NO_RATE = 70;

	private final Random random = new Random();

	private final Mode<String> modeYaw = new Mode<>("Yaw mode", "Style of expression", "None", "Random", "Say no");

	private final Mode<String> modePitch = new Mode<>("Pitch mode", "Style of expression", "None", "Random", "Headless", "Headbang");

	private final LookTask lookTask = new LookTask(this);

	private int yawPosition = 0, pitchPosition;

	private boolean headbangUp = false, noLeft = false;

	public Retard() {
		super("Retard", "Look as if you were brendan on a lazy sunday afternoon.");
		setCategory(Category.MISC);
		addChildren(modeYaw, modePitch);
		setAuthor("Halalaboos");
		huzuni.lookManager.registerTaskHolder(this);
		subscribe(PreMotionUpdateEvent.class, this::onPreUpdate);
	}

	@Override
	public void onDisable() {
		huzuni.lookManager.withdrawTask(lookTask);
	}

	private void onPreUpdate(PreMotionUpdateEvent event) {
		if (huzuni.lookManager.hasPriority(this)) {
			switch (modeYaw.getSelected()) {
				case 0:
					lookTask.setYaw(getPlayer().getYaw());
					break;
				case 1:
					lookTask.setYaw(random.nextBoolean() ? random.nextInt(180)  : -random.nextInt(180));
					break;
				case 2:
					yawPosition += (noLeft ? -8 : 8);
					if (yawPosition < -NO_RATE) {
						noLeft = false;
						yawPosition = -NO_RATE;
					} else if (yawPosition > NO_RATE) {
						noLeft = true;
						yawPosition = NO_RATE;
					}
					lookTask.setYaw(getPlayer().getYaw() + yawPosition);
					break;
			}

			switch (modePitch.getSelected()) {
				case 0:
					lookTask.setPitch(getPlayer().getPitch());
					break;
				case 1:
					lookTask.setPitch(random.nextBoolean() ? random.nextInt(90)  : -random.nextInt(90));
					break;
				case 2:
					lookTask.setPitch(180);
					break;
				case 3:
					pitchPosition += (headbangUp ? -8 : 8);
					if (pitchPosition < -HEADBANG_RATE) {
						headbangUp = false;
						pitchPosition = -HEADBANG_RATE;
					} else if (pitchPosition > HEADBANG_RATE) {
						headbangUp = true;
						pitchPosition = HEADBANG_RATE;
					}
					lookTask.setPitch(getPlayer().getPitch() + pitchPosition);
					break;
			}
			huzuni.lookManager.requestTask(this, lookTask);
		}
	}

}
