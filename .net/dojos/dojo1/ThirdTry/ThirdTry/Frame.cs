namespace ThirdTry
{
    public class Frame
    {
        public Frame(int firstBall, int secondBall)
        {
            FirstBall = firstBall;
            SecondBall = secondBall;
        }

        public int FirstBall { get; }
        public int SecondBall { get; }

        public int Score => CalculateScore();
        public Frame Next { get; set; }

        protected virtual int CalculateScore()
        {
            return FirstBall + SecondBall + Bonus();
        }

        private int Bonus()
        {
            var isSpare = FirstBall != 10 && FirstBall + SecondBall == 10;
            var isStrike = FirstBall == 10;
            if (isSpare)
            {
                return NextBallScore();
            }
            if (isStrike)
            {
                return NextBallScore() + SecondNextBallScore();
            }
            return 0;
        }

        private int NextBallScore()
        {
            return Next.FirstBall;
        }

        private int SecondNextBallScore()
        {
            var nextIsStrike = Next.FirstBall == 10;
            var nextIsNotLast = Next.Next != null;
            if (nextIsStrike && nextIsNotLast)
            {
                return Next.Next.FirstBall;
            }
            return Next.SecondBall;
        }
    }
}