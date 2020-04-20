package enums;

public enum Resolution {
    /**
     * Only resolutions with ratio 16:9, for now.
     * More will be added in the future.
     * **/
    RESOLUTION_854_480 (0),
    RESOLUTION_1280_720 (1),
    RESOLUTION_1600_900 (2),
    RESOLUTION_1920_1080 (3),
    RESOLUTION_2560_1440(4),
    RESOLUTION_3840_2160(5);

    int resolutionValue;

    Resolution(int resolutionValue) {
        this.resolutionValue = resolutionValue;
    }

    public int getResolutionValue() {
        return resolutionValue;
    }

    public static Resolution getResolution(int width, int height) {
        switch (height) {
            case 480:
                return RESOLUTION_854_480;
            case 720:
                return RESOLUTION_1280_720;
            case 900:
                return RESOLUTION_1600_900;
            case 1080:
            default:
                return RESOLUTION_1920_1080;
            case 1440:
                return RESOLUTION_2560_1440;
            case 2160:
                return RESOLUTION_3840_2160;
        }
    }

    public int[] getResolution() {
        switch (this) {
            case RESOLUTION_854_480:
                return new int[]{853, 480};
            case RESOLUTION_1280_720:
                return new int[]{1280, 720};
            case RESOLUTION_1600_900:
                return new int[]{1600, 900};
            case RESOLUTION_1920_1080:
            default:
                return new int[]{1920, 1080};
            case RESOLUTION_2560_1440:
                return new int[]{2560, 1440};
            case RESOLUTION_3840_2160:
                return new int[]{3840, 2160};
        }
    }

    public String toString() {
        switch (this) {
            case RESOLUTION_854_480:
                return "854x480";
            case RESOLUTION_1280_720:
                return "1280x720";
            case RESOLUTION_1600_900:
                return "1600x900";
            case RESOLUTION_1920_1080:
            default:
                return "1920x1080";
            case RESOLUTION_2560_1440:
                return "2560x1440";
            case RESOLUTION_3840_2160:
                return "3840x2160";
        }
    }
}
