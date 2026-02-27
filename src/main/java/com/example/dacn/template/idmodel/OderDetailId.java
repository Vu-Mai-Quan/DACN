package com.example.dacn.template.idmodel;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Embeddable
@Builder
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class OderDetailId implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1488220545815768246L;
	
	@Column(name="product_id", updatable=false, nullable=false)
	private UUID productId;
	
	@Column(name="order_id", updatable=false, nullable=false)
	private Long orderId;

}
