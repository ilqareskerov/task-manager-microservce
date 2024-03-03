package com.ilqar.taskuserservice.config;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class ApplicationConfiguration {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(managment->managment.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests-> {
                    requests.requestMatchers(HttpMethod.POST,"/auth/signup").permitAll();
                    requests.requestMatchers(HttpMethod.POST,"/auth/login").permitAll();
                    requests.requestMatchers("/swagger-ui/index.html").permitAll();
                    requests.anyRequest().permitAll();
                })
        .addFilterAfter(new TokenValidator(), BasicAuthenticationFilter.class);
        http.cors(cors->{
            cors.configurationSource(new CorsConfigurationSource() {
                @Override
                public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
//                    corsConfiguration.addAllowedOrigin(Arrays.asList("http://localhost:3000").toString());
                    corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
                    corsConfiguration.setAllowCredentials(true);
                    corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
                    corsConfiguration.setExposedHeaders(Arrays.asList("Authorization"));
                    corsConfiguration.setMaxAge(3600L);
                    return corsConfiguration;
                }});
        })
        .csrf(csrf->csrf.disable())
        .formLogin(formLogin->formLogin.disable());
        return http.build();
    }
//    private CorsConfigurationSource corsConfiguration() {
//       return request -> {
//           CorsConfiguration corsConfiguration = new CorsConfiguration();
//           corsConfiguration.addAllowedOrigin(Collections.singletonList("*").toString());
//           corsConfiguration.addAllowedMethod(Collections.singletonList("*").toString());
//           corsConfiguration.addAllowedHeader(Collections.singletonList("*").toString());
//           corsConfiguration.setAllowCredentials(true);
//           corsConfiguration.setMaxAge(3600L);
//           corsConfiguration.setExposedHeaders(Arrays.asList("Authorization"));
//           return corsConfiguration;
//       };
//
//    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
