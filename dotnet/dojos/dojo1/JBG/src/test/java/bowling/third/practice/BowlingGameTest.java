package bowling.third.practice;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BowlingGameTest {
    @Test
    public void shouldCountTotalScore() throws Exception {
        //given
        BowlingGame game = new BowlingGame();

        //when
        game.play(1, 9); //11
        game.play(1, 2); //3
        game.play(3, 7); //11
        game.play(1, 2); //3
        game.play(10, 0); //13
        game.play(1, 2); //3
        game.play(10, 0); //21
        game.play(10, 0); //13
        game.play(1, 2); //3
        game.play(1, 9, 1); //11
        //11+3+11+3+13+3+21+23+3+11
        int totalScore = game.countTotalScore();

        //then
        assertThat(totalScore, is(92));
    }
}
