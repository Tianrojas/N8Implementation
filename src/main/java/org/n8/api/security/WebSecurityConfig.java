package org.n8.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Clase de configuración de seguridad para la aplicación. Define las reglas de acceso
 * a rutas específicas, habilita CORS globalmente y configura la autenticación JWT
 * para proteger los endpoints.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    /**
     * Configura el filtro de CORS globalmente para la aplicación.
     * Esto permite solicitudes desde dominios específicos, definiendo los métodos,
     * encabezados y credenciales permitidos.
     *
     * @return CorsFilter Configuración global de CORS.
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // Permite el uso de credenciales (cookies, headers de autorización, etc.)
        config.addAllowedOrigin("http://localhost:3000"); // Cambia a tu dominio frontend en producción
        config.addAllowedHeader("*"); // Permite todos los encabezados
        config.addAllowedMethod("*"); // Permite todos los métodos HTTP
        source.registerCorsConfiguration("/**", config); // Aplica esta configuración a todas las rutas
        return new CorsFilter(source);
    }

    /**
     * Configura el acceso a los endpoints de la aplicación, habilitando CORS
     * y definiendo las reglas de autenticación y autorización para cada ruta.
     *
     * La aplicación opera en un modo sin estado (stateless) debido al uso de JWT
     * para la autenticación.
     *
     * @param http El objeto HttpSecurity utilizado para definir las reglas de acceso.
     * @throws Exception Manejo de posibles excepciones en la configuración de seguridad.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // Deshabilita CSRF ya que usamos autenticación basada en tokens (JWT)
                .cors() // Habilita el soporte de CORS
                .and()
                .authorizeRequests()
                // Endpoints accesibles sin autenticación
                .antMatchers("/login.html", "/register.html", "/style.css", "/login.js", "/register.js").permitAll()
                // Reglas de acceso basado en rol
                .antMatchers("/user/customer.html").hasAuthority("Customer")
                .antMatchers("/user/hoster.html").hasAuthority("Hoster")
                // Permitir endpoints de login y registro sin autenticación
                .antMatchers("/users/login", "/users/register").permitAll()
                // Reglas de acceso detalladas para clientes y hosters
                .antMatchers("/users/**/tickets/**").hasAuthority("Customer")
                .antMatchers("/users/**/venues/**").hasAuthority("Hoster")
                // Todos los demás endpoints requieren autenticación
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                // Configura la sesión para que sea stateless, eliminando sesiones de servidor
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Añade el filtro de JWT antes del filtro de autenticación de usuario/contraseña
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * Permite que ciertos recursos estáticos se ignoren en la configuración de seguridad.
     * Así, archivos como CSS, JS y otros recursos no requieren autenticación.
     *
     * @param web El objeto WebSecurity para definir las rutas ignoradas.
     * @throws Exception Manejo de posibles excepciones en la configuración de seguridad.
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**", "/static/**", "/user/**");
    }
}
