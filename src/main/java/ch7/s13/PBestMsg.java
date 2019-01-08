package ch7.s13;

public final class PBestMsg {
    final PsoValue value;

    public PBestMsg(PsoValue v) {
        value = v;
    }

    public PsoValue getValue() {
        return value;
    }

    public String toString(){
        return value.toString();
    }
}
