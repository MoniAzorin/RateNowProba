package com.RateNowProba.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties({ "id" })
@Entity(name = "Chart")
public class Chart implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
		
    @NotNull
    @Size(min=2, max=30)
	private String title;
		
	private int[] valuestoShow;
	
	
	public Chart() {
	
	}

	public Chart(String title, int[] valuestoShow) {
		this.title = title;
		this.valuestoShow=valuestoShow;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int[] getValuestoShow() {
		return valuestoShow;
	}

	public void setValuestoShow(int[] valuestoShow) {
		this.valuestoShow = valuestoShow;
	}

	
	
	

}
