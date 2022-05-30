package com.ruby.setting;

import java.util.List;

import com.ruby.common.entity.setting.Setting;

public class PaymentSettingBag extends com.ruby.common.entity.setting.SettingBag {

	public PaymentSettingBag(List<Setting> listSettings) {
		super(listSettings);
	}

	public String getURL() {
		return super.getValue("PAYPAL_API_BASE_URL");
	}

	public String getClientID() {
		return super.getValue("PAYPAL_API_CLIENT_ID");
	}

	public String getClientSecret() {
		return super.getValue("PAYPAL_API_CLIENT_SECRET");
	}
}