package dfs.mchnt.espritdumal.datas.members;

import dfs.mchnt.espritdumal.generic.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface MemberMapper extends GenericMapper<MemberDto, Member> {
}
