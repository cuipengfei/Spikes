using System;
using Bowling;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace BowlingTest
{
    [TestClass]
    public class ScoreBoardTest
    {
        [TestMethod]
        public void ScoreBoardShouldCountTotalScores()
        {
            //given
            var scoreBoard = new ScoreBoard();
            scoreBoard.AddFrame(1, 2); //3
            scoreBoard.AddFrame(2, 3); //5
            scoreBoard.AddFrame(3, 4); //7
            scoreBoard.AddFrame(3, 6); //9
            scoreBoard.AddFrame(5, 5); //20
            scoreBoard.AddFrame(10, 0); //19
            scoreBoard.AddFrame(2, 7); //9
            scoreBoard.AddFrame(6, 4); //20
            scoreBoard.AddFrame(10, 0); //15
            scoreBoard.AddLastFrame(3, 2); //5

            //when
            var totalScore = scoreBoard.Score;

            //then
            Assert.AreEqual(112, totalScore);
        }

        [TestMethod]
        public void ScoreBoardShouldCountTotalScoresWhenThereAreConsectiveStrikesAndSpares()
        {
            //given
            var scoreBoard = new ScoreBoard();
            scoreBoard.AddFrame(10, 0); //30
            scoreBoard.AddFrame(10, 0); //30
            scoreBoard.AddFrame(10, 0); //25
            scoreBoard.AddFrame(10, 0); //20
            scoreBoard.AddFrame(5, 5); //10
            scoreBoard.AddFrame(0, 0); //0
            scoreBoard.AddFrame(2, 8); //16
            scoreBoard.AddFrame(6, 4); //20
            scoreBoard.AddFrame(10, 0); //15
            scoreBoard.AddLastFrame(3, 2); //5

            //when
            var totalScore = scoreBoard.Score;

            //then
            Assert.AreEqual(171, totalScore);
        }

        [TestMethod]
        public void ScoreBoardShouldCountTotalScoresOfPerfectGame()
        {
            //given
            var scoreBoard = new ScoreBoard();
            scoreBoard.AddFrame(10, 0);
            scoreBoard.AddFrame(10, 0);
            scoreBoard.AddFrame(10, 0);
            scoreBoard.AddFrame(10, 0);
            scoreBoard.AddFrame(10, 0);
            scoreBoard.AddFrame(10, 0);
            scoreBoard.AddFrame(10, 0);
            scoreBoard.AddFrame(10, 0);
            scoreBoard.AddFrame(10, 0);
            scoreBoard.AddLastFrame(10, 10, 10);

            //when
            var totalScore = scoreBoard.Score;

            //then
            Assert.AreEqual(300, totalScore);
        }

        [TestMethod]
        [ExpectedException(typeof(ArgumentOutOfRangeException))]
        public void ScoreBoardShouldThrowExceptionWhenAddingMoreThanTenFrames()
        {
            var scoreBoard = new ScoreBoard();
            scoreBoard.AddFrame(10, 0);
            scoreBoard.AddFrame(10, 0);
            scoreBoard.AddFrame(10, 0);
            scoreBoard.AddFrame(10, 0);
            scoreBoard.AddFrame(10, 0);
            scoreBoard.AddFrame(10, 0);
            scoreBoard.AddFrame(10, 0);
            scoreBoard.AddFrame(10, 0);
            scoreBoard.AddFrame(10, 0);
            scoreBoard.AddFrame(10, 0);
            scoreBoard.AddFrame(10, 10);
        }
    }
}