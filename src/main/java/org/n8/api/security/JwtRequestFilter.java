package org.n8.api.security;

import org.n8.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;

/**
 * Filtro de solicitud para validar el token JWT en cada petición y autenticar al usuario si el token es válido.
 * Extiende OncePerRequestFilter para asegurar que el filtro se ejecute una vez por cada solicitud.
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    /**
     * Método que intercepta cada solicitud HTTP y valida el token JWT, autenticando al usuario en el contexto de seguridad si es válido.
     *
     * @param request  Solicitud HTTP entrante.
     * @param response Respuesta HTTP saliente.
     * @param chain    Cadena de filtros a ejecutar.
     * @throws ServletException Si ocurre un error en el procesamiento de la solicitud.
     * @throws IOException      Si ocurre un error de entrada/salida durante el filtrado.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Recupera el token JWT del encabezado de autorización
        final String authorizationHeader = request.getHeader("Authorization");

        String email = null;
        String jwt = null;
        String role = null;

        // Extrae el token JWT del encabezado si está presente y tiene el prefijo "Bearer "
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            email = jwtUtil.extractEmail(jwt);  // Extrae el email del usuario del token
            role = jwtUtil.extractClaims(jwt).get("role", String.class); // Extrae el rol del token
        }

        // Valida el token y establece la autenticación en el contexto de seguridad si es válido
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtUtil.validateToken(jwt, email)) {
                // Crea un objeto UserDetails con el email y rol del usuario
                UserDetails userDetails = new User(email, "", Collections.singletonList(new SimpleGrantedAuthority(role)));

                // Autenticación en el contexto de seguridad con las credenciales del usuario
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        // Continúa con el siguiente filtro en la cadena
        chain.doFilter(request, response);
    }
}
