package zabieru.jeopardy;

public class Question {
    private String question;
    private String answer;
    private String img;

    private String audio;

    private boolean done;

    public Question(String question, String answer, String img, String audio) {
        this.question = question;
        this.answer = answer;
        this.img = img;
        this.audio = audio;
        done = false;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        if (question != null) {
            this.question = question;
        }
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        if (answer != null) {
            this.answer = answer;
        }
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        if (img != null) {
            this.img = img;
        }
    }

    public void change_infos(String question, String answer, String img, String audio) {
        setImg(img);
        setAnswer(answer);
        setQuestion(question);
        setAudio(audio);
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return question + ":,:" + answer + ":,:" + img + ":,:" + audio;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }
}

