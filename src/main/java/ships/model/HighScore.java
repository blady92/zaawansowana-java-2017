package ships.model;

public class HighScore {
    protected Integer score;
    protected String nickname;

    public HighScore(Integer score, String nick) {
        this.score = score;
        this.nickname = nick;
    }

    /**
     * @return the score
     */
    public Integer getScore() {
        return score;
    }

    /**
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }
}
