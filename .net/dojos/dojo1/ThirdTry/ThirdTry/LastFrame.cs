namespace ThirdTry
{
    public class LastFrame:Frame
    {
        public int ThirdBall { get; }

        public LastFrame(int firstBall, int secondBall, int thirdBall) : base(firstBall,secondBall)
        {
            this.ThirdBall = thirdBall;
        }

        protected override int CalculateScore()
        {
            return FirstBall + SecondBall + ThirdBall;
        }
    }
}