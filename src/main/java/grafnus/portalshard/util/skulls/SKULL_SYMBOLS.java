package grafnus.portalshard.util.skulls;

public enum SKULL_SYMBOLS {

    ARROW_UP("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTRhMDRiMzJkMmI3NTk4ZjlkZDhlMjNmYjQwMTVjNjljM2NkOTQyYTM3YTllYTg0ZDA2ODY5ZjQ1OWYxIn19fQ=="),
    GLOBE("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOThkYWExZTNlZDk0ZmYzZTMzZTFkNGM2ZTQzZjAyNGM0N2Q3OGE1N2JhNGQzOGU3NWU3YzkyNjQxMDYifX19");

    private String base64;

    SKULL_SYMBOLS(String base64) {
        this.base64 = base64;
    }

    @Override
    public String toString() {
        return base64;
    }
}
