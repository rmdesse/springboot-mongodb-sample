package org.home.devhub.faq.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document (collection = "Faq")
public class Faq {

	@Transient
    public static final String SEQUENCE_NAME = "faq_sequence";
	
	@Id
	private long id;
	
	@NotBlank
    @Size(max=100)
    @Indexed(unique=true)
	private String question;
	private String answer;
	
	@NotBlank
    @Indexed(unique=true)
	private Integer position;
	
	
	
	public Faq() {
		
	}
	
	public Faq(String question, String answer) {
		this.question = question;
		this.answer = answer;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	@Override
	public String toString() {
		return "Faq [id=" + id + ", question=" + question + ", answer=" + answer 
				+ "position=" + position + "]";
	}	
}
