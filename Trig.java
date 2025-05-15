public enum Trig {
    SIN, COS;

    public Trig differentiate(){
        return switch (this){
            case SIN -> COS;
            case COS -> SIN;
        };
    }
    public int willBeNegative(){
        return switch (this){
            case SIN -> 1;
            case COS -> -1;
        };
    }
}
