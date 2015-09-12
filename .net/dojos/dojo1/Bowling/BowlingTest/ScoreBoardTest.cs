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
            scoreBoard.AddFrame(new Frame {FirstTry = 1, SecondTry = 2}); //3
            scoreBoard.AddFrame(new Frame {FirstTry = 2, SecondTry = 3}); //5
            scoreBoard.AddFrame(new Frame {FirstTry = 3, SecondTry = 4}); //7
            scoreBoard.AddFrame(new Frame {FirstTry = 3, SecondTry = 6}); //9
            scoreBoard.AddFrame(new Frame {FirstTry = 5, SecondTry = 5}); //20
            scoreBoard.AddFrame(new Frame {FirstTry = 10, SecondTry = 0}); //19
            scoreBoard.AddFrame(new Frame {FirstTry = 2, SecondTry = 7}); //9
            scoreBoard.AddFrame(new Frame {FirstTry = 6, SecondTry = 4}); //20
            scoreBoard.AddFrame(new Frame {FirstTry = 10, SecondTry = 0}); //15
            scoreBoard.AddFrame(new Frame {FirstTry = 3, SecondTry = 2}); //5

            //when
            var totalScore = scoreBoard.Score;

            //then
            Assert.AreEqual(112, totalScore);
        }
    }
}