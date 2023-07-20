package bot.entitie;


public class User {

    private final String NOTIFICATION_DEFAULT = "all";
    private final String USER = "Друг";

    Long id;
    String name;
    String firstName;
    String lastName;
    Integer dateMessage;
    String notification;
    Boolean isInDatabase = false;

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

    public String getNotification() {
        if (notification == null) {
            return NOTIFICATION_DEFAULT;
        } else {
            return notification;
        }
    }
    public void setNotification(String notification) {
        this.notification = notification;
    }

    public Boolean getIsInDatabase() {
        return isInDatabase;
    }
    public void setIsInDatabase(Boolean isInDatabase) {
        this.isInDatabase = isInDatabase;
    }

    public String getUser() {
        if (getFirstName() != null) {
            return getFirstName();
        } else if (getFirstName() == null && getName() != null) {
            return getName();
        } else {
            return USER;
        }
    }
}