package dfs.mchnt.espritdumal.conf;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AngularForwardController {

  @GetMapping("{path:^(?!api|public|swagger)[^\\.]*}/**")
  public String handleForward(@PathVariable String path) {
    return "forward:/";
  }

}
