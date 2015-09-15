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

        public int Score { get { return FirstBall + SecondBall; } }
    }
}