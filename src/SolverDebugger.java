import java.util.ArrayList;

public class SolverDebugger {
    public static void PrintCombinationsList(ArrayList<ArrayList<CharSequence>> combinationsList) {
        System.out.println("Найдено разбиений: " + combinationsList.size());
        for (ArrayList<CharSequence> combination : combinationsList) {
            System.out.println(combination);
        }
    }
}
