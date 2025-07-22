package me.zyouime.visiblechat;

import net.labymod.addon.AddonTransformer;
import net.labymod.api.TransformerType;

public class VisibleChatTransformer extends AddonTransformer {

  @Override
  public void registerTransformers() {
    this.registerTransformer(TransformerType.VANILLA, "holychatheads.mixin.json");
  }
}
