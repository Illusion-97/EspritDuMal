package dfs.mchnt.espritdumal.datas.members;

import dfs.mchnt.espritdumal.generic.controller.GenericController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/members")
public class MemberController extends GenericController<MemberDto, MemberService> {
  public MemberController(MemberService service) {
    super(service);
  }

}
