package dfs.mchnt.espritdumal.datas.guild;

import dfs.mchnt.espritdumal.generic.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface GuildMapper extends GenericMapper<GuildDto, Guild> {
}
