using System;

namespace FourthTry
{
    public class Frame
    {
        public int FirstBall { get; }
        public int SecondBall { get; }

        public Frame(int firstBall, int secondBall)
        {
            this.FirstBall = firstBall;
            this.SecondBall = secondBall;
            CheckBallLimit();
        }

        protected virtual void CheckBallLimit()
        {
            CheckSingleBall(FirstBall,nameof(FirstBall));
            CheckSingleBall(SecondBall,nameof(SecondBall));
            if (FirstBall + SecondBall > 10)
            {
                throw new ArgumentException("two balls sum upper limit is 10");
            }
        }

        protected void CheckSingleBall(int ball,string name)
        {
            if (ball > 10)
            {
                throw new ArgumentOutOfRangeException(name, "ball upper limit is 10");
            }
        }

        public int Score => CalculateScore();

        protected virtual int CalculateScore()
        {
            return FirstBall + SecondBall+Bonus();
        }

        private int Bonus()
        {
            var isStrike= FirstBall == 10;
            var isSpare = !isStrike&&(FirstBall + SecondBall == 10);
            if (isSpare)
            {
                return Next1Ball();
            }
            if (isStrike)
            {
                return Next1Ball() + Next2Ball();
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
            return Next.SecondBall;
        }

        private int Next1Ball()
        {
            return Next.FirstBall;
        }

        public Frame Next { get; set; }
    }
}