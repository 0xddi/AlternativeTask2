import java.util.ArrayList;

public class Solver {

    public static void SolveTask(CharSequence str) {
        // Получаем все возможные разбиения строки на числа
        ArrayList<ArrayList<CharSequence>> numberCombinations = getAllNumberCombinations(str);

        // Создаем все шаблоны с комбинациями операторов и чисел исходя из комбинаций чисел
        ArrayList<ExpressionTemplate> templates = ExpressionTemplateFactory(numberCombinations);

        // Выводим только те комбинации, где результат равен 100
        PrintCombinationsWithTarget(templates, 100);
    }
    private static void PrintCombinationsWithTarget(ArrayList<ExpressionTemplate> expressionTemplateList, int target) {
        int count = 0;
        final double EPSILON = 1e-9; // Допустимая погрешность для double

        for (var template : expressionTemplateList) {
            double result = template.calculateExpression();
            // Проверяем, что результат близок к целому числу и равен target
            if (Math.abs(result - target) < EPSILON) {
                System.out.println(template + "=" + target);
                count++;
            }
        }

        System.out.println("Всего найдено комбинаций: " + count);
    }
    private static ArrayList<ExpressionTemplate> ExpressionTemplateFactory(ArrayList<ArrayList<CharSequence>> numberCombinationsList) {
        var combinationTemplateList = new ArrayList<ExpressionTemplate>();
        char[] defaultOperations = {'+', '-', '*', '/'};

        for (var numberCombination : numberCombinationsList) {
            int opsCount = numberCombination.size() - 1; // операторов всегда на 1 меньше

            if (opsCount <= 0) {
                combinationTemplateList.add(new ExpressionTemplate(numberCombination, new char[0]));
                continue;
            }

            // Всегда количество комбинаций = 4^opsCount
            int totalCombinations = (int) Math.pow(defaultOperations.length, opsCount);

            for (int combo = 0; combo < totalCombinations; combo++) {
                char[] operationCombination = new char[opsCount];
                int temp = combo;

                for (int i = 0; i < opsCount; i++) {
                    operationCombination[i] = defaultOperations[temp % defaultOperations.length];
                    temp /= defaultOperations.length;
                }

                combinationTemplateList.add(new ExpressionTemplate(numberCombination, operationCombination));
            }
        }
        return combinationTemplateList;
    }


    private static ArrayList<ArrayList<CharSequence>> getAllNumberCombinations(CharSequence str) {
        int length = str.length();

        ArrayList<ArrayList<CharSequence>> numberCombinationsList = new ArrayList<>();



        // Генерируем все комбинации чисел из данных нам цифр
        generateSplits(str, length, 0, new ArrayList<>(length), numberCombinationsList);

        return numberCombinationsList;

        // Для дебага
        // DebugSolver.PrintCombinationsList(combinationsList);
    }

    private static void generateSplits(CharSequence strRef, int strLength, int start,
                                       ArrayList<CharSequence> currentNumberCombination,
                                       ArrayList<ArrayList<CharSequence>> numberCombinationsListRef) {

        if (start >= strLength) {
            numberCombinationsListRef.add(new ArrayList<>(currentNumberCombination));
            return;
        }

        // Пробуем все возможные длины следующего числа
        for (int len = 1; len <= strLength - start; len++) {
            CharSequence part = strRef.subSequence(start, start + len);
            currentNumberCombination.add(part);
            generateSplits(strRef, strLength, start + len, currentNumberCombination, numberCombinationsListRef);
            currentNumberCombination.removeLast();
        }
    }
}