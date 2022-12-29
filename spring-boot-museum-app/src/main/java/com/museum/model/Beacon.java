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
@Table(name="Audio")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Beacon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="beacon_name")
	private String beacon_name;
	
	
	
	@Column(name="status")
	private Boolean status;
	
	@Column(name="description")
	private String description;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="museum_id")
	private Museum Tblmuseum;

	
	

	
}
