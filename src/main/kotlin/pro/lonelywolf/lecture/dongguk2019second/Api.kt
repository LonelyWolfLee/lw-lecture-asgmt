package pro.lonelywolf.lecture.dongguk2019second

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/")
class ApiController {

  @GetMapping("")
  fun home(model: Model): String {
    model.addAttribute("username", SecurityContextHolder.getContext().authentication.name)
    return "home"
  }
}