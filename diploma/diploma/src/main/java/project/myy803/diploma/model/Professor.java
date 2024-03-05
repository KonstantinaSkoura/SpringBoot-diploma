package project.myy803.diploma.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="professors")
public class Professor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int id;
	
	@Column
	private String name;
	
	@Column
	private String surname;
	
	@Column
	private String specialty;
	
	@OneToMany(mappedBy="professor")
	private List<Subject> subjects;
	
	@OneToMany(mappedBy="professor")
	private List<Thesis> theses;
	
	public Professor() {
		
	}
	
	public Professor(int id, String name, String surname, String specialty) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.specialty = specialty;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	
	public List<Subject> getSubjects() {
		return subjects;
	}
	
	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}
	
	public List<Thesis> getTheses() {
		return theses;
	}
	
	public void setTheses(List<Thesis> theses) {
		this.theses = theses;
	}
	
	@Override
	public String toString() {
		return "Professor [id=" + id + ", name=" + name + ", surname=" + surname + ", specialty=" + specialty + "]";
	}
}
