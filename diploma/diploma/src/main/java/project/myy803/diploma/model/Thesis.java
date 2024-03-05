package project.myy803.diploma.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="theses")
public class Thesis {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int id;
	
	@OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;
	
	@OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private Subject subject;
	
	@ManyToOne
    @JoinColumn(name="professor_id")
	private Professor professor;
	
	@Column
	private double implementationGrade;
	
	@Column
	private double reportGrade;
	
	@Column
	private double presentationGrade;
	
	@Column
	private boolean archived;
	
	public Thesis() {
		
	}
	
	public Thesis(Student student, Subject subject, Professor professor, boolean archived) {
		this.student = student;
		this.subject = subject;
		this.professor = professor;
		this.archived = archived;
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
	
	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public Double getImplementationGrade() {
		return implementationGrade;
	}

	public void setImplementationGrade(Double implementationGrade) {
		this.implementationGrade = implementationGrade;
	}

	public Double getReportGrade() {
		return reportGrade;
	}

	public void setReportGrade(Double reportGrade) {
		this.reportGrade = reportGrade;
	}

	public Double getPresentationGrade() {
		return presentationGrade;
	}

	public void setPresentationGrade(Double presentationGrade) {
		this.presentationGrade = presentationGrade;
	}
	
	public boolean getArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}
	
	@Override
	public String toString() {
		return "Thesis [id=" + id + ", studentId=" + student.getId() + ", subjectId=" + subject.getId() + ", professorId=" + professor.getId() + "]";
	}
}
