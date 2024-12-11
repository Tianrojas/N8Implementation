package org.n8.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Clase JwtUtil que maneja la generación, extracción y validación de tokens JWT.
 * Los tokens generados contienen la identidad y el rol del usuario y están firmados
 * usando una clave secreta configurada en application.properties.
 */
@Component
public class JwtUtil {

    // Clave secreta para la firma del token, inyectada desde application.properties
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    /**
     * Genera un token JWT para el usuario que contiene su email y rol.
     * El token tiene una expiración de 10 horas.
     *
     * @param email Correo electrónico del usuario para identificarlo en el token.
     * @param role Rol del usuario (por ejemplo, "Customer", "Hoster").
     * @return Un string que representa el token JWT.
     */
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 7 días
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /**
     * Extrae todos los claims (información) del token JWT.
     * Los claims contienen información útil, como el correo electrónico y el rol del usuario.
     *
     * @param token El token JWT del cual se extraen los claims.
     * @return Un objeto Claims que contiene los datos extraídos del token.
     */
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Extrae el correo electrónico del usuario (subject) del token JWT.
     *
     * @param token El token JWT del cual se extrae el correo electrónico.
     * @return Un string con el correo electrónico del usuario.
     */
    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    /**
     * Verifica si el token JWT ha expirado comparando la fecha de expiración con la fecha actual.
     *
     * @param token El token JWT a verificar.
     * @return true si el token ha expirado; false en caso contrario.
     */
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    /**
     * Valida el token JWT comparando el correo electrónico del token con el proporcionado
     * y verificando que el token no haya expirado.
     *
     * @param token El token JWT a validar.
     * @param userEmail Correo electrónico del usuario que se compara con el del token.
     * @return true si el token es válido (correo y expiración coinciden); false en caso contrario.
     */
    public boolean validateToken(String token, String userEmail) {
        return (extractEmail(token).equals(userEmail) && !isTokenExpired(token));
    }
}
