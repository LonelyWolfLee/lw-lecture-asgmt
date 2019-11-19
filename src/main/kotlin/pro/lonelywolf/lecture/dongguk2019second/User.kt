package pro.lonelywolf.lecture.dongguk2019second

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class CustomUserDetailService : UserDetailsService {
  object Crypt {
    val passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()
  }

  override fun loadUserByUsername(username: String?): UserDetails {
    return if (username.equals("lonelywolf")) {
      User("lonelywolf", Crypt.passwordEncoder.encode("test1234"), mutableSetOf(SimpleGrantedAuthority("ROLE_ADMIN")))
    } else User(username, "test1234", setOf(SimpleGrantedAuthority("ROLE_USER")))
  }
}