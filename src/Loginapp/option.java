package Loginapp;

public enum option {
    Admin, Firma, Pracownik;

    option() {
    }

    public static option fromvalue(String v) {
        return valueOf(v);
    }

    public String value() {
        return name();
    }

}
