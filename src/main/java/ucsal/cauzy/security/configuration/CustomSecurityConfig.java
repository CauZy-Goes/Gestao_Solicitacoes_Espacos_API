package ucsal.cauzy.security.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ucsal.cauzy.security.auth.CustomClientAuthenticationFilter;
import ucsal.cauzy.security.auth.CustomClientAuthenticationProvider;

@Configuration
@RequiredArgsConstructor
@Order(2)
public class CustomSecurityConfig {

    private final CustomClientAuthenticationProvider customProvider;

    @Bean
    public SecurityFilterChain customSecurityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationManager authManager = new ProviderManager(customProvider);

        http
                .securityMatcher("/oauth2/token") // <-- Delimita escopo
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/oauth2/token").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .addFilterBefore(new CustomClientAuthenticationFilter(authManager), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

