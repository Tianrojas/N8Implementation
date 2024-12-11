package org.n8.api.security;

import io.jsonwebtoken.ExpiredJwtException;
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

        final String authorizationHeader = request.getHeader("Authorization");
        String email = null;
        String jwt = null;
        String role = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                email = jwtUtil.extractEmail(jwt);
                role = jwtUtil.extractClaims(jwt).get("role", String.class);
            } catch (ExpiredJwtException e) {
                // Manejar tokens expirados: Permitir al cliente renovar el token
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token expirado");
                return; // Detiene el procesamiento de la solicitud
            }
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtUtil.validateToken(jwt, email)) {
                UserDetails userDetails = new User(email, "", Collections.singletonList(new SimpleGrantedAuthority(role)));
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }
}
