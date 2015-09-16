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
    }
}
