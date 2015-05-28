package ParseDBCommunicator;

/**
 * Created by Saboor Salaam on 11/29/2014.
 */
public class Job {
    public String name;
    public String description;
    public String major;
    public String company;
    public String date;
    public String type;
    public Number gpa, salary;

    public Job(String name, String description, String major, String company, String date, Number gpa, Number salary, String type) {
        this.name = name;
        this.description = description;
        this.major = major;
        this.company = company;
        this.date = date;
        this.gpa = gpa;
        this.salary = salary;
        this.type = type;
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Number getGpa() {
        return gpa;
    }

    public void setGpa(Number gpa) {
        this.gpa = gpa;
    }

    public Number getSalary() {
        return salary;
    }

    public void setSalary(Number salary) {
        this.salary = salary;
    }
}
