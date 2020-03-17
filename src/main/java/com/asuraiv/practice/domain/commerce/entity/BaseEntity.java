package com.asuraiv.practice.domain.commerce.entity;

import com.mysql.cj.conf.PropertyDefinitions;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.MappedSuperclass;
import java.util.Date;

@Getter
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity {

	private Date createdAt;

	private Date updatedAt;

	public BaseEntity(Date createdAt, Date updatedAt) {
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
}
