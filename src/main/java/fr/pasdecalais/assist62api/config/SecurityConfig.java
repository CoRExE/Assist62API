package fr.pasdecalais.assist62api.config;

import fr.pasdecalais.assist62api.model.Role;
import fr.pasdecalais.assist62api.utils.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthFilter jwtFilter;
    private static final String API_PREFIX = "/api";
    private static final String CATEGORY_ENDPOINT = API_PREFIX + "/category/**";
    private static final String USER_ENDPOINT = API_PREFIX + "/user/**";
    private static final String PROBLEM_ENDPOINT = API_PREFIX + "/problem/**";
    private static final String STEP_ENDPOINT = API_PREFIX + "/step/**";
    private static final String CHOICE_ENDPOINT = API_PREFIX + "/choice/**";
    private static final String NEWS_ENDPOINT = API_PREFIX + "/news/**";
    private static final String IMAGES_ENDPOINT = API_PREFIX + "/images/**";

    @Autowired
    public SecurityConfig(JwtAuthFilter jwtFilter, AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(
                    r -> r
                            .requestMatchers("/api").permitAll()
                            .requestMatchers("/api/auth/login").permitAll()
                            // Category endpoint access control
                            .requestMatchers(HttpMethod.GET, CATEGORY_ENDPOINT).permitAll()
                            .requestMatchers(HttpMethod.POST, CATEGORY_ENDPOINT).hasAnyAuthority(Role.ADMIN.name(), Role.MODO.name())
                            .requestMatchers(HttpMethod.PUT, CATEGORY_ENDPOINT).hasAnyAuthority(Role.ADMIN.name(), Role.MODO.name())
                            .requestMatchers(HttpMethod.DELETE, CATEGORY_ENDPOINT).hasAnyAuthority(Role.ADMIN.name(), Role.MODO.name())
                            // User endpoint access control
                            .requestMatchers("/api/user/all").hasAuthority(Role.ADMIN.name())
                            .requestMatchers(HttpMethod.GET, USER_ENDPOINT).authenticated()
                            .requestMatchers(HttpMethod.POST, "/api/user").hasAuthority(Role.ADMIN.name())
                            .requestMatchers(HttpMethod.PUT, USER_ENDPOINT).hasAuthority(Role.ADMIN.name())
                            .requestMatchers(HttpMethod.DELETE, USER_ENDPOINT).hasAuthority(Role.ADMIN.name())
                            // Problem endpoint access control
                            .requestMatchers(HttpMethod.GET, PROBLEM_ENDPOINT).permitAll()
                            .requestMatchers(HttpMethod.POST, PROBLEM_ENDPOINT).hasAnyAuthority(Role.ADMIN.name(), Role.MODO.name())
                            .requestMatchers(HttpMethod.PUT, PROBLEM_ENDPOINT).hasAnyAuthority(Role.ADMIN.name(), Role.MODO.name())
                            .requestMatchers(HttpMethod.DELETE, PROBLEM_ENDPOINT).hasAnyAuthority(Role.ADMIN.name(), Role.MODO.name())
                            // DecisionStep and Choice endpoints access control
                            .requestMatchers(HttpMethod.GET,STEP_ENDPOINT).permitAll()
                            .requestMatchers(HttpMethod.POST,STEP_ENDPOINT).hasAnyAuthority(Role.ADMIN.name(), Role.MODO.name())
                            .requestMatchers(HttpMethod.PUT,STEP_ENDPOINT).hasAnyAuthority(Role.ADMIN.name(), Role.MODO.name())
                            .requestMatchers(HttpMethod.DELETE,STEP_ENDPOINT).hasAnyAuthority(Role.ADMIN.name(), Role.MODO.name())
                            // Choice endpoint access control
                            .requestMatchers(HttpMethod.GET,CHOICE_ENDPOINT).permitAll()
                            .requestMatchers(HttpMethod.POST,CHOICE_ENDPOINT).hasAnyAuthority(Role.ADMIN.name(), Role.MODO.name())
                            .requestMatchers(HttpMethod.PUT,CHOICE_ENDPOINT).hasAnyAuthority(Role.ADMIN.name(), Role.MODO.name())
                            .requestMatchers(HttpMethod.DELETE,CHOICE_ENDPOINT).hasAnyAuthority(Role.ADMIN.name(), Role.MODO.name())
                            // News endpoint access control
                            .requestMatchers(HttpMethod.GET, NEWS_ENDPOINT).permitAll()
                            .requestMatchers(HttpMethod.POST, NEWS_ENDPOINT).hasAnyAuthority(Role.ADMIN.name(), Role.MODO.name())
                            .requestMatchers(HttpMethod.PUT, NEWS_ENDPOINT).hasAnyAuthority(Role.ADMIN.name(), Role.MODO.name())
                            .requestMatchers(HttpMethod.DELETE, NEWS_ENDPOINT).hasAnyAuthority(Role.ADMIN.name(), Role.MODO.name())
                            // Image endpoint access control
                            .requestMatchers(HttpMethod.GET, IMAGES_ENDPOINT).permitAll()
                            .requestMatchers(HttpMethod.POST, IMAGES_ENDPOINT).hasAnyAuthority(Role.ADMIN.name(), Role.MODO.name())
                            .requestMatchers(HttpMethod.DELETE, IMAGES_ENDPOINT).hasAnyAuthority(Role.ADMIN.name(), Role.MODO.name())
                            // Any other request must be authenticated
                            .anyRequest().authenticated()
            )
            .build();
    }
}
