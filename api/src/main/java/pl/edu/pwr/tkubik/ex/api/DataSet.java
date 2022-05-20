package pl.edu.pwr.tkubik.ex.api;

/**
 * Klasa reprezentująca zbiór danych w postaci tabelarycznej.
 * Przechowuje nagłówek (jednowymiarowa tablica z nazwami kolumn)
 * oraz dane (dwuwymiarowa tablica, której wiersze reprezentują wektory danych).
 * Zakładamy, że będą zawsze istnieć przynajmniej dwie kolumny o nazwach:
 * "RecordId" - w kolumnie tej przechowywane są identyfikatory rekordów danych;
 * "CategoryId" - w kolumnie tej przechowywane są identyfikatory kadegorii rekordów danych (wynik analizy skupień).
 *
 * @author tkubik
 *
 */
public class DataSet {
    private String[] header = {};
    private String[][] data = {{}};

    private <T> T[][] deepCopy(T[][] matrix) {
        return java.util.Arrays.stream(matrix).map(el -> el.clone()).toArray(i -> matrix.clone());
    }

    public String[] getHeader() {
        return header;
    }
    public void setHeader(String[] header) {
        this.header = header.clone();
    }
    public String[][] getData() {
        return data;
    }
    public void setData(String[][] data) {
        this.data = deepCopy(data);
    }
    public void print(){
        int maxWidth = header.length;
        for(String[] s : data) if (s.length > maxWidth) maxWidth = s.length;
        String line = "";
        for (int i = 0; i < maxWidth+1; i++) {
            line+="==========";
        }
        System.out.println(line);
        for (int i = 0; i < header.length; i++) {
            System.out.format("|%10s", header[i]);
        }

        for (int i = 0; i < maxWidth - header.length; i++) {
            System.out.format("|%10s", "");
        }
        System.out.print("|\n");
        System.out.println(line);
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                System.out.format("|%10s", data[i][j]);
            }
            for (int j = 0; j < maxWidth - data[i].length; j++) {
                System.out.format("|%10s", " ");
            }
            System.out.print("|");
            System.out.println();
        }

    }
}
