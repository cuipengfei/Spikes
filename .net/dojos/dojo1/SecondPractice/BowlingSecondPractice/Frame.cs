namespace BowlingSecondPractice
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

        public Frame NextFrame { get; set; }

        protected virtual int CalculateScore()
        {
            return FirstBall + SecondBall + Bonus();
        }

        private int Bonus()
        {
            if (IsStrike())
            {
                return NextBall() + SecondToNextBall();
            }
            if (IsSpare())
            {
                return NextBall();
            }
            return 0;
        }

        private int NextBall()
        {
            return NextFrame.FirstBall;
        }

        private bool IsSpare()
        {
            return FirstBall + SecondBall == 10;
        }

        private int SecondToNextBall()
        {
            var nextIsNotLast = NextFrame.NextFrame!=null;
            if (NextFrame.IsStrike()&&nextIsNotLast)
            {
                return NextFrame.NextFrame.FirstBall;
            }
            return NextFrame.SecondBall;
        }

        private bool IsStrike()
        {
            return FirstBall == 10;
        }
    }
}