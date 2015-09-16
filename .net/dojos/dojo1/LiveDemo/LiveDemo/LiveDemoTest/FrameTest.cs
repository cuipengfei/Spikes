using System.Text;
using System.Collections.Generic;
using System.Linq;
using LiveDemo;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace LiveDemoTest
{
    [TestClass]
    public class FrameTest
    {
        [TestMethod]
        public void FrameShouldCountScoreWhenThereIsNoBonus()
        {
            //given
            Frame frame=new Frame(2,3);

            //when
            int score=frame.Score;

            //then
            Assert.AreEqual(5,score);
        }
        
        [TestMethod]
        public void FrameShouldIncludeNext1BallAsBonusWhenThereIsSpare()
        {
            //given
            Frame frame=new Frame(2,8);
            Frame frame2=new Frame(3,4);
            frame.Next = frame2;

            //when
            int score=frame.Score;

            //then
            Assert.AreEqual(13,score);
        }

        [TestMethod]
        public void FrameShouldIncludeNext2BallsAsBonusWhenThereIsStrike()
        {
            //given
            Frame frame=new Frame(10,0);
            Frame frame2=new Frame(3,4);
            frame.Next = frame2;

            //when
            int score=frame.Score;

            //then
            Assert.AreEqual(17,score);
        }
    }
}
