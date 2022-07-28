package fr.dawan.evalnico;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import fr.dawan.evalnico.interceptors.TokenInterceptor;
import no.api.freemarker.java8.Java8ObjectWrapper;

@SpringBootApplication
public class DawanEvalNicoApplication {
	@Autowired
	private TokenInterceptor tokenInterceptor;
	
	public static void main(String[] args) {
		SpringApplication.run(DawanEvalNicoApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer myMvcConfigurer() {
		return new WebMvcConfigurer() {
			// AJOUT D'UN FILTRE
			@Override
			public void addInterceptors(InterceptorRegistry registry) {
				 //registry.addInterceptor(tokenInterceptor);
			}

			// CROS ORIGIN
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH",
						"OPTIONS");
			}

			@Override
			public void addResourceHandlers(ResourceHandlerRegistry registry) {
				registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
			}

		};
	}

	@Configuration
	public class FreemarkerConfig implements BeanPostProcessor {

		@Override
		public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

			if (bean instanceof FreeMarkerConfigurer) {
				FreeMarkerConfigurer configurer = (FreeMarkerConfigurer) bean;

				configurer.getConfiguration()
						.setObjectWrapper(new Java8ObjectWrapper(freemarker.template.Configuration.getVersion()));

			}
			return bean;
		}
	}

}
