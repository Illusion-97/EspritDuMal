package dfs.mchnt.espritdumal.datas.members;

import dfs.mchnt.espritdumal.generic.service.GenericServiceImpl;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl extends GenericServiceImpl<Member, MemberRepository, MemberDto, MemberMapper> implements MemberService {
  public MemberServiceImpl(MemberRepository repository, MemberMapper mapper) {
    super(repository, mapper);
  }

  @EventListener
  public void handle(RenameEvent rename) {
    SlashCommandInteractionEvent event = rename.getEvent();
    String name = event.getOption("name").getAsString();
    String id = event.getUser().getId();
    event.reply("You're now recognized as : %s".formatted(repository.save(repository.findBySnow(id)
        .map(m -> m.setIgn(name))
        .orElseGet(() -> new Member(id, name))).getIgn()))
      .setEphemeral(true)
      .queue();
  }
}
