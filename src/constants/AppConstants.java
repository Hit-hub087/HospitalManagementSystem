package constants;

import java.util.List;

public final class AppConstants {
    private AppConstants() {}

    public static final int PAGE_SIZE = 5;
    public static final String DATE_PATTERN = "dd-MM-uuuu";
    public static final List<String> VALID_TIME_SLOTS = List.of(
            "09:00", "10:00", "11:00", "12:00",
            "14:00", "15:00", "16:00", "17:00"
    );

    public static final String STATUS_SCHEDULED = "Scheduled";
    public static final String STATUS_CANCELLED = "Cancelled";
    public static final String STATUS_COMPLETED = "Completed";
}
