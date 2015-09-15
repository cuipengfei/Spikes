using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using FourthTry;

namespace FourthTest
{
    [TestClass]
    public class ScoreBoardTest
    {
        [TestMethod]
        public void ScoreBoardShouldCalculateTotalScoreOf10Frames()
        {
            //given
            var scoreBoard=new ScoreBoard();

            //when
            scoreBoard.Play(1,2);//3
            scoreBoard.Play(2,3);//5
            scoreBoard.Play(3,4);//7
            scoreBoard.Play(5,5);//11
            scoreBoard.Play(1,2);//3
            scoreBoard.Play(10,0);//20
            scoreBoard.Play(7,3);//11
            scoreBoard.Play(1,2);//3
            scoreBoard.Play(10,0);//13
            scoreBoard.Play(1,2);//3

            int total = scoreBoard.TotalScore;
            //then
            Assert.AreEqual(79,total);
        }
    }
}
