package dfs.mchnt.espritdumal.datas.guild;

import dfs.mchnt.espritdumal.generic.controller.GenericController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/guilds")
public class GuildController extends GenericController<GuildDto, GuildService> {
  public GuildController(GuildService service) {
    super(service);
  }
}
