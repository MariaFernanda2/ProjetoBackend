package br.com.itau.desafioApi.produtosDeSeguros;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProdutosDeSegurosApplication {

	@Bean
	MeterRegistryCustomizer<PrometheusMeterRegistry> metricsCommonTags() {
		return registry -> registry.config().commonTags("application", "ProdutosDeSegurosApplication");
	}

	public static void main(String[] args) {
		SpringApplication.run(ProdutosDeSegurosApplication.class, args);
	}
}
