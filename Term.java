import java.util.ArrayList;

public class Term{


    public double degree;//constant terms will have a degree of 0
    public double coefficient; //if constant, the coefficient is the value 
    public Term value; //if the term is a constant, this will be null
    public Trig trig; 

    public Term(double coefficient){//constant declaration
        this.coefficient = coefficient;
        this.value = null;
        this.degree = degree;
        this.trig = null;
    }
    
    public Term(double coefficient, double degree){
        this.coefficient = coefficient;
        this.value = null;
        this.degree = degree;
        this.trig = null;
    }
    
    
    public Term(double coefficient, double degree, Trig trig){
        this.coefficient = coefficient;
        this.value = null;
        this.degree = degree;
        this.trig = trig;
    }
    public Term(double coefficient,  double degree, Term value){
        this.coefficient = coefficient;
        this.value = value;
        this.degree = degree;
        this.trig = null;
    }
    public Term(double coefficient, double degree, Term value, Trig trig){
        this.coefficient = coefficient;
        this.value = value;
        this.degree = degree;
        this.trig = trig;
    }

    public Term powerRule(){
        
        if(coefficient != 0 && degree != 0){
            Term newTerm = new Term(coefficient*degree, degree-1, value, trig);
            return newTerm;
        }
        else{
            return this;
        }
        
        
    }

    public boolean isDifferentiable(){
        if(value == null && degree == 0){
            return false;
        }
        if(value != null){
            return value.isDifferentiable();
        }
        return true;
        
    }

    public Term trigDiff(){
        if(trig != null){
            Term newTerm = new Term(trig.willBeNegative(), 1, value, trig.differentiate());
            return newTerm;
        }
        else{
            return this;
        }
    
    }

    public ArrayList<Term> differentiate(){
        ArrayList<Term> terms = new ArrayList<>();

        if(!isDifferentiable()){
            throw new IllegalArgumentException("The term is not differentiable");
        }
        
        terms.add(this.powerRule());

        if(trig != null){
            terms.add(this.trigDiff());
        }
        
        if(value != null && value.isDifferentiable()){
            
            terms.addAll(value.differentiate());
        }
        

        return terms;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        if(degree == 0){
            return coefficient + "";
        }
        if(coefficient == 0){
            return "0";
        }
        if(coefficient<0){
            sb.append("-");
        }
        if(coefficient != 1){
            sb.append(Math.abs(coefficient));
        }
        
        
        if(trig != null){
            sb.append("*").append(trig.toString()).append("(");
        }
        if(value == null){
            sb.append("x");
        }
        else{
            sb.append(value.toString());
        }
        if(trig!=null){
            sb.append(")");
        }
        if(degree == 1){
            return sb.toString();
        }
        else{
            sb.append("^").append(degree);
        }
        return sb.toString();

    }
    public static String toString(ArrayList<Term> termList){
        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i < termList.size(); i++){
            Term curr = termList.get(i);
            sb.append(curr.toString());
            if(i != termList.size()-1){
                sb.append(" * ");
            }
        }
        return sb.toString();
    }
    public static ArrayList<Term> simplify(ArrayList<Term> terms){
        ArrayList<Term> simplifiedTerms = new ArrayList<>();
        double leadingCoefficient = 1;
        for(int i = 0 ; i < terms.size(); i++){
            Term a = terms.get(i);
            leadingCoefficient *= a.coefficient;
            a.coefficient = 1;
            for(int j = i+1; j < terms.size(); j++){
                Term b = terms.get(j);
                if(canCombine(a, terms.get(j))){
                    Term newTerm = new Term(1,a.degree+b.degree, a.value, a.trig);
                    a = newTerm;
                    terms.remove(j);
                    j--;
                }
            }
            simplifiedTerms.add(a);
        }
        if(simplifiedTerms.size()>=1){
            simplifiedTerms.get(0).coefficient = leadingCoefficient;
        }
        return simplifiedTerms;

    }
    public static boolean canCombine(Term a, Term b){
        return a.value == b.value && a.trig == b.trig;

    }

    public static Term toTerm(String str){
        str = str.toUpperCase();
        StringBuilder sb = new StringBuilder();
        double coefficient = 1;
        double degree = 1;
        Term value = null;
        Trig trig = null;
        for(int i = 0 ; i < str.length(); i++){
            if(str.charAt(i) == '('){
                coefficient = Double.parseDouble(sb.toString());
                sb = new StringBuilder();
                while(str.charAt(i) != ')'){
                    sb.append(str.charAt(i));
                    i++;
                }
                if(sb.toString() == "X"){
                    value = null;
                }
                else{
                    value = Term.toTerm(sb.toString());
                }
            }
            else if(str.charAt(i) == '^'){
                degree = Double.parseDouble(str.substring(i+1));
                break;
            }
            else if(str.charAt(i) == 'S'){
                coefficient = Double.parseDouble(sb.toString());
                
                trig = Trig.SIN;
            }else if(str.charAt(i) == 'C'){
                coefficient = Double.parseDouble(sb.toString());
                
                trig = Trig.COS;
            }
            else{
                sb.append(str.charAt(i));
            }
            
        }
        return new Term(coefficient, degree, value, trig);
    }
    

}
