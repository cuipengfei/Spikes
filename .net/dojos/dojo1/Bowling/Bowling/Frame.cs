using System.Linq;

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
            var score = FirstTry + SecondTry;
            if (IsStrike())
            {
                score += (NextBall() + NextToNextBall());
            }
            else if (IsSpare())
            {
                score += NextBall();
            }
            return score;
        }

        private int NextToNextBall()
        {
            if (Next.IsStrike()&&NextIsNotLast())
            {
                return Next.Next.FirstTry;
            }
            return Next.SecondTry;
        }

        private bool NextIsNotLast()
        {
            return Next.Next!=null;
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