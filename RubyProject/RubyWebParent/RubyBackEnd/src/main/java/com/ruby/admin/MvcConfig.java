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
		String dirName = "user-photos";

		Path userPhotosDir = Paths.get(dirName);

		String userPhotosPath = userPhotosDir.toFile().getAbsolutePath();

		registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:/" + userPhotosPath + "/");

		// Lấy hình ảnh loại sản phẩm
		String categoryImagesName = "../category-images";

		Path categoryImagesDir = Paths.get(categoryImagesName);

		String categoryImagePath = categoryImagesDir.toFile().getAbsolutePath();

		registry.addResourceHandler("/category-images/**").addResourceLocations("file:/" + categoryImagePath + "/");

		// Lấy hình ảnh thương hiệu sản phẩm
		String brandLogosDirName = "../brand-logos";

		Path brandLogosDir = Paths.get(brandLogosDirName);

		String brandLogosPath = brandLogosDir.toFile().getAbsolutePath();

		registry.addResourceHandler("/brand-logos/**").addResourceLocations("file:/" + brandLogosPath + "/");

	}

}
