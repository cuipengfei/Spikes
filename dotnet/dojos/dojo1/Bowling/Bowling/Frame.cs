using System;

namespace Bowling
{
    public class Frame
    {
        public int FirstTry { get; set; }
        public int SecondTry { get; set; }

        public int Score => CalculateScore();

        public Frame Next { get; set; }

        protected virtual int CalculateScore()
        {
            if (IsStrike() && SecondTry != 0)
            {
                throw new ArgumentOutOfRangeException(nameof(SecondTry),
                    "Second try should not be play when it's strike.");
            }
            return FirstTry + SecondTry + Bonus();
        }

        private int Bonus()
        {
            if (IsStrike())
            {
                return NextBall() + NextToNextBall();
            }
            if (IsSpare())
            {
                return NextBall();
            }
            return 0;
        }

        private int NextToNextBall()
        {
            if (Next.IsStrike() && NextIsNotLast())
            {
                return Next.Next.FirstTry;
            }
            return Next.SecondTry;
        }

        private bool NextIsNotLast()
        {
            return Next.Next != null;
        }

        private int NextBall()
        {
            return Next.FirstTry;
        }

        private bool IsSpare()
        {
            return FirstTry + SecondTry == 10;
        }

        private bool IsStrike()
        {
            return FirstTry == 10;
        }
    }
}