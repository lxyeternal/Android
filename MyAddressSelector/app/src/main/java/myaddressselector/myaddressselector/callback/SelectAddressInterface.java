package myaddressselector.myaddressselector.callback;

public interface SelectAddressInterface {

	/**
	 * 用于地址选择器完成选择后更新最新的地址
	 * @param area
	 */
	void setAreaString(String area);

	void setAreaString(String currentProvinceName, String currentCityName, String currentDistrictName, String currentZipCode);

	void setAreaString(String currentProvinceName, String currentCityName);
}
