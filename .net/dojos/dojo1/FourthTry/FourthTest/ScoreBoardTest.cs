using System;
using FourthTry;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace FourthTest
{
    [TestClass]
    public class ScoreBoardTest
    {
        [TestMethod]
        public void ScoreBoardShouldCalculateTotalScoreOf10Frames()
        {
            //given
            var scoreBoard = new ScoreBoard();

            //when
            scoreBoard.Play(1, 2); //3
            scoreBoard.Play(2, 3); //5
            scoreBoard.Play(3, 4); //7
            scoreBoard.Play(5, 5); //11
            scoreBoard.Play(1, 2); //3
            scoreBoard.Play(10, 0); //20
            scoreBoard.Play(7, 3); //11
            scoreBoard.Play(1, 2); //3
            scoreBoard.Play(10, 0); //13
            scoreBoard.Play(1, 2); //3

            var total = scoreBoard.TotalScore;
            //then
            Assert.AreEqual(79, total);
        }

        [TestMethod]
        public void ScoreBoardShouldCalculateTotalScoreWhenLastFrameHasSpare()
        {
            //given
            var scoreBoard = new ScoreBoard();

            //when
            scoreBoard.Play(1, 2); //3
            scoreBoard.Play(2, 3); //5
            scoreBoard.Play(3, 4); //7
            scoreBoard.Play(5, 5); //11
            scoreBoard.Play(1, 2); //3
            scoreBoard.Play(10, 0); //20
            scoreBoard.Play(7, 3); //11
            scoreBoard.Play(1, 2); //3
            scoreBoard.Play(10, 0); //20
            scoreBoard.Play(1, 9, 2); //12

            var total = scoreBoard.TotalScore;
            //then
            Assert.AreEqual(95, total);
        }

        [TestMethod]
        public void ScoreBoardShouldCalculateTotalScoreWhenLastFrameHasStrike()
        {
            //given
            var scoreBoard = new ScoreBoard();

            //when
            scoreBoard.Play(1, 2); //3
            scoreBoard.Play(2, 3); //5
            scoreBoard.Play(3, 4); //7
            scoreBoard.Play(5, 5); //11
            scoreBoard.Play(1, 2); //3
            scoreBoard.Play(10, 0); //20
            scoreBoard.Play(7, 3); //11
            scoreBoard.Play(1, 2); //3
            scoreBoard.Play(10, 0); //29
            scoreBoard.Play(10, 9, 2); //21

            var total = scoreBoard.TotalScore;
            //then
            Assert.AreEqual(113, total);
        }

        [TestMethod]
        public void ScoreBoardShouldCalculateTotalOfPerfectGame()
        {
            //given
            var scoreBoard = new ScoreBoard();

            //when
            scoreBoard.Play(10, 0);
            scoreBoard.Play(10, 0);
            scoreBoard.Play(10, 0);
            scoreBoard.Play(10, 0);
            scoreBoard.Play(10, 0);
            scoreBoard.Play(10, 0);
            scoreBoard.Play(10, 0);
            scoreBoard.Play(10, 0);
            scoreBoard.Play(10, 0);
            scoreBoard.Play(10, 10, 10);

            var total = scoreBoard.TotalScore;
            //then
            Assert.AreEqual(300, total);
        }

        [TestMethod]
        [ExpectedException(typeof (ArgumentException))]
        public void ScoreBoardShouldNotHaveMoreThan10Frames()
        {
            var scoreBoard = new ScoreBoard();

            scoreBoard.Play(10, 0);
            scoreBoard.Play(10, 0);
            scoreBoard.Play(10, 0);
            scoreBoard.Play(10, 0);
            scoreBoard.Play(10, 0);
            scoreBoard.Play(10, 0);
            scoreBoard.Play(10, 0);
            scoreBoard.Play(10, 0);
            scoreBoard.Play(10, 0);
            scoreBoard.Play(10, 0);
            scoreBoard.Play(10, 0);
        }

        [TestMethod]
        [ExpectedException(typeof (ArgumentException))]
        public void LastFrameMustBeLast()
        {
            var scoreBoard = new ScoreBoard();
            scoreBoard.Play(1, 1,1);
        }
    }
}