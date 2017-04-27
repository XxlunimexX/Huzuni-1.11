package net.halalaboos.mcwrapper.impl.mixin.client;

import net.halalaboos.mcwrapper.api.client.Session;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.util.Session.class)
public abstract class MixinSession implements Session {

	@Shadow @Final private String playerID;
	@Shadow @Final private String token;
	@Shadow @Final private String username;

	@Mutable
	@Shadow @Final private net.minecraft.util.Session.Type sessionType;

	private String fakeId, fakeToken, fakeUsername;

	@Inject(method = "<init>", at = @At("RETURN"))
	private void onInit(CallbackInfo ci) {
		this.fakeId = playerID;
		this.fakeToken = token;
		this.fakeUsername = username;
	}

	/**
	 * @reason Replaces the player ID with a non-final one.
	 * @author b
	 */
	@Overwrite
	public String getPlayerID() {
		return fakeId;
	}

	/**
	 * @reason Replaces the token with a non-final one.
	 * @author b
	 */
	@Overwrite
	public String getToken() {
		return fakeToken;
	}

	/**
	 * @reason Replaces the username with a non-final one.
	 * @author b
	 */
	@Overwrite
	public String getUsername() {
		return fakeUsername;
	}

	/**
	 * @reason Replaces the session ID with a non-final one.
	 * @author b
	 */
	@Overwrite
	public String getSessionID() {
		return "token:" + this.fakeToken + ":" + this.fakeId;
	}

	@Override
	public void set(String... params) {
		this.fakeUsername = params[0];
		this.fakeId = params[1];
		this.fakeToken = params[2];
		if (params.length > 3) {
			this.sessionType = params[3].equals("mojang") ? net.minecraft.util.Session.Type.MOJANG : net.minecraft.util.Session.Type.LEGACY;
		} else {
			this.sessionType = net.minecraft.util.Session.Type.MOJANG;
		}
	}

	@Override
	public String name() {
		return fakeUsername;
	}

	@Override
	public String[] getParams() {
		return new String[] {fakeUsername, fakeId, fakeToken, getSessionID() };
	}
}
