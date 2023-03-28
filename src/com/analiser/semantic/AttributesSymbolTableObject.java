package com.analiser.semantic;

public class AttributesSymbolTableObject {

	private String nameAttribute;
	private String typeAttribute;

	public AttributesSymbolTableObject(String nameAttribute, String typeAttribute) {

		this.nameAttribute = nameAttribute;
		this.typeAttribute = typeAttribute;

	}

	public AttributesSymbolTableObject() {
		
	}

	public String getNameAttribute() {
		return nameAttribute;
	}

	public void setNameAttribute(String nameAttribute) {
		this.nameAttribute = nameAttribute;
	}

	public String getTypeAttribute() {
		return typeAttribute;
	}

	public void setTypeAttribute(String typeAttribute) {
		this.typeAttribute = typeAttribute;
	}

}
