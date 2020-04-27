package com.yq.shopping.po;

import java.io.Serializable;
import java.util.List;

/**
 * 规格组合实体类
 * 
 * @author HaiLin
 *
 */
public class TbSpecification implements Serializable {

	private Specification specification;// 一方

	private List<SpecificationOption> specificationOptionList;// 多方

	public Specification getSpecification() {
		return specification;
	}

	public List<SpecificationOption> getSpecificationOptionList() {
		return specificationOptionList;
	}

	public void setSpecification(Specification specification) {
		this.specification = specification;
	}

	public void setSpecificationOptionList(List<SpecificationOption> specificationOptionList) {
		this.specificationOptionList = specificationOptionList;
	}

	@Override
	public String toString() {
		return "TbSpecification [specification=" + specification + ", specificationOptionList="
				+ specificationOptionList + "]";
	}

}
