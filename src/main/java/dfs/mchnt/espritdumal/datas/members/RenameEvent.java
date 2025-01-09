package dfs.mchnt.espritdumal.datas.members;

import dfs.mchnt.espritdumal.discord.listener.events.SlashEvent;
import lombok.Getter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

@Getter
public class RenameEvent extends SlashEvent {
  public RenameEvent(Object source, SlashCommandInteractionEvent event) {
    super(source, event);
  }
}
