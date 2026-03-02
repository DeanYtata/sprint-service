package com.tuproyecto.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Component
public class LogFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String correlationId = UUID.randomUUID().toString();
        
        // Agregamos el ID a la cabecera de la petición que va a los microservicios
        exchange.getRequest().mutate()
                .header("X-Correlation-Id", correlationId)
                .build();
        
        System.out.println("Nueva petición con ID: " + correlationId);
        
        return chain.filter(exchange);
    }
}