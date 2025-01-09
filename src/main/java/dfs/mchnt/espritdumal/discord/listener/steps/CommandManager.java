package dfs.mchnt.espritdumal.discord.listener.steps;

import dfs.mchnt.espritdumal.datas.access.Access;
import dfs.mchnt.espritdumal.datas.access.AccessEvent;
import dfs.mchnt.espritdumal.datas.members.RenameEvent;
import dfs.mchnt.espritdumal.discord.listener.events.FailEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.requests.RestAction;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;

public interface CommandManager {

  OptionData ignOption = new OptionData(OptionType.STRING, "name", "In Game Name", true);

  ApplicationEventPublisher getPublisher();

  default void setup(Guild guild) {
    Arrays.stream(Slash.values()).parallel().map(Slash::command).map(guild::upsertCommand).forEach(RestAction::queue);
  }

  default void handle(ListenerAdapter adapter, SlashCommandInteractionEvent event) {
    getPublisher().publishEvent(Arrays.stream(Slash.values()).parallel().filter(slash -> slash.name().toLowerCase().equals(event.getName())).findFirst()
      .map(slash -> slash.reaction.apply(adapter, event))
      .orElseGet(() -> new FailEvent(adapter, "Unknown command : %s".formatted(event.getName()))));
  }

  @RequiredArgsConstructor
  @Getter
  enum Slash {
    ADD("Register a new member",
      List.of(ignOption, new OptionData(OptionType.ROLE, "role", "Role to give when the member register")),
      (adapter, event) -> new AccessEvent(adapter, event, Access.AccessType.GRANTED)
    ),
    BAN("Banish the mole!",
      List.of(ignOption),
      (adapter, event) -> new AccessEvent(adapter, event, Access.AccessType.BANNED)
    ),
    RENAME("Rename yourself",
      List.of(ignOption),
      RenameEvent::new
    );
    private final String description;
    private final Collection<OptionData> options;
    private final BiFunction<ListenerAdapter, SlashCommandInteractionEvent, ApplicationEvent> reaction;

    public SlashCommandData command() {
      return Commands.slash(name().toLowerCase(), description).addOptions(options);
    }
  }
}
