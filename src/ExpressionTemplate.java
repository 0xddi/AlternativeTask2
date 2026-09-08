import java.util.ArrayList;
import java.util.List;

public class ExpressionTemplate {
    private final List<CharSequence> numberCombination;
    private final char[] operationCombination;

    public ExpressionTemplate(ArrayList<CharSequence> numberCombination, char[] operationCombination) {
        this.numberCombination = numberCombination;
        this.operationCombination = operationCombination;
    }

    public double calculateExpression() {
        ArrayList<Double> numbers = new ArrayList<>();
        ArrayList<Character> ops = new ArrayList<>();

        numbers.add((double) Integer.parseInt(this.numberCombination.getFirst().toString()));

        for (int i = 0; i < this.operationCombination.length; i++) {
            char op = this.operationCombination[i];
            double nextNum = Integer.parseInt(this.numberCombination.get(i + 1).toString());

            if (op == '*' || op == '/') {
                double last = numbers.removeLast();
                if (op == '*') {
                    numbers.add(last * nextNum);
                } else {
                    if (nextNum != 0) {
                        numbers.add(last / nextNum);
                    } else {
                        throw new ArithmeticException("Division by zero");
                    }
                }
            } else {
                numbers.add(nextNum);
                ops.add(op);
            }
        }

        double result = numbers.getFirst();
        for (int i = 0; i < ops.size(); i++) {
            if (ops.get(i) == '+') {
                result += numbers.get(i + 1);
            } else {
                result -= numbers.get(i + 1);
            }
        }

        return result;
    }


    // Вывод примерно такой: 98+7*6-5/4*32
    @Override
    public String toString() {
        StringBuilder temp = new StringBuilder();

        for (int i = 0; i < this.numberCombination.size(); i++) {
            temp.append(this.numberCombination.get(i));
            if (i < this.operationCombination.length) {
                temp.append(this.operationCombination[i]);
            }
        }

        return temp.toString();
    }


}
