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

        public int Score { get { return CalculateScore(); } }

        private int CalculateScore()
        {
            return FirstBall+SecondBall+Bonus();
        }

        private int Bonus()
        {
            var isSpare = FirstBall + SecondBall == 10;
            if (isSpare)
            {
                return Next.FirstBall;
            }
            return 0;
        }

        public Frame Next { get; set; }
    }
}