package net.halalaboos.huzuni.mod.visual.nametags.provider;

import net.halalaboos.mcwrapper.api.entity.living.Living;

public interface TagRenderProvider extends TagProvider {

	void render(Living living, float x, float y);

}
