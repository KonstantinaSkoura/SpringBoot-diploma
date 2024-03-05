package project.myy803.diploma.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="applications")
public class Application {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int id;
		
	@ManyToOne
    @JoinColumn
	private Student student;
	
	@ManyToOne
    @JoinColumn
	private Subject subject;
	
	public Application() {
		
	}
	
	public Application(Student student, Subject subject) {
		this.student = student;
		this.subject = subject;
	}
	
	public Application(int id, Student student, Subject subject) {
		this.id = id;
		this.student = student;
		this.subject = subject;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	
	@Override
	public String toString() {
		return "Student [id=" + id + ", studentId=" + student.getId() + ", subjectId=" + subject.getId() + "]";
	}
}
