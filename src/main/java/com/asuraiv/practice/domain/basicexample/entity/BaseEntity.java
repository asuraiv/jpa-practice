package com.asuraiv.practice.domain.basicexample.entity;

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
