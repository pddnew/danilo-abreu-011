package com.classsocialnet.model;

public class Student {

	private String rm;
	
	public Student(String rm){
		this.rm = rm;
	}

	public String getRm() {
		return rm;
	}

	public void setRm(String rm) {
		this.rm = rm;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rm == null) ? 0 : rm.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (rm == null) {
			if (other.rm != null)
				return false;
		} else if (!rm.equals(other.rm))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.rm;
	}
}
