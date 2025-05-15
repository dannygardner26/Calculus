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
}
