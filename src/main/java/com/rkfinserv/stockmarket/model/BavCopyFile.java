package com.rkfinserv.stockmarket.model;

import com.rkfinserv.stockmarket.dto.BavCopyDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Builder
@ToString
@Document
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BavCopyFile {
	@EqualsAndHashCode.Include
	@Id
	private String name;
}
