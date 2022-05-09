package com.ruby.admin.setting;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ruby.common.entity.Setting;
import com.ruby.common.entity.SettingCategory;

public interface SettingRepository extends CrudRepository<Setting, String> {

	public List<Setting> findByCategory(SettingCategory category);

}
