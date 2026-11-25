package com.proyecto.veterinaria.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

// Se ejecuta en cada peticion: si trae un token valido, deja pasar al usuario
@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        // el token viene asi: "Bearer eyJhbGciOi..."
        if(header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            if(jwtUtil.validarToken(token)) {
                String correo = jwtUtil.obtenerCorreo(token);
                UsernamePasswordAuthenticationToken usuario =
                        new UsernamePasswordAuthenticationToken(correo, null, new ArrayList<>());
                SecurityContextHolder.getContext().setAuthentication(usuario);
            }
        }

        filterChain.doFilter(request, response);
    }

}
