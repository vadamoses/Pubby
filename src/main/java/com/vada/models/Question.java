package com.vada.models;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Component
@Table(name = "question")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "qId", "qContext", "qAnswers", "qText" })
public class Question implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("qId")
	private int qId;
	@JsonProperty("qContext")
	private String qContext;
	@JsonProperty("qAnswers")
	private List<String> qAnswers;
	@JsonProperty("qText")
	private String qText;

	/**
	 * No args constructor for use in serialization
	 *
	 */
	public Question() {
	}
	
	/**
	 *
	 * @param qContext
	 * @param qText
	 * @param qAnswers
	 */
	public Question(String qContext, List<String> qAnswers, String qText) {
		super();
		this.qContext = qContext;
		this.qAnswers = qAnswers;
		this.qText = qText;
	}

	/**
	 *
	 * @param qContext
	 * @param qId
	 * @param qText
	 * @param qAnswers
	 */
	public Question(int qId, String qContext, List<String> qAnswers, String qText) {
		super();
		this.qId = qId;
		this.qContext = qContext;
		this.qAnswers = qAnswers;
		this.qText = qText;
	}

	@JsonProperty("qId")
	public int getqId() {
		return qId;
	}

	/**
	 * @param qId the qId to set
	 */
	public void setqId(int qId) {
		this.qId = qId;
	}

	@JsonProperty("qContext")
	public String getqContext() {
		return qContext;
	}

	@JsonProperty("qContext")
	public void setqContext(String qContext) {
		this.qContext = qContext;
	}

	@JsonProperty("qAnswers")
	public List<String> getqAnswers() {
		return qAnswers;
	}

	@JsonProperty("qAnswers")
	public void setqAnswers(List<String> qAnswers) {
		this.qAnswers = qAnswers;
	}

	@JsonProperty("qText")
	public String getqText() {
		return qText;
	}

	@JsonProperty("qText")
	public void setqText(String qText) {
		this.qText = qText;
	}

	@Override
	public String toString() {
		return "Question [qId=" + qId + ", qContext=" + qContext + ", qAnswers=" + qAnswers + ", qText=" + qText + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(qAnswers, qContext, qId, qText);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Question)) {
			return false;
		}
		Question other = (Question) obj;
		return Objects.equals(qAnswers, other.qAnswers) && Objects.equals(qContext, other.qContext)
				&& Objects.equals(qId, other.qId) && Objects.equals(qText, other.qText);
	}

}