package com.sistemaOficina.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sistemaOficina.backend.jwt.JwtAuthenticationEntryPoint;
import com.sistemaOficina.backend.jwt.JwtAuthorizationFilter;

@EnableMethodSecurity
@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final String[] DOCUMENTATION_OPENAPI = {
        "/docs/index.html",
        "/docs-park.html", "/docs-park/**",
        "/v3/api-docs/**",
        "/swagger-ui-custom.html", "/swagger-ui.html", "/swagger-ui/**",
        "/**.html", "/webjars/**", "/configuration/**", "/swagger-resources/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .authorizeHttpRequests(auth -> auth

                .requestMatchers(HttpMethod.GET, "/api/modelos/**").authenticated() // Exemplo de autenticação obrigatória
                .requestMatchers(HttpMethod.PUT, "/api/modelos/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/api/modelos/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/veiculos/**").authenticated() // Para todos os endpoints de veiculo
                .requestMatchers(HttpMethod.PUT, "/api/veiculos/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/api/veiculos/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/servicos/**").authenticated() // Todos os endpoints de serviços
                .requestMatchers(HttpMethod.PUT, "/api/servicos/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/api/servicos/**").authenticated()
                .requestMatchers(DOCUMENTATION_OPENAPI).permitAll()
                .requestMatchers(HttpMethod.POST, "api/v1/usuarios").permitAll()
                .requestMatchers(HttpMethod.POST, "api/v1/auth").permitAll()
                // Adicione mais rotas conforme necessário
                .anyRequest().authenticated()
                ).sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ).addFilterBefore(
                        jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class
                ).exceptionHandling(ex -> ex
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                ).build();
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
