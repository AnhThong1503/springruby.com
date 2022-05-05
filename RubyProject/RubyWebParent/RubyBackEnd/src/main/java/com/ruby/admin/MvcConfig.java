package com.ruby.admin;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// Lấy hình ảnh người dùng

		exposeDirectory("user-photos", registry);

		// Lấy hình ảnh loại sản phẩm

		exposeDirectory("../category-images", registry);

		// Lấy hình ảnh thương hiệu sản phẩm

		exposeDirectory("../brand-logos", registry);

		// Lấy hình ảnh sản phẩm

		exposeDirectory("../product-images", registry);
	}

	private void exposeDirectory(String pathPattern, ResourceHandlerRegistry registry) {
		Path path = Paths.get(pathPattern);
		String absolutePath = path.toFile().getAbsolutePath();

		String logicalPath = pathPattern.replace("../", "") + "/**";

		registry.addResourceHandler(logicalPath).addResourceLocations("file:/" + absolutePath + "/");
	}

}
