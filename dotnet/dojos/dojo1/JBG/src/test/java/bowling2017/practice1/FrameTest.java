package bowling2017.practice1;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FrameTest {
  @Test
  public void shouldCountItsOwnScoreWhenForNonBonusCase() throws Exception {
    //given
    Frame frame = new Frame(1, 2);

    //when
    int score = frame.countScore();

    //then
    assertThat(score, is(3));
  }

  @Test
  public void shouldCountScorePlusBonusForSpare() throws Exception {
    //given
    Frame frame = new Frame(1, 9);
    Frame nextFrame = new Frame(1, 2);
    frame.setNext(nextFrame);

    //when
    int score = frame.countScore();

    //then
    assertThat(score, is(11));
  }

  @Test
  public void shouldCountScorePlusBonusForStrike() throws Exception {
    //given
    Frame frame = new Frame(10, 0);
    Frame nextFrame = new Frame(1, 2);
    frame.setNext(nextFrame);

    //when
    int score = frame.countScore();

    //then
    assertThat(score, is(13));
  }

  @Test
  public void shouldCountScorePlusBonusForStrikeWhenNextFrameIsAlsoStrike() throws Exception {
    //given
    Frame frame1 = new Frame(10, 0);
    Frame frame2 = new Frame(10, 0);
    Frame frame3 = new Frame(10, 0);

    frame1.setNext(frame2);
    frame2.setNext(frame3);

    //when
    int score = frame1.countScore();

    //then
    assertThat(score, is(30));
  }

  @Test
  public void shouldCountScorePlusBonusForStrikeWhenNextFrameIsLastFrame() throws Exception {
//given
    Frame frame1 = new Frame(10, 0);
    Frame frame2 = new Frame(10, 5);

    frame1.setNext(frame2);

    //when
    int score = frame1.countScore();

    //then
    assertThat(score, is(25));
  }
}