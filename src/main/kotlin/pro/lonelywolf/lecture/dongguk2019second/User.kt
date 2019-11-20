package pro.lonelywolf.lecture.dongguk2019second

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import javax.persistence.*

@Service
class CustomUserDetailService(val userInfoRepository: UserInfoRepository) : UserDetailsService {

  object Crypt {
    val passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()
  }

  override fun loadUserByUsername(username: String?): UserDetails {
    return if (username.equals("lonelywolf")) {
      User("lonelywolf", Crypt.passwordEncoder.encode("test1234"), mutableSetOf(SimpleGrantedAuthority("ROLE_ADMIN")))
    } else {
      val userInfo = userInfoRepository.findUserInfoByUsername(username!!)
      if (userInfo != null) User(username, Crypt.passwordEncoder.encode(userInfo.password), setOf(SimpleGrantedAuthority("ROLE_USER")))
      else throw UsernameNotFoundException("No username for $username")
    }
  }
}

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

@Entity
@Table(name = "user_info")
data class UserInfo(
    val username: String,
    val password: String,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null
)

interface UserInfoRepository : JpaRepository<UserInfo, Long> {
  fun findUserInfoByUsername(username: String): UserInfo?
}