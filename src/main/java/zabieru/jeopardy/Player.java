package zabieru.jeopardy;

public class Player {
    private String username;
    private int points;

    public Player(String username) {
        this.username = username;
        this.points = 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void change_Score(int points) {
        this.points += points;
    }
}

