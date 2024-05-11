package zabieru.jeopardy;

public class Controller {
    Jeopardy jeopardy;
    Player[] players;

    public Controller() {
        jeopardy = new Jeopardy();
        players = new Player[3];
    }

    public void change_num_category(int new_num) {
        if (new_num < 8 && new_num >= 0) {
            jeopardy.change_size_board(new_num);
        } else {
            System.out.println("NUMBER TOO BIG");
        }
    }

    public Question[][] get_board() {
        return jeopardy.getDisplay_question();
    }

    public String[] get_categories() {
        return jeopardy.getCategory_name();
    }

    public void add_question(int index, String question, String answer, String img, String audio) {
        jeopardy.add_question(index, question, answer, img, audio);
    }

    public int get_num_categories() {
        return jeopardy.getNum_categories();
    }

    public void add_categorie(String s, int i) {
        if (i < 7) {
            jeopardy.change_category(s, i);
        }
    }

    public boolean check_board() {
        if (jeopardy.getNum_categories() == 0) {
            return false;
        }
        Question[][] temp = jeopardy.getDisplay_question();
        for (int i = 0; i < temp.length; ++i) {
            for (int j = 0; j < 5; ++j) {
                if (temp[i][j] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    public void save_board(String name) {
        jeopardy.save_board(name);
    }

    public void load_board(String name) {
        jeopardy.load_board(name);
    }

    public void reset() {
        jeopardy.reset();
    }

    public void add_player(String username1, String username2, String username3) {
        players[0] = new Player(username1);
        players[1] = new Player(username2);
        players[2] = new Player(username3);
    }

    public void update_score(Player player, int points) {
        player.change_Score(points);
    }

    public void update_done() {
        jeopardy.update_done();
    }

    public int get_done() {
        return jeopardy.get_done();
    }

    public String get_board_name() {
        return jeopardy.getBoard_name();
    }
}