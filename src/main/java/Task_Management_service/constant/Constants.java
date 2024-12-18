package Task_Management_service.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
    public final String ASCENDING = "ASC";
    public final String DESCENDING = "DESC";
    public final String GENDER_REGEX = "^(?i)(male|female|others)$";
    public final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
}
