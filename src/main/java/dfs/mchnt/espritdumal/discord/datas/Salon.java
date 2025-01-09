package dfs.mchnt.espritdumal.discord.datas;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.channel.ChannelType;

@RequiredArgsConstructor
@Getter
public enum Salon {
  /*GENERAL("général", ChannelType.TEXT, ChannelCategory.TABLEAUX),
  LINKS("liens-documents", ChannelType.TEXT, ChannelCategory.TABLEAUX),
  CODE("code-exercices", ChannelType.TEXT, ChannelCategory.TABLEAUX),
  CPE("bureau", ChannelType.TEXT, ChannelCategory.TABLEAUX),
  ANYTHING("anything-goes", ChannelType.TEXT, ChannelCategory.TABLEAUX),
  COLLECTIVE("Collective", ChannelType.VOICE, ChannelCategory.SALLES),*/
  LANDING("💀-autel-du-mal-💀", ChannelType.TEXT, null);
  private final String name;
  private final ChannelType type;
  private final ChannelCategory category;
}
