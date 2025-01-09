package dfs.mchnt.espritdumal.discord.listener.events;

import lombok.Getter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.context.ApplicationEvent;

@Getter
public class SlashEvent extends ApplicationEvent {
  protected final SlashCommandInteractionEvent event;

  public SlashEvent(Object source, SlashCommandInteractionEvent event) {
    super(source);
    this.event = event;
  }
}
