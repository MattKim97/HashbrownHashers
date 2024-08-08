package learn.hashbrown_hashers.security;


import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtConverter converter;

    public SecurityConfig(JwtConverter converter) {
        this.converter = converter;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors() // Enable CORS before other configurations
                .and()
                .csrf().disable() // Disable CSRF protection
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/**").permitAll() // Allow GET requests to all endpoints
                .antMatchers(HttpMethod.POST, "/**").permitAll() // Allow GET requests to all endpoints
                .antMatchers(HttpMethod.DELETE, "/**").permitAll() // Allow GET requests to all endpoints
                .antMatchers(HttpMethod.PUT, "/**").permitAll() // Allow GET requests to all endpoints
                .antMatchers("/api/user/authenticate").permitAll() // Allow unauthenticated access to authenticate endpoint
                .antMatchers("/api/user/current-user").permitAll() // Allow unauthenticated access to authenticate endpoint
                .antMatchers("/api/user/register").permitAll() // Allow unauthenticated access to register endpoint
                .antMatchers("/api/admin/**").hasRole("ADMIN") // Require ADMIN role for /api/admin/**
                .anyRequest().authenticated() // Require authentication for all other requests
                .and()
                .addFilter(new JwtRequestFilter(authenticationManager(), converter)) // Add custom JWT filter
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Use stateless sessions
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("*");
            }
        };
    }

    @Bean
    public HandlerMappingIntrospector handlerMappingIntrospector() {
        return new HandlerMappingIntrospector();
    }

}