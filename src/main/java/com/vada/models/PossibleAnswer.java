package com.vada.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "possible_answers")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "answerId", "accurate", "answerText" })
public class PossibleAnswer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long answerId;
	@Column(name = "accurate")
	private boolean accurate;
	@Column(name = "answerText")
	private String answerText;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "question_id")
	@JsonBackReference
	private Question question;

}
