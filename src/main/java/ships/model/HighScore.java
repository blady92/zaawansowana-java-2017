package ships.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o instanceof HighScore) {
            return Objects.equals(this.score, ((HighScore)o).getScore()) && Objects.equals(this.nickname, ((HighScore)o).getNickname());
        }
        return super.equals(o); //To change body of generated methods, choose Tools | Templates.
    }

}