import java.util.ArrayList;

public class Term{


    public double degree;//constant terms will have a degree of 0
    public double coefficient; //if constant, the coefficient is the value 
    public Term value; //if the term is a constant, this will be null
    public Trig trig; 

    public Term(double coefficient){//constant declaration
        this.coefficient = coefficient;
        this.value = null;
        this.degree = 0;
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
            Term newTerm = new Term(coefficient, degree-1, value, trig);
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
            Term newTerm = new Term(trig.willBeNegative() * coefficient, degree, value, trig.differentiate());
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
        if(trig == null || value != null){
            terms.add(this.powerRule());

        }
        // else{
        //     terms.add(this.powerRule());

        // }

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
        if(coefficient != 1 && coefficient != -1 || (trig == null && coefficient == -1)) {
            sb.append(Math.abs(coefficient));
        }
        
        
        if(trig != null){
            sb.append(trig.toString()).append("(");
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
        if(!simplifiedTerms.isEmpty()) {
        Term firstTerm = simplifiedTerms.get(0);
        simplifiedTerms.set(0, new Term(leadingCoefficient, firstTerm.degree, 
                                      firstTerm.value, firstTerm.trig));
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
        
        
        if(str.charAt(0) == '-'){
            coefficient = -1;
            str = str.substring(1);
        }
        


        for(int i = 0 ; i < str.length(); i++){
            if(str.charAt(i) == '('){
                if(sb.length() > 0){
                    coefficient = Double.parseDouble(sb.toString());
                }else{
                    coefficient = 1;
                }
                sb = new StringBuilder();
                i++;
                while(i < str.length() && str.charAt(i) != ')'){
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
                if(i!=0){
                    coefficient = Double.parseDouble(sb.toString());
                }
                i+=2;
                
                
                trig = Trig.SIN;
            }else if(str.charAt(i) == 'C'){
                if(i!=0){
                    coefficient = Double.parseDouble(sb.toString());
                }
                i+=2;
                trig = Trig.COS;
            }else if(str.charAt(i) == 'X'){
                if(i!=0){
                    coefficient = Double.parseDouble(sb.toString());
                }
                sb = new StringBuilder();
            }
            else{
                sb.append(str.charAt(i));
            }
            
        }
        // if(isNegative){
        //     coefficient *= -1;
        // }
        return new Term(coefficient, degree, value, trig);
    }
    public static Equation multiRule(Term a, Term b){
        ArrayList<Term> termList = new ArrayList<>();
        ArrayList<Character> operatorList = new ArrayList<>();
        termList.add(a);
        ArrayList<Term> bDiff = b.differentiate();
        for(int i = 0; i < bDiff.size(); i++){
            operatorList.add('*');
            termList.add(bDiff.get(i));
        }
        operatorList.add('+');

        termList.add(b);
        ArrayList<Term> aDiff = a.differentiate();

        for(int i = 0; i < aDiff.size(); i++){
            operatorList.add('*');
            termList.add(aDiff.get(i));
        }
        Equation eq = new Equation(termList, operatorList);
        return eq;
        

        
    
        

    }
    public static Equation quotientRule(Term a, Term b){//for an a/b function
        ArrayList<Term> termList = new ArrayList<>();
        ArrayList<Character> operatorList = new ArrayList<>();


        
        termList.add(b);

        ArrayList<Term> aDiff = a.differentiate();//take bottom deriv first
        for(int i = 0; i < aDiff.size(); i++){
            operatorList.add('*');
            termList.add(aDiff.get(i));
        }
        operatorList.add('/');
        termList.add(new Term(1,2,b));

        
        operatorList.add('-');
        termList.add(a);
        ArrayList<Term> bDiff = b.differentiate();
        for(int i = 0; i < bDiff.size(); i++){
            operatorList.add('*');
            termList.add(bDiff.get(i));
        }
        operatorList.add('/');
        termList.add(new Term(1,2,b));
        
        Equation eq = new Equation(termList, operatorList);
        return eq;

    }
    

}
