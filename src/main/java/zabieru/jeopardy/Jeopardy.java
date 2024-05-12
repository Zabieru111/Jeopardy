package zabieru.jeopardy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Jeopardy {
    private String board_name;
    private Question[][] display_question;
    private int done;
    private String[] category_name;
    private int num_categories;

    public Jeopardy() {
        this.num_categories = 0;
        display_question = new Question[num_categories][5];
        category_name = new String[0];
        done = 0;
    }

    public void change_category(String category, int index) {
        category_name[index] = category;
    }

    public void change_size_board(int new_size) {
        Question[][] temp = new Question[new_size][5];
        String[] temp_names = new String[new_size];
        int minimum = Math.min(new_size, num_categories);
        for (int i = 0; i < minimum; ++i) {
            temp_names[i] = category_name[i];
            for (int j = 0; j < 5; ++j) {
                temp[i][j] = display_question[i][j];
            }
        }
        num_categories = new_size;
        category_name = temp_names;
        display_question = temp;
    }

    public int getNum_categories() {
        return num_categories;
    }

    public void add_question(int index, String question, String answer, String img, String audio) {
        int x = index / 5;
        int y = index % 5;
        display_question[x][y] = new Question(question, answer, img, audio);
    }

    public void change_question(int index, String question, String answer, String img, String audio) {
        int x = index / 5;
        int y = index % 5;
        Question temp = display_question[x][y];
        temp.change_infos(question, answer, img, audio);
    }

    public Question[][] getDisplay_question() {
        return display_question;
    }

    public String[] getCategory_name() {
        return category_name;
    }

    public void save_board(String Board_Name) {
        try (FileWriter fw = new FileWriter("Boards/" + Board_Name)) {
            fw.write(num_categories + "\n");
            fw.write(String.join(":,:", category_name) + "\n");
            for (int i = 0; i < num_categories; ++i) {
                for (int j = 0; j < 5; ++j) {
                    fw.write(display_question[i][j].toString() + "\n");
                }
            }
            this.board_name = Board_Name;
        } catch (IOException e) {
            System.out.println("Something went wrong while saving your board");
        }
    }

    public void load_board(String Board_Name) {
        try (BufferedReader br = new BufferedReader(new FileReader("Boards/" + Board_Name))) {
            String line = br.readLine();
            int num_cat = Integer.parseInt(line.strip());
            change_size_board(num_cat);
            line = br.readLine();
            category_name = line.strip().split(":,:");
            int index = 0;
            while ((line = br.readLine()) != null) {
                line = line.strip();
                String[] infos = line.split(":,:", -1);
                String question = infos[0];
                String answer = infos[1];
                String img = infos[2];
                String audio = infos[3];
                Question temp = new Question(question, answer, img, audio);
                int x = index / 5;
                int y = index % 5;
                display_question[x][y] = temp;
                index++;
            }
            this.board_name = Board_Name;
        } catch (IOException e) {
            System.out.println("Something went wrong while downloading your board");
        }
    }

    public void reset() {
        done = 0;
        num_categories = 0;
        change_size_board(0);
    }

    public void update_done() {
        done++;
    }

    public int get_done() {
        return done;
    }

    public String getBoard_name() {
        return board_name;
    }
}
