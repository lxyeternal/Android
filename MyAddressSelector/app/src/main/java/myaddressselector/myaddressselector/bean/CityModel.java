package myaddressselector.myaddressselector.bean;

import java.util.List;

public class CityModel extends ModuleBase {
	public String name;
	public String id;
	public String pinyin;

	public CityModel(String name, String id, String pinyin) {
		this.name = name;
		this.id = id;
		this.pinyin = pinyin;
	}
	public List<DistrictModel> districtList;
	
	public CityModel() {
		super();
	}
	public CityModel(String name, List<DistrictModel> districtList) {
		super();
		this.name = name;
		this.districtList = districtList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DistrictModel> getDistrictList() {
		return districtList;
	}

	public void setDistrictList(List<DistrictModel> districtList) {
		this.districtList = districtList;
	}

	@Override
	public String toString() {
		return "CityModel [name=" + name + ", districtList=" + districtList
				+ "]";
	}

	
}
