package pro.lonelywolf.lecture.dongguk2019second

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class SecurityConfiguration(val userDetailService: CustomUserDetailService) : WebSecurityConfigurerAdapter() {

  @Bean
  fun passwordEncoder(): PasswordEncoder {
    return CustomUserDetailService.Crypt.passwordEncoder
  }

  override fun configure(http: HttpSecurity?) {
    http!!
        .csrf().disable()
        .headers().frameOptions().disable()
        .and()
        .authorizeRequests()
        .antMatchers(
            "/swagger/**",
            "/v2/api-docs",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/swagger/**"
        ).hasRole("ADMIN")
        .anyRequest().authenticated()
        .and().formLogin()
  }

  override fun configure(auth: AuthenticationManagerBuilder?) {
    auth!!.userDetailsService(userDetailService).passwordEncoder(passwordEncoder())
  }
}

