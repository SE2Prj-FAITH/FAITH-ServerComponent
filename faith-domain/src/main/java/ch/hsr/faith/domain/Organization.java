package ch.hsr.faith.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Organization {

	@Id
	@GeneratedValue
	private long Id;

	private String name;
	private int state;
	private String address;
	private int zip;
	private String city;
	private String homepage;

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	@Override
	public boolean equals(Object obj) {
		Organization other = (Organization) obj;
		return this.getAddress().equals(other.getAddress())
				&& this.getCity().equals(other.getCity())
				&& this.getHomepage().equals(other.getHomepage())
				&& this.getId() == other.getId()
				&& this.getName().equals(other.getName())
				&& this.getState() == other.getState()
				&& this.getZip() == other.getZip();
	}

}
