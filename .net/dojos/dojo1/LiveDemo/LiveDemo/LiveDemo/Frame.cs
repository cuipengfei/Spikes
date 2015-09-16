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

        protected virtual int CalculateScore()
        {
            return FirstBall+SecondBall+Bonus();
        }

        private int Bonus()
        {
            var isStrike = FirstBall  == 10;
            var isSpare = FirstBall + SecondBall == 10;
            if (isStrike)
            {
                return Next1Ball() + Next2Ball();
            }
            if (isSpare)
            {
                return Next1Ball();
            }
            return 0;
        }

        private int Next2Ball()
        {
            var nextIsStrike = Next.FirstBall == 10;
            var nextIsNotLast = Next.Next != null;
            if (nextIsStrike&&nextIsNotLast)
            {
                return Next.Next.FirstBall;
            }
            return  Next.SecondBall;
        }

        private int Next1Ball()
        {
            return Next.FirstBall;
        }

        public Frame Next { get; set; }
    }
}