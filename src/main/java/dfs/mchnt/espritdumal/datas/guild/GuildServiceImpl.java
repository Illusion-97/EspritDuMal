package dfs.mchnt.espritdumal.datas.guild;

import dfs.mchnt.espritdumal.generic.service.GenericServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class GuildServiceImpl extends GenericServiceImpl<Guild, GuildRepository, GuildDto, GuildMapper> implements GuildService {
  public GuildServiceImpl(GuildRepository repository, GuildMapper mapper) {
    super(repository, mapper);
  }
}
