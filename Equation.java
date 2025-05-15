import java.util.ArrayList;

public class Equation {
    public ArrayList<Term> terms;
    public ArrayList<Character> operators;

    public Equation(ArrayList<Term> terms, ArrayList<Character> operators){
        this.terms = terms;
        this.operators = operators;
    }
    public Equation(){
        this.terms = new ArrayList<>();
        this.operators = new ArrayList<>();
    }
    public void addTerm(Term term){
        terms.add(term);
    }
    public void addOperator(char operator){
        operators.add(operator);
    }
    public boolean isDifferentiable(){
        for(Term term : terms){
            if(!term.isDifferentiable()){
                System.out.println("The equation is not differentiable at " + term.value);
                return false;
            }
        }
        return true;
    }
    public static Equation toEquation(String str){
        str = str.toUpperCase();
        str = str.replaceAll("\\s", "");
        Equation equation = new Equation();

        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i < str.length(); i++){
            
            if(str.charAt(i) == '+' || str.charAt(i) == '-' || str.charAt(i) == '*' || str.charAt(i) == '/'){
                equation.addTerm(Term.toTerm(sb.toString()));
                equation.addOperator(str.charAt(i));
                sb = new StringBuilder();
            }
            sb.append(str.charAt(i));
        }
        equation.addTerm(Term.toTerm(sb.toString()));
        return equation;
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < terms.size(); i++){
            sb.append(terms.get(i).toString());
            if(i < operators.size()){
                sb.append(operators.get(i));
            }
        }
        return sb.toString();
    }
}
