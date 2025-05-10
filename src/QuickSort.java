import java.util.List;

public class QuickSort {
    public static void sort(List<Product> list, int low, int high) {
        if (low < high) {
            int pi = partition(list, low, high);
            sort(list, low, pi - 1);
            sort(list, pi + 1, high);
        }
    }

    private static int partition(List<Product> list, int low, int high) {
        double pivot = list.get(high).getPrice();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (list.get(j).getPrice() <= pivot) {
                i++;
                Product temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }
        Product temp = list.get(i + 1);
        list.set(i + 1, list.get(high));
        list.set(high, temp);
        return i + 1;
    }
}