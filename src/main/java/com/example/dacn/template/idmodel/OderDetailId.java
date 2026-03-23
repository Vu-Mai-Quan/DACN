package com.example.dacn.template.idmodel;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Builder
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class OderDetailId implements Serializable {
	/**
	* 
	*/
	@Serial
    private static final long serialVersionUID = 1488220545815768246L;
	
	@Column(name="product_id", updatable=false, nullable=false)
	private UUID productId;
	
	@Column(name="order_id", updatable=false, nullable=false)
	private Long orderId;

}
