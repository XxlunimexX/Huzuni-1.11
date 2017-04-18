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
	private String _playerId, _token, _username;

	@Inject(method = "<init>", at = @At("RETURN"))
	private void onInit(CallbackInfo ci) {
		this._playerId = playerID;
		this._token = token;
		this._username = username;
	}

	@Overwrite
	public String getPlayerID() {
		return _playerId;
	}

	@Overwrite
	public String getToken() {
		return _token;
	}

	@Overwrite
	public String getUsername() {
		return _username;
	}

	@Overwrite
	public String getSessionID() {
		return "token:" + this._token + ":" + this._playerId;
	}

	@Override
	public void set(String... params) {
		this._username = params[0];
		this._playerId = params[1];
		this._token = params[2];
		if (params.length > 3) {
			this.sessionType = params[3].equals("mojang") ? net.minecraft.util.Session.Type.MOJANG : net.minecraft.util.Session.Type.LEGACY;
		} else {
			this.sessionType = net.minecraft.util.Session.Type.MOJANG;
		}
	}

	@Override
	public String name() {
		return _username;
	}

	@Override
	public String[] getParams() {
		return new String[] { _username, _playerId, _token, getSessionID() };
	}
}
