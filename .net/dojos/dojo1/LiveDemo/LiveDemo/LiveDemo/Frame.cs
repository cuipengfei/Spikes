using System;

namespace LiveDemo
{
    public class Frame
    {
        public int FirstBall { get; set; }
        public int SecondBall { get; set; }

        public Frame(int firstBall, int secondBall)
        {
            FirstBall = firstBall;
            SecondBall = secondBall;
        }

        public int Score { get { return FirstBall+SecondBall; } }
    }
}