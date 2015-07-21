package org.samples.javaee7.arquillian;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Employee implements Serializable {

    private String firstname;
    private String lastname;

    public Employee(@JsonProperty("firstname") String firstname, @JsonProperty("lastname") String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Employee employee = (Employee) o;

        if (firstname != null ? !firstname.equals(employee.firstname) : employee.firstname != null)
            return false;
        return !(lastname != null ? !lastname.equals(employee.lastname) : employee.lastname != null);

    }

    @Override
    public int hashCode() {
        int result = firstname != null ? firstname.hashCode() : 0;
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Employee{" + "firstname='" + firstname + '\'' + ", lastname='" + lastname + '\'' + '}';
    }
}
