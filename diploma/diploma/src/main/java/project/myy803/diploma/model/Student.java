package project.myy803.diploma.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="students")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int id;
	
	@Column
	private String name;
	
	@Column
	private String surname;
	
	@Column
	private int yearOfStudies;
	
	@Column
	private double avgGrade;
	
	@Column
	private int remainingCourses;
	
	@OneToMany(mappedBy="student")
	private List<Application> applications;
	
	@OneToOne(mappedBy = "student")
    private Thesis thesis;

	public Student() {
		
	}
	
	public Student(int id, String name, String surname, int yearOfStudies, double avgGrade, int remainingCourses, List<Application> applications, Thesis thesis) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.yearOfStudies = yearOfStudies;
		this.avgGrade = avgGrade;
		this.remainingCourses = remainingCourses;
		this.applications = applications;
		this.thesis = thesis;
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

	public int getYearOfStudies() {
		return yearOfStudies;
	}

	public void setYearOfStudies(int yearOfStudies) {
		this.yearOfStudies = yearOfStudies;
	}

	public double getAvgGrade() {
		return avgGrade;
	}

	public void setAvgGrade(double avgGrade) {
		this.avgGrade = avgGrade;
	}

	public int getRemainingCourses() {
		return remainingCourses;
	}

	public void setRemainingCourses(int remainingCourses) {
		this.remainingCourses = remainingCourses;
	}
	
	public List<Application> getApplications() {
		return applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}
	
	public Thesis getThesis() {
		return thesis;
	}

	public void setThesis(Thesis thesis) {
		this.thesis = thesis;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", surname=" + surname + ", yearOfStudies=" + yearOfStudies + ", avgGrade=" + avgGrade + ", remainingCourses=" + remainingCourses + "]";
	}
}
