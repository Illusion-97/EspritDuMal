package dfs.mchnt.espritdumal.datas.access;

import dfs.mchnt.espritdumal.discord.listener.events.SlashEvent;
import lombok.Getter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

@Getter
public class AccessEvent extends SlashEvent {
  private final Access.AccessType type;

  public AccessEvent(Object source, SlashCommandInteractionEvent event, Access.AccessType type) {
    super(source, event);
    this.type = type;
  }
}
