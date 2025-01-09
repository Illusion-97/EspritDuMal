package dfs.mchnt.espritdumal.datas.access;

import lombok.Getter;
import net.dv8tion.jda.api.entities.Message;
import org.springframework.context.ApplicationEvent;

@Getter
public class RegisterEvent extends ApplicationEvent {
  private final Message message;

  public RegisterEvent(Object source, Message message) {
    super(source);
    this.message = message;
  }
}
