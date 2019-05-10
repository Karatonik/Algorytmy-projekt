package Model;

public enum Option {
    Admin, Firma, Pracownik;

    Option() {
    }

    public static Option fromvalue(String v) {
        return valueOf(v);
    }

    public String value() {
        return name();
    }

}
