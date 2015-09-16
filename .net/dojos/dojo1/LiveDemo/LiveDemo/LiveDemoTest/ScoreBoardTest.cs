using System.Collections.Generic;
using System.Linq;
using System.Text;
using LiveDemo;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace LiveDemoTest
{
    [TestClass]
    public class ScoreBoardTest
    {
        [TestMethod]
        public void ScoreBoardShouldCountTotalScoreOf10Frames()
        {
            //given
            var scoreBoard=new ScoreBoard();

            //when
            scoreBoard.Play(1,2);//3
            scoreBoard.Play(2,2);//4
            scoreBoard.Play(3,2);//5
            scoreBoard.Play(4,6);//10+1
            scoreBoard.Play(1,2);//3
            scoreBoard.Play(5,5);//10+1
            scoreBoard.Play(1,9);//10+7
            scoreBoard.Play(7,2);//9
            scoreBoard.Play(3,6);//9
            scoreBoard.Play(1,2);//3

            //then
            Assert.AreEqual(75,scoreBoard.TotalScore);
        }
    }
}
