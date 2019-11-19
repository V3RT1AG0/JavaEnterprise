package swehw3;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;


// Managed bean for the survey form
@Entity
@Table(name = "student", schema="swehw3_db", uniqueConstraints = { @UniqueConstraint(columnNames = "id") })
@ManagedBean(name = "student", eager = true)
public class Student implements Serializable {

	private static final long serialVersionUID = -6907715579092107438L;

	private long id;

	private String lastname;
	private String firstname;
	private String address;
	private String city;
	private String state;
	private String zipcode;
	private String email;
	@Transient
	private String[] likeCampus;
	private String likeCampus_db;
	private String interestUniversity;
	private String recommend;
	private String comments;
	private String raffle;
	private String phoneNum;
	private String date;

	@Transient
	ArrayList<Student> surveyDetailList = new ArrayList<Student>();

	@Transient
	@ManagedProperty(value = "#{winningResult}")
	Result winResult;

	StudentService ss;
	
	// Getters/Setters for various form cells

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, unique = true, nullable = false)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "likeCampus")
	public String getLikeCampus_db() {
		return likeCampus_db;
	}

	public void setLikeCampus_db(String likeCampus_db) {
		this.likeCampus_db = likeCampus_db;
	}

	@Transient
	public Result getWinResult() {
		return winResult;
	}

	@Transient
	public void setWinResult(Result winResult) {
		this.winResult = winResult;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getRaffle() {
		return raffle;
	}

	public void setRaffle(String raffle) {
		this.raffle = raffle;
	}

	public String getRecommend() {
		return recommend;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}

	@Transient
	public String[] getLikeCampus() {
		return likeCampus;
	}

	@Transient
	public void setLikeCampus(String[] likeCampus) {
		this.likeCampus = likeCampus;
	}

	@Column(name = "formDate")
	public String getDate() {
		return date;
	}

	public String getInterestUniversity() {
		return interestUniversity;
	}

	public void setInterestUniversity(String interestUniversity) {
		this.interestUniversity = interestUniversity;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	// Method to calculate mean and standard deviation using business logic StudentService.

	@Transient
	public String register() throws IOException {

		try {
			ss = new StudentService(lastname, firstname, address, city, state, zipcode, email, likeCampus,
					interestUniversity, recommend, comments, raffle, phoneNum, date);
			ss.evaluateRaffle();
			winResult.setMean(ss.getMeanValue());
			winResult.setStandardDeviation(ss.getSDValue());
			ss.writeToDB();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (winResult.getMean() > 90)
			return "WinnerAcknowledgement";
		else
			return "SimpleAcknowledgement";

	}

	
	// returns survey list data
	@Transient
	public ArrayList<Student> getSurveyData() throws IOException {
		ss = new StudentService();
		surveyDetailList = ss.displaySurveyData();
		return surveyDetailList;
	}

	// Method for populating the drop down
	@Transient
	public List<String> completeRecommendation(String query) {
		List<String> results = new ArrayList<String>();
		results.add("Very Likely");
		results.add("Likely");
		results.add("Unlikely");
		return results;
	}

}