package com.museum.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Table
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Audio_file {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="title")
	private String title; 
	
	@Column(name="discrption")
	private String discription; 
	
	@Column(name="language")
	private String language;
	
	@Column(name="status")
	private Boolean status;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="beacon_id")
	private Beacon Tblbeacon;
	
	@Column(name="audio_file")
	@Lob 
	private byte[] audio_file;
}
