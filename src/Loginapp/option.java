package Loginapp;

public enum option {
    Admin,Firma,Pracownik;
    private option(){}

    public String value(){
        return  name();
    }
    public static  option fromvalue (String v){
        return valueOf(v);
    }

}
