package com.museum.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Museum {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "museum")
	private String museumName;

	@Column(name = "address")
	private String address;

	@Column(name = "icon")
	@Lob
	private byte[] icon;

	@Column(name = "default_Audio")
	@Lob
	private byte[] default_Audio;

	public Museum(Long id, byte[] default_Audio) {
		super();
		this.id = id;
		this.default_Audio = default_Audio;
	}

}
