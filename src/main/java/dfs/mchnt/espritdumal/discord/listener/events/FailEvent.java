package dfs.mchnt.espritdumal.discord.listener.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class FailEvent extends ApplicationEvent {
  private final String message;

  public FailEvent(Object source, String message) {
    super(source);
    this.message = message;
  }
}
