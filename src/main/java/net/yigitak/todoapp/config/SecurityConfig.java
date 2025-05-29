package net.yigitak.todoapp.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain ( HttpSecurity http ) throws Exception {

    http.cors( Customizer.withDefaults() )

        .authorizeHttpRequests( authorize -> authorize.anyRequest().authenticated() )

        .oauth2ResourceServer( o -> {
          o.jwt( Customizer.withDefaults() );
        } );

    return http.build();
  }


  @Bean
  public CorsConfigurationSource corsConfigurationSource ( ) {

    CorsConfiguration cfg = new CorsConfiguration();
    cfg.setAllowedOrigins( List.of( "http://localhost:5173" ) );   // SPA origin
    cfg.setAllowedMethods( List.of( "GET" , "POST" , "PUT" , "DELETE" , "OPTIONS" ) );
    cfg.setAllowedHeaders( List.of( "*" ) );
    cfg.setAllowCredentials( true ); // only if you send cookies; OK for Bearer-token too

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration( "/**" , cfg ); // apply to every endpoint
    return source;
  }

}

