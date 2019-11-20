package pro.lonelywolf.lecture.dongguk2019second

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import kotlin.math.absoluteValue
import kotlin.random.Random

@Controller
@RequestMapping("/")
class ApiController {

  @GetMapping("")
  fun home(model: Model): String {
    val name = SecurityContextHolder.getContext().authentication.name
    val userName = if (name == "lonelywolf") "2121212121" else name
    val rand = Random(userName.toLong())
    val assignment = Assignment(
        (rand.nextInt().absoluteValue % 2) + 1,
        (rand.nextInt().absoluteValue % 4) + 1,
        (rand.nextInt().absoluteValue % 512) + 1,
        (rand.nextInt().absoluteValue % 2) + 1,
        (rand.nextInt().absoluteValue % 2) + 1
    )
    model.addAttribute("username", name)
    model.addAttribute("assignment", assignmentString(assignment))
    model.addAttribute("data", assignment)
    return "home"
  }

  private fun assignmentString(assignment: Assignment): String {
    val processingMethod = when (assignment.processing) {
      1 -> "왼쪽 상단에서 오른쪽 하단으로 대각선 방향 반전"
      2 -> "오른쪽 상단에서 왼쪽 하단으로 대각선 방향 반전"
      3 -> "왼쪽으로 90도 회전"
      4 -> "오른쪽으로 90도 회전"
      else -> "error"
    }

    return "여러분이 처리 할 이미지는 각 픽셀의 크기가 8 bit (1 byte)로 이루어진 가로 세로 512 x 512 인 grayscale raw image 이다(총 262144 bytes). " +
        "자신에게 주어진 '${if (assignment.image == 1) "lena" else "barbara"}' 이미지 파일을 사용하여 '$processingMethod' 시킨 같은 크기의 새로운 이미지 파일을생성하는 프로그램을 작성하여라. " +
        "각각의 이미지를 '${if (assignment.markOrder == 1) "처리하기 전에" else "처리한 이후에"}' 이미지의 '${assignment.markLine} 번째줄 제일 ${if (assignment.markPos == 1) "앞" else "뒤"}'에 " +
        "자신의 '학번'을 2개 숫자씩 묶어서 총 5 픽셀로 나누어 저장한다."
  }
}

data class Assignment(
    val image: Int,
    val processing: Int,
    val markLine: Int,
    val markPos: Int,
    val markOrder: Int
)

@RestController
@RequestMapping("/users")
class UserInfoController(val userInfoRepository: UserInfoRepository) {

  @GetMapping("all")
  fun showAll(): List<UserInfo> {
    return userInfoRepository.findAll()
  }

  @PostMapping("all")
  fun postAll(@RequestBody users: List<UserInfo>): List<UserInfo> {
    return userInfoRepository.saveAll(users)
  }
}