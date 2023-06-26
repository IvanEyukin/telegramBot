package LibBaseDto.DtoBaseUser;

public class UserInfo {

    Long id;
    String name;
    String firstName;
    String lastName;
    Integer dateMessage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getDateMessage() {
        return dateMessage;
    }

    public void setDateMessage(Integer dateMessage) {
        this.dateMessage = dateMessage;
    }

    public String getUser() {

        if (getFirstName() != null) {
            return getFirstName();
        } else if (getFirstName() == null && getName() != null) {
            return getName();
        } else {
            return "Друг";
        }

    }

}