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
        }

        public int Score => CalculateScore();

        private int CalculateScore()
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