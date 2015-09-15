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
            if (FirstBall + SecondBall == 10)
            {
                return Next.FirstBall;
            }
            return 0;
        }

        public Frame Next { get; set; }
    }
}