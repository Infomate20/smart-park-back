package smartPark.smart_park.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import smartPark.smart_park.security.CustomUserDetailsService;
import smartPark.smart_park.security.JwtAuthenticationEntryPoint;
import smartPark.smart_park.security.JwtAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    @Autowired
    private final CustomUserDetailsService userDetailsService;
    @Autowired
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Origines autorisées, configurables par {@code CORS_ALLOWED_ORIGINS}
     * (liste séparée par des virgules). Cette configuration est la seule
     * autorité en matière de CORS : aucun contrôleur ne doit porter de
     * {@code @CrossOrigin}, sous peine de rétablir les divergences que cette
     * centralisation corrige.
     */
    @Value("${app.cors.allowed-origins:http://localhost:4200}")
    private List<String> allowedOrigins;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        // allowCredentials(true) interdit la valeur '*' : les origines doivent
        // être énumérées explicitement.
        config.setAllowedOrigins(allowedOrigins);
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authz -> authz
                        // ---------------------------------------------------------------
                        // 1. API : le seul point d'entrée public est l'authentification.
                        //    L'ordre est significatif — les règles /api/** doivent être
                        //    déclarées AVANT la règle des routes SPA plus bas, sans quoi
                        //    l'API redeviendrait accessible sans authentification.
                        // ---------------------------------------------------------------
                        .requestMatchers("/api/auth/**").permitAll()
                        // Endpoints pour ADMIN seulement
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        // Endpoints pour ADMIN et TECHNICIEN
                        .requestMatchers("/api/tech/**").hasAnyRole("ADMIN", "TECHNICIEN")
                        // Tout le reste de l'API exige un jeton valide. Le contrôle fin
                        // des rôles est assuré par @PreAuthorize sur les contrôleurs.
                        .requestMatchers("/api/**").authenticated()

                        // ---------------------------------------------------------------
                        // 2. Ressources statiques du SPA Angular
                        // ---------------------------------------------------------------
                        .requestMatchers("/", "/index.html", "/favicon.ico").permitAll()
                        .requestMatchers("/*.js", "/*.css", "/*.png", "/*.jpg", "/*.svg", "/*.ico").permitAll()
                        .requestMatchers("/media/**", "/assets/**").permitAll()
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()

                        // ---------------------------------------------------------------
                        // 3. Routes Angular : un chemin sans extension est une route du
                        //    SPA, renvoyée vers index.html par SpaFallbackController.
                        //    Les chemins /api/** ont déjà été traités au-dessus.
                        // ---------------------------------------------------------------
                        .requestMatchers(request -> !request.getRequestURI().contains(".")).permitAll()

                        // Tous les autres endpoints necessitate une authentification
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}